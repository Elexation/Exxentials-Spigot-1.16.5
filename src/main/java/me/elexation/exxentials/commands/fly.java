package me.elexation.exxentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("exxentials.fly")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (!(player.getAllowFlight())) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Flying enabled"));
			player.setAllowFlight(true);
			return true;
		} else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Flying disabled"));
			player.setAllowFlight(false);
			return true;
		}

	}
}