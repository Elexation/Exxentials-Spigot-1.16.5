package me.elexation.exxentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class warp implements CommandExecutor, Listener {

    private static final ArrayList<Player> PlayerList = new ArrayList<>();
    private final JavaPlugin plugin;

    public warp(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.warp")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1)
            return false;
        String warpLocationPath = "settings.worlds." + player.getWorld().getUID() + ".warpLocations."
                + args[0];
        String warpNamesPath = "settings.worlds." + player.getWorld().getUID() + ".warpNames";
        Location loc = plugin.getConfig().getLocation(warpLocationPath);
        List<String> warpNames = plugin.getConfig().getStringList(warpNamesPath);
        if (loc == null || !loc.isWorldLoaded() || !warpNames.contains(args[0])) {
            plugin.getConfig().set(warpLocationPath, null);
            plugin.saveConfig();
            player.sendMessage(ChatColor.DARK_RED + "Warp does not exist");
            return true;
        }
        player.sendMessage(ChatColor.GOLD + "Dont move. You will be teleported in " + ChatColor.RED + "5"
                + ChatColor.GOLD + " seconds");
        PlayerList.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (PlayerList.contains(player)) {
                    player.teleport(loc);
                    PlayerList.remove(player);
                    player.sendMessage(ChatColor.GOLD + "Teleported to warp " + ChatColor.DARK_RED + args[0]);
                }
            }
        }.runTaskLater(plugin, 100);
        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX() && e.getFrom().getZ() != e.getTo().getZ())
            if (PlayerList.contains(player)) {
                PlayerList.remove(player);
                player.sendMessage(ChatColor.DARK_RED + "Warp teleportation canceled");
            }
    }
}
