package me.mnemosyne.teamfight.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Team {
    @Getter @Setter private String teamName;
    @Getter @Setter private UUID teamUUID;
    @Getter @Setter private UUID teamLeaderUUID;
    @Getter @Setter private Collection<UUID>captainList;
    @Getter @Setter private Collection<UUID>memberList;
    @Getter @Setter private Collection<UUID>pendingInviteRequests;
    @Getter @Setter private boolean isFightInProgress;
    @Getter @Setter private int wins;
    @Getter @Setter private int losses;

    public Team(Player teamLeader) {
        memberList = new ArrayList<>();
        captainList = new ArrayList<>();
        pendingInviteRequests = new ArrayList<>();

        this.teamUUID = UUID.randomUUID();
        this.teamLeaderUUID = teamLeader.getUniqueId();

        this.isFightInProgress = false;

        this.wins = 0;
        this.losses = 0;
    }

    public int getPlayerCount(){
        return 1 + captainList.size() + memberList.size();
    }

    public Collection<Player>getPlayerList(){
        Collection<Player>playerList = new ArrayList<>();

        playerList.add(Bukkit.getPlayer(teamLeaderUUID));

        for(UUID uuid : captainList) { playerList.add(Bukkit.getPlayer(uuid)); }
        for(UUID uuid : memberList) { playerList.add(Bukkit.getPlayer(uuid)); }

        return playerList;
    }

    public Collection<UUID>getUUIDList(){
        Collection<UUID>UUIDList = new ArrayList<>();

        UUIDList.add(teamLeaderUUID);

        for(UUID uuid : captainList){ UUIDList.add(uuid); }
        for(UUID uuid : memberList){ UUIDList.add(uuid); }

        return UUIDList;
    }

    public Player getLeader(){
        return Bukkit.getPlayer(this.teamLeaderUUID);
    }

    public boolean isLeader(Player player){
        return player.getUniqueId().equals(teamLeaderUUID);
    }

    public void sendMessageToTeam(String message){
        for(Player player : getPlayerList()){
            player.sendMessage(message);
        }
    }
}
