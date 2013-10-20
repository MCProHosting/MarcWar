package com.mcprohosting.plugins.marcwar.listeners;

import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent event) {
		Location location = TeamHandler.assignTeam(event.getPlayer().getName());
		if (!(location.getBlockY() == 0)) {
			event.getPlayer().teleport(location);
		}
	}

}
