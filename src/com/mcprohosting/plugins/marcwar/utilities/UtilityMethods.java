package com.mcprohosting.plugins.marcwar.utilities;

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
