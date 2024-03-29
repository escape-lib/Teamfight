package me.mnemosyne.teamfight.listener;

import me.mnemosyne.teamfight.TeamfightPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DebugChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        if(!event.getMessage().equalsIgnoreCase("debug")){
            return;
        }

        player.openInventory(TeamfightPlugin.getInstance().getGameInventory().getFightInventory(null, null));
        event.setCancelled(true);
    }
}
