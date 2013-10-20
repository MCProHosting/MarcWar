package com.mcprohosting.plugins.marcwar.listeners;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Team;
import com.mcprohosting.plugins.marcwar.utilities.FlagReset;
import com.mcprohosting.plugins.marcwar.utilities.FontFormat;
import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import com.mcprohosting.plugins.marcwar.utilities.UtilityMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		//UtilityMethods.redirectToServer("1-marcwars-lobby", event.getEntity());
	}

	@EventHandler(priority =  EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		Team playerTeam = TeamHandler.getPlayerTeam(event.getPlayer().getName());
		Team red = TeamHandler.getTeam("red");

		if (playerTeam == null) {
			MarcWar.getPlugin().getLogger().info("Team is null");
			return;
		}

		if (event.getBlock().getLocation().getBlockX() == red.getFlag().getBlockX() &&
				event.getBlock().getLocation().getBlockY() == red.getFlag().getBlockY() &&
				event.getBlock().getLocation().getBlockZ() == red.getFlag().getBlockZ()){
			event.setCancelled(true);

			if (playerTeam.getColor().equalsIgnoreCase("blue")) {
				event.getBlock().setType(Material.AIR);
				UtilityMethods.dropFlag(event.getBlock().getLocation());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDisconnect(PlayerQuitEvent event) {
		TeamHandler.getPlayerTeam(event.getPlayer().getName()).removePlayer(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		Team playerTeam = TeamHandler.getPlayerTeam(player.getName());

		ItemStack stack = event.getItem().getItemStack();
		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName()) {
			if (meta.getDisplayName().equalsIgnoreCase(FontFormat.stripColor(meta.getDisplayName()))) {
				if (playerTeam.getColor().equalsIgnoreCase("blue")) {
					Bukkit.broadcastMessage("The flag has been captured by " + player.getName());
				} else {
					Bukkit.broadcastMessage("The flag has been reclaimed by " + player.getName());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Team playerTeam = TeamHandler.getPlayerTeam(player.getName());

		ItemStack stack = event.getItemDrop().getItemStack();
		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName()) {
			if (meta.getDisplayName().equalsIgnoreCase(FontFormat.stripColor(meta.getDisplayName()))) {
				if (playerTeam.getColor().equalsIgnoreCase("blue")) {
					Bukkit.broadcastMessage("The flag has been dropped by " + player.getName() + " at x: " +
					event.getPlayer().getLocation().getBlockX() + ", y: " + event.getPlayer().getLocation().getBlockY() +
					", z: " + event.getPlayer().getLocation().getBlockZ() +"!");
					FlagReset.setFlag(event.getItemDrop());
					Bukkit.getScheduler().runTaskLater(MarcWar.getPlugin(), new FlagReset(), 20 * 60);
				}
			}
		}
	}

}
