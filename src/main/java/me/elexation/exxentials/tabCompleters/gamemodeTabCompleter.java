package me.elexation.exxentials.tabCompleters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class gamemodeTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return null;
		Player player = (Player) sender;
		if (!player.hasPermission("essentials.gamemode"))
			return null;
		if (args.length == 1) {
			List<String> gamemodes = Arrays
					.asList(new String[] { "creative", "survival", "spectator", "adventure", "s", "c", "spec", "adv" });
			return gamemodes;
		} else if (args.length == 2) {
			List<String> playerNames = new ArrayList<>();
			for (Player onlinePlayer : Bukkit.getOnlinePlayers())
				playerNames.add(onlinePlayer.getName());
			return playerNames;
		}
		return null;
	}

}
