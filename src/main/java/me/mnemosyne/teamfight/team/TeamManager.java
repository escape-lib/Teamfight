package me.mnemosyne.teamfight.team;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamManager {
    @Getter private Collection<Team> teamList = new ArrayList<>();

    public Team getTeam(Player player){
        UUID playerUUID = player.getUniqueId();

        for(Team team : teamList){
            if(team.getUUIDs().contains(playerUUID)){
                return team;
            }
        }

        return null;
    }

    public Team getTeam(UUID playerUUID){
        return getTeam(Bukkit.getPlayer(playerUUID));
    }

    public void updateTeam(Team team){
        Collection<Team> removed = new ArrayList<>();
        Collection<Team> added = new ArrayList<>();

        for(Team t : teamList) {
            if (t.getTeamUUID().equals(team.getTeamUUID())) {
                removed.add(t);
                added.add(team);
            }
        }

        teamList.removeAll(removed);
        teamList.addAll(added);
    }

    public void updateTeam(Team[] teams){
        Collection<Team> removed = new ArrayList<>();
        Collection<Team> added = new ArrayList<>();

        for(Team team : teams){
            for(Team t : teamList){
                if(t.getTeamUUID().equals(team.getTeamUUID())){
                    removed.add(t);
                    added.add(team);
                }
            }
        }

        teamList.removeAll(removed);
        teamList.addAll(added);
    }

    public void addTeam(Team team){
        teamList.add(team);
    }

    public void removeTeam(Team team){
        Collection<Team> removed = new ArrayList<>();

        for(Team itTeam : teamList){
            if(itTeam.getTeamUUID().equals(team.getTeamUUID())){
                removed.add(itTeam);
            }
        }

        teamList.removeAll(removed);
    }

    public Collection<Team>getTeamsFromPlayerOrTeamName(String in){
        Collection<Team>teams = new ArrayList<>();
        Player potentialPlayer = Bukkit.getPlayer(in);

        for(Team itTeam : teamList){
            if(itTeam.getTeamName().equalsIgnoreCase(in)){
                teams.add(itTeam);
            }
            if (potentialPlayer != null
                    && itTeam.getUUIDs().contains(potentialPlayer.getUniqueId())){

                teams.add(itTeam);
            }
        }

        if(teams.size() == 0){
            return null;
        }

        return teams.stream().distinct().collect(Collectors.toList());
    }

    public boolean teamExists(String teamName){
        for(Team itTeam : teamList){
            if(itTeam.getTeamName().equalsIgnoreCase(teamName)){
                return true;
            }
        }

        return false;
    }

    public Team getTeamByName(String teamName){
        for(Team itTeam : teamList){
            if(itTeam.getTeamName().equalsIgnoreCase(teamName)){
                return itTeam;
            }
        }

        return null;
    }
}
