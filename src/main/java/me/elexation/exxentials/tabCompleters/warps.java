package me.elexation.exxentials.tabCompleters;

import me.elexation.exxentials.Exxentials;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class warps implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return null;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.warp"))
            return null;
        if (args.length == 1) {
            String warpNamesPath = "settings.worlds." + player.getWorld().getUID() + ".warpNames";
            String warpLocationsPath = "settings.worlds." + player.getWorld().getUID() + ".warpLocations";
            List<String> warps = Exxentials.plugin.getConfig().getStringList(warpNamesPath);
            List<String> newWarps = Exxentials.plugin.getConfig().getStringList(warpNamesPath);
            for (String warp : warps) {
                Location loc = Exxentials.plugin.getConfig().getLocation(warpLocationsPath + "." + warp);
                if (loc == null || !loc.isWorldLoaded()) {
                    Exxentials.plugin.getConfig().set(warpLocationsPath + "." + warp, null);
                    Exxentials.plugin.getConfig().set(warpNamesPath + "." + warp, null);
                    newWarps.remove(warp);
                }
            }
            Exxentials.plugin.saveConfig();
            return newWarps;
        }
        return null;
    }
}
