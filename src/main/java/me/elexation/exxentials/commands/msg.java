package me.elexation.exxentials.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class msg implements CommandExecutor {

	private static Map<Player, Player> replyList = new HashMap<Player, Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String message = "";
		boolean isPlayerValid = false;
		Player target = null;
		if (args.length >= 2) {
			message = getMessage(args, false);
			target = Bukkit.getPlayer(args[0]);
			if (target != null)
				if (target.getName().equals(args[0]))
					isPlayerValid = true;
		}
		if (!(sender instanceof Player)) {
			if (args.length < 2)
				return false;
			if (!isPlayerValid) {
				sender.sendMessage(ChatColor.DARK_RED + "Player not found");
				return true;
			}
			sender.sendMessage(
					ChatColor.translateAlternateColorCodes('&', String.format("&8(&aTo &7%s&8) &7", target.getName()))
							+ message);
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&aFrom &7CONSOLE&8) &7") + message);
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("essentials.msg")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (args.length < 2)
			return false;
		if (!isPlayerValid) {
			player.sendMessage(ChatColor.DARK_RED + "Player not found");
			return true;
		}
		replyList.put(player, target);
		player.sendMessage(
				ChatColor.translateAlternateColorCodes('&', String.format("&8(&aTo &7%s&8) &7", target.getName()))
						+ message);
		target.sendMessage(
				ChatColor.translateAlternateColorCodes('&', String.format("&8(&aFrom &7%s&8) &7", player.getName()))
						+ message);
		return true;
	}

	public String getMessage(String[] args, boolean isReply) {
		String message = "";
		for (int i = 0; i < args.length; i++)
			if (isReply)
				message += args[i] + " ";
			else {
				if (i > 0)
					message += args[i] + " ";
			}
		return message;
	}

	public Map<Player, Player> getReplyList() {
		return replyList;
	}

}
