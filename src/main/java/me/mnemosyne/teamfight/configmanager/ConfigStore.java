package me.mnemosyne.teamfight.configmanager;

import lombok.Getter;
import lombok.Setter;

public class ConfigStore {
    @Getter @Setter private static String scoreboardTitle;
    @Getter @Setter private static String scoreboardSpacer;
    @Getter @Setter private static String spawnScoreboard;
    @Getter @Setter private static String spawnStaffScoreboard;

    @Getter @Setter private static String redisHost;
    @Getter @Setter private static int redisPort;
}
