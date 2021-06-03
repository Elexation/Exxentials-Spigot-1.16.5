package me.elexation.exxentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class vanish implements CommandExecutor {

	public boolean handleVanish(Player player) {
		String state = "";
		if (player.isInvisible()) {
			state = "visible";
			player.setInvisible(false);
		}
		else {
			state = "invisible";
			player.setInvisible(true);
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You are now " + state));
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return true;
		Player player = (Player) sender;
		if (!player.hasPermission("essentials.vanish")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		return handleVanish(player);
	}
}
