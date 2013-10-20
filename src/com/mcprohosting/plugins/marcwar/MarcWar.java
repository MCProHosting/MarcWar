package com.mcprohosting.plugins.marcwar;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MarcWar extends JavaPlugin {

	private static Plugin plugin;

	public void onEnable() {
		// Allow this to be accessed statically
		plugin = this;
	}

	public void onDisable() {

	}

	public Plugin getPlugin() {
		return plugin;
	}

}
