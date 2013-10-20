package com.mcprohosting.plugins.marcwar.utilities;

import com.mcprohosting.plugins.marcwar.MarcWar;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResult;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class UtilityMethods {

	public static void redirectToServer(String server, final Player player) {
		try {
			FutureResult<RedirectResult> futureResult = MarcWar.getConnect().request(new RedirectRequest(server, player.getName()));
			RedirectResult result = futureResult.await(5000L);
			if (!result.getStatusCode().equals(StatusCode.SUCCESS)) {
				player.sendMessage(ChatColor.RED.toString() + "That server is current not available: !");
				Bukkit.getLogger().warning("RedirectRequest failed for player: " + player.getName());
			}
		} catch (RequestException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
		} catch (InterruptedException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
			Bukkit.getLogger().warning("RedirectRequest interrupted for player: " + player.getName());
		}
	}

	public static Block getBlockLookedAt(Player player) {
		return player.getTargetBlock(null, 200);
	}

	public static void addFlag(Location location) {
		Block block = location.getBlock();
		block.setType(Material.WOOL);
		block.setData((byte) 14);
	}

	public static void dropFlag(Location location) {
		ItemStack flag = new ItemStack(Material.WOOL, 1, (byte)14);
		ItemMeta meta = flag.getItemMeta();

		meta.setDisplayName(ChatColor.GOLD + "Flag");
		ArrayList<String> lore = new ArrayList<String>();

		meta.setLore(lore);
		flag.setItemMeta(meta);
		Item item = location.getWorld().dropItem(location, flag);
	}

}
