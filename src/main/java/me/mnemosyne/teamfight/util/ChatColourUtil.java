package me.mnemosyne.teamfight.util;

import org.bukkit.ChatColor;

public class ChatColourUtil {
    public static String convert(String in){
        return ChatColor.translateAlternateColorCodes('&', in);
    }
}
