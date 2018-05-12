package com.adidas.hackathon.events.service.generator;

import com.adidas.hackathon.events.domain.Event;
import com.adidas.hackathon.events.domain.Match;
import com.adidas.hackathon.events.domain.NationalTeam;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class CornerGeneratorHelper extends EventGeneratorHelper {

    @Override
    public int generateEvent(Match match, NationalTeam mainTeam, NationalTeam rivalTeam){
        Integer cornersPoints = mainTeam.getAttack()*2 - rivalTeam.getDefense();
        Integer cornersProb = (cornersPoints > 1) ? cornersPoints : 2;
        int corners = ThreadLocalRandom.current().nextInt(1, cornersProb + 1);
        for(int i=0; i<corners; i++){
            int minute = randomMinuteGenerator();
            saveEvent(Event.corner(match, mainTeam, minute));
        }
        return corners;
    }

}
