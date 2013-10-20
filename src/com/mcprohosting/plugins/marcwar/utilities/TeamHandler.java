package com.mcprohosting.plugins.marcwar.utilities;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Participant;
import com.mcprohosting.plugins.marcwar.entities.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TeamHandler {

	private static Team blue;
	private static Team red;
	private static Map<String, Team> players;

	public static void inititializeTeams() {
		blue = new Team("blue");
		red = new Team("red");
		players = new HashMap<String, Team>();
	}

	public static void setupSpawnsFromConfiguration() {
		ConfigurationSection config = MarcWar.getPlugin().getConfig().getConfigurationSection("spawns");

		ConfigurationSection spawnblue = config.getConfigurationSection("spawnblue");
		blue.setSpawn(new Location(Bukkit.getWorlds().get(0), spawnblue.getDouble("x"), spawnblue.getDouble("y"),
				spawnblue.getDouble("z"), new Float(spawnblue.getDouble("yaw")), new Float(spawnblue.getDouble("pitch"))));

		ConfigurationSection spawnred = config.getConfigurationSection("spawnred");
		red.setSpawn(new Location(Bukkit.getWorlds().get(0), spawnred.getDouble("x"), spawnred.getDouble("y"),
				spawnred.getDouble("z"), new Float(spawnred.getDouble("yaw")), new Float(spawnred.getDouble("pitch"))));

		ConfigurationSection flag = MarcWar.getPlugin().getConfig().getConfigurationSection("flag");
		red.setFlag(new Location(Bukkit.getWorlds().get(0), flag.getDouble("x"), flag.getDouble("y"), flag.getDouble("z")));

		ConfigurationSection capture = MarcWar.getPlugin().getConfig().getConfigurationSection("capture");
		blue.setFlag(new Location(Bukkit.getWorlds().get(0), capture.getDouble("x"), capture.getDouble("y"), capture.getDouble("z")));
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

	public static void setFlagLocation(Location location) {
		red.setFlag(location);
		saveFlagToConfiguration(location);
	}

	public static void setCaptureLocation(Location location) {
		blue.setFlag(location);
		saveCaptureToConfiguration(location);
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

	public static void saveFlagToConfiguration(Location location) {
		ConfigurationSection section = new YamlConfiguration();
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());

		MarcWar.getPlugin().getConfig().set("flag", section);
		MarcWar.getPlugin().saveConfig();
	}

	public static void saveCaptureToConfiguration(Location location) {
		ConfigurationSection section = new YamlConfiguration();
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());

		MarcWar.getPlugin().getConfig().set("capture", section);
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
				return null;
		}
	}

	public static void addPlayer(String name, Team team) {
		players.put(name, team);
	}

	public static void removePlayer(String name) {
		players.remove(name);
	}

	public static Team getPlayerTeam(String name) {
		return players.get(name);
	}

	public static Team getTeam(String color) {
		switch(color.toLowerCase()) {
			case "blue":
				return blue;
			case "red":
				return red;
			default:
				return null;
		}
	}

}
