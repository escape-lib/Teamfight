package me.mnemosyne.teamfight.team;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.user.User;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

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

    private boolean isAlphaNumeric(String in) {
        return in != null && in.matches("^[a-zA-Z0-9]*$");
    }

    private String showTeam(Team team){
        String build = ChatColourUtil.convert(
                "\n\n" + TeamfightPlugin.getInstance().getChatSpacer() + "\n&7Team: &6" + team.getTeamName() + " &7[&a" + team.getPlayerCount() + " Online&7]\n" +
                        "&fLeader: &a" + team.getLeader().getName() + "\n");


        String captainsString = "&fCaptains: &a";
        for(UUID uuid : team.getCaptainList()){
            captainsString += Bukkit.getPlayer(uuid).getName() + "&7,&a";
        }
        if(captainsString.endsWith(",")){
            captainsString = captainsString.substring(0, captainsString.length() - 1);
        } else {
            captainsString += ChatColourUtil.convert("&7None");
        }


        String membersString = "&fMembers: &a";
        for(UUID uuid : team.getMemberList()){
            membersString += Bukkit.getPlayer(uuid).getName() + "&7,&a";
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
        if(args.length == 1) {
            player.sendMessage(ChatColourUtil.convert("&f/t create &7<teamName>"));
            return;

        } else if(!isAlphaNumeric(args[1])
                || args[1].length() > 16) {

            player.sendMessage(TeamfightPlugin.getInstance().getSuitableNameMessage());
            return;

        } else if(userTeam != null){
            player.sendMessage(TeamfightPlugin.getInstance().getAlreadyInTeamMessage());
            return;

        } else if (!user.isInSpawn()){
            player.sendMessage(TeamfightPlugin.getInstance().getInFightMessage());
            return;

        } else if (TeamfightPlugin.getInstance().getTeamManager().teamExists(args[1])){
            player.sendMessage(TeamfightPlugin.getInstance().getTeamExistsMessage().replace("%team_name", args[1]));
            return;
        }

        Team team = new Team(player);
        team.setTeamName(args[1]);
        TeamfightPlugin.getInstance().getTeamManager().addTeam(team);

        //player.sendMessage(ChatColourUtil.convert("&aSuccessfully created team &7" + team.getTeamName()));

        for(Player itPlayer : Bukkit.getOnlinePlayers()){
            itPlayer.sendMessage(ChatColourUtil.convert("&fTeam &6" + team.getTeamName() + " &fhas been created by &7" + player.getName()));
        }
    }

    public void disbandTeam(){
        if(userTeam == null){
            player.sendMessage(TeamfightPlugin.getInstance().getNotInTeamMessage());
            return;
        } else if (!user.isInSpawn() || userTeam.isFightInProgress()){
            player.sendMessage(TeamfightPlugin.getInstance().getInFightMessage());
            return;
        } else if (!userTeam.isLeader(player)){
            player.sendMessage(TeamfightPlugin.getInstance().getNotLeaderMessage());
            return;
        }

        TeamfightPlugin.getInstance().getTeamManager().removeTeam(userTeam);

        for(Player itPlayer : Bukkit.getOnlinePlayers()){
            itPlayer.sendMessage(ChatColourUtil.convert("&fTeam &6" + userTeam.getTeamName() + " &fhas been disbanded by &7" + player.getName()));
        }
    }

    public void showTeam(){
        if(args.length == 1){
            player.sendMessage(ChatColourUtil.convert("&f/t show &7<teamName|playerName>"));
            return;
        }

        Collection<Team>teamsToBeShown = TeamfightPlugin.getInstance().getTeamManager().getTeamsFromPlayerOrTeamName(args[1]);

        if(teamsToBeShown == null){
            String message = TeamfightPlugin.getInstance().getNoTeamFound();
            player.sendMessage(message.replace("%udefined%", args[1]));

            return;
        }

        for(Team itTeam : teamsToBeShown){
            player.sendMessage(this.showTeam(itTeam));
        }
    }

    public void invitePlayer(){
        if(args.length == 1){
            player.sendMessage(ChatColourUtil.convert("&f/t invite &7<player>"));
            return;

        } else if(userTeam == null){
            player.sendMessage(TeamfightPlugin.getInstance().getNotInTeamMessage());
            return;

        } else if (!user.isInSpawn()){
            player.sendMessage(TeamfightPlugin.getInstance().getInFightMessage());
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if(targetPlayer == null){
            player.sendMessage(TeamfightPlugin.getInstance().getOfflinePlayerMessage());
            return;
        }

        if(userTeam.getUUIDList().contains(targetPlayer.getUniqueId())){
            player.sendMessage(TeamfightPlugin.getInstance().getPlayerIsInTeamMessage());
            return;
        }

        String inviteMessage = TeamfightPlugin.getInstance().getInvitingMessage();

        inviteMessage = inviteMessage.replace("%inviting_player%", player.getName());
        inviteMessage = inviteMessage.replace("%team_name%", userTeam.getTeamName());

        targetPlayer.sendMessage(inviteMessage);
    }

    public void unInvitePlayer(){
        if(args.length == 1){
            player.sendMessage(ChatColourUtil.convert("&f/t uninvite &7<player>"));
            return;

        } else if(userTeam == null){
            player.sendMessage(TeamfightPlugin.getInstance().getNotInTeamMessage());
            return;

        } else if (!user.isInSpawn()){
            player.sendMessage(TeamfightPlugin.getInstance().getInFightMessage());
            return;
        }
    }

    public void kickPlayer(){
        if(args.length == 1){
            player.sendMessage(ChatColourUtil.convert("&f/t kick &7<player>"));
            return;

        } else if(userTeam == null){
            player.sendMessage(TeamfightPlugin.getInstance().getNotInTeamMessage());
            return;

        } else if (!user.isInSpawn()){
            player.sendMessage(TeamfightPlugin.getInstance().getInFightMessage());
            return;
        }
    }
}
