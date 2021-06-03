package me.elexation.exxentials.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class sandFall implements Listener {
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	private void onSandFall(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.AIR) {
			if (event.getBlock().getType() == Material.SAND) {
				event.setCancelled(true);
				event.getBlock().getState().update(false, false);
			}
		}
	}

	@EventHandler
	private void onEntityExplode(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType().equals(EntityType.CREEPER)) {
			Creeper creeper = (Creeper) entity;
			creeper.eject();
			event.setCancelled(true);
			World world = event.getLocation().getWorld();
			world.spawnEntity(event.getLocation(), EntityType.CREEPER);
		}
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() == EntityType.CREEPER) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void onEntityTarget(EntityTargetEvent event) {
		if (event.getEntity().getType() == EntityType.CREEPER) {
			event.setCancelled(true);
		}
	}
}
