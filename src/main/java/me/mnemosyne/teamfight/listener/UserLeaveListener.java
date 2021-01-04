package me.mnemosyne.teamfight.listener;

import fr.mrmicky.fastboard.FastBoard;
import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.log.Log;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserLeaveListener implements Listener {
    private void manage(Player player){
        if(TeamfightPlugin.getUserManager().userExists(player.getUniqueId())){

            User user = TeamfightPlugin.getUserManager().getUser(player.getUniqueId());
            user.setOnline(false);

            TeamfightPlugin.getUserManager().updateUser(user);
            Log.log("&eUser has left! Setting online status to false with UUID: " + player.getUniqueId());

            FastBoard userBoard = user.getUserFastboard();
            userBoard.delete();

            user.setUserFastboard((FastBoard) null);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        manage(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event){
        manage(event.getPlayer());
    }
}
