package me.mnemosyne.teamfight;

import lombok.Getter;
import me.mnemosyne.teamfight.command.BuildCommand;
import me.mnemosyne.teamfight.configmanager.ConfigLoad;
import me.mnemosyne.teamfight.configmanager.ConfigStore;
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

    @Getter private String teamUsageMessage;
    @Getter private String chatSpacer;
    @Getter private String noPermissionMessage;
    @Getter private String offlinePlayerMessage;
    @Getter private String notInTeamMessage;
    @Getter private String suitableNameMessage;
    @Getter private String alreadyInTeamMessage;
    @Getter private String inFightMessage;
    @Getter private String notLeaderMessage;
    @Getter private String noTeamFoundMessage;
    @Getter private String invitingMessage;
    @Getter private String playerIsInTeamMessage;
    @Getter private String playerUninviteIsInTeamMessage;
    @Getter private String teamExistsMessage;
    @Getter private String isInvitingToTeamMessage;
    @Getter private String uninvitingTeamMessage;
    @Getter private String uninviteMessage;
    @Getter private String alreadyInvitedPlayerMessage;
    @Getter private String doesNotHaveInviteMessage;

    @Getter private JedisPool jedisPool;
    @Getter private TeamManager teamManager;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private Scoreboard spawnScoreboard;
    @Getter private org.bukkit.scoreboard.Team spawnTeam;
    @Getter private GeneralUtil generalUtil;
    @Getter private NametagHandler nametagHandler;

    @Override
    public void onEnable(){

        instance = this;
        userManager = new UserManager();

        chatSpacer = ChatColourUtil.convert("&7&m--------------------------------");
        teamUsageMessage = ChatColourUtil.convert(
                "\n\n" + chatSpacer + "\n&7&l-Team Commands-\n\n" +
                        "&f/t create &7<teamName>\n" +
                        "&f/t show &7<teamName|playerName>\n" +
                        "&f/t join &7<teamName>\n" +
                        "&f/t invite &7<player>\n" +
                        "&f/t uninvite &7<player>\n" +
                        "&f/t kick &7<player>\n" +
                        "&f/t disband\n"
                        + chatSpacer);

        noPermissionMessage = ChatColourUtil.convert("&cYou do not have permission to execute this command.");
        offlinePlayerMessage = ChatColourUtil.convert("&cThat player is offline!");
        notInTeamMessage = ChatColourUtil.convert("&7You are not in a team!");
        suitableNameMessage = ChatColourUtil.convert("&cTeam name must be alphanumeric and between 3 and 16 characters in length!");
        alreadyInTeamMessage = ChatColourUtil.convert("&cYou are already in a team!");
        inFightMessage = ChatColourUtil.convert("&cYou cannot do this while in a fight!");
        notLeaderMessage = ChatColourUtil.convert("&cYou are not the leader of this team!");
        noTeamFoundMessage = ChatColourUtil.convert("&cNo matching team or member with name %udefined%");
        invitingMessage = ChatColourUtil.convert("&7%inviting_player% &fis inviting you to join the team &6%team_name%&f. Please type &7/t join %team_name% &fto join the team");
        playerIsInTeamMessage = ChatColourUtil.convert("&cYou cannot invite players that are in your team!");
        playerUninviteIsInTeamMessage = ChatColourUtil.convert("&cYou cannot uninvite players that are in your team!");
        teamExistsMessage = ChatColourUtil.convert("&cTeam with name %team_name% already exists!");
        isInvitingToTeamMessage = ChatColourUtil.convert("&7%inviting_player% &fhas invited &7%invited_player% &fto join the team");
        uninvitingTeamMessage = ChatColourUtil.convert("&7%inviting_player% &fhas uninvited &7%invited_player%");
        uninviteMessage = ChatColourUtil.convert("&fYou have been uninvited from joining the team &7%team_name%");
        alreadyInvitedPlayerMessage = ChatColourUtil.convert("&cThis player has already been invited to the team!");
        doesNotHaveInviteMessage = ChatColourUtil.convert("&cThis player does not have an invite to the team!");


        teamManager = new TeamManager();
        generalUtil = new GeneralUtil();
        nametagHandler = new NametagHandler();
        scoreboardManager = new ScoreboardManager();
        spawnScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        jedisPool = new JedisPool(ConfigStore.getRedisHost(), ConfigStore.getRedisPort());

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

    }

    private void setupCommands(){
        this.getCommand("build").setExecutor(new BuildCommand());
        this.getCommand("team").setExecutor(new TeamCommand());

    }

    private void setupSchedulers(){
        new UpdateScoreboardSchedule().execute();
    }
}
