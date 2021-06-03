package me.elexation.exxentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class exreload implements CommandExecutor {

    JavaPlugin plugin;

    public exreload(JavaPlugin plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Exxentials reloaded");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.reload")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        plugin.reloadConfig();
        player.sendMessage(ChatColor.GREEN + "Exxentials reloaded");
        return true;
    }
}
