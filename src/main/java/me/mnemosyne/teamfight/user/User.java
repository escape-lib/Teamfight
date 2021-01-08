package me.mnemosyne.teamfight.user;

import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import lombok.Setter;
import me.mnemosyne.teamfight.TeamfightPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID playerUUID;

    private boolean isOnline;
    private TeamfightPlugin.PLAYER_PLACE_FLAG place;
    private boolean isTagged;
    private boolean inBuildMode;

    private Scoreboard teamScoreboard;
    private FastBoard userFastboard;

    public User(Player player){
        this.playerUUID = player.getUniqueId();

        this.teamScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.isOnline = true;
        this.place = TeamfightPlugin.PLAYER_PLACE_FLAG.PLAYER_SPAWN;
    }
}
