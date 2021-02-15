package me.mnemosyne.teamfight.util;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.arena.Arena;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;

public class GeneralUtil {
    public void teleportToSpawn(Player player){
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        User user = TeamfightPlugin.getUserManager().getUser(player);

        user.setPlace(TeamfightPlugin.PLAYER_PLACE_FLAG.PLAYER_SPAWN);
        TeamfightPlugin.getUserManager().updateUser(user);
    }

    public void teleportTeamToGame(Arena arena){

    }

}
