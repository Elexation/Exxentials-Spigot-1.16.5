package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.elexation.exxentials.datamanagers.nicknameFile;
import me.elexation.exxentials.miscellaneous.ColorConverter;

public class nickname implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("exxentials.nickname")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}
		if (args.length == 2) {
			String targetNick = args[1];
			if (player.getName().equals(args[0]))
				return handleNickname(player, null, targetNick, true);
			for (Player target : Bukkit.getOnlinePlayers())
				if (target.getName().equals(args[0]))
					return handleNickname(player, target, targetNick, false);
			player.sendMessage(ChatColor.DARK_RED + "Player not found");
			return true;
		} else if (args.length == 1) {
			for (Player target : Bukkit.getOnlinePlayers())
				if (target.getName().equals(args[0]) && !target.getName().equals(player.getName()))
					return handleNickname(player, target, target.getName(), false);
			String playerNick = args[0];
			return handleNickname(player, null, playerNick, true);
		} else
			return handleNickname(player, null, player.getName(), true);
	}

	private boolean handleNickname(Player player, Player target, String nick, boolean isSenderSelf) {
		String targetUniqueId;
		if (ColorConverter.fromColor(nick).length() > 10) {
			player.sendMessage(ChatColor.DARK_RED + "Nickname must be no more than 10 characters " + ChatColor.GOLD
					+ "(not including color codes)");
			return true;
		}
		nick = ColorConverter.toColor(nick);
		if (isSenderSelf) {
			targetUniqueId = player.getUniqueId().toString();
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', String.format("&6Nickname set to &r%s", nick)));
			player.setPlayerListName(nick);
			player.setDisplayName(nick);
		} else {
			targetUniqueId = target.getUniqueId().toString();
			target.sendMessage(
					ChatColor.translateAlternateColorCodes('&', String.format("&6Nickname set to &r%s", nick)));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					String.format("&4%s &6nickname set to &r%s", target.getDisplayName(), nick)));
			target.setDisplayName(nick);
			target.setPlayerListName(nick);
		}
		nicknameFile.getConfig().set("players." + targetUniqueId + ".nick", nick);
		nicknameFile.save();
		return true;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (nicknameFile.getConfig().contains("players." + player.getUniqueId())) {
			String nick = nicknameFile.getConfig().getString("players." + player.getUniqueId() + ".nick");
			player.setDisplayName(nick);
			player.setPlayerListName(nick);
		}
	}

}
