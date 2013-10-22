package com.mcprohosting.plugins.marcwar.listeners;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Team;
import com.mcprohosting.plugins.marcwar.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

	private static FlagReset flagReset;

	@EventHandler(priority = EventPriority.NORMAL)
	public void onLogin(PlayerLoginEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("started")) {
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Game In Progress");
		}
		if (MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Game Is Restarting");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent event) {
		TeamHandler.assignTeam(event.getPlayer().getName());
		if (!(TeamHandler.getLobby().getBlockY() == 0)) {
			MarcWar.getPlugin().getLogger().info("Teleporting player to lobby");
			event.getPlayer().teleport(TeamHandler.getLobby());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDeath(PlayerDeathEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.getEntity().setHealth(10.0);
			return;
		}

		if (MarcWar.getProxyEnabled()) {
			LilyPadManager.redirectToServer("lobby", event.getEntity());
		} else {
			event.getEntity().kickPlayer("You have been eliminated from play!");
		}
	}

	@EventHandler(priority =  EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}

		Team playerTeam = TeamHandler.getPlayerTeam(event.getPlayer().getName());
		Team red = TeamHandler.getTeam("red");

		if (playerTeam == null) {
			MarcWar.getPlugin().getLogger().info("Team is null");
			return;
		}

		if (playerTeam.getColor().equalsIgnoreCase("red")) {
			event.setCancelled(true);
		}

		if (event.getBlock().getLocation().getBlockX() == red.getFlag().getBlockX() &&
				event.getBlock().getLocation().getBlockY() == red.getFlag().getBlockY() &&
				event.getBlock().getLocation().getBlockZ() == red.getFlag().getBlockZ()){
			if (playerTeam.getColor().equalsIgnoreCase("blue")) {
				event.getBlock().setType(Material.AIR);
				UtilityMethods.dropFlag(event.getBlock().getLocation());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDisconnect(PlayerQuitEvent event) {
		if (TeamHandler.getPlayerTeam(event.getPlayer().getName()) != null) {
			TeamHandler.getPlayerTeam(event.getPlayer().getName()).removePlayer(event.getPlayer().getName());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryPickup(PlayerPickupItemEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}

		Player player = event.getPlayer();
		Team playerTeam = TeamHandler.getPlayerTeam(player.getName());

		ItemStack stack = event.getItem().getItemStack();
		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName()) {
			String message;
			if (meta.getDisplayName().equalsIgnoreCase(FontFormat.stripColor(meta.getDisplayName()))) {
				if (flagReset != null) {
					FlagReset.setFlag(null);
				}

				if (playerTeam.getColor().equalsIgnoreCase("blue")) {
					message = FontFormat.BOLD + "The flag has been taken by " + player.getName();
					Bukkit.broadcastMessage(FontFormat.RED + message);
				} else {
					message = FontFormat.BOLD + "The flag has been reclaimed by " + player.getName();
					Bukkit.broadcastMessage(FontFormat.BLUE + message);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryDrop(PlayerDropItemEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}

		Player player = event.getPlayer();
		Team playerTeam = TeamHandler.getPlayerTeam(player.getName());

		ItemStack stack = event.getItemDrop().getItemStack();
		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName()) {
			if (meta.getDisplayName().equalsIgnoreCase(FontFormat.stripColor(meta.getDisplayName()))) {
				if (playerTeam.getColor().equalsIgnoreCase("blue")) {
					String message = FontFormat.BOLD + "The flag has been dropped by " + player.getName() + " at x: " +
					event.getPlayer().getLocation().getBlockX() + ", y: " + event.getPlayer().getLocation().getBlockY() +
					", z: " + event.getPlayer().getLocation().getBlockZ() +"!";
					Bukkit.broadcastMessage(FontFormat.RED + message);
					FlagReset.setFlag(event.getItemDrop());
					flagReset = new FlagReset();
					Bukkit.getScheduler().runTaskLater(MarcWar.getPlugin(), flagReset, 20 * 30);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}

		Team team = TeamHandler.getPlayerTeam(event.getPlayer().getName());

		ItemStack stack = event.getItemInHand();
		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName()) {
			if (meta.getDisplayName().equalsIgnoreCase(FontFormat.stripColor(meta.getDisplayName()))) {
				if (event.getBlockPlaced().getLocation().getBlockX() == team.getFlag().getBlockX() &&
						event.getBlockPlaced().getLocation().getBlockY() == team.getFlag().getBlockY() &&
						event.getBlockPlaced().getLocation().getBlockZ() == team.getFlag().getBlockZ()) {
					String message = FontFormat.BOLD + "The flag has been captured. Blue team wins!";
					Bukkit.broadcastMessage(FontFormat.BLUE + message);
					MarcWar.setGameProgress("gameover");
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onMobTarget(EntityTargetLivingEntityEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}

		if (event.getTarget() instanceof Player) {
			Player player = (Player) event.getTarget();
			if (TeamHandler.getPlayerTeam(player.getName()).getColor().equals("red")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}

		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Player attacked = (Player) event.getEntity();
			if (TeamHandler.getPlayerTeam((damager.getName())) == TeamHandler.getPlayerTeam(attacked.getName())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamage(EntityDamageEvent event) {
		if (MarcWar.getGameProgress().equalsIgnoreCase("starting") || MarcWar.getGameProgress().equalsIgnoreCase("gameover")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryInteract(InventoryClickEvent event) {
		if (event.getCurrentItem().getType() != null) {
			ItemStack stack = event.getCurrentItem();
			ItemMeta meta = stack.getItemMeta();

			if (meta != null) {
				if (meta.getDisplayName().equalsIgnoreCase("flag")) {
					return;
				}
			}

			if (event.getCurrentItem().getType().equals(Material.WOOL)) {
				event.setCancelled(true);
			}
		}
	}
}
