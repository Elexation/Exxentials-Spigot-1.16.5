package me.elexation.exxentials.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class gamemode implements CommandExecutor {

	private static List<String> possibleArguments = Arrays
			.asList(new String[] { "creative", "survival", "spectator", "adventure", "s", "c", "spec", "adv" });

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("essentials.gamemode")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (args.length == 2) {
			if (possibleArguments.contains(args[0])) {
				if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s"))
					return handleGamemode(player, args, GameMode.SURVIVAL);
				else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c"))
					return handleGamemode(player, args, GameMode.CREATIVE);
				else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("adv"))
					return handleGamemode(player, args, GameMode.ADVENTURE);
				else
					return handleGamemode(player, args, GameMode.SPECTATOR);
			}
			return false;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s"))
				return handleGamemode(player, args, GameMode.SURVIVAL);
			else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c"))
				return handleGamemode(player, args, GameMode.CREATIVE);
			else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spec"))
				return handleGamemode(player, args, GameMode.SPECTATOR);
			else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("adv"))
				return handleGamemode(player, args, GameMode.ADVENTURE);
			else
				return false;
		} else
			return false;
	}

	public boolean handleGamemode(Player player, String[] args, GameMode gamemode) {
		String gamemodeName;
		if (gamemode.equals(GameMode.CREATIVE))
			gamemodeName = "creative";
		else if (gamemode.equals(GameMode.SURVIVAL))
			gamemodeName = "survival";
		else if (gamemode.equals(GameMode.ADVENTURE))
			gamemodeName = "adventure";
		else
			gamemodeName = "spectator";
		if (args.length == 2) {
			String targetName = args[1];
			if (player.getName().equals(targetName)) {
				if (player.getGameMode().equals(gamemode))
					return true;
				player.sendMessage(ChatColor.DARK_GRAY + "Gamemode set to " + gamemodeName);
				player.setGameMode(gamemode);
				return true;
			}
			for (Player target : Bukkit.getOnlinePlayers()) {
				if (target.getName().equals(targetName)) {
					if (target.getGameMode().equals(gamemode))
						return true;
					target.sendMessage(ChatColor.DARK_GRAY + "Gamemode set to " + gamemodeName);
					player.sendMessage(ChatColor.DARK_GRAY + target.getName() + " gamemode set to " + gamemodeName);
					target.setGameMode(gamemode);
					return true;
				}
			}
			player.sendMessage(ChatColor.DARK_RED + "Could not find player");
			return true;
		} else if (args.length == 1) {
			if (player.getGameMode().equals(gamemode))
				return true;
			player.sendMessage(ChatColor.DARK_GRAY + "Gamemode set to " + gamemodeName);
			player.setGameMode(gamemode);
			return true;
		}
		return false;
	}
}
