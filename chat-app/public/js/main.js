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
    chatMessages.scrollTop = chatMessages.scrollHeight;
})

//Message submit
chatForm.addEventListener('submit', (e) => { 
    e.preventDefault();

    const msg = e.target.elements.msg.value.trim();
    const file = document.getElementById('file-input').files[0];

    if(file !== undefined && validateFileSize(file)) {
        console.log(file);
        socket.emit('chat-message', msg, file);
    }
    else if(msg !== '') {
        socket.emit('chat-message', msg, null);
    }

    e.target.elements.msg.value = '';
    e.target.elements.msg.focus();
    clearFileInput();
    socket.emit('stoppedTyping');
});

socket.on('room-users', ({room, users}) => {
    updateRoomName(room);
    updateRoomUsers(users, username);
});

function publishMessage(message) {
    const newMsgDiv = document.createElement('div');
    newMsgDiv.classList.add('message');

    // check if message is sent by the current user
    if (message.username === username) {
        newMsgDiv.classList.add('message-right');
    }
    else if(message.username === 'Chat Rooms') {
        newMsgDiv.classList.add('message-chat-rooms');
    }
    else{
        newMsgDiv.classList.add('message-left');
    }

    if(message.file == null) {
        newMsgDiv.innerHTML =  `<p class="meta">${message.username} <span>${message.time}</span></p>
                                <p class="text">${message.text}</p>`;
    }
    else {
        console.log(message.file);
        const fileURL =  URL.createObjectURL(new Blob([message.file]));
        console.log(fileURL);
        newMsgDiv.innerHTML =  `<p class="meta">${message.username} <span>${message.time}</span></p>
                            <img src="${fileURL}" alt="file" class="image"/>
                            <p class="text">${message.text}</p>`;
    }

    document.querySelector('.chat-messages .messages').appendChild(newMsgDiv);
}

function updateRoomName(name) {
    roomName.innerHTML = name;
}

function updateRoomUsers(users, currentUsername) {
    console.log(users);
    roomUsers.innerHTML = `
    ${users.map(user =>`<li id="${user.username}">${user.username}<\li>`).join('')}
    `;

    for(let i = 0; i < users.length; i++) {
        const user = document.querySelector(`#${users[i].username}`);
        if(users[i].username === currentUsername) {
            user.classList.add('current-user');
        }
    }

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
    chatMessages.scrollTop = chatMessages.scrollHeight;
});

socket.on('stoppedTyping', () => {
    typingIndicator.textContent = '';
    typingIndicator.classList.remove("typing-indicator-active");
    chatMessages.scrollTop = chatMessages.scrollHeight;
});

// change label written on the input field for files to the name of chosen file
const fileInput = document.getElementById('file-input');
const fileInputLabel = document.getElementById('file-input-label');
fileInput.addEventListener('change', () => {
    if(fileInput.files.length === 0) {
        clearFileInput();
        return;
    }
    fileInputLabel.innerHTML = `<i class="fas fa-file-image"></i>  ${fileInput.files[0].name}`;
});

function clearFileInput() {
    fileInput.value = '';
    fileInputLabel.innerHTML = `<i class="fas fa-file-image"></i>  Choose an image`;
}

function validateFileSize(file) {
    const fileSize = file.size;
    const maxSize = document.getElementById('max-file-size').value;

    if (fileSize > maxSize) {
        alert('File size exceeds the maximum size limit');
        return false;
    }
    return true;
}

