package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.HashMap;
import java.util.Map;

public class track implements CommandExecutor, Listener {

    private static Map<Player, Player> trackedPlayers = new HashMap<Player, Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.track")){
            player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) return false;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.getName().equals(args[0])){
            player.sendMessage(ChatColor.DARK_RED + "Player not found");
            return true;
        }
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS) && !player.getInventory().getItemInOffHand().getType().equals(Material.COMPASS)){
            player.sendMessage(ChatColor.GOLD + "Please hold a compass in your hand");
            return true;
        }
        ItemStack compass;
        if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) compass = player.getInventory().getItemInMainHand();
        else compass = player.getInventory().getItemInOffHand();
        CompassMeta compassMeta = (CompassMeta) compass;
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(target.getLocation());
        compassMeta.setDisplayName(ChatColor.GOLD + "Tracking " + target.getName());
        trackedPlayers.put(player, target);
        return true;
    }

    public Map<Player, Player> getTrackedPlayers(){
        return trackedPlayers;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if (!trackedPlayers.containsKey(player)) return;
        if (player.getInventory().contains(Material.COMPASS) && trackedPlayers.get(player) != null){
            if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)
                    || player.getInventory().getItemInOffHand().getType().equals(Material.COMPASS)){
                if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)){
                    CompassMeta compassMeta = (CompassMeta) player.getInventory().getItemInMainHand();
                    compassMeta.setLodestone(trackedPlayers.get(player).getLocation());
                }
                else{
                    CompassMeta compassMeta = (CompassMeta) player.getInventory().getItemInOffHand();
                    compassMeta.setLodestone(trackedPlayers.get(player).getLocation());
                }
            }
            return;
        }
        player.sendMessage(ChatColor.GOLD + "Tracking stopped");
        trackedPlayers.remove(player);
    }
}
