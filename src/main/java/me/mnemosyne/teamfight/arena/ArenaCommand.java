package me.mnemosyne.teamfight.arena;

import me.mnemosyne.teamfight.TeamfightPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command from console!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(TeamfightPlugin.getInstance().getMessage().getTeamUsageMessage());

            return true;
        }

        args[0] = args[0].toLowerCase();
        String firstArg = args[0];



        return false;
    }
}
