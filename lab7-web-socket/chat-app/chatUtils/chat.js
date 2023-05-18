const mongoose = require('mongoose');

const messageSchema = new mongoose.Schema({
    username: String,
    text: String,
    file: String,
    time: String
});

const chatSchema = new mongoose.Schema({
    room: String,
    messages: [messageSchema]
});

const Chat = mongoose.model('Chat', chatSchema);

module.exports = Chat;
