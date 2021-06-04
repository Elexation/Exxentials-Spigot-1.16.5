package me.elexation.exxentials.datamanagers;

import org.bukkit.plugin.java.JavaPlugin;

public class configSetup {

    public static void setup(JavaPlugin plugin) {
        plugin.getConfig().options().copyDefaults(true);
        saveDefaultConfig(plugin);
        if (!plugin.getConfig().getBoolean("settings.HealthActionBar"))
            plugin.getConfig().set("settings.HealthActionBar", false);
        if (plugin.getConfig().getBoolean("settings.AfkTimer"))
            plugin.getConfig().set("settings.AfkTimer", true);
        saveConfig(plugin);
    }

    private static void saveDefaultConfig(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
    }

    private static void saveConfig(JavaPlugin plugin) {
        plugin.saveConfig();
    }

}
