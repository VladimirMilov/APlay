package com.adidas.hackathon.events.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "IEVENTID")
    private Integer id;

    @Column(name = "STITLE")
    private String title;

    @Column(name = "SDESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STYPE")
    private Type type;

    @Column(name = "ITIME")
    private Integer time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INATIONALTEAMID", referencedColumnName = "INATIONALTEAMID")
    private NationalTeam nationalTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IPLAYERID", referencedColumnName = "IPLAYERID")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IMATCHID", referencedColumnName = "IMATCHID")
    private Match match;

    private static final String HALF_TIME_TEXT = "Half time";

    public enum Type {
        START,
        GOAL,
        CORNER,
        YELLOW_CARD,
        END_HALF_TIME,
        START_HALF_TIME,
        END
    }

    public static Event startMatch(Match match) {
        Event event = new Event();
        event.setTitle("Start match");
        event.setDescription("The match has just started ...");
        event.setType(Event.Type.START);
        event.setTime(0);
        event.setMatch(match);

        return event;
    }

    public static Event endHalfTime(Match match){
        Event event = new Event();
        event.setTitle(HALF_TIME_TEXT);
        event.setDescription(HALF_TIME_TEXT);
        event.setType(Type.END_HALF_TIME);
        event.setTime(45);
        event.setMatch(match);

        return event;
    }

    public static Event startHalfTime(Match match){
        Event event = new Event();
        event.setTitle(HALF_TIME_TEXT);
        event.setDescription(HALF_TIME_TEXT);
        event.setType(Type.START_HALF_TIME);
        event.setTime(45);
        event.setMatch(match);

        return event;
    }

    public static  Event endMatch(Match match) {
        Event event = new Event();
        event.setTitle("End match");
        event.setDescription("The match has just ended ...");
        event.setType(Event.Type.END);
        event.setTime(90);
        event.setMatch(match);

        return event;
    }

    public static Event goal(Match match, NationalTeam team, Player player, Integer time) {
        Event event = new Event();
        event.setTitle("Goal");
        event.setDescription(String.format("Goal %s", player));
        event.setType(Type.GOAL);
        event.setTime(time);
        event.setMatch(match);
        event.setNationalTeam(team);
        event.setPlayer(player);

        return event;
    }

    public static Event corner(Match match, NationalTeam team, Integer time) {
        Event event = new Event();
        event.setTitle("Corner");
        event.setDescription(String.format("Corner for %s", team.getName()));
        event.setType(Type.CORNER);
        event.setTime(time);
        event.setMatch(match);
        event.setNationalTeam(team);

        return event;
    }

    public static Event yellowCard(Match match, NationalTeam team, Player player, Integer time) {
        Event event = new Event();
        event.setTitle("Yellow card");
        event.setDescription(String.format("Yellow Card %s", player));
        event.setType(Type.YELLOW_CARD);
        event.setTime(time);
        event.setMatch(match);
        event.setNationalTeam(team);
        event.setPlayer(player);

        return event;
    }
}
