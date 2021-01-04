package me.mnemosyne.teamfight.util;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.arena.Arena;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GeneralUtil {
    public void teleportToSpawn(Player player){
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        User user = TeamfightPlugin.getUserManager().getUser(player);

        user.setInSpawn(true);
        TeamfightPlugin.getUserManager().updateUser(user);
    }

    public void teleportTeamToGame(Arena arena){

    }
}
