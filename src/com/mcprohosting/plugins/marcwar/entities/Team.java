package com.mcprohosting.plugins.marcwar.entities;

import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import com.mcprohosting.plugins.marcwar.utilities.UtilityMethods;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class Team {

	String color;
	Location spawn;
	Location flag;
	Map<String, Participant> players;
	boolean capturedFlag;

	public Team(String color) {
		this.color = color;
		this.players = new HashMap<String, Participant>();
		this.capturedFlag = false;
	}

	public String getColor() {
		return this.color;
	}

	public void setSpawn(Location location) {
		this.spawn = location;
	}

	public Location getSpawn() {
		return this.spawn;
	}

	public void setFlag(Location location) {
		this.flag = location;

		if (color.equalsIgnoreCase("red")) {
			UtilityMethods.addFlag(location);
		}
	}

	public Location getFlag() {
		return this.flag;
	}

	public Map<String, Participant> getPlayers() {
		return players;
	}

	public void addPlayer(String name, Participant player) {
		this.players.put(name, player);
		TeamHandler.addPlayer(name, this);
	}

	public void removePlayer(String name) {
		this.players.remove(name);
		TeamHandler.removePlayer(name);
	}

	public boolean capturedFlag() {
		return capturedFlag;
	}

}
