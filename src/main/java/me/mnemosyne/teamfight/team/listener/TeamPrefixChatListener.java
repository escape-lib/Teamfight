package me.mnemosyne.teamfight.team.listener;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.team.Team;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class TeamPrefixChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Team team = TeamfightPlugin.getInstance().getTeamManager().getTeam(player);

        if(team == null){
            for(Player itPlayer : Bukkit.getOnlinePlayers()){
                itPlayer.sendMessage(ChatColourUtil.convert(player.getDisplayName() + "&7: &f" + event.getMessage()));
            }

            event.setCancelled(true);
            return;
        }

        Collection<? extends Player>playersNotInChatterTeam = new ArrayList<>(Bukkit.getOnlinePlayers());

        playersNotInChatterTeam.removeAll(team.getPlayerList());

        for(Player itPlayer : playersNotInChatterTeam){
            itPlayer.sendMessage(ChatColourUtil.convert("&7[&6" + team.getTeamName() + "&7]&f" + player.getDisplayName() + "&7: &f" + event.getMessage()));
        }

        team.sendMessageToTeam(ChatColourUtil.convert("&7[&2" + team.getTeamName() + "&7]&f" + player.getDisplayName() + "&7: &f" + event.getMessage()));

        event.setCancelled(true);
    }
}
