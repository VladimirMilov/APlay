package com.adidas.hackathon.events.service.generator;

import com.adidas.hackathon.events.domain.Event;
import com.adidas.hackathon.events.domain.Match;
import com.adidas.hackathon.events.domain.NationalTeam;
import com.adidas.hackathon.events.domain.Player;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class GoalGeneratorHelper extends EventGeneratorHelper {

    @Override
    public int generateEvent(Match match, NationalTeam mainTeam, NationalTeam rivalTeam){
        Integer attackPoints = (mainTeam.getAttack()*2 - rivalTeam.getDefense())/2;
        Integer goalsProb = (attackPoints > 0) ? attackPoints : 1;
        int goals = ThreadLocalRandom.current().nextInt(0, goalsProb + 1);
        for(int i=0; i<goals; i++){
            int minute = randomMinuteGenerator();
            int player = randomPlayerNumGenerator();
            Player footballPlayer = getFootballPlayerFromNationalTeam(mainTeam, player);
            saveEvent(Event.goal(match, mainTeam, footballPlayer, minute));
        }
        return goals;
    }

}
