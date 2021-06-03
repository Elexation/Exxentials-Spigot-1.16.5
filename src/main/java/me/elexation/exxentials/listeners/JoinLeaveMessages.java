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
		JoinMessage = ChatColor.translateAlternateColorCodes('&', JoinMessage);
		JoinMessage = JoinMessage.replace("<player>", event.getPlayer().getName());
		event.setJoinMessage(JoinMessage);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		LeaveMessage = ChatColor.translateAlternateColorCodes('&', LeaveMessage);
		LeaveMessage = LeaveMessage.replace("<player>", event.getPlayer().getName());
		event.setQuitMessage(LeaveMessage);
	}
}