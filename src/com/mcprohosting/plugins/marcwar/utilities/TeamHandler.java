package com.mcprohosting.plugins.marcwar.utilities;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Participant;
import com.mcprohosting.plugins.marcwar.entities.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Random;

public class TeamHandler {

	private static Team blue;
	private static Team red;

	public static void inititializeTeams() {
		blue = new Team("blue");
		red = new Team("red");
	}

	public static void setupSpawnsFromConfiguration() {
		ConfigurationSection config = MarcWar.getPlugin().getConfig().getConfigurationSection("spawns");

		ConfigurationSection spawnblue = config.getConfigurationSection("spawnblue");
		blue.setSpawn(new Location(Bukkit.getWorlds().get(0), spawnblue.getDouble("x"), spawnblue.getDouble("y"),
				spawnblue.getDouble("z"), new Float(spawnblue.getDouble("yaw")), new Float(spawnblue.getDouble("pitch"))));

		ConfigurationSection spawnred = config.getConfigurationSection("spawnred");
		red.setSpawn(new Location(Bukkit.getWorlds().get(0), spawnred.getDouble("x"), spawnred.getDouble("y"),
				spawnred.getDouble("z"), new Float(spawnred.getDouble("yaw")), new Float(spawnred.getDouble("pitch"))));
	}

	public static void setSpawnLocation(String color, Location location) {
		switch (color) {
			case "blue":
				blue.setSpawn(location);
				saveSpawnToConfiguration(color, location);
			case "red":
				red.setSpawn(location);
				saveSpawnToConfiguration(color, location);
			default:
				break;
		}
	}

	public static void saveSpawnToConfiguration(String color, Location location) {
		ConfigurationSection section = new YamlConfiguration();
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());

		MarcWar.getPlugin().getConfig().set("spawns.spawn" + color, section);
		MarcWar.getPlugin().saveConfig();
	}

	public static Location assignTeam(String name) {
		int bluePlayers = blue.getPlayers().size();
		int redPlayres = red.getPlayers().size();

		if (bluePlayers == redPlayres) {
			return assignRandom(name);
		} else if (bluePlayers > redPlayres) {
			red.addPlayer(name, new Participant(name, "red"));
			return red.getSpawn();
		} else {
			blue.addPlayer(name, new Participant(name, "blue"));
			return blue.getSpawn();
		}
	}

	public static Location assignRandom(String name) {
		Random random = new Random();
		switch (random.nextInt(2) + 1) {
			case 1:
				blue.addPlayer(name, new Participant(name, "blue"));
				return blue.getSpawn();
			case 2:
				red.addPlayer(name, new Participant(name, "red"));
				return red.getSpawn();
			default:
				break;
		}
		return null;
	}

}
