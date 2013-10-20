package com.mcprohosting.plugins.marcwar.utilities;

import com.mcprohosting.plugins.marcwar.MarcWar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class TeamHandler {

	private static Location blueSpawn;
	private static Location redSpawn;

	public static void setupSpawnsFromConfiguration() {
		ConfigurationSection config = MarcWar.getPlugin().getConfig().getConfigurationSection("spawns");

		ConfigurationSection spawnblue = config.getConfigurationSection("spawnblue");
		blueSpawn = new Location(Bukkit.getWorlds().get(0), spawnblue.getDouble("x"), spawnblue.getDouble("y"),
				spawnblue.getDouble("z"), new Float(spawnblue.getDouble("yaw")), new Float(spawnblue.getDouble("pitch")));

		ConfigurationSection spawnred = config.getConfigurationSection("spawnred");
		redSpawn = new Location(Bukkit.getWorlds().get(0), spawnred.getDouble("x"), spawnred.getDouble("y"),
				spawnred.getDouble("z"), new Float(spawnred.getDouble("yaw")), new Float(spawnred.getDouble("pitch")));
	}

	public static void setSpawnLocation(String color, Location location) {
		switch (color) {
			case "blue":
				blueSpawn = location;
				saveSpawnToConfiguration(color, location);
			case "red":
				redSpawn = location;
				saveSpawnToConfiguration(color, location);
			default:
				break;
		}
	}

	public static void saveSpawnToConfiguration(String color, Location location) {
		ConfigurationSection section = new YamlConfiguration();
		section.set("x", location.getY());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());

		MarcWar.getPlugin().getConfig().set("spawns.spawn" + color, section);
	}

}
