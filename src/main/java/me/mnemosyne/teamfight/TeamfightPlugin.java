package me.mnemosyne.teamfight;

import lombok.Getter;
import me.mnemosyne.teamfight.cache.PlayerCache;
import me.mnemosyne.teamfight.cache.listener.JoinCacheListener;
import me.mnemosyne.teamfight.command.BuildCommand;
import me.mnemosyne.teamfight.configmanager.ConfigLoad;
import me.mnemosyne.teamfight.configmanager.ConfigStore;
import me.mnemosyne.teamfight.constant.GameInventory;
import me.mnemosyne.teamfight.constant.Message;
import me.mnemosyne.teamfight.listener.*;
import me.mnemosyne.teamfight.nametag.NametagHandler;
import me.mnemosyne.teamfight.scoreboard.ScoreboardManager;
import me.mnemosyne.teamfight.scoreboard.schedule.UpdateScoreboardSchedule;
import me.mnemosyne.teamfight.team.TeamCommand;
import me.mnemosyne.teamfight.team.TeamManager;
import me.mnemosyne.teamfight.team.listener.TeamPrefixChatListener;
import me.mnemosyne.teamfight.user.UserManager;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import me.mnemosyne.teamfight.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import redis.clients.jedis.JedisPool;

public class TeamfightPlugin extends JavaPlugin {
    @Getter private static TeamfightPlugin instance;
    @Getter private static UserManager userManager;

    @Getter private JedisPool jedisPool;
    @Getter private TeamManager teamManager;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private Scoreboard spawnScoreboard;
    @Getter private org.bukkit.scoreboard.Team spawnTeam;
    @Getter private GeneralUtil generalUtil;
    @Getter private NametagHandler nametagHandler;
    @Getter private PlayerCache playerCache;
    @Getter private Message message;
    @Getter private GameInventory gameInventory;

    @Override
    public void onEnable(){

        instance = this;
        userManager = new UserManager();

        teamManager = new TeamManager();
        generalUtil = new GeneralUtil();
        nametagHandler = new NametagHandler();
        scoreboardManager = new ScoreboardManager();
        spawnScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        jedisPool = new JedisPool(ConfigStore.getRedisHost(), ConfigStore.getRedisPort());
        playerCache = new PlayerCache();
        message = new Message();
        gameInventory = new GameInventory();

        new ConfigLoad().load();

        setupListeners();
        setupCommands();
        setupSchedulers();

        this.spawnTeam = spawnScoreboard.registerNewTeam("spawn");
        this.spawnTeam.setPrefix(ChatColourUtil.convert("&2"));
        this.spawnTeam.setAllowFriendlyFire(false);

        /*

        try{
            Jedis jedis = jedisPool.getResource();
            jedis.close();
            Log.log("&aSuccessfully connected to redis server! | HOST: " + ConfigStore.getRedisHost() + " | PORT: " + ConfigStore.getRedisPort());

        } catch (JedisConnectionException e){
            Log.log("&4Could not connect to redis server! | HOST: " + ConfigStore.getRedisHost() + " | PORT: " + ConfigStore.getRedisPort());
            this.getServer().shutdown();
        }

         */
    }

    private void setupListeners(){
        this.getServer().getPluginManager().registerEvents(new UserJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new UserLeaveListener(), this);
        this.getServer().getPluginManager().registerEvents(new InSpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new ScoreboardTeamJoinLeaveListener(), this);
        this.getServer().getPluginManager().registerEvents(new DebugChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new TeamPrefixChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinCacheListener(), this);
        this.getServer().getPluginManager().registerEvents(new UnacceptedCommandListener(), this);
    }

    private void setupCommands(){
        this.getCommand("build").setExecutor(new BuildCommand());
        this.getCommand("team").setExecutor(new TeamCommand());

    }

    private void setupSchedulers(){
        new UpdateScoreboardSchedule().execute();
    }
}
