package me.mnemosyne.teamfight.team;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.user.User;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamSubcommandHandler {
    private final Player player;
    private final String[] args;
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
    protected enum PLAYER_CHECK_FLAGS{
        EXECUTOR_IN_TEAM,
        _EXECUTOR_IN_TEAM,

        EXECUTOR_IS_LEADER,
        _EXECUTOR_IS_LEADER,

        EXECUTOR_IN_SPAWN,
        _EXECUTOR_IN_SPAWN,

        TEAM_IN_FIGHT,
        _TEAM_IN_FIGHT
    }

    private boolean checkPlayer(Collection<PLAYER_CHECK_FLAGS>flags, String usage, int requiredArgsCount) {
        if (args.length < requiredArgsCount) {
            player.sendMessage(usage);
            return true;
        }

        for (PLAYER_CHECK_FLAGS itFlag : flags) {
            if (itFlag.equals(PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM) && userTeam == null) {
                player.sendMessage(TeamfightPlugin.getInstance().getMessage().getNotInTeamMessage());
                return true;

            } else if (itFlag.equals(PLAYER_CHECK_FLAGS._EXECUTOR_IN_TEAM) && userTeam != null) {
                player.sendMessage(TeamfightPlugin.getInstance().getMessage().getAlreadyInTeamMessage());
                return true;
            }


            if(itFlag.equals(PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER) && userTeam != null && !userTeam.isLeader(player.getUniqueId())){
                player.sendMessage(TeamfightPlugin.getInstance().getMessage().getNotLeaderMessage());
                return true;

            } else if (itFlag.equals(PLAYER_CHECK_FLAGS._EXECUTOR_IS_LEADER) && userTeam != null && userTeam.isLeader(player.getUniqueId())){
                player.sendMessage(TeamfightPlugin.getInstance().getMessage().getCannotDoThisAsLeaderMessage());
                return true;
            }


            if(itFlag.equals(PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN) && !(user.getPlace() == TeamfightPlugin.PLAYER_PLACE_FLAG.PLAYER_SPAWN)){
                player.sendMessage(TeamfightPlugin.getInstance().getMessage().getInFightMessage());
                return true;
            }

            if(itFlag.equals(PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT) && userTeam != null && userTeam.isFightInProgress()){
                player.sendMessage(TeamfightPlugin.getInstance().getMessage().getCannotDoThisWhileTeamInFightMessage());
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
        Collection<UUID> captainsUUIDs = team.getCaptainsUUIDs();
        Collection<UUID> membersUUIDs = team.getMembersUUIDs();


        String build = ChatColourUtil.convert(
                "\n\n" + TeamfightPlugin.getInstance().getMessage().getChatSpacer() + "\n&7Team: &6" + team.getTeamName() + " &7[&a" + team.getOnlinePlayerCount() + " Online&7]\n" +
                        "&fLeader: &a" + teamLeaderName + "\n");

        String captainsString = "&fCaptains: &a";
        if(captainsUUIDs == null || captainsUUIDs.isEmpty()){
            captainsString += ChatColourUtil.convert("&7None");
        } else {
            Player lastPlayer = null;

            for(UUID uuid : captainsUUIDs){

                String itPlayerName = TeamfightPlugin.getInstance().getPlayerCache().getPlayerNameByUUID(uuid);
                Player itPlayer = Bukkit.getPlayer(uuid);

                if(itPlayer == null){
                    captainsString += "&7" + itPlayerName + ",";
                } else {
                    captainsString += "&a" + itPlayerName + "&7,";
                }

                lastPlayer = itPlayer;
            }

            if(lastPlayer == null){
                captainsString = captainsString.substring(0, captainsString.length() - 1);
            } else {
                captainsString = captainsString.substring(0, captainsString.length() - 3);
            }
        }


        String membersString = "&fMembers: &a";
        if(membersUUIDs == null || membersUUIDs.isEmpty()){
            membersString += ChatColourUtil.convert("&7None");
        } else {
            Player lastPlayer = null;

            for(UUID uuid : membersUUIDs){

                String itPlayerName = TeamfightPlugin.getInstance().getPlayerCache().getPlayerNameByUUID(uuid);
                Player itPlayer = Bukkit.getPlayer(uuid);

                if(itPlayer == null){
                    membersString += "&7" + itPlayerName + ",";
                } else {
                    membersString += "&a" + itPlayerName + "&7,";
                }

                lastPlayer = itPlayer;
            }

            if(lastPlayer == null){
                membersString = membersString.substring(0, membersString.length() - 1);
            } else {
                membersString = membersString.substring(0, membersString.length() - 3);
            }
        }

        build += ChatColourUtil.convert(
                captainsString + "\n" +
                membersString + "\n" +
                "&fWins: " + team.getWins() + "\n" +
                        "&fLosses: " + team.getLosses() + "\n" + TeamfightPlugin.getInstance().getMessage().getChatSpacer());

        return build;
    }

    public String showTeamList(){
        String build = "\n\n" + TeamfightPlugin.getInstance().getMessage().getChatSpacer() + "\n&7&l-Team List-\n \n";

        Collection<Team> teamList = TeamfightPlugin.getInstance().getTeamManager().getTeamList();

        if(teamList != null && teamList.size() > 0){
            for(Team itTeam : TeamfightPlugin.getInstance().getTeamManager().getTeamList()){
                build += "&6" + itTeam.getTeamName() + "&7: [";

                if(itTeam.getOnlinePlayerCount() == 0){
                    build += itTeam.getOnlinePlayerCount() + " Online]\n";
                } else {
                    build += "&a" + itTeam.getOnlinePlayerCount() + " Online&7]\n";
                }
            }
        }
        build += TeamfightPlugin.getInstance().getMessage().getChatSpacer();

        return ChatColourUtil.convert(build);
    }

    public void createTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS._EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

                ChatColourUtil.convert("&f/t create &7<teamName>"),
                2)) { return; }

        if(!isAlphaNumeric(args[1])
                || args[1].length() > 16
                || args[1].length() < 3) {

            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getSuitableNameMessage());
            return;

        } else if (TeamfightPlugin.getInstance().getTeamManager().teamExists(args[1])){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getTeamExistsMessage().replace("%team_name%", args[1]));
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
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

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
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getNoTeamFoundMessage().replace("%udefined%", args[1]));

            return;
        }

        for(Team itTeam : teamsToBeShown){
            player.sendMessage(this.showTeam(itTeam));
        }
    }

    public void invitePlayer(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

                ChatColourUtil.convert("&f/t invite &7<player>"),
                2)) { return; }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if(targetPlayer == null){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getOfflinePlayerMessage());
            return;
        }

        if(userTeam.isInTeam(targetPlayer.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getPlayerInviteIsInTeamMessage());
            return;

        } else if (userTeam.getPendingInviteRequests().contains(targetPlayer.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getAlreadyInvitedPlayerMessage());
            return;
        }

        String inviteMessage = TeamfightPlugin.getInstance().getMessage().getInvitingMessage();
        String isInvitingToTeamMessage = TeamfightPlugin.getInstance().getMessage().getIsInvitingToTeamMessage();

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
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

                ChatColourUtil.convert("&f/t uninvite &7<player>"),
                2)) { return; }

        UUID targetPlayerUUID = TeamfightPlugin.getInstance().getPlayerCache().getPlayerUUIDByName(args[1]);

        if(userTeam.isInTeam(targetPlayerUUID)){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getPlayerUninviteIsInTeamMessage());
            return;

        } else if (!userTeam.getPendingInviteRequests().contains(targetPlayerUUID)){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getDoesNotHaveInviteMessage());
            return;
        }

        player.sendMessage(TeamfightPlugin.getInstance().getMessage().getUninviteMessage());

        Collection<UUID>pendingInvites = new ArrayList<>(userTeam.getPendingInviteRequests());
        pendingInvites.remove(targetPlayerUUID);

        userTeam.setPendingInviteRequests(pendingInvites);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }

    public void joinTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS._EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

                ChatColourUtil.convert("&f/t join &7<teamName>"),
                2)) { return; }

        if (!TeamfightPlugin.getInstance().getTeamManager().teamExists(args[1])){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getNoTeamFoundMessage().replace("%udefined%", args[1]));
            return;
        }

        Team teamToBeJoined = TeamfightPlugin.getInstance().getTeamManager().getTeamByName(args[1]);
        Collection<UUID>pendingInvites = new ArrayList<>(teamToBeJoined.getPendingInviteRequests());

        if(!pendingInvites.contains(player.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getLocalDoesNotHaveInviteMessage());
            return;
        }

        pendingInvites.remove(player.getUniqueId());
        teamToBeJoined.addPlayer(player);

        teamToBeJoined.setPendingInviteRequests(pendingInvites);

        teamToBeJoined.sendMessageToTeam(TeamfightPlugin.getInstance().getMessage().getPlayerHasJoinedTeamMessage().replace("%player_name%", player.getName()));

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(teamToBeJoined);
    }

    public void kickPlayer(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS.EXECUTOR_IS_LEADER, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

                ChatColourUtil.convert("&f/t kick &7<player>"),
                2)) { return; }

        UUID targetPlayerUUID = TeamfightPlugin.getInstance().getPlayerCache().getPlayerUUIDByName(args[1]);
        String targetPlayerName = TeamfightPlugin.getInstance().getPlayerCache().getPlayerNameByUUID(targetPlayerUUID);
        Player targetPlayer = Bukkit.getPlayer(targetPlayerUUID);

        if(targetPlayerUUID == null){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getOfflinePlayerMessage());
            return;

        } else if(!userTeam.isInTeam(targetPlayerUUID)) {
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getPlayerIsNotInYourTeamMessage());
            return;

        } else if (targetPlayerUUID.equals(player.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getCannotDoThisToYourselfMessage());
            return;

        }

        userTeam.removePlayer(targetPlayerUUID);

        String teamKickMessage = TeamfightPlugin.getInstance().getMessage().getPlayerHasBeenKickedMessage().replace("%player_name%", targetPlayerName);
        String localKickMessage = TeamfightPlugin.getInstance().getMessage().getLocalPlayerHasBeenKickedMessage().replace("%team_name%", userTeam.getTeamName());

        if(targetPlayer != null){
            targetPlayer.sendMessage(localKickMessage);
        }
        userTeam.sendMessageToTeam(teamKickMessage);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }

    public void leaveTeam(){
        if(checkPlayer(Arrays.asList(
                PLAYER_CHECK_FLAGS.EXECUTOR_IN_TEAM, PLAYER_CHECK_FLAGS.EXECUTOR_IN_SPAWN, PLAYER_CHECK_FLAGS._EXECUTOR_IS_LEADER, PLAYER_CHECK_FLAGS._TEAM_IN_FIGHT),

                ChatColourUtil.convert(""),
                1)) { return; }

        userTeam.removePlayer(player.getUniqueId());

        String teamLeaveMessage = TeamfightPlugin.getInstance().getMessage().getTeamPlayerHasLeftMessage().replace("%player_name%", player.getName());

        player.sendMessage(TeamfightPlugin.getInstance().getMessage().getLocalPlayerHasLeftMessage());
        userTeam.sendMessageToTeam(teamLeaveMessage);

        TeamfightPlugin.getInstance().getTeamManager().updateTeam(userTeam);
    }

    public void listTeams(){
        player.sendMessage(this.showTeamList());
    }
}
