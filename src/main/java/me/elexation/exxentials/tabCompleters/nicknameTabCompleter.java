package me.elexation.exxentials.tabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class nicknameTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return null;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.nickname"))
            return null;
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                playerNames.add(onlinePlayer.getName());
            return playerNames;
        }
        return null;
    }

}
