package me.mnemosyne.teamfight.protect;

import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SwBlockProtectionListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent event){
        if(!(event.getPlayer() instanceof Player)){
            return;
        }

        Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
        if(player.isBlocking()){
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColourUtil.convert("&flook! &7" + player.getName() + " &ftried to use the sword block glitch! looks like they cant win a fight without cheating =)"));
            Bukkit.broadcastMessage("");

            player.kickPlayer(ChatColourUtil.convert("&cwhy u glitching? too shit to win fights without it? =)"));
        }
    }
}
