package me.mnemosyne.teamfight.match;

import lombok.Getter;
import me.mnemosyne.teamfight.arena.Arena;
import me.mnemosyne.teamfight.team.Team;

import java.util.ArrayList;
import java.util.Collection;

public class MatchManager {
    @Getter private Collection<Match> matches;

    public void addMatch(Match match){
        this.matches.add(match);
    }

    public void removeMatch(Match match){
        Collection<Match> removed = new ArrayList<>();

        for(Match itMatch : this.matches){
            if(itMatch.getMatchUUID().equals(match.getMatchUUID())) { removed.add(itMatch); }
        }

        matches.removeAll(removed);
    }

    public void updateMatch(Match match){
        Collection<Match> removed = new ArrayList<>();

        for(Match itMatch : this.matches){
            if(itMatch.getMatchUUID().equals(match.getMatchUUID())) { removed.add(itMatch); }
        }

        matches.removeAll(removed);
        matches.add(match);
    }
}
