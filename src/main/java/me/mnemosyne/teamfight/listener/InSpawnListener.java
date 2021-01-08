package me.mnemosyne.teamfight.listener;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class InSpawnListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        User user = TeamfightPlugin.getUserManager().getUser((Player)event.getEntity());

        if(user.getPlace() == TeamfightPlugin.PLAYER_PLACE_FLAG.PLAYER_SPAWN) { event.setCancelled(true); }
    }

    @EventHandler
    public void onHungerDecrease(FoodLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        User user = TeamfightPlugin.getUserManager().getUser((Player)event.getEntity());

        if(user.getPlace() == TeamfightPlugin.PLAYER_PLACE_FLAG.PLAYER_SPAWN) { event.setCancelled(true); }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        User user = TeamfightPlugin.getUserManager().getUser(player);

        if(!user.isInBuildMode()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        User user = TeamfightPlugin.getUserManager().getUser(player);

        if(!user.isInBuildMode()){
            event.setCancelled(true);
        }
    }
}
