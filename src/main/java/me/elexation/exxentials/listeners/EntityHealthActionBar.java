package me.elexation.exxentials.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class EntityHealthActionBar implements Listener {

	private static Map<LivingEntity, Timer> HealthTimerList = new HashMap<LivingEntity, Timer>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player || e.getDamager() instanceof Arrow)
				&& e.getEntity() instanceof LivingEntity) {
			Player player;
			if (e.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) e.getDamager();
				if (!(arrow.getShooter() instanceof Player))
					return;
				player = (Player) arrow.getShooter();
			} else
				player = (Player) e.getDamager();
			LivingEntity entity = (LivingEntity) e.getEntity();
			if (HealthTimerList.containsKey(entity)) {
				HealthTimerList.get(entity).cancel();
			}
			int numHearts = 0;
			if (entity.getMaxHealth() >= 40)
				numHearts = (int) (entity.getHealth() - e.getFinalDamage()) / 5;
			else
				numHearts = (int) (entity.getHealth() - e.getFinalDamage()) / 2;
			if (numHearts < 1 && !((entity.getHealth() - e.getFinalDamage()) < 1))
				numHearts = 1;
			String hearts = "";
			for (int i = 1; i <= numHearts; i++)
				hearts += "\u2764";
			// int currentHealth;
			// if ((entity.getHealth()-e.getFinalDamage()) < 0) currentHealth = 0;
			// else currentHealth = (int)entity.getHealth()-(int)e.getFinalDamage();
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText(ChatColor.DARK_RED + hearts));
			entity.setCustomName(ChatColor.DARK_RED + hearts + "");
			entity.setCustomNameVisible(true);
			Timer timer = new Timer();
			HealthTimerList.put(entity, timer);
			HealthTimerList.get(entity).schedule(new TimerTask() {
				@Override
				public void run() {
					HealthTimerList.get(entity).purge();
					HealthTimerList.remove(entity);
					entity.setCustomName("");
					entity.setCustomNameVisible(false);
				}
			}, 5000);
		}
	}
}
