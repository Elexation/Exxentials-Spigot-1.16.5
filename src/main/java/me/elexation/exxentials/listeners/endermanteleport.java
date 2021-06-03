package me.elexation.exxentials.listeners;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class endermanteleport implements Listener {
	@EventHandler
	public void onTeleport(EntityTeleportEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onArrowHit(ProjectileHitEvent event) {
		if (event.getEntity().getType() == EntityType.ARROW) {
			event.getHitEntity().getType();
			if (event.getHitEntity().getType() == EntityType.ENDERMAN) {
				if (event.getEntity().getShooter() instanceof LivingEntity) {
					LivingEntity shooter = (LivingEntity) event.getEntity().getShooter();
					Enderman enderman = (Enderman) event.getHitEntity();
					event.getEntity().remove();
					enderman.setArrowsInBody(1);
					enderman.damage(15);
					enderman.setTarget(shooter);
				}
			}
		}
	}
}
