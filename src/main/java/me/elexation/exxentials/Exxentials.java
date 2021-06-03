package me.elexation.exxentials;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.elexation.exxentials.commands.*;
import me.elexation.exxentials.datamanagers.*;
import me.elexation.exxentials.listeners.*;
import me.elexation.exxentials.miscellaneous.*;
import me.elexation.exxentials.tabCompleters.*;

import java.util.Objects;

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

		new nicknameConfigSetup();
		new configSetup(this);

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
		Objects.requireNonNull(this.getCommand("delwarp")).setTabCompleter(warps);
		Objects.requireNonNull(this.getCommand("gamemode")).setTabCompleter(new gamemodeTabCompleter());
		Objects.requireNonNull(this.getCommand("msg")).setTabCompleter(new msgTabCompleter());
		Objects.requireNonNull(this.getCommand("nick")).setTabCompleter(new nicknameTabCompleter());
		Objects.requireNonNull(this.getCommand("warp")).setTabCompleter(warps);
	}

	public void setCommandExecutors() {
		Objects.requireNonNull(this.getCommand("afk")).setExecutor(afk);
		Objects.requireNonNull(this.getCommand("god")).setExecutor(new god());
		Objects.requireNonNull(this.getCommand("trackstop")).setExecutor(new trackstop(track));
		Objects.requireNonNull(this.getCommand("butcher")).setExecutor(new butcher());
		Objects.requireNonNull(this.getCommand("delwarp")).setExecutor(new delwarp(this));
		Objects.requireNonNull(this.getCommand("fly")).setExecutor(new fly());
		Objects.requireNonNull(this.getCommand("track")).setExecutor(track);
		Objects.requireNonNull(this.getCommand("gamemode")).setExecutor(new gamemode());
		Objects.requireNonNull(this.getCommand("gmc")).setExecutor(new gmc());
		Objects.requireNonNull(this.getCommand("gms")).setExecutor(new gms());
		Objects.requireNonNull(this.getCommand("heal")).setExecutor(new heal());
		Objects.requireNonNull(this.getCommand("msg")).setExecutor(msg);
		Objects.requireNonNull(this.getCommand("nickname")).setExecutor(nick);
		Objects.requireNonNull(this.getCommand("reply")).setExecutor(new reply(msg));
		Objects.requireNonNull(this.getCommand("setspawn")).setExecutor(new setspawn(this));
		Objects.requireNonNull(this.getCommand("setwarp")).setExecutor(new setwarp(this));
		Objects.requireNonNull(this.getCommand("spawn")).setExecutor(spawn);
		Objects.requireNonNull(this.getCommand("vanish")).setExecutor(new vanish());
		Objects.requireNonNull(this.getCommand("warp")).setExecutor(warp);
	}

	public void setCommandUsages(String format) {
		Objects.requireNonNull(this.getCommand("afk")).setUsage(format);
		Objects.requireNonNull(this.getCommand("god")).setUsage(format + "[player]");
		Objects.requireNonNull(this.getCommand("trackstop")).setUsage(format);
		Objects.requireNonNull(this.getCommand("track")).setUsage(format + "<player>");
		Objects.requireNonNull(this.getCommand("butcher")).setUsage(format);
		Objects.requireNonNull(this.getCommand("fly")).setUsage(format);
		Objects.requireNonNull(this.getCommand("gamemode")).setUsage(format + "<gamemode> [player]");
		Objects.requireNonNull(this.getCommand("gmc")).setUsage(format + "[player]");
		Objects.requireNonNull(this.getCommand("gms")).setUsage(format + "[player]");
		Objects.requireNonNull(this.getCommand("heal")).setUsage(format + "[player]");
		Objects.requireNonNull(this.getCommand("msg")).setUsage(format + "<player> <message>");
		Objects.requireNonNull(this.getCommand("nickname")).setUsage(format + "[player] <nickname>");
		Objects.requireNonNull(this.getCommand("reply")).setUsage(format);
		Objects.requireNonNull(this.getCommand("setspawn")).setUsage(format);
		Objects.requireNonNull(this.getCommand("setwarp")).setUsage(format + "<warpName>");
		Objects.requireNonNull(this.getCommand("spawn")).setUsage(format);
		Objects.requireNonNull(this.getCommand("vanish")).setUsage(format + "[player]");
		Objects.requireNonNull(this.getCommand("warp")).setUsage(format + "<warpName>");
	}

}
