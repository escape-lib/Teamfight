package me.mnemosyne.teamfight.scoreboard.schedule;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.scoreboard.ScoreboardManager;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdateScoreboardSchedule {
    public void execute(){

        TeamfightPlugin.getInstance().getServer().getScheduler().runTaskTimer(TeamfightPlugin.getInstance(), () ->{
            ScoreboardManager scoreboardManager = TeamfightPlugin.getInstance().getScoreboardManager();

            for(Player player : Bukkit.getOnlinePlayers()) {
                User user = TeamfightPlugin.getUserManager().getUser(player);
                scoreboardManager.updateUserScoreboard(scoreboardManager.getUserScoreboard(user), user);
            }

        },0L, 10);
    }
}
