package com.adidas.hackathon.events.service;

import com.adidas.hackathon.events.dao.EventsDAO;
import com.adidas.hackathon.events.dao.MatchesDAO;
import com.adidas.hackathon.events.domain.Event;
import com.adidas.hackathon.events.domain.NationalTeam;
import com.adidas.hackathon.events.service.generator.GoalGeneratorHelper;
import com.adidas.hackathon.events.service.generator.YellowCardGeneratorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventGeneratorService {

    @Autowired
    private MatchesDAO matchesDAO;

    @Autowired
    private EventsDAO eventsDAO;

    @Autowired
    private GoalGeneratorHelper goalEventGenerator;

    @Autowired
    private GoalGeneratorHelper cornerEventGenerator;

    @Autowired
    private YellowCardGeneratorHelper yellowCardEventGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsService.class);

    public void generate(){
        matchesDAO.findAll().forEach(match -> {

            // START AND END EVENTS
            eventsDAO.save(Event.startMatch(match));
            eventsDAO.save(Event.endHalfTime(match));
            eventsDAO.save(Event.startHalfTime(match));
            eventsDAO.save(Event.endMatch(match));

            // GET COUNTRY DATA
            NationalTeam home = match.getHome();
            NationalTeam away = match.getAway();

            // GOALS GENERATION
            int homeGoals = goalEventGenerator.generateEvent(match, home, away);
            int awayGoals = goalEventGenerator.generateEvent(match, away, home);

            // CORNERS GENERATION
            int homeCorners = cornerEventGenerator.generateEvent(match, home, away);
            int awayCorners = cornerEventGenerator.generateEvent(match, away, home);

            // YELLOW CARD GENERATION
            int homeCards = yellowCardEventGenerator.generateEvent(match, home, away);
            int awayCards = yellowCardEventGenerator.generateEvent(match, away, home);

            // LOG RESULTS
            String matchResult = String.format("%d. %s %d - %d %s ___ Corners: %d - %d ___ Cards: %d - %d",
                    match.getId(), home.getName(), homeGoals, awayGoals, away.getName(),
                    homeCorners, awayCorners, homeCards, awayCards);
            LOGGER.info(matchResult);
        });
    }
}
