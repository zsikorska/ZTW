const path = require('path')
const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);
const formatMessage = require('./chatUtils/messages');
const { userJoin, getCurrentUser, userLeave, getRoomUsers } = require('./chatUtils/users')

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', (socket) => {

  socket.on('join-room', ({ username, room }) => { 

    const user = userJoin(socket.id, username, room);
    socket.join(room);
    socket.emit('message', formatMessage('Chat Rooms', 'Welcome to ChatApp!', null));

    socket.broadcast.to(user.room).emit('message', formatMessage('Chat Rooms', `${user.username} has joined the chat`, null));
    io.to(user.room).emit('room-users', {
      room: user.room,
      users: getRoomUsers(user.room)
    })
  });

  socket.on('chat-message', (msg, file) => {
    const user = getCurrentUser(socket.id);
    io.to(user.room).emit('message', formatMessage(user.username, msg, file));
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
      io.to(user.room).emit('message', formatMessage("Chat Rooms", `${user.username} has left the chat`));
    }
    io.to(user.room).emit('room-users', {
      room: user.room,
      users: getRoomUsers(user.room)
    })
  });
});



server.listen(3000, () => {
  console.log('listening on *:3000');
});