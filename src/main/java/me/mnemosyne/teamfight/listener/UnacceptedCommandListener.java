package me.mnemosyne.teamfight.listener;

import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class UnacceptedCommandListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        String command = event.getMessage();
        Player player = event.getPlayer();

        if(command.contains(":")){
            event.setCancelled(true);
            player.sendMessage(ChatColourUtil.convert("&cThis command syntax is disallowed."));
        } else if (command.startsWith("/me")){
            event.setCancelled(true);
            player.sendMessage(ChatColourUtil.convert("&cnah"));
        }
    }
}
