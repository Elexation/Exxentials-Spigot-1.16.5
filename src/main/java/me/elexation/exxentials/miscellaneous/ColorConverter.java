package me.elexation.exxentials.miscellaneous;

import org.bukkit.ChatColor;

public class ColorConverter {
    public static String toColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String fromColor(String string) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string));
    }
}
