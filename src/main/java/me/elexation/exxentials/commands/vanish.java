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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class vanish implements CommandExecutor, Listener {

	private static List<Player> invisibleList = new ArrayList<Player>();
	private final JavaPlugin plugin;

	public vanish(JavaPlugin plugin){
		this.plugin = plugin;
	}

	public void invisOnJoin(Player player){
		for (Player other : Bukkit.getOnlinePlayers()) {
			if (!other.hasPermission("essentials.vanish")) other.hidePlayer(player);
		}
	}

	public boolean handleVanish(Player player) {
		String state = "";
		if (invisibleList.contains(player)) {
			state = "visible";
			invisibleList.remove(player);
			for (Player other : Bukkit.getOnlinePlayers()) {
				if (!other.hasPermission("essentials.vanish")) other.hidePlayer(player);
			}
		}
		else {
			state = "invisible";
			invisibleList.add(player);
			for (Player other : Bukkit.getOnlinePlayers()) other.showPlayer(player);
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You are now " + state));
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("essentials.vanish")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		return handleVanish(player);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (invisibleList.contains(player)) invisOnJoin(player);
		if (!player.hasPermission("essentials.vanish")){
			for (Player target : invisibleList) player.hidePlayer(target);
		}
	}

}
