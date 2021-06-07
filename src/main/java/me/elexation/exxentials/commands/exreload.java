package me.elexation.exxentials.commands;

import me.elexation.exxentials.Exxentials;
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

public class exreload implements CommandExecutor {

    private final JoinLeaveMessages messages;
    private final colorCodedChat chat;
    private final afk afk;
    private final EntityHealthActionBar HealthActionBar;

    public exreload(JoinLeaveMessages messages, colorCodedChat chat, afk afk, EntityHealthActionBar HealthActionBar)  {
        this.messages = messages;
        this.chat = chat;
        this.afk = afk;
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
        Exxentials.plugin.reloadConfig();
        configSetup.setup(Exxentials.plugin);
        nicknameConfigSetup.setup();
        chat.reload();
        messages.reload();
        afk.setIsRunnableOn(Exxentials.plugin.getConfig().getBoolean("settings.AfkTimer"));
        HealthActionBar.setIsOn(Exxentials.plugin.getConfig().getBoolean("settings.HealthActionBar"));
        Exxentials.plugin.getLogger().info(ChatColor.GREEN + "[Exxentials] Reloaded! ");
    }
}
