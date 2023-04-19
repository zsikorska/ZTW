const chatForm = document.getElementById('chat-form');
const chatMessages = document.querySelector('.chat-messages');
const roomUsers = document.getElementById('users');
const roomName = document.getElementById('room-name');


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
});

socket.on('room-users', ({room, users}) => {
    updateRoomName(room);
    updateRoomUsers(users);
});

function publishMessage(message) {
    const newMsgDiv = document.createElement('div'); 
    newMsgDiv.classList.add('message');
    newMsgDiv.innerHTML =  `<p class="meta">${message.username}<span>${message.time}</span></p><p class="text">${message.text}</p>`;
    document.querySelector('.chat-messages').appendChild(newMsgDiv);
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
