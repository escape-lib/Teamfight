package me.mnemosyne.teamfight.command;

import me.mnemosyne.teamfight.TeamfightPlugin;
import me.mnemosyne.teamfight.user.User;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command from console!");
        }

        Player player = (Player) sender;

        if (!player.hasPermission("teamfight.build")) {
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getNoPermissionMessage());
            return true;
        }

        User user = TeamfightPlugin.getUserManager().getUser(player);

        if(user.isInBuildMode()){
            user.setInBuildMode(false);
            player.sendMessage(ChatColourUtil.convert("&7Build mode has been &cdisabled"));

        } else {
            user.setInBuildMode(true);
            player.sendMessage(ChatColourUtil.convert("&7Build mode has been &aenabled"));
        }

        TeamfightPlugin.getUserManager().updateUser(user);

        return true;
    }
}
