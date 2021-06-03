package me.elexation.exxentials.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.elexation.exxentials.miscellaneous.ColorConverter;

public class colorCodedChat implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setFormat(ChatColor.translateAlternateColorCodes('&',
				String.format("%s &6&l>> &r%s", event.getPlayer().getDisplayName(), event.getMessage())));
		Player player = event.getPlayer();
		if (player.hasPermission("exxentials.color") || !player.hasPermission("essentials.*")) {
			event.setMessage(ColorConverter.toColor(event.getMessage()));
		}
	}
}
