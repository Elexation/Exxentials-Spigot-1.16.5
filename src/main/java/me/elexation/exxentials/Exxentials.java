package me.elexation.exxentials;

import me.elexation.exxentials.commands.*;
import me.elexation.exxentials.datamanagers.*;
import me.elexation.exxentials.listeners.*;
import me.elexation.exxentials.miscellaneous.*;
import me.elexation.exxentials.tabCompleters.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Exxentials extends JavaPlugin {

    public static Exxentials plugin;
    private JoinLeaveMessages messages;
    private colorCodedChat chat;
    private afk afk;
    private EntityHealthActionBar HealthActionBar;
    private msg msg;
    private nickname nick;
    private spawn spawn;
    private warp warp;
    private warps warps;
    private track track;

    @Override
    public void onEnable() {
        initializeVars();
        System.out.println("Exxentials version 1.0 - Elexation");

        nicknameConfigSetup.setup();
        configSetup.setup(this);

        setCommandUsages("Usage: " + ChatColor.GOLD + "/<command> ");
        setCommandExecutors();
        setCommandTabCompleters();
        registerListeners();
        startSchedules();
    }

    private void initializeVars(){
        plugin = this;
        messages = new JoinLeaveMessages();
        chat = new colorCodedChat();
        afk = new afk();
        HealthActionBar = new EntityHealthActionBar();
        msg = new msg();
        nick = new nickname();
        spawn = new spawn();
        warp = new warp();
        warps = new warps();
        track = new track();
    }

    private void startSchedules() {
        afk.setIsRunnableOn(this.getConfig().getBoolean("settings.AfkTimer"));
        afk.getTask().runTaskTimer(this, 0, 400);
        new dayTime().runTaskTimer(this, 0, 400);
    }

    public void registerListeners() {
        this.getServer().getPluginManager().registerEvents(chat, this);
        this.getServer().getPluginManager().registerEvents(afk, this);
        this.getServer().getPluginManager().registerEvents(messages, this);
        this.getServer().getPluginManager().registerEvents(nick, this);
        this.getServer().getPluginManager().registerEvents(new sunnyDay(), this);
        this.getServer().getPluginManager().registerEvents(spawn, this);
        this.getServer().getPluginManager().registerEvents(warp, this);
        this.getServer().getPluginManager().registerEvents(HealthActionBar, this);
        HealthActionBar.setIsOn(getConfig().getBoolean("settings.HealthActionBar"));
        this.getServer().getPluginManager().registerEvents(new fireFromBow(), this);
        // this.getServer().getPluginManager().registerEvents(new endermanteleport(), this);
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
        this.getCommand("sudo").setExecutor(new sudo());
        this.getCommand("speed").setExecutor(new speed());
        this.getCommand("afk").setExecutor(afk);
        this.getCommand("exreload").setExecutor(new exreload(messages, chat, afk, HealthActionBar));
        this.getCommand("god").setExecutor(new god());
        this.getCommand("trackstop").setExecutor(new trackstop(track));
        this.getCommand("butcher").setExecutor(new butcher());
        this.getCommand("delwarp").setExecutor(new delwarp());
        this.getCommand("fly").setExecutor(new fly());
        this.getCommand("track").setExecutor(track);
        this.getCommand("gamemode").setExecutor(new gamemode());
        this.getCommand("gmc").setExecutor(new gmc());
        this.getCommand("gms").setExecutor(new gms());
        this.getCommand("heal").setExecutor(new heal());
        this.getCommand("msg").setExecutor(msg);
        this.getCommand("nickname").setExecutor(nick);
        this.getCommand("reply").setExecutor(new reply(msg));
        this.getCommand("setspawn").setExecutor(new setspawn());
        this.getCommand("setwarp").setExecutor(new setwarp());
        this.getCommand("spawn").setExecutor(spawn);
        this.getCommand("vanish").setExecutor(new vanish());
        this.getCommand("warp").setExecutor(warp);
    }

    public void setCommandUsages(String format) {
        this.getCommand("afk").setUsage(format);
        this.getCommand("speed").setUsage(format + "[speed]");
        this.getCommand("sudo").setUsage(format + "<player> <commandLine>");
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
