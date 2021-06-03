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
		if (args.length < 1) return healPlayer(player);
		if (player.getName().equals(args[0])) return healPlayer(player);
		Player target = Bukkit.getPlayer(args[0]);
		if (target != null)
			if (target.getName().equals(args[0])) return healPlayerOther(player, target);
		player.sendMessage(org.bukkit.ChatColor.DARK_RED + "Player not found");
		return true;
	}

	private boolean healPlayer(Player player){
		player.sendMessage(ChatColor.DARK_RED + "You have been healed");
		player.setFoodLevel(20);
		player.setHealth(20);
		return true;
	}

	private boolean healPlayerOther(Player player, Player target) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&',
				String.format("&6Player &4%s &6has been healed", target.getName())));
		target.sendMessage(ChatColor.RED + "You have been healed");
		target.setFoodLevel(20);
		target.setHealth(20);
		return true;
	}

}
