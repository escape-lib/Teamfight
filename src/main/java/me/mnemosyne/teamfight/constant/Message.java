package me.mnemosyne.teamfight.constant;

import lombok.Getter;
import me.mnemosyne.teamfight.util.ChatColourUtil;

@Getter
public class Message {
     private final String teamUsageMessage;
     private final String chatSpacer;
     private final String noPermissionMessage;
     private final String offlinePlayerMessage;
     private final String notInTeamMessage;
     private final String suitableNameMessage;
     private final String alreadyInTeamMessage;
     private final String inFightMessage;
     private final String notLeaderMessage;
     private final String noTeamFoundMessage;
     private final String invitingMessage;
     private final String playerIsInTeamMessage;
     private final String playerUninviteIsInTeamMessage;
     private final String teamExistsMessage;
     private final String isInvitingToTeamMessage;
     private final String uninviteMessage;
     private final String alreadyInvitedPlayerMessage;
     private final String doesNotHaveInviteMessage;
     private final String localDoesNotHaveInviteMessage;
     private final String playerHasJoinedTeamMessage;
     private final String playerHasBeenKickedMessage;
     private final String localPlayerHasBeenKickedMessage;
     private final String teamPlayerHasLeftMessage;
     private final String localPlayerHasLeftMessage;
     private final String cannotDoThisAsLeaderMessage;

    public Message(){
        chatSpacer = ChatColourUtil.convert("&7&m--------------------------------");
        teamUsageMessage = ChatColourUtil.convert(
                "\n\n" + chatSpacer + "\n&7&l-Team Commands-\n\n" +
                        "&f/t create &7<teamName>\n" +
                        "&f/t show &7<teamName|playerName>\n" +
                        "&f/t join &7<teamName>\n" +
                        "&f/t invite &7<player>\n" +
                        "&f/t uninvite &7<player>\n" +
                        "&f/t kick &7<player>\n" +
                        "&f/t leave\n" +
                        "&f/t disband\n"
                        + chatSpacer);

        noPermissionMessage = ChatColourUtil.convert("&cYou do not have permission to execute this command.");
        offlinePlayerMessage = ChatColourUtil.convert("&cThat player is offline!");
        notInTeamMessage = ChatColourUtil.convert("&7You are not in a team!");
        suitableNameMessage = ChatColourUtil.convert("&cTeam name must be alphanumeric and between 3 and 16 characters in length!");
        alreadyInTeamMessage = ChatColourUtil.convert("&cYou are already in a team!");
        inFightMessage = ChatColourUtil.convert("&cYou cannot do this while in a fight!");
        notLeaderMessage = ChatColourUtil.convert("&cYou are not the leader of this team!");
        noTeamFoundMessage = ChatColourUtil.convert("&cNo matching team or member with name %udefined%");
        invitingMessage = ChatColourUtil.convert("&7%inviting_player% &fis inviting you to join the team &6%team_name%&f. Please type &7/t join %team_name% &fto join the team");
        playerIsInTeamMessage = ChatColourUtil.convert("&cYou cannot invite players that are in your team!");
        playerUninviteIsInTeamMessage = ChatColourUtil.convert("&cYou cannot uninvite players that are in your team!");
        teamExistsMessage = ChatColourUtil.convert("&cTeam with name %team_name% already exists!");
        isInvitingToTeamMessage = ChatColourUtil.convert("&7%inviting_player% &fhas invited &7%invited_player% &fto join the team");
        uninviteMessage = ChatColourUtil.convert("&fYou have successfully uninvited this player.");
        alreadyInvitedPlayerMessage = ChatColourUtil.convert("&cThis player has already been invited to the team!");
        doesNotHaveInviteMessage = ChatColourUtil.convert("&cThis player does not have an invite to the team!");
        localDoesNotHaveInviteMessage = ChatColourUtil.convert("&cYou do not have an invite to this team!");
        playerHasJoinedTeamMessage = ChatColourUtil.convert("&7%player_name% &fhas joined the team!");
        playerHasBeenKickedMessage = ChatColourUtil.convert("&7%player_name% &fhas been kicked from the team!");
        localPlayerHasBeenKickedMessage = ChatColourUtil.convert("&fYou have been kicked from the team &7%team_name%");
        cannotDoThisAsLeaderMessage = ChatColourUtil.convert("&cYou cannot do this while you are leader!");
        localPlayerHasLeftMessage = ChatColourUtil.convert("&fYou have left the team");
        teamPlayerHasLeftMessage = ChatColourUtil.convert("&7%player_name% &fhas left the team");
    }
}
