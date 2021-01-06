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

        user.setInSpawn(true);
        TeamfightPlugin.getUserManager().updateUser(user);
    }

    public void teleportTeamToGame(Arena arena){

    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
