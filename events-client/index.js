var amqp = require('amqplib/callback_api');

const exchange = 'amq.topic';

const key = process.env.TOPIC;

const queue_name = process.env.QUEUE_NAME;

console.log("Queue name: " + queue_name + " - Topic: " + key);

amqp.connect('amqp://rabbitmq-broker', function(err, conn) {

    conn.createChannel(function(err, channel) {

        channel.assertQueue(queue_name, {exclusive: true}, function(err, q){

            channel.bindQueue(q.queue, exchange, key); 

            channel.consume(q.queue, function(msg) {
                var event = JSON.parse(msg.content.toString())
                console.log(`Topic: ${msg.fields.routingKey} & event.type: ${event.type}`);
            });

        });

    });

});