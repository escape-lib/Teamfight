package me.mnemosyne.teamfight.redis.schedule;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.log.Log;
import me.mnemosyne.teamfight.team.Team;
import me.mnemosyne.teamfight.util.ChatColourUtil;

import java.util.Collection;

public class SaveAsyncThread {
    public void execute(Collection<Team> teamList){
        TeamfightPlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(TeamfightPlugin.getInstance(), new Runnable() {

            @Override
            public void run() {
                Log.log(ChatColourUtil.convert("&eAttempting to save to redis database!"));
            }

        });
    }
}
