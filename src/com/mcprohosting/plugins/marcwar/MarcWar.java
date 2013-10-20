package com.mcprohosting.plugins.marcwar;

import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MarcWar extends JavaPlugin {

	private static Plugin plugin;

	public void onEnable() {
		// Allow this to be accessed statically
		plugin = this;

		// Load team spawns from config
		TeamHandler.setupSpawnsFromConfiguration();
	}

	public void onDisable() {

	}

	public static Plugin getPlugin() {
		return plugin;
	}

}
