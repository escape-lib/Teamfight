package me.mnemosyne.teamfight.team;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.user.User;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamSubcommandHandler {
    private Player player;
    private String[] args;
    private Team userTeam;
    private User user;

    public TeamSubcommandHandler(Player player, String[] args){
        this.player = player;
        this.args = args;

        this.userTeam = TeamfightPlugin.getInstance().getTeamManager().getTeam(player);
        this.user = TeamfightPlugin.getUserManager().getUser(player);

    }

    /*
    * logic
    *
    * no _ prefix : the player should be this flag (eg. IN_TEAM),
    * the player should be in a team. if they werent, it would send
    * them a message saying something along the lines of : "You are not in a team!"
    * returns: true
    *
    * _ prefix : the player should not be this flag (eg. " "),
    * the player should not be in a team. " " : "You cannot do this while you are in a team!"
    * returns: true
    *
     */
    private enum PLAYER_CHECK_FLAGS{
        EXECUTOR_IN_TEAM,
        _EXECUTOR_IN_TEAM,

        EXECUTOR_IS_LEADER,
        _EXECUTOR_IS_LEADER,

        EXECUTOR_IN_SPAWN,
        _EXECUTOR_IN_SPAWN
    }

    private boolean checkPlayer(Collection<PLAYER_CHECK_FLAGS>flags, String usage, int requiredArgsCount) {
        if (args.length < requiredArgsCount) {
            player.sendMessage(usage);
            return true;
        }

        for (PLAYER_CHECK_FLAGS itFlag : flags) {
            if (itFlag.equals(PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM) && userTeam == null) {
                player.sendMessage(TeamfightPlugin.getInstance().getNotInTeamMessage());
                return true;

            } else if (itFlag.equals(PLAYER_CHECK_FLAGS._EXECUTOR_IN_TEAM) && userTeam != null) {
                player.sendMessage(TeamfightPlugin.getInstance().getAlreadyInTeamMessage());
                return true;
            }


            if(itFlag.equals(PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER) && !userTeam.isLeader(player.getUniqueId())){
                player.sendMessage(TeamfightPlugin.getInstance().getNotLeaderMessage());
                return true;

            } else if (itFlag.equals(PLAYER_CHECK_FLAGS._EXECUTOR_IS_LEADER) && userTeam.isLeader(player.getUniqueId())){
                player.sendMessage(TeamfightPlugin.getInstance().getCannotDoThisAsLeaderMessage());
                return true;
            }


            if(itFlag.equals(PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN) && !user.isInSpawn()){
                player.sendMessage(TeamfightPlugin.getInstance().getInFightMessage());
                return true;
            }



        }

        return false;
    }

    private boolean isAlphaNumeric(String in) {
        return in != null && in.matches("^[a-zA-Z0-9]*$");
    }

    private String showTeam(Team team){
        String teamLeaderName = team.getTeamLeaderName();
        Collection<String> captainsNames = team.getCaptainsNames();
        Collection<String> membersNames = team.getMembersNames();


        String build = ChatColourUtil.convert(
                "\n\n" + TeamfightPlugin.getInstance().getChatSpacer() + "\n&7Team: &6" + team.getTeamName() + " &7[&a" + team.getPlayerCount() + " Online&7]\n" +
                        "&fLeader: &a" + teamLeaderName + "\n");


        String captainsString = "&fCaptains: &a";
        for(String name : captainsNames){
            captainsString += name + "&7,&a";
        }
        if(captainsString.endsWith(",")){
            captainsString = captainsString.substring(0, captainsString.length() - 1);
        } else {
            captainsString += ChatColourUtil.convert("&7None");
        }


        String membersString = "&fMembers: &a";
        for(String name : membersNames){
            membersString += name + "&7,&a";
        }
        if(membersString.endsWith(",")){
            membersString = membersString.substring(0, membersString.length() - 1);
        } else {
            membersString += ChatColourUtil.convert("&7None");
        }


        build += ChatColourUtil.convert(
                captainsString + "\n" +
                membersString + "\n" +
                "&fWins: " + team.getWins() + "\n" +
                        "&fLosses: " + team.getLosses() + "\n" + TeamfightPlugin.getInstance().getChatSpacer());

        return build;
    }

    public void createTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS._EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN),

                ChatColourUtil.convert("&f/t create &7<teamName>"),
                2)) { return; }

        if(!isAlphaNumeric(args[1])
                || args[1].length() > 16
                || args[1].length() < 3) {

            player.sendMessage(TeamfightPlugin.getInstance().getSuitableNameMessage());
            return;

        } else if (TeamfightPlugin.getInstance().getTeamManager().teamExists(args[1])){
            player.sendMessage(TeamfightPlugin.getInstance().getTeamExistsMessage().replace("%team_name%", args[1]));
            return;
        }

        Team team = new Team(player, args[1]);
        TeamfightPlugin.getInstance().getTeamManager().addTeam(team);

        for(Player itPlayer : Bukkit.getOnlinePlayers()){
            itPlayer.sendMessage(ChatColourUtil.convert("&fTeam &6" + team.getTeamName() + " &fhas been created by &7" + player.getName()));
        }
    }

    public void disbandTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER),

                "",
                1)) { return; }

        TeamfightPlugin.getInstance().getTeamManager().removeTeam(userTeam);

        for(Player itPlayer : Bukkit.getOnlinePlayers()){
            itPlayer.sendMessage(ChatColourUtil.convert("&fTeam &6" + userTeam.getTeamName() + " &fhas been disbanded by &7" + player.getName()));
        }
    }

    /*exception to flags as player can t show without having 2nd arg*/
    public void showTeam(){
        if(args.length == 1 && userTeam == null){
            player.sendMessage(ChatColourUtil.convert("&f/t show &7<teamName|playerName>"));
            return;

        } else if (args.length == 1){
            player.sendMessage(this.showTeam(userTeam));
            return;
        }

        Collection<Team>teamsToBeShown = TeamfightPlugin.getInstance().getTeamManager().getTeamsFromPlayerOrTeamName(args[1]);

        if(teamsToBeShown == null){
            player.sendMessage(TeamfightPlugin.getInstance().getNoTeamFoundMessage().replace("%udefined%", args[1]));

            return;
        }

        for(Team itTeam : teamsToBeShown){
            player.sendMessage(this.showTeam(itTeam));
        }
    }

    public void invitePlayer(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER),

                ChatColourUtil.convert("&f/t invite &7<player>"),
                2)) { return; }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if(targetPlayer == null){
            player.sendMessage(TeamfightPlugin.getInstance().getOfflinePlayerMessage());
            return;
        }

        if(userTeam.isInTeam(targetPlayer.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getPlayerIsInTeamMessage());
            return;

        } else if (userTeam.getPendingInviteRequests().contains(targetPlayer.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getAlreadyInvitedPlayerMessage());
            return;
        }

        String inviteMessage = TeamfightPlugin.getInstance().getInvitingMessage();
        String isInvitingToTeamMessage = TeamfightPlugin.getInstance().getIsInvitingToTeamMessage();

        inviteMessage = inviteMessage.replace("%inviting_player%", player.getName());
        inviteMessage = inviteMessage.replace("%team_name%", userTeam.getTeamName());

        isInvitingToTeamMessage = isInvitingToTeamMessage.replace("%inviting_player%", player.getName());
        isInvitingToTeamMessage = isInvitingToTeamMessage.replace("%invited_player%", targetPlayer.getName());

        targetPlayer.sendMessage(inviteMessage);
        userTeam.sendMessageToTeam(isInvitingToTeamMessage);

        Collection<UUID>pendingInvites = new ArrayList<>(userTeam.getPendingInviteRequests());
        pendingInvites.add(targetPlayer.getUniqueId());

        userTeam.setPendingInviteRequests(pendingInvites);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }

    public void unInvitePlayer(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER),

                ChatColourUtil.convert("&f/t uninvite &7<player>"),
                2)) { return; }

        UUID targetPlayerUUID = TeamfightPlugin.getInstance().getPlayerCache().getPlayerUUIDByName(args[1]);

        if(userTeam.isInTeam(targetPlayerUUID)){
            player.sendMessage(TeamfightPlugin.getInstance().getPlayerUninviteIsInTeamMessage());
            return;

        } else if (!userTeam.getPendingInviteRequests().contains(targetPlayerUUID)){
            player.sendMessage(TeamfightPlugin.getInstance().getDoesNotHaveInviteMessage());
            return;
        }

        player.sendMessage(TeamfightPlugin.getInstance().getUninviteMessage());

        Collection<UUID>pendingInvites = new ArrayList<>(userTeam.getPendingInviteRequests());
        pendingInvites.remove(targetPlayerUUID);

        userTeam.setPendingInviteRequests(pendingInvites);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }

    public void joinTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS._EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN),

                ChatColourUtil.convert("&f/t join &7<teamName>"),
                2)) { return; }

        if (!TeamfightPlugin.getInstance().getTeamManager().teamExists(args[1])){
            player.sendMessage(TeamfightPlugin.getInstance().getNoTeamFoundMessage().replace("%udefined%", args[1]));
            return;
        }

        Team teamToBeJoined = TeamfightPlugin.getInstance().getTeamManager().getTeamByName(args[1]);
        Collection<UUID>pendingInvites = new ArrayList<>(teamToBeJoined.getPendingInviteRequests());

        if(!pendingInvites.contains(player.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getLocalDoesNotHaveInviteMessage());
            return;
        }

        pendingInvites.remove(player.getUniqueId());
        teamToBeJoined.addPlayer(player);

        teamToBeJoined.sendMessageToTeam(TeamfightPlugin.getInstance().getPlayerHasJoinedTeamMessage().replace("%player_name%", player.getName()));

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(teamToBeJoined);
    }

    public void kickPlayer(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER),

                ChatColourUtil.convert("&f/t kick &7<player>"),
                2)) { return; }

        UUID targetPlayerUUID = TeamfightPlugin.getInstance().getPlayerCache().getPlayerUUIDByName(args[1]);
        String targetPlayerName = TeamfightPlugin.getInstance().getPlayerCache().getPlayerNameByUUID(targetPlayerUUID);
        Player targetPlayer = Bukkit.getPlayer(targetPlayerUUID);

        if(targetPlayerUUID == null){
            player.sendMessage(TeamfightPlugin.getInstance().getOfflinePlayerMessage());
            return;

        } else if(userTeam.isInTeam(targetPlayerUUID)) {
            player.sendMessage(TeamfightPlugin.getInstance().getPlayerIsInTeamMessage());
            return;
        }

        userTeam.removePlayer(targetPlayerUUID);

        String teamKickMessage = TeamfightPlugin.getInstance().getPlayerHasBeenKickedMessage().replace("%player_name%", targetPlayerName);
        String localKickMessage = TeamfightPlugin.getInstance().getLocalPlayerHasBeenKickedMessage().replace("%team_name%", userTeam.getTeamName());

        if(targetPlayer != null){
            player.sendMessage(localKickMessage);
        }
        userTeam.sendMessageToTeam(teamKickMessage);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }

    public void leaveTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS._EXECUTOR_IS_LEADER),

                ChatColourUtil.convert(""),
                1)) { return; }

        userTeam.removePlayer(player.getUniqueId());

        String teamLeaveMessage = TeamfightPlugin.getInstance().getTeamPlayerHasLeftMessage().replace("%player_name", player.getName());

        player.sendMessage(TeamfightPlugin.getInstance().getLocalPlayerHasLeftMessage());
        userTeam.sendMessageToTeam(teamLeaveMessage);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }
}
