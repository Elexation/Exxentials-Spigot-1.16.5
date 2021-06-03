package me.elexation.exxentials.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reply implements CommandExecutor {

    private final msg msgCommand;

    public reply(msg msg) {
        this.msgCommand = msg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.msg")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1)
            return false;
        if (!msgCommand.getReplyList().containsKey(player)) {
            player.sendMessage(ChatColor.DARK_RED + "You have not messaged anyone");
            return true;
        }
        Player target = msgCommand.getReplyList().get(player);
        String message = msgCommand.getMessage(args, true);
        player.sendMessage(
                ChatColor.translateAlternateColorCodes('&', String.format("&8(&aTo &7%s&8) &7", target.getName()))
                        + message);
        target.sendMessage(
                ChatColor.translateAlternateColorCodes('&', String.format("&8(&aFrom &7%s&8) &7", player.getName()))
                        + message);
        return true;
    }

}
