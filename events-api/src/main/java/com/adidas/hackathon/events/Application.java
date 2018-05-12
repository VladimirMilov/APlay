package com.adidas.hackathon.events;

import com.adidas.hackathon.events.service.EventsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    /**
     * Main starting point of the application.
     *
     * @param args an array of arguments passed to the application
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(EventsService.class).doEventsLoop();
    }
}