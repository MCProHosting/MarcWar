package com.mcprohosting.plugins.marcwar;

import com.mcprohosting.plugins.marcwar.commands.SetSpawn;
import com.mcprohosting.plugins.marcwar.listeners.PlayerListener;
import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import lilypad.client.connect.api.Connect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MarcWar extends JavaPlugin {

	private static Plugin plugin;
	private static Connect connect;

	public void onEnable() {
		// Allow this to be accessed statically
		plugin = this;

		// Initialize teams
		TeamHandler.inititializeTeams();

		// Save the default config to file
		saveDefaultConfig();

		// Load team spawns from config
		TeamHandler.setupSpawnsFromConfiguration();

		// Register listeners
		registerListeners();

		// Register commands
		registerCommands();

		// Register lilypad connection
		//connect = plugin.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}

	public void onDisable() {

	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static Connect getConnect() {
		return connect;
	}

	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public void registerCommands() {
		getCommand("setspawn").setExecutor(new SetSpawn());
	}

}
