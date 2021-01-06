package me.mnemosyne.teamfight.team;

import lombok.Getter;
import lombok.Setter;
import me.mnemosyne.teamfight.TeamfightPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Team {
    @Getter @Setter private String teamName;
    @Getter @Setter private UUID teamUUID;

    @Getter @Setter private UUID teamLeaderUUID;
    @Getter @Setter private Collection<UUID> captainsUUIDs;
    @Getter @Setter private Collection<UUID> membersUUIDs;

    @Getter @Setter private Collection<UUID>pendingInviteRequests;
    @Getter @Setter private boolean isFightInProgress;
    @Getter @Setter private int wins;
    @Getter @Setter private int losses;

    public Team(Player teamLeaderPlayer, String teamName) {
        membersUUIDs = new ArrayList<>();
        captainsUUIDs = new ArrayList<>();
        pendingInviteRequests = new ArrayList<>();

        this.teamUUID = UUID.randomUUID();
        this.teamLeaderUUID = teamLeaderPlayer.getUniqueId();
        this.teamName = teamName;

        this.isFightInProgress = false;

        this.wins = 0;
        this.losses = 0;
    }

    public int getPlayerCount(){
        return 1 + captainsUUIDs.size() + membersUUIDs.size();
    }

    public Collection<Player>getOnlinePlayers(){
        Collection<Player>players = new ArrayList<>();

        Player teamLeaderPlayer = Bukkit.getPlayer(teamLeaderUUID);
        if(teamLeaderPlayer != null) { players.add(teamLeaderPlayer); }

        for(UUID uuid : captainsUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null){ players.add(player); }
        }
        for(UUID uuid : membersUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null){ players.add(player); }
        }

        return players;
    }

    public Collection<UUID>getUUIDs(){
        Collection<UUID>UUIDList = new ArrayList<>();

        UUIDList.add(teamLeaderUUID);

        UUIDList.addAll(captainsUUIDs);
        UUIDList.addAll(membersUUIDs);

        return UUIDList;
    }

    public boolean isLeader(UUID uuid){
        return uuid.equals(teamLeaderUUID);
    }

    public boolean isCaptain(UUID uuid){
        return captainsUUIDs.contains(uuid);
    }

    public boolean isMember(UUID uuid){
        return membersUUIDs.contains(uuid);
    }

    public void addPlayer(Player player){
        this.membersUUIDs.add(player.getUniqueId());
    }

    public void removePlayer(UUID uuid){
        captainsUUIDs.remove(uuid);
        membersUUIDs.remove(uuid);
    }

    public void promotePlayerToCaptain(UUID uuid){
        if(membersUUIDs.contains(uuid)){
            membersUUIDs.remove(uuid);
            captainsUUIDs.add(uuid);
        }
    }

    public boolean isInTeam(UUID uuid){
        return this.getUUIDs().contains(uuid);
    }

    public void sendMessageToTeam(String message){
        for(Player player : getOnlinePlayers()){
            player.sendMessage(message);
        }
    }

    public Collection<String> getAllNames(){
        Collection<String>names = new ArrayList<>();

        names.add(TeamfightPlugin.getInstance().getPlayerCache().getPlayerNameByUUID(teamLeaderUUID));
        names.addAll(TeamfightPlugin.getInstance().getPlayerCache().getPlayersNamesByUUIDs(captainsUUIDs));
        names.addAll(TeamfightPlugin.getInstance().getPlayerCache().getPlayersNamesByUUIDs(membersUUIDs));

        return names;
    }

    public String getTeamLeaderName(){
        return TeamfightPlugin.getInstance().getPlayerCache().getPlayerNameByUUID(teamLeaderUUID);
    }

    public Collection<String> getCaptainsNames(){
        return TeamfightPlugin.getInstance().getPlayerCache().getPlayersNamesByUUIDs(captainsUUIDs);
    }

    public Collection<String> getMembersNames(){
        return TeamfightPlugin.getInstance().getPlayerCache().getPlayersNamesByUUIDs(membersUUIDs);
    }
}
