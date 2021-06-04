package me.elexation.exxentials.listeners;

import me.elexation.exxentials.miscellaneous.ColorConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class colorCodedChat implements Listener {

    private String chatFormat;
    private final JavaPlugin plugin;

    public colorCodedChat(JavaPlugin plugin){
        this.plugin = plugin;
        this.chatFormat = plugin.getConfig().getString("settings.ChatFormat");
    }

    public void reload(){
        this.chatFormat = plugin.getConfig().getString("settings.ChatFormat");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String format = this.chatFormat;
        format = format.replace("<player>", player.getName());
        format = ColorConverter.toColor(format);
        if (player.hasPermission("exxentials.color")) e.setMessage(ColorConverter.toColor(e.getMessage()));
        format = format.replace("<message>", e.getMessage());
        e.setFormat(format);
    }
}
