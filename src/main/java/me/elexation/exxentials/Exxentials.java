package me.elexation.exxentials;

import me.elexation.exxentials.commands.*;
import me.elexation.exxentials.datamanagers.configSetup;
import me.elexation.exxentials.datamanagers.nicknameConfigSetup;
import me.elexation.exxentials.listeners.*;
import me.elexation.exxentials.miscellaneous.dayTime;
import me.elexation.exxentials.tabCompleters.gamemodeTabCompleter;
import me.elexation.exxentials.tabCompleters.msgTabCompleter;
import me.elexation.exxentials.tabCompleters.nicknameTabCompleter;
import me.elexation.exxentials.tabCompleters.warps;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Exxentials extends JavaPlugin {

	private final afk afk = new afk();
	private final msg msg = new msg();
	private final nickname nick = new nickname();
	private final spawn spawn = new spawn(this);
	private final warp warp = new warp(this);
	private final warps warps = new warps(this);
	private final track track = new track(this);

	@Override
	public void onEnable() {
		System.out.println("Exxentials version 1.0 - Elexation");

		nicknameConfigSetup.setup();
		configSetup.setup(this);

		setCommandUsages("Usage: " + ChatColor.GOLD + "/<command> ");
		setCommandExecutors();
		setCommandTabCompleters();
		registerListeners();
		startSchedules();
	}

	@Override
	public void onDisable() {

	}

	public void startSchedules() {
		if (getConfig().getBoolean("settings.AfkTimer"))
			afk.run(this);
		new dayTime().runTaskTimer(this, 0, 400);
	}

	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new colorCodedChat(), this);
		this.getServer().getPluginManager().registerEvents(afk, this);
		this.getServer().getPluginManager().registerEvents(new JoinLeaveMessages(this), this);
		this.getServer().getPluginManager().registerEvents(nick, this);
		this.getServer().getPluginManager().registerEvents(new sunnyDay(), this);
		this.getServer().getPluginManager().registerEvents(spawn, this);
		this.getServer().getPluginManager().registerEvents(warp, this);
		if (getConfig().getBoolean("settings.HealthActionBar"))
			this.getServer().getPluginManager().registerEvents(new EntityHealthActionBar(), this);
		this.getServer().getPluginManager().registerEvents(new fireFromBow(), this);
		// this.getServer().getPluginManager().registerEvents(new endermanteleport(),
		// this);
		// this.getServer().getPluginManager().registerEvents(new sandFall(), this);
	}

	public void setCommandTabCompleters() {
		this.getCommand("delwarp").setTabCompleter(warps);
		this.getCommand("gamemode").setTabCompleter(new gamemodeTabCompleter());
		this.getCommand("msg").setTabCompleter(new msgTabCompleter());
		this.getCommand("nick").setTabCompleter(new nicknameTabCompleter());
		this.getCommand("warp").setTabCompleter(warps);
	}

	public void setCommandExecutors() {
		this.getCommand("afk").setExecutor(afk);
		this.getCommand("exreload").setExecutor(new exreload(this));
		this.getCommand("god").setExecutor(new god());
		this.getCommand("trackstop").setExecutor(new trackstop(track));
		this.getCommand("butcher").setExecutor(new butcher());
		this.getCommand("delwarp").setExecutor(new delwarp(this));
		this.getCommand("fly").setExecutor(new fly());
		this.getCommand("track").setExecutor(track);
		this.getCommand("gamemode").setExecutor(new gamemode());
		this.getCommand("gmc").setExecutor(new gmc());
		this.getCommand("gms").setExecutor(new gms());
		this.getCommand("heal").setExecutor(new heal());
		this.getCommand("msg").setExecutor(msg);
		this.getCommand("nickname").setExecutor(nick);
		this.getCommand("reply").setExecutor(new reply(msg));
		this.getCommand("setspawn").setExecutor(new setspawn(this));
		this.getCommand("setwarp").setExecutor(new setwarp(this));
		this.getCommand("spawn").setExecutor(spawn);
		this.getCommand("vanish").setExecutor(new vanish());
		this.getCommand("warp").setExecutor(warp);
	}

	public void setCommandUsages(String format) {
		this.getCommand("afk").setUsage(format);
		this.getCommand("god").setUsage(format + "[player]");
		this.getCommand("trackstop").setUsage(format);
		this.getCommand("track").setUsage(format + "<player>");
		this.getCommand("butcher").setUsage(format);
		this.getCommand("fly").setUsage(format);
		this.getCommand("gamemode").setUsage(format + "<gamemode> [player]");
		this.getCommand("gmc").setUsage(format + "[player]");
		this.getCommand("gms").setUsage(format + "[player]");
		this.getCommand("heal").setUsage(format + "[player]");
		this.getCommand("msg").setUsage(format + "<player> <message>");
		this.getCommand("nickname").setUsage(format + "[player] <nickname>");
		this.getCommand("reply").setUsage(format);
		this.getCommand("setspawn").setUsage(format);
		this.getCommand("setwarp").setUsage(format + "<warpName>");
		this.getCommand("spawn").setUsage(format);
		this.getCommand("vanish").setUsage(format + "[player]");
		this.getCommand("warp").setUsage(format + "<warpName>");
	}
}
