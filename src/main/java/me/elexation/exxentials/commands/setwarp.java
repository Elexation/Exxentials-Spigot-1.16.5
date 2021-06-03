package me.elexation.exxentials.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class setwarp implements CommandExecutor {

	private final JavaPlugin plugin;

	public setwarp(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("exxentials.setwarp")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (args.length < 1)
			return false;
		String warpLocationPath = "settings.worlds." + player.getWorld().getUID().toString() + ".warpLocations."
				+ args[0];
		String warpNamePath = "settings.worlds." + player.getWorld().getUID().toString() + ".warpNames";
		Location loc = plugin.getConfig().getLocation(warpLocationPath);
		List<String> warpNames = plugin.getConfig().getStringList(warpNamePath);
		if (loc != null && warpNames.contains(args[0])) {
			player.sendMessage(ChatColor.DARK_RED + "Warp already exists");
			return true;
		}
		warpNames.removeAll(Arrays.asList(args[0]));
		warpNames.add(args[0]);
		plugin.getConfig().set(warpLocationPath, player.getLocation());
		plugin.getConfig().set(warpNamePath, warpNames);
		plugin.saveConfig();
		player.sendMessage(
				ChatColor.translateAlternateColorCodes('&', String.format("&6warp &4%s &6has been created", args[0])));
		return true;
	}

}
