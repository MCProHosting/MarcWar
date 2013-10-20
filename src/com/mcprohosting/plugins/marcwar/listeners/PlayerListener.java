package com.mcprohosting.plugins.marcwar.listeners;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Team;
import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent event) {
		Location location = TeamHandler.assignTeam(event.getPlayer().getName());
		if (!(location.getBlockY() == 0)) {
			event.getPlayer().teleport(location);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDeath(PlayerDeathEvent event) {
		TeamHandler.getPlayerTeam(event.getEntity().getName()).removePlayer(event.getEntity().getName());
		//UtilityMethods.redirectToServer("1-marcwars-lobby", event.getEntity());
	}

	@EventHandler(priority =  EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		Team blue = TeamHandler.getPlayerTeam(event.getPlayer().getName());

		if (blue == null) {
			MarcWar.getPlugin().getLogger().info("Team is null");
			return;
		}

		if (blue.getColor().equalsIgnoreCase("blue")) {
			Team red = TeamHandler.getTeam("red");
			if (event.getBlock().getLocation().getBlockX() == red.getFlag().getBlockX() &&
					event.getBlock().getLocation().getBlockY() == red.getFlag().getBlockY() &&
					event.getBlock().getLocation().getBlockZ() == red.getFlag().getBlockZ()) {
				Bukkit.broadcastMessage("The flag has been captured by " + event.getPlayer().getName());
			}
		} else {
			event.setCancelled(true);
		}
	}

}
