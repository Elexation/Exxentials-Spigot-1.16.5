package me.elexation.exxentials;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.elexation.exxentials.commands.*;
import me.elexation.exxentials.datamanagers.*;
import me.elexation.exxentials.listeners.*;
import me.elexation.exxentials.miscellaneous.*;
import me.elexation.exxentials.tabCompleters.*;

public class Exxentials extends JavaPlugin {

	private  afk afk = new afk();
	private  msg msg = new msg();
	private  nickname nick = new nickname();
	private  spawn spawn = new spawn(this);
	private  warp warp = new warp(this);
	private  warps warps = new warps(this);
	private track track = new track(this);

	@Override
	public void onEnable() {
		System.out.println("Exxentials plugin - Elexation");

		new nicknameConfigSetup();
		new configSetup(this);

		setCommandUsages("Usage: " + ChatColor.GOLD);
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
		this.getCommand("afk").setUsage(format + "/<command>");
		this.getCommand("god").setUsage(format + "/<command> [player]");
		this.getCommand("trackstop").setUsage(format + "/<command>");
		this.getCommand("track").setUsage(format + "/<command> <player>");
		this.getCommand("butcher").setUsage(format + "/<command>");
		this.getCommand("fly").setUsage(format + "/<command>");
		this.getCommand("gamemode").setUsage(format + "/<command> <gamemode> [player]");
		this.getCommand("gmc").setUsage(format + "/<command> [player]");
		this.getCommand("gms").setUsage(format + "/<command> [player]");
		this.getCommand("heal").setUsage(format + "/<command> [player]");
		this.getCommand("msg").setUsage(format + "/<command> <player> <message>");
		this.getCommand("nickname").setUsage(format + "/<command> [player] <nickname>");
		this.getCommand("reply").setUsage(format + "/<command>");
		this.getCommand("setspawn").setUsage(format + "/<command>");
		this.getCommand("setwarp").setUsage(format + "/<command> <warpName>");
		this.getCommand("spawn").setUsage(format + "/<command>");
		this.getCommand("vanish").setUsage(format + "/<command> [player]");
		this.getCommand("warp").setUsage(format + "/<command> <warpName>");
	}

}
