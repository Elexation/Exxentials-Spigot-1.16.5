package me.elexation.exxentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class speed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission("exxentials.speed")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) {
            float walkSpeed = (float)0.2;
            float flySpeed = (float)0.1;
            player.setWalkSpeed(walkSpeed);
            player.setFlySpeed(flySpeed);
            player.sendMessage(ChatColor.GOLD + "Speed set to default");
            return true;
        }
        try{
            int input = Integer.parseInt(args[0]);
            if (input > 40) input = 40;
            float speed = (float)(Double.valueOf(input) / 40.0);
            player.setFlySpeed(speed);
            player.setWalkSpeed(speed);
            player.sendMessage(ChatColor.GOLD + "Speed set to " + input);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
