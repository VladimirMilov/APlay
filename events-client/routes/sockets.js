module.exports = function (app, passport) {

    app.io = require('socket.io')();


    app.io.on('connection', function (socket) {


        socket.on('events', function (data) {
            io.emit('events', data);
        })

    })
}