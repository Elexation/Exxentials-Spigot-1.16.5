package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sudo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){

        }
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.sudo")){
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        if (args.length < 2) return false;
        Player target = Bukkit.getPlayer(args[0]);
        if (!target.getName().equals(args[0])){
            player.sendMessage(ChatColor.DARK_RED + "Player not found");
            return true;
        }
        target.performCommand(getCommand(args));
        return true;
    }

    private String getCommand(String[] args){
        StringBuilder command = new StringBuilder();
        for (int i=0; i< args.length; i++){
            if (i==0) continue;
            command.append(args[i] + " ");
        }
        return command.toString();
    }
}
