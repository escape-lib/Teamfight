package me.mnemosyne.teamfight.listener;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardTeamJoinLeaveListener implements Listener {
    private void handleOffline(Player player){
        TeamfightPlugin.getInstance().getSpawnTeam().removeEntry(player.getName());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = TeamfightPlugin.getUserManager().getUser(player);
        TeamfightPlugin.getInstance().getSpawnTeam().addEntry(player.getName());

        player.setScoreboard(TeamfightPlugin.getInstance().getSpawnScoreboard());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        handleOffline(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerKickEvent event){
        handleOffline(event.getPlayer());
    }
}
