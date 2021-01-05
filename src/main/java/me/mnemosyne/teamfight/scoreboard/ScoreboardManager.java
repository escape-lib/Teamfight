package me.mnemosyne.teamfight.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.configmanager.ConfigStore;
import me.mnemosyne.teamfight.user.User;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

public class ScoreboardManager {
    public Collection<String> getUserScoreboard(User user){
        String format = "";

        if(user.isInSpawn()){
            if(Bukkit.getPlayer(user.getPlayerUUID()).hasPermission("teamfight.staff")){
                format = ConfigStore.getSpawnStaffScoreboard();
            } else {
                format = ConfigStore.getSpawnScoreboard();
            }


            /*
            DateFormat dateFormat = new SimpleDateFormat("h:mm:ss a");
            Date today = Calendar.getInstance().getTime();
            String dateStr = dateFormat.format(today);
             */

            double tps = Bukkit.getServer().spigot().getTPS()[0];
            if(tps > 20) { tps = 20.0; }

            double tpsPrecision = BigDecimal.valueOf(tps)
                    .setScale(1, RoundingMode.HALF_EVEN)
                    .doubleValue();

            format = format.replace("%scoreboard_spacer%", ConfigStore.getScoreboardSpacer());
            format = format.replace("%online_players%", Integer.toString(Bukkit.getOnlinePlayers().size()));
            format = format.replace("%teams_online%", Integer.toString(TeamfightPlugin.getInstance().getTeamManager().getTeamList().size()));
            format = format.replace("%tps%", Double.toString(tpsPrecision));

        }

        return Arrays.asList(format.split("\n"));
    }

    public void updateUserScoreboard(Collection<String>scoreboardData, User user){
        FastBoard userBoard = user.getUserFastboard();
        userBoard.updateLines(scoreboardData);
        user.setUserFastboard(userBoard);
    }
}
