package com.adidas.hackathon.events.service.generator;

import com.adidas.hackathon.events.dao.EventsDAO;
import com.adidas.hackathon.events.dao.PlayersDAO;
import com.adidas.hackathon.events.domain.Event;
import com.adidas.hackathon.events.domain.Match;
import com.adidas.hackathon.events.domain.NationalTeam;
import com.adidas.hackathon.events.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ThreadLocalRandom;

public abstract class EventGeneratorHelper {

    @Autowired
    private EventsDAO eventsDAO;

    @Autowired
    private PlayersDAO playersDAO;

    protected int randomMinuteGenerator() {
        return ThreadLocalRandom.current().nextInt(0, 90) + 1;
    }

    protected int randomPlayerNumGenerator() {
        return ThreadLocalRandom.current().nextInt(1, 11) + 1;
    }

    protected Player getFootballPlayerFromNationalTeam(NationalTeam mainTeam, int player) {
        Player footballPlayer = playersDAO.findByNationalTeamAndStartNumber(mainTeam, player);
        return (footballPlayer == null) ? createDefaultPlayer(mainTeam, player) : footballPlayer;
    }

    private Player createDefaultPlayer(NationalTeam mainTeam, int player) {
        Player footballPlayer;
        footballPlayer = new Player();
        footballPlayer.setFirstName(String.format("Player %d", player));
        footballPlayer.setLastName("");
        footballPlayer.setNationalTeam(mainTeam);
        footballPlayer.setStartNumber(player);
        playersDAO.save(footballPlayer);
        return footballPlayer;
    }

    protected void saveEvent(Event event){
        eventsDAO.save(event);
    }

    public abstract int generateEvent(Match match, NationalTeam mainTeam, NationalTeam rivalTeam);

}
