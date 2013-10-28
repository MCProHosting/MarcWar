package com.mcprohosting.plugins.marcwar;

import com.mcprohosting.plugins.marcwar.commands.*;
import com.mcprohosting.plugins.marcwar.listeners.PlayerListener;
import com.mcprohosting.plugins.marcwar.utilities.Game;
import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import com.mcprohosting.plugins.marcwar.utilities.proxy.BungeeCordManager;
import com.mcprohosting.plugins.marcwar.utilities.proxy.LilyPadManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MarcWar extends JavaPlugin {

	private static Plugin plugin;
	private static String gameProgress;
	private static String proxyMode;
	private static String returnServer;

	public void onEnable() {
		// Allow this to be accessed statically
		plugin = this;

		// Set game progress
		gameProgress = "Starting";

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

		// Proxy Registration
		setProxyMode();
		switch (proxyMode.toLowerCase()) {
			case "bungeecord":
				Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
				new BungeeCordManager();
				break;
			case "lilypad":
				new LilyPadManager();
				break;
			default:
				break;
		}

		// Start the game
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Game(), 20, 20);
	}

	public void onDisable() {

	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public void registerCommands() {
		getCommand("setspawn").setExecutor(new SetSpawn());
		getCommand("setflag").setExecutor(new SetFlag());
		getCommand("setcapture").setExecutor(new SetCapture());
		getCommand("setlobby").setExecutor(new SetLobby());
		getCommand("startgame").setExecutor(new StartGame());
	}

	public static String getGameProgress() {
		return gameProgress;
	}

	public static void setGameProgress(String status) {
		gameProgress = status;
	}

	private void setProxyMode() {
		ConfigurationSection proxy = getConfig().getConfigurationSection("proxy");
		proxyMode = proxy.getString("mode");
		returnServer = proxy.getString("server");
		saveProxyToConfig();
	}

	public void saveProxyToConfig() {
		ConfigurationSection section = new YamlConfiguration();
		section.set("mode", getProxyMode());
		section.set("server", getReturnServer());

		MarcWar.getPlugin().getConfig().set("proxy", section);
		MarcWar.getPlugin().saveConfig();
	}

	public static String getProxyMode() {
		return proxyMode;
	}

	public static String getReturnServer() {
		return returnServer;
	}

}
