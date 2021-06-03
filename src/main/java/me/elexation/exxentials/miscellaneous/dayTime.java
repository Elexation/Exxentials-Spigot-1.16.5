package me.elexation.exxentials.miscellaneous;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class dayTime extends BukkitRunnable {

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            if (!world.getName().endsWith("_the_end") && !world.getName().endsWith("_nether")) {
                world.setTime(1);
            }
        }
    }

}
