const chatForm = document.getElementById('chat-form');
const chatMessages = document.querySelector('.chat-messages');
const roomUsers = document.getElementById('users');
const roomName = document.getElementById('room-name');
const msg = document.getElementById('msg');
const typingIndicator = document.getElementById('typing-indicator');


const { username, room } = Qs.parse(location.search, {
    ignoreQueryPrefix: true
})
console.log(username, room);


const socket = io();

socket.emit('join-room', {username, room} );

socket.on('message', message => {
    console.log(message);
    publishMessage(message);
    chatMessages.scrollTop = chatMessages.scrollHeight
})

//Message submit
chatForm.addEventListener('submit', (e) => { 
    e.preventDefault();

    const msg = e.target.elements.msg.value
    socket.emit('chat-message', msg);

    e.target.elements.msg.value = ''; 
    e.target.elements.msg.focus();
    socket.emit('stoppedTyping');
});

socket.on('room-users', ({room, users}) => {
    updateRoomName(room);
    updateRoomUsers(users);
});

function publishMessage(message) {
    const newMsgDiv = document.createElement('div');
    newMsgDiv.classList.add('message');

    // check if message is sent by the current user
    if (message.username === username) {
        newMsgDiv.classList.add('message-right');
    } else {
        newMsgDiv.classList.add('message-left');
    }

    newMsgDiv.innerHTML =  `<p class="meta">${message.username} <span>${message.time}</span></p><p class="text">${message.text}</p>`;
    document.querySelector('.chat-messages .messages').appendChild(newMsgDiv);
}

function updateRoomName(name) {
    roomName.innerHTML = name;
}

function updateRoomUsers(users) { 
    console.log(users);
    roomUsers.innerHTML = `
    ${users.map(user => `<li>${user.username}<\li>`).join('')}
    `; 
}

let timer;

msg.addEventListener('keydown', () => {
    socket.emit('typing');
    clearTimeout(timer);
    timer = setTimeout(() => {
        socket.emit('stoppedTyping');
    }, 2000);
});


socket.on('typing', (username) => {
    typingIndicator.textContent = `${username} is typing...`;
    typingIndicator.classList.add("typing-indicator-active");
});

socket.on('stoppedTyping', () => {
    typingIndicator.textContent = '';
    typingIndicator.classList.remove("typing-indicator-active");
});
