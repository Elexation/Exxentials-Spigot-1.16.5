package me.elexation.exxentials.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class afk implements CommandExecutor, Listener {

	private static List<String> afkList = new ArrayList<>();
	private static Map<String, Location> previousLocations = new HashMap<>();
	private static Map<String, Long> playerTimers = new HashMap<>();

	public void run(JavaPlugin plugin) {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					Location previousLocation = player.getLocation();
					if (previousLocations.containsKey(player.getName()))
						previousLocation = previousLocations.get(player.getName());
					Location currentLocation = player.getLocation();
					previousLocations.put(player.getName(), currentLocation);
					if (previousLocation.getX() != currentLocation.getX()
							|| previousLocation.getZ() != currentLocation.getZ()) {
						playerTimers.put(player.getName(), (System.currentTimeMillis() + (600 * 1000)));
						return;
					}
					if (playerTimers.containsKey(player.getName()))
						if ((playerTimers.get(player.getName()) < System.currentTimeMillis())
								&& !afkList.contains(player.getName())) {
							afkList.add(player.getName());
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p.getName().equals(player.getName()))
									p.sendMessage(ChatColor.GRAY + "you are now afk");
								else
									p.sendMessage(ChatColor.GRAY + ""
											+ String.format("%s is now afk", player.getDisplayName()));
							}
						}
				}

			}

		}.runTaskTimer(plugin, 0, 20);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("exxentials.afk")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		if (!afkList.contains(player.getName())) {
			afkList.add(player.getName());
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().equals(player.getName()))
					p.sendMessage(ChatColor.GRAY + "you are now afk");
				else
					p.sendMessage(ChatColor.GRAY + "" + String.format("%s is now afk", player.getDisplayName()));
			}
			return true;
		}
		afkList.remove(player.getName());
		playerTimers.put(player.getName(), (System.currentTimeMillis() + (600 * 1000)));
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(player.getName()))
				p.sendMessage(ChatColor.GRAY + "you are no longer afk");
			else
				p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
		}
		return true;
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		afkList.remove(player.getName());
		previousLocations.remove(player.getName());
		playerTimers.remove(player.getName());
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		playerTimers.put(player.getName(), System.currentTimeMillis() + (600 * 1000));
		if (!afkList.contains(player.getName())) return;
		afkList.remove(player.getName());
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(player.getName()))
				p.sendMessage(ChatColor.GRAY + "you are no longer afk");
			else
				p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
		}
	}

	@EventHandler
	public void onPrePlayerCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		playerTimers.put(player.getName(), System.currentTimeMillis() + (600 * 1000));
		if (!afkList.contains(player.getName())) return;
		afkList.remove(player.getName());
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(player.getName()))
				p.sendMessage(ChatColor.GRAY + "you are no longer afk");
			else
				p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
		}
	}

	@EventHandler
	public void onPrePlayerCommand(PlayerCommandSendEvent e) {
		Player player = e.getPlayer();
		playerTimers.put(player.getName(), System.currentTimeMillis() + (600 * 1000));
		if (!afkList.contains(player.getName())) return;
		afkList.remove(player.getName());
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(player.getName()))
				p.sendMessage(ChatColor.GRAY + "you are no longer afk");
			else
				p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getFrom().getZ() != event.getTo().getZ() && event.getFrom().getX() != event.getTo().getX())
			if (afkList.contains(player.getName())) {
				afkList.remove(player.getName());
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getName().equals(player.getName()))
						p.sendMessage(ChatColor.GRAY + "you are no longer afk");
					else
						p.sendMessage(
								ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
				}
			}
	}
}
