package me.mnemosyne.teamfight.match;

import lombok.Getter;
import me.mnemosyne.teamfight.arena.Arena;
import me.mnemosyne.teamfight.team.Team;

import java.util.UUID;

public class Match {
    @Getter private UUID matchUUID;

    public Match(Team team1, Team team2, Arena arena){
        this.matchUUID = UUID.randomUUID();
    }
}
