package me.elexation.exxentials.listeners;

import me.elexation.exxentials.Exxentials;
import me.elexation.exxentials.miscellaneous.ColorConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class colorCodedChat implements Listener {

    private String chatFormat = Exxentials.plugin.getConfig().getString("settings.ChatFormat");

    public void reload(){
        chatFormat = Exxentials.plugin.getConfig().getString("settings.ChatFormat");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String format = chatFormat;
        format = format.replace("<player>", player.getName());
        format = ColorConverter.toColor(format);
        if (player.hasPermission("exxentials.color")) e.setMessage(ColorConverter.toColor(e.getMessage()));
        format = format.replace("<message>", e.getMessage());
        e.setFormat(format);
    }
}
