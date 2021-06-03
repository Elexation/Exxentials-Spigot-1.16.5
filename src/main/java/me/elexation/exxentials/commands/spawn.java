package me.elexation.exxentials.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class spawn implements CommandExecutor, Listener {

	private static ArrayList<Player> PlayerList = new ArrayList<Player>();
	private JavaPlugin plugin;

	public spawn(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (PlayerList.contains(player))
			return true;
		if (!player.hasPermission("essentials.spawn")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		String path = "settings.worlds." + player.getWorld().getUID().toString() + ".spawn";
		Location loc = plugin.getConfig().getLocation(path);
		if (loc == null || !loc.isWorldLoaded()) {
			player.sendMessage(ChatColor.DARK_RED + "Spawn not set");
			return true;
		}
		player.sendMessage(ChatColor.GOLD + "Dont move. You will be teleported in " + ChatColor.RED + "5"
				+ ChatColor.GOLD + " seconds");
		PlayerList.add(player);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (PlayerList.contains(player)) {
					player.teleport(loc);
					PlayerList.remove(player);
					player.sendMessage(ChatColor.GOLD + "Teleported to spawn");
				}
			}
		}.runTaskLater(plugin, 100);
		return true;
	}

	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		String path = "settings.worlds." + player.getWorld().getUID().toString() + ".spawn";
		if (plugin.getConfig().getLocation(path) == null || !plugin.getConfig().getLocation(path).isWorldLoaded()) {
			e.setRespawnLocation(player.getWorld().getSpawnLocation());
			return;
		}
		e.setRespawnLocation(plugin.getConfig().getLocation(path));
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (e.getFrom().getX() != e.getTo().getX() && e.getFrom().getZ() != e.getTo().getZ())
			if (PlayerList.contains(player)) {
				PlayerList.remove(player);
				player.sendMessage(ChatColor.DARK_RED + "Spawn teleportation canceled");
			}
	}

}
