package com.adidas.hackathon.events.service.generator;

import com.adidas.hackathon.events.domain.Event;
import com.adidas.hackathon.events.domain.Match;
import com.adidas.hackathon.events.domain.NationalTeam;
import com.adidas.hackathon.events.domain.Player;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class YellowCardGeneratorHelper extends EventGeneratorHelper {

    @Override
    public int generateEvent(Match match, NationalTeam mainTeam, NationalTeam rivalTeam){
        Integer cardPoints = mainTeam.getAggressive() + rivalTeam.getAggressive()/2;
        Integer cardProb = (cardPoints > 1) ? cardPoints : 2;
        int yellowCards = ThreadLocalRandom.current().nextInt(1, cardProb + 1);
        for(int i=0; i<yellowCards; i++){
            int minute = randomMinuteGenerator();
            int player = randomPlayerNumGenerator();
            Player footballPlayer = getFootballPlayerFromNationalTeam(mainTeam, player);
            saveEvent(Event.yellowCard(match, mainTeam, footballPlayer, minute));
        }
        return yellowCards;
    }

}
