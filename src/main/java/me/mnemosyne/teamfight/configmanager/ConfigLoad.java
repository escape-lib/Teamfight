package me.mnemosyne.teamfight.configmanager;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.log.Log;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigLoad {
    private File messagesFile;
    private File redisFile;
    private File scoreboardFile;

    private FileConfiguration messagesConfig;
    private FileConfiguration redisConfig;
    private FileConfiguration scoreboardConfig;

    private final String successLogMsg = ChatColourUtil.convert("&aSuccessfully loaded config: %config_name%!");
    private final String failLogMsg    = ChatColourUtil.convert("&cCould not load config: %config_name%!");
    private final String creatingMsg   = ChatColourUtil.convert("&eCould not find %config_name%! Creating it now...");

    public void load(){
        TeamfightPlugin.getInstance().saveDefaultConfig();
        Log.log(successLogMsg.replace("%config_name%", "config.yml"));

        CustomConfigHolder messagesHolder = createCustomConfig(messagesFile, messagesConfig, "messages.yml", "/messages/");
        this.messagesFile = messagesHolder.getFile();
        this.messagesConfig = messagesHolder.getConfig();

        CustomConfigHolder redisHolder = createCustomConfig(redisFile, redisConfig, "redis.yml", "/database/");
        this.redisFile = redisHolder.getFile();
        this.redisConfig = redisHolder.getConfig();

        CustomConfigHolder scoreboardHolder = createCustomConfig(scoreboardFile, scoreboardConfig, "scoreboard.yml", "");
        this.scoreboardFile = scoreboardHolder.getFile();
        this.scoreboardConfig = scoreboardHolder.getConfig();

        ConfigStore.setRedisHost(redisConfig.getString("host"));
        ConfigStore.setRedisPort(redisConfig.getInt("port"));

        ConfigStore.setScoreboardTitle(ChatColourUtil.convert(scoreboardConfig.getString("scoreboard_title")));
        ConfigStore.setScoreboardSpacer(ChatColourUtil.convert(scoreboardConfig.getString("scoreboard_spacer")));
        ConfigStore.setSpawnScoreboard(ChatColourUtil.convert(scoreboardConfig.getString("spawn_scoreboard")));
        ConfigStore.setSpawnStaffScoreboard(ChatColourUtil.convert(scoreboardConfig.getString("spawn_staff_scoreboard")));

    }

    private CustomConfigHolder createCustomConfig(File fileConfig, FileConfiguration config, String resourceName, String directories) {
        CustomConfigHolder customConfigHolder = new CustomConfigHolder();
        boolean success = true;

        fileConfig = new File(TeamfightPlugin.getInstance().getDataFolder() + directories, resourceName);
        if (!fileConfig.exists()) {
            Log.log(creatingMsg.replace("%config_name%", resourceName));

            fileConfig.getParentFile().mkdirs();
            TeamfightPlugin.getInstance().saveResource(resourceName, false);

            if(!directories.isEmpty()){
                File actualYML = new File(TeamfightPlugin.getInstance().getDataFolder() + "/" + resourceName);
                actualYML.renameTo(new File(TeamfightPlugin.getInstance().getDataFolder() + directories + resourceName));
            }
        }

        config = new YamlConfiguration();

        try{
            config.load(fileConfig);
        } catch (IOException | InvalidConfigurationException e){
            success = false;
            e.printStackTrace();
        }

        customConfigHolder.setConfig(config);
        customConfigHolder.setFile(fileConfig);

        if(success){
            Log.log(successLogMsg.replace("%config_name%", resourceName));
        } else {
            Log.log(failLogMsg.replace("%config_name%", resourceName));
        }

        return customConfigHolder;
    }
}
