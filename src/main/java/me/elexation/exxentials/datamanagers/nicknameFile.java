package me.elexation.exxentials.datamanagers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class nicknameFile {

    private static File file;
    private static FileConfiguration nicknamesFile;

    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Exxentials")).getDataFolder(), "nicknames.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Exxentials")).getLogger().log(Level.SEVERE,
                        "Failed to create nicknames.yml");
            }
        }
        nicknamesFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return nicknamesFile;
    }

    public static void save() {
        try {
            nicknamesFile.save(file);
        } catch (IOException e) {
            Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Exxentials")).getLogger().log(Level.SEVERE,
                    "Failed save to nicknames.yml");
        }
    }

    public static void reload() {
        nicknamesFile = YamlConfiguration.loadConfiguration(file);
    }

}
