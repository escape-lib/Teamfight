package me.mnemosyne.teamfight.arena;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.team.Team;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.entity.Player;

public class ArenaSubcommandHandler {
    private final Player player;
    private final String[] args;
    private Team userTeam;
    private User user;

    public ArenaSubcommandHandler(Player player, String[] args){
        this.player = player;
        this.args = args;

        this.userTeam = TeamfightPlugin.getInstance().getTeamManager().getTeam(player);
        this.user = TeamfightPlugin.getUserManager().getUser(player);
    }
}
