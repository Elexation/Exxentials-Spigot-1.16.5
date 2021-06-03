package me.elexation.exxentials.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class track implements CommandExecutor, Listener {

    private static final Map<Player, Player> trackedPlayers = new HashMap<>();
    private static final Map<Player, BukkitRunnable> playerRunables = new HashMap<>();
    private final JavaPlugin plugin;

    public track(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.track")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) return false;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.getName().equals(args[0])) {
            player.sendMessage(ChatColor.DARK_RED + "Player not found");
            return true;
        }
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS) && !player.getInventory().getItemInOffHand().getType().equals(Material.COMPASS)) {
            player.sendMessage(ChatColor.GOLD + "Please hold a compass in your hand");
            return true;
        }
        if (trackedPlayers.containsKey(player))
            if (trackedPlayers.get(player).equals(target)) return true;
        if (playerRunables.containsKey(player))
            if (!playerRunables.get(player).isCancelled()) playerRunables.get(player).cancel();
        ItemStack compass;
        if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS))
            compass = player.getInventory().getItemInMainHand();
        else compass = player.getInventory().getItemInOffHand();
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setLodestoneTracked(false);
        compassMeta.setDisplayName(ChatColor.GOLD + "Tracking: " + ChatColor.GREEN + target.getName());
        compass.setItemMeta(compassMeta);
        player.sendMessage(ChatColor.GOLD + "Tracking " + target.getName());
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                if (!trackedPlayers.containsKey(player)) {
                    this.cancel();
                    playerRunables.remove(player);
                    return;
                }
                if (!player.getInventory().contains(Material.COMPASS) || !trackedPlayers.get(player).isOnline()) {
                    player.sendMessage(ChatColor.GOLD + "Tracking stopped");
                    trackedPlayers.remove(player);
                    playerRunables.remove(player);
                    this.cancel();
                }
                ItemStack compass;
                if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)
                        || player.getInventory().getItemInOffHand().getType().equals(Material.COMPASS)) {
                    if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS))
                        compass = player.getInventory().getItemInMainHand();
                    else compass = player.getInventory().getItemInOffHand();
                    Player target = trackedPlayers.get(player);
                    CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                    compassMeta.setLodestone(target.getLocation());
                    compass.setItemMeta(compassMeta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', String.format("&6X: &2%d &6Z: &2%d", target.getLocation().getBlockX(), target.getLocation().getBlockZ()))));
                }
            }

        };
        trackedPlayers.put(player, target);
        playerRunables.put(player, run);
        run.runTaskTimer(plugin, 0, 200);
        return true;
    }

    public Map<Player, Player> getTrackedPlayers() {
        return trackedPlayers;
    }
}
