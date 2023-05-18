const path = require('path')
const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);
const formatMessage = require('./chatUtils/messages');
const { userJoin, getCurrentUser, userLeave, getRoomUsers } = require('./chatUtils/users')
const Chat = require('./chatUtils/chat');
const {connect} = require("mongoose");
const fs = require('fs');

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

connect('mongodb://localhost:27017/chat', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => {
  console.log('Connected to MongoDB database');
}).catch((err) => {
  console.error('Error connecting to MongoDB database', err);
});

io.on('connection', (socket) => {
  socket.on('join-room', ({ username, room }) => { 

    const user = userJoin(socket.id, username, room);

    getChatHistory(socket, room);
    socket.join(room);

    let message = formatMessage('Chat Rooms', `${user.username} has joined the chat`, null);
    addMessageToChatHistory(user.room, message);

    socket.broadcast.to(user.room).emit('message', message);
    io.to(user.room).emit('room-users', {
      room: user.room,
      users: getRoomUsers(user.room)
    })
  });

  socket.on('chat-message', (msg, file) => {
    const user = getCurrentUser(socket.id);
    const message = formatMessage(user.username, msg, file);
    addMessageToChatHistory(user.room, message);
    io.to(user.room).emit('message', message);
  });

  socket.on('typing', () => {
    const user = getCurrentUser(socket.id);
    socket.broadcast.to(user.room).emit('typing', user.username);
  });

  socket.on('stoppedTyping', () => {
    const user = getCurrentUser(socket.id);
    socket.broadcast.to(user.room).emit('stoppedTyping');
  });

  socket.on('disconnect', () => {
    const user = userLeave(socket.id);
    if (user != null) {
      let message = formatMessage("Chat Rooms", `${user.username} has left the chat`, null);
      addMessageToChatHistory(user.room, message);
      io.to(user.room).emit('message', message);
    }
    io.to(user.room).emit('room-users', {
      room: user.room,
      users: getRoomUsers(user.room)
    })
  });
});

function addMessageToChatHistory(room, message) {
  Chat.findOneAndUpdate(
      { room: room },
      { $push: { messages: message } },
      { upsert: true, new: true }
  ).then(chat => {
    console.log('Saved message:', message);
  }).catch(err => {
    console.error('Error saving message:', err);
  });
}

function getChatHistory(socket, room) {
  Chat.findOne({room: room})
      .then(chat => {
        if (chat != null) {
          chat.messages.forEach(message => {
            if (message.text) {
              const msg = formatMessage(message.username, message.text, null);
              msg.time = message.time;
              socket.emit('message', msg);
            }
          });
        }
      })
}

server.listen(3000, () => {
  console.log('listening on *:3000');
});