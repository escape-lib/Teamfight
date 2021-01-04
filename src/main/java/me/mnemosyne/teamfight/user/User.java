package me.mnemosyne.teamfight.user;

import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID playerUUID;

    private boolean isOnline;
    private boolean isInSpawn;
    private boolean isTagged;
    private boolean inBuildMode;

    private Scoreboard teamScoreboard;
    private FastBoard userFastboard;

    public User(Player player){
        this.playerUUID = player.getUniqueId();

        this.teamScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.isOnline = true;
        this.isInSpawn = true;
    }
}
