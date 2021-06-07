package me.elexation.exxentials.commands;

import me.elexation.exxentials.Exxentials;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.setspawn")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        World world = player.getWorld();
        Location loc = player.getLocation();
        Exxentials.plugin.getConfig().set("settings.worlds." + world.getUID() + ".spawn", loc);
        world.setSpawnLocation((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
        Exxentials.plugin.saveConfig();
        player.sendMessage(ChatColor.GOLD + "Spawnpoint set");
        return true;
    }
}
