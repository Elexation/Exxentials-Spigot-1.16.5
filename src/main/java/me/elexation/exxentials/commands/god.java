package me.elexation.exxentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class god implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.god")){
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) return handleGodMode(player, !player.isInvulnerable());
        if (args[0].equals(player.getName())) return handleGodMode(player, !player.isInvulnerable());
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null)
            if (target.getName().equals(args[0])) return handleGodModeOther(player, target, !target.isInvulnerable());
        player.sendMessage(ChatColor.DARK_RED + "Player not found");
        return true;
    }

    private boolean handleGodMode(Player player, boolean state){
        player.setInvulnerable(state);
        player.sendMessage(ChatColor.GOLD + String.format("God mode %s", state ? "activated" : "deactivated"));
        return true;
    }

    private boolean handleGodModeOther(Player player, Player target, boolean state){
        target.setInvulnerable(state);
        target.sendMessage(ChatColor.GOLD + String.format("God mode %s", state ? "activated" : "deactivated"));
        player.sendMessage(ChatColor.GOLD + String.format("God mode %s for %s", state ? "activated" : "deactivated", target.getName()));
        return true;
    }
}
