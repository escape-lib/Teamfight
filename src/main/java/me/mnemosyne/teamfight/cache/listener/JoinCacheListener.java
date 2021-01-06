package me.mnemosyne.teamfight.cache.listener;

import me.mnemosyne.teamfight.TeamfightPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinCacheListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        /*add player method already has exist checking*/
        TeamfightPlugin.getInstance().getPlayerCache().addPlayerToCache(event.getPlayer());
    }
}
