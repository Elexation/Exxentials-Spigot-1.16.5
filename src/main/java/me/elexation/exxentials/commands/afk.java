package me.elexation.exxentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class afk implements CommandExecutor, Listener {

    private final static List<String> afkList = new ArrayList<>();
    private final static Map<String, Location> previousLocations = new HashMap<>();
    private final static Map<String, Long> playerTimes = new HashMap<>();
    private boolean isRunnableOn = true;
    private BukkitRunnable afkTimer = setup();

    public void setIsRunnableOn(boolean isRunnableOn){
        this.isRunnableOn = isRunnableOn;
    }

    private BukkitRunnable setup(){
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (!isRunnableOn) return;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("s");
                    Location previousLocation = player.getLocation();
                    if (previousLocations.containsKey(player.getName()))
                        previousLocation = previousLocations.get(player.getName());
                    Location currentLocation = player.getLocation();
                    previousLocations.put(player.getName(), currentLocation);
                    if (previousLocation.getX() != currentLocation.getX()
                            || previousLocation.getZ() != currentLocation.getZ()) {
                        playerTimes.put(player.getName(), (System.currentTimeMillis() + (600 * 1000)));
                        return;
                    }
                    if (playerTimes.containsKey(player.getName()))
                        if ((playerTimes.get(player.getName()) < System.currentTimeMillis())
                                && !afkList.contains(player.getName())) {
                            afkList.add(player.getName());
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.getName().equals(player.getName()))
                                    p.sendMessage(ChatColor.GRAY + "you are now afk");
                                else
                                    p.sendMessage(ChatColor.GRAY + ""
                                            + String.format("%s is now afk", player.getDisplayName()));
                            }
                        }
                }
            }
        };
    }

    public BukkitRunnable getTask(){
        return afkTimer;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.afk")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (!afkList.contains(player.getName())) {
            afkList.add(player.getName());
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equals(player.getName()))
                    p.sendMessage(ChatColor.GRAY + "you are now afk");
                else
                    p.sendMessage(ChatColor.GRAY + "" + String.format("%s is now afk", player.getDisplayName()));
            }
            return true;
        }
        afkList.remove(player.getName());
        playerTimes.put(player.getName(), (System.currentTimeMillis() + (600 * 1000)));
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(player.getName()))
                p.sendMessage(ChatColor.GRAY + "you are no longer afk");
            else
                p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
        }
        return true;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        afkList.remove(player.getName());
        previousLocations.remove(player.getName());
        playerTimes.remove(player.getName());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        playerTimes.put(player.getName(), System.currentTimeMillis() + (600 * 1000));
        if (!afkList.contains(player.getName())) return;
        afkList.remove(player.getName());
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(player.getName()))
                p.sendMessage(ChatColor.GRAY + "you are no longer afk");
            else
                p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandSendEvent e) {
        Player player = e.getPlayer();
        playerTimes.put(player.getName(), System.currentTimeMillis() + (600 * 1000));
        if (!afkList.contains(player.getName())) return;
        afkList.remove(player.getName());
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(player.getName()))
                p.sendMessage(ChatColor.GRAY + "you are no longer afk");
            else
                p.sendMessage(ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getZ() != Objects.requireNonNull(event.getTo()).getZ() && event.getFrom().getX() != event.getTo().getX())
            if (afkList.contains(player.getName())) {
                afkList.remove(player.getName());
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().equals(player.getName()))
                        p.sendMessage(ChatColor.GRAY + "you are no longer afk");
                    else
                        p.sendMessage(
                                ChatColor.GRAY + "" + String.format("%s is no longer afk", player.getDisplayName()));
                }
            }
    }
}
