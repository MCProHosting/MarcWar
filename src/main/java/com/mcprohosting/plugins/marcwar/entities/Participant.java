package com.mcprohosting.plugins.marcwar.entities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Participant {

	private String name;
	private String team;
	private boolean hasFlag;
	private boolean isSpectating;

	public Participant(String name, String team) {
		this.name = name;
		this.team = team;
		this.hasFlag = false;
		this.isSpectating = false;

		this.setInventory();
	}

	public String getName() {
		return this.name;
	}

	public String getTeam() {
		return this.team;
	}

	public boolean hasFlag() {
		return this.hasFlag;
	}

	public void setFlag() {
		hasFlag = !hasFlag;
	}

	public void setInventory() {
		Player player = Bukkit.getPlayer(name);
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.addItem(new ItemStack(Material.IRON_AXE));
		inventory.addItem(new ItemStack(Material.DIAMOND_SWORD));
		inventory.addItem(new ItemStack(Material.BREAD));

		inventory.setBoots(new ItemStack(Material.IRON_BOOTS));
		inventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		inventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));

		if (team.equalsIgnoreCase("blue")) {
			inventory.setHelmet(new ItemStack(Material.WOOL, 1, (short) 11));
		} else {
			inventory.setHelmet(new ItemStack(Material.WOOL, 1, (short) 14));
		}
	}

	public void setSpectating() {
		isSpectating = true;
	}

	public boolean isSpectating() {
		return isSpectating;
	}

}
