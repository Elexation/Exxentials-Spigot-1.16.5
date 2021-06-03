package me.elexation.exxentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class delwarp implements CommandExecutor {

    private final JavaPlugin plugin;

    public delwarp(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.delwarp")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1)
            return false;
        String warpLocationPath = "settings.worlds." + player.getWorld().getUID() + ".warpLocations."
                + args[0];
        String warpNamePath = "settings.worlds." + player.getWorld().getUID() + ".warpNames";
        Location loc = plugin.getConfig().getLocation(warpLocationPath);
        List<String> warpNames = plugin.getConfig().getStringList(warpNamePath);
        if (loc == null || !loc.isWorldLoaded() || !warpNames.contains(args[0])) {
            plugin.getConfig().set(warpLocationPath, null);
            plugin.saveConfig();
            player.sendMessage(ChatColor.DARK_RED + "Warp does not exist");
            return true;
        }
        warpNames.removeAll(Collections.singletonList(args[0]));
        plugin.getConfig().set(warpLocationPath, null);
        plugin.getConfig().set(warpNamePath, warpNames);
        plugin.saveConfig();
        player.sendMessage(
                ChatColor.translateAlternateColorCodes('&', String.format("&6warp &4%s &6has been deleted", args[0])));
        return true;
    }
}
