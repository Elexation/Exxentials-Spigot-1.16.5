package me.elexation.exxentials.commands;

import me.elexation.exxentials.Exxentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class vanish implements CommandExecutor {

    private final List<Player> invisList = new ArrayList<>();

    private boolean handleVanish(Player player, boolean state) {
        if (state){
            invisList.add(player);
            for (Player target : Bukkit.getOnlinePlayers())
                target.hidePlayer(Exxentials.plugin, player);
        }
        else{
            invisList.remove(player);
            for (Player target : Bukkit.getOnlinePlayers())
                target.showPlayer(Exxentials.plugin, player);
        }
        player.setCollidable(state);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&6You are now %s", state ? "invisible" : "visible")));
        return true;
    }

    private boolean handleVanishOther(Player player, Player target, boolean state) {
        if (state){
            invisList.add(target);
            for (Player other : Bukkit.getOnlinePlayers())
                other.hidePlayer(Exxentials.plugin, target);
        }

        else{
            invisList.remove(target);
            for (Player other : Bukkit.getOnlinePlayers())
                other.showPlayer(Exxentials.plugin, target);
        }
        target.setCollidable(state);
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&6You are now %s", state ? "invisible" : "visible")));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&6Vanish %s for %s", state ? "on" : "off", target.getName())));
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.vanish")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) return handleVanish(player, !invisList.contains(player));
        if (args[0].equals(player.getName())) return handleVanish(player, !invisList.contains(player));
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null)
            if (target.getName().equals(args[0])) return handleVanishOther(player, target, !invisList.contains(target));
        player.sendMessage(ChatColor.DARK_RED + "Player not found");
        return true;
    }
}
