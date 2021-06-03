package me.elexation.exxentials.datamanagers;

import org.bukkit.plugin.java.JavaPlugin;

public class configSetup {

	private final JavaPlugin plugin;

	public configSetup(JavaPlugin plugin) {
		this.plugin = plugin;

		plugin.getConfig().addDefault("settings.JoinMessage", "&6[&2+&6] <player>");
		plugin.getConfig().addDefault("settings.LeaveMessage", "&6[&4-&6] <player>");
		plugin.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		if (!plugin.getConfig().getBoolean("settings.HealthActionBar"))
			plugin.getConfig().set("settings.HealthActionBar", false);
		if (plugin.getConfig().getBoolean("settings.AfkTimer"))
			plugin.getConfig().set("settings.AfkTimer", true);
		saveConfig();
	}

	public void saveDefaultConfig() {
		plugin.saveDefaultConfig();
	}

	public void saveConfig() {
		plugin.saveConfig();
	}

}
