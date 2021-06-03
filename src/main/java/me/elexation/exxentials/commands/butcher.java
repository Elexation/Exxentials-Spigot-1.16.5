package me.elexation.exxentials.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class butcher implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (!player.hasPermission("exxentials.butcher")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			return true;
		}
		World world = player.getWorld();
		List<LivingEntity> entities = world.getLivingEntities();
		int counter = 0;
		for (LivingEntity entity : entities) {
			if (entity instanceof Monster) {
				counter++;
				entity.remove();
			}
		}
		player.sendMessage(ChatColor.GOLD + String.format("Removed %d entities", counter));
		return true;
	}

}
