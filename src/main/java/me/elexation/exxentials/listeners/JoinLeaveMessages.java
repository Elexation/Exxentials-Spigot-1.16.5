package me.elexation.exxentials.listeners;

import me.elexation.exxentials.Exxentials;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveMessages implements Listener {

    private String JoinMessage = Exxentials.plugin.getConfig().getString("settings.JoinMessage");
    private String LeaveMessage = Exxentials.plugin.getConfig().getString("settings.LeaveMessage");

    public void reload(){
        JoinMessage = Exxentials.plugin.getConfig().getString("settings.JoinMessage");
        LeaveMessage = Exxentials.plugin.getConfig().getString("settings.LeaveMessage");
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