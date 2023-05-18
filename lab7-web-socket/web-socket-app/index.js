//importing http module
const http = require('http');
//importing ws module
const websocket = require('ws');

//creating a http server
const server = http.createServer((req, res) => {
    res.end("I am connected");
});

//creating websocket server
const wss = new websocket.Server({ server });

//calling a method 'on' which is available on websocket object
wss.on('headers', (headers, req) => {
    //logging the header
    console.log('WebSocket.on headers:\n');
    console.log(headers);
});

console.log('Listening on http://localhost:8000 ...');

//making it listen to port 8000
server.listen(8000);

//Event: 'connection'
wss.on('connection', (ws, req) => {
    ws.send('Welcome, your connection is ready');
    //receive the message from client on Event: 'message'
    ws.on('message', (msg, isBinary) => {
    console.log('Received message from client:');
    const message = isBinary ? msg : msg.toString();
    console.log(message);
    });
    });