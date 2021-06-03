package me.elexation.exxentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class sunnyDay implements Listener {
	@EventHandler
	public void onRain(WeatherChangeEvent e) {
		if (e.toWeatherState())
			e.setCancelled(true);
	}
}
