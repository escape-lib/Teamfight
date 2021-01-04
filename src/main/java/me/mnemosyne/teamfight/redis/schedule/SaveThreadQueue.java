package me.mnemosyne.teamfight.redis.schedule;

import me.mnemosyne.teamfight.TeamfightPlugin;

public class SaveThreadQueue {
    public void executeSchedule(){
        TeamfightPlugin.getInstance().getServer().getScheduler().runTaskTimer(TeamfightPlugin.getInstance(), () ->{

            SaveAsyncThread saveAsyncThread = new SaveAsyncThread();
            saveAsyncThread.execute(TeamfightPlugin.getInstance().getTeamManager().getTeamList());

        },0L, 1200L);
    }
}
