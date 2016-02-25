package me.plazmaz.bukkit.capegiver;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Dylan on 2/24/2016.
 */
public class CapeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("cape")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command must be executed as a player!");
                return true;
            }
            if(args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /cape <cape>");
                return true;
            }
            if(CapeObject.DEFAULT_CAPES.containsKey(args[0])) {
                CapeObject.PLAYER_CAPES.put(((Player) sender).getUniqueId(), CapeObject.DEFAULT_CAPES.get(args[0]));
                sender.sendMessage(ChatColor.GREEN + "Cape changed! Reconnect to see the changes!");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "The cape: " + args[0] + " is not supported.");
                sender.sendMessage(ChatColor.RED + "Supported capes:");
                final String[] capes = {""};
                CapeObject.DEFAULT_CAPES.keySet().stream().forEach((str) -> capes[0] += str + ", ");
                capes[0] = capes[0].substring(0, capes[0].length() - 1);
                sender.sendMessage(ChatColor.RED + capes[0]);
                return true;
            }

        }
        return false;
    }
}
