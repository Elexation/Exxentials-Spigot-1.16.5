package me.elexation.exxentials.commands;

import me.elexation.exxentials.datamanagers.configSetup;
import me.elexation.exxentials.datamanagers.nicknameConfigSetup;
import me.elexation.exxentials.listeners.EntityHealthActionBar;
import me.elexation.exxentials.listeners.JoinLeaveMessages;
import me.elexation.exxentials.listeners.colorCodedChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class exreload implements CommandExecutor {

    private final JavaPlugin plugin;
    private final JoinLeaveMessages messages;
    private final colorCodedChat chat;
    private final afk afk;
    private final EntityHealthActionBar HealthActionBar;

    public exreload(JavaPlugin plugin, JoinLeaveMessages messages, colorCodedChat chat, afk afk, EntityHealthActionBar HealthActionBar)  {
        this.messages = messages;
        this.chat = chat;
        this.afk = afk;
        this.plugin = plugin;
        this.HealthActionBar = HealthActionBar;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            handleReloads();
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.reload")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        handleReloads();
        player.sendMessage(ChatColor.GREEN + "Exxentials reloaded");
        return true;
    }

    private void handleReloads(){
        plugin.reloadConfig();
        configSetup.setup(plugin);
        nicknameConfigSetup.setup();
        chat.reload();
        messages.reload();
        afk.setIsRunnableOn(plugin.getConfig().getBoolean("settings.AfkTimer"));
        HealthActionBar.setIsOn(plugin.getConfig().getBoolean("settings.HealthActionBar"));
        plugin.getLogger().info(ChatColor.GREEN + "[Exxentials] Reloaded! ");
    }
}
