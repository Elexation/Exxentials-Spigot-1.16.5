package me.elexation.exxentials.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

public class fireFromBow implements Listener {

	private final static List<Arrow> arrows = new ArrayList<>();

	@EventHandler
	public void onShootBow(EntityShootBowEvent e) {
		if (e.getProjectile() instanceof Arrow)
			if (e.getBow().containsEnchantment(Enchantment.ARROW_FIRE))
				arrows.add((Arrow) e.getProjectile());

	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		Block block = e.getHitBlock();
		BlockFace blockFace = e.getHitBlockFace();
		Entity entity = e.getEntity();
		if (block != null && blockFace != null && entity instanceof Arrow){
			Arrow arrow = (Arrow) entity;
			if (block.getType().isFlammable() && arrows.contains(arrow)) {
				arrows.remove(arrow);
				if (!blockFace.equals(BlockFace.UP)){
					Location loc = block.getRelative(blockFace).getLocation();
					loc.setY(loc.getY()-1);
					if (loc.getBlock().getType().isAir()){
						loc = block.getLocation();
						loc.setY(loc.getY()+1);
						if (loc.getBlock().getType().isAir()){
							loc.getBlock().setType(Material.FIRE);
							return;
						}

					}
				}
				block.getRelative(blockFace).setType(Material.FIRE);
			}
		}
	}
}
