package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class heal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("exxentials.heal")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (args.length == 1) {
			if (player.getName().equals(args[0]))
				return healPlayer(player, null, true, args);
			for (Player target : Bukkit.getOnlinePlayers())
				if (target.getName().equals(args[0]))
					return healPlayer(player, target, false, args);
			player.sendMessage(ChatColor.DARK_RED + "Player not found");
			return true;

		}
		return healPlayer(player, null, true, args);
	}

	private boolean healPlayer(Player player, Player target, boolean isPlayerSender, String[] args) {
		if (isPlayerSender) {
			player.sendMessage(ChatColor.DARK_RED + "You have been healed");
			player.setFoodLevel(20);
			player.setHealth(20);
		} else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					String.format("&6Player &4%s &6has been healed", args[0])));
			target.sendMessage(ChatColor.RED + "You have been healed");
			target.setFoodLevel(20);
			target.setHealth(20);
		}
		return true;
	}

}
