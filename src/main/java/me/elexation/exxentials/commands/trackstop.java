package me.elexation.exxentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class trackstop implements CommandExecutor {

    private final track track;

    public trackstop(track track){
        this.track = track;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.track")){
            player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
            return true;
        }
        if (!track.getTrackedPlayers().containsKey(player)){
            player.sendMessage(ChatColor.DARK_RED + "You are not tracking anyone");
            return true;
        }
        player.sendMessage(ChatColor.GOLD + "Tracking stopped");
        track.getTrackedPlayers().remove(player);
        return true;
    }
}
