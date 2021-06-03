package me.elexation.exxentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class JoinLeaveMessages implements Listener {

	private String JoinMessage;
	private String LeaveMessage;

	public JoinLeaveMessages(JavaPlugin plugin) {
		JoinMessage = plugin.getConfig().getString("settings.JoinMessage");
		LeaveMessage = plugin.getConfig().getString("settings.LeaveMessage");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		String message = ChatColor.translateAlternateColorCodes('&', JoinMessage);
		message = message.replace("<player>", event.getPlayer().getName());
		event.setJoinMessage(message);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		String message = ChatColor.translateAlternateColorCodes('&', LeaveMessage);
		message = message.replace("<player>", event.getPlayer().getName());
		event.setQuitMessage(message);
	}
}