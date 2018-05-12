package com.adidas.hackathon.events.service;

import com.adidas.hackathon.events.dao.EventsDAO;
import com.adidas.hackathon.events.dao.MatchesDAO;
import com.adidas.hackathon.events.domain.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class EventsService {

    private static final String HOST = "rabbitmq-broker";
    private static final String EXCHANGE = "amq.topic";
    private static final String ROUTING_KEY = "fifa.worldcup";
    private static final Integer MATCH_DURATION = 105;
    // Time in millis a minute in a match will elapse
    private static final Long DELAY_ESCALATION = 100L;
    // Delay in millis between loop executions
    private static final Long LOOP_DELAY = 10000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsService.class);
    private final Sender sender;
    private final ObjectMapper mapper;
    private final EventsDAO eventsDAO;
    private final MatchesDAO matchesDAO;
    private Integer rounds = 0;

    @Autowired
    public EventsService(EventsDAO eventsDAO, MatchesDAO matchesDAO, EventGeneratorService eventGeneratorService) {

        this.eventsDAO = eventsDAO;

        this.matchesDAO = matchesDAO;

        this.mapper = new ObjectMapper();

        this.sender = createSender();

        eventGeneratorService.generate();
    }

    private Sender createSender() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        connectionFactory.setHost(HOST);

        SenderOptions senderOptions = new SenderOptions()
                .connectionFactory(connectionFactory);

        return ReactorRabbitMq.createSender(senderOptions);
    }

    public void send() {
        eventsDAO.findAll().forEach(event -> {

            OutboundMessage msg = transform(event);

            Integer delay = (event.getMatch().getId() - 1) * MATCH_DURATION + event.getTime();

            if (event.getTime() >= 45 && event.getType() != Event.Type.END_HALF_TIME) {
                delay = delay + 15;
            }

            Mono<OutboundMessage> mono = Mono.just(msg).delayElement(Duration.ofMillis(delay * DELAY_ESCALATION));

            Flux<OutboundMessageResult> confirmations = sender.sendWithPublishConfirms(mono);

            confirmations
                    .doOnError(e -> LOGGER.error("Send failed", e))
                    .subscribe(r -> {
                        if (r.isAck()) {
                            LOGGER.info("Message {} sent successfully ", new String(r.getOutboundMessage().getBody()));
                        }
                    });

        });
    }

    public void doEventsLoop(){
        Long matchNum = matchesDAO.count();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                rounds++;
                LOGGER.info(String.format("Starting round: %d", rounds));
                send();
            }

        }, 0, DELAY_ESCALATION * MATCH_DURATION * matchNum + LOOP_DELAY);
    }

    private OutboundMessage transform(Event event) {
        return new OutboundMessage(EventsService.EXCHANGE, getRoutingKey(event), getContent(event));
    }

    private String getRoutingKey(Event event) {
        String key = EventsService.ROUTING_KEY;

        key = key.concat("." + event.getMatch().getId());

        key = key.concat("." + event.getMatch().getStadium().getId());

        key = key.concat("." + event.getMatch().getHome().getShortName());

        key = key.concat("." + event.getMatch().getAway().getShortName());

        key = key.concat("." + event.getType().toString().toLowerCase().trim());

        return key;
    }

    private byte[] getContent(Event event) {
        byte[] content = null;

        try {
            content = mapper.writeValueAsBytes(event);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }

        return content;
    }

}
