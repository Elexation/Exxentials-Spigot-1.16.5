package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class gms implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("essentials.gamemode")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (args.length == 1) {
			if (player.getName().equals(args[0])) {
				if (player.getGameMode().equals(GameMode.SURVIVAL))
					return true;
				player.sendMessage(ChatColor.DARK_GRAY + "Gamemode set to survival");
				player.setGameMode(GameMode.SURVIVAL);
				return true;
			}
			for (Player target : Bukkit.getOnlinePlayers()) {
				if (target.getName().equals(args[0])) {
					if (target.getGameMode().equals(GameMode.SURVIVAL))
						return true;
					target.sendMessage(ChatColor.DARK_GRAY + "Gamemode set to survival");
					player.sendMessage(ChatColor.DARK_GRAY + target.getName() + " gamemode set to survival");
					target.setGameMode(GameMode.SURVIVAL);
					return true;
				}
			}
			player.sendMessage(ChatColor.DARK_RED + "Could not find player");
			return true;
		} else {
			if (player.getGameMode().equals(GameMode.SURVIVAL))
				return true;
			player.sendMessage(ChatColor.DARK_GRAY + "Gamemode set to survival");
			player.setGameMode(GameMode.SURVIVAL);
			return true;
		}
	}

}