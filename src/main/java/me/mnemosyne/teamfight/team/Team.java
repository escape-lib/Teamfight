package me.mnemosyne.teamfight.team;

import lombok.Getter;
import lombok.Setter;
import me.mnemosyne.teamfight.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class Team {
    @Getter @Setter private String teamName;
    @Getter @Setter private UUID teamUUID;

    @Getter @Setter private UUID teamLeaderUUID;
    @Getter @Setter private String teamLeaderNameCache;

    @Getter @Setter private HashMap<UUID, String>captainList;
    @Getter @Setter private HashMap<UUID, String>memberList;

    @Getter @Setter private Collection<UUID>pendingInviteRequests;
    @Getter @Setter private boolean isFightInProgress;
    @Getter @Setter private int wins;
    @Getter @Setter private int losses;

    public Team(Player teamLeaderPlayer) {
        memberList = new HashMap<>();
        captainList = new HashMap<>();
        pendingInviteRequests = new ArrayList<>();

        this.teamUUID = UUID.randomUUID();
        this.teamLeaderUUID = teamLeaderPlayer.getUniqueId();
        this.teamLeaderNameCache = teamLeaderPlayer.getName();

        this.isFightInProgress = false;

        this.wins = 0;
        this.losses = 0;
    }

    public int getPlayerCount(){
        return 1 + captainList.size() + memberList.size();
    }

    public Collection<Player>getOnlinePlayerList(){
        Collection<Player>playerList = new ArrayList<>();

        Player teamLeaderPlayer = Bukkit.getPlayer(teamLeaderUUID);
        if(teamLeaderPlayer != null) { playerList.add(teamLeaderPlayer); }

        for(UUID uuid : captainList.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null){ playerList.add(player); }
        }
        for(UUID uuid : memberList.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null){ playerList.add(player); }
        }

        return playerList;
    }

    public Collection<UUID>getTotalUUIDList(){
        Collection<UUID>UUIDList = new ArrayList<>();

        UUIDList.add(teamLeaderUUID);

        UUIDList.addAll(captainList.keySet());
        UUIDList.addAll(memberList.keySet());

        return UUIDList;
    }

    public UUID getLeaderUUID(){
        return this.teamLeaderUUID;
    }

    public boolean isLeader(UUID uuid){
        return uuid.equals(teamLeaderUUID);
    }

    public boolean isCaptain(UUID uuid){
        return captainList.containsKey(uuid);
    }

    public boolean isMember(UUID uuid){
        return memberList.containsKey(uuid);
    }

    public void addPlayerToTeam(Player player){
        this.memberList.put(player.getUniqueId(), player.getName());
    }

    public void promotePlayerToCaptain(String playerName){
        UUID playerUUID = GeneralUtil.getKeyByValue(memberList, playerName);

        if(memberList.containsKey(playerUUID)){
            memberList.remove(playerUUID);
            captainList.put(playerUUID, playerName);
        }
    }

    public boolean isInTeam(String playerName){
        for(String name : getEntireTeamPlayerListByName()){
            if(name.equalsIgnoreCase(playerName)) { return true; }
        }

        return false;
    }

    public void sendMessageToTeam(String message){
        for(Player player : getOnlinePlayerList()){
            player.sendMessage(message);
        }
    }

    public String getTeamLeaderName(){
        return this.teamLeaderNameCache;
    }

    public Collection<String>getCaptainNameList(){
        Collection<String>list = new ArrayList<>();

        list.addAll(captainList.values());

        return list;
    }

    public Collection<String>getMemberNameList() {
        Collection<String>list = new ArrayList<>();

        list.addAll(memberList.values());

        return list;
    }

    public Collection<String>getEntireTeamPlayerListByName(){
        Collection<String>list = new ArrayList<>();

        list.add(this.getTeamLeaderName());
        list.addAll(this.getCaptainNameList());
        list.addAll(this.getMemberNameList());

        return list;
    }
}
