package me.mnemosyne.teamfight.log;

import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;

public class Log {
    public static void log(String msg){
        Bukkit.getConsoleSender().sendMessage(ChatColourUtil.convert("[Teamfight] " + msg));
    }
}
