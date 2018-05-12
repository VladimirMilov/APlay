var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var roomsRouter = require('./routes/rooms');
var app = express();
var amqp = require('amqplib/callback_api');

const exchange = 'amq.topic';

const key = process.env.TOPIC;

const queue_name = process.env.QUEUE_NAME;

console.log("Queue name: " + queue_name + " - Topic: " + key);


app.io = require('socket.io')();

require('./routes/sockets')(app);

amqp.connect('amqp://rabbitmq-broker', function(err, conn) {

    conn.createChannel(function(err, channel) {

        channel.assertQueue(queue_name, {exclusive: true}, function(err, q){

            channel.bindQueue(q.queue, exchange, key); 

            channel.consume(q.queue, function(msg) {
                var event = JSON.parse(msg.content.toString())
                console.log(`Topic: ${msg.fields.routingKey} & event.type: ${event.type}`);
                app.io.emit('events', event);
            });

        });

    });

});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/products', usersRouter);
app.use('/rooms', roomsRouter);
app.use('/', usersRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
