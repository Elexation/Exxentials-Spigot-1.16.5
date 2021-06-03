package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {

    private boolean handleFly(Player player, boolean state) {
        player.setAllowFlight(state);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&6Flying %s", state ? "enabled" : "disabled")));
        return true;
    }

    private boolean handleFlyOther(Player player, Player target, boolean state) {
        target.setAllowFlight(state);
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&6Flying %s", state ? "enabled" : "disabled")));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&6Flying %s for %s", state ? "on" : "off", target.getName())));
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.fly")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) return handleFly(player, !player.getAllowFlight());
        if (args[0].equals(player.getName())) return handleFly(player, !player.getAllowFlight());
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null)
            if (target.getName().equals(args[0])) return handleFlyOther(player, target, !target.isInvulnerable());
        player.sendMessage(ChatColor.DARK_RED + "Player not found");
        return true;

    }
}