package me.mnemosyne.teamfight.listener;

import fr.mrmicky.fastboard.FastBoard;
import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.configmanager.ConfigStore;
import me.mnemosyne.teamfight.log.Log;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserJoinListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user;

        if(!TeamfightPlugin.getUserManager().userExists(player.getUniqueId())){
            user = new User(player);
            TeamfightPlugin.getUserManager().addUser(user);

            Log.log("&aAdded new user object with UUID: " + player.getUniqueId());
        } else {
            user = TeamfightPlugin.getUserManager().getUser(player.getUniqueId());
            user.setOnline(true);
            TeamfightPlugin.getUserManager().updateUser(user);

            Log.log("&cPlayer already has user object with UUID: " + player.getUniqueId());
            Log.log("&aSetting online status to true!");
        }

        FastBoard userBoard = new FastBoard(player);
        userBoard.updateTitle(ConfigStore.getScoreboardTitle());
        userBoard.updateLines(
                ConfigStore.getScoreboardSpacer(),
                ConfigStore.getScoreboardSpacer());

        user.setPlace(TeamfightPlugin.PLAYER_PLACE_FLAG.PLAYER_SPAWN);
        user.setUserFastboard(userBoard);
    }
}
