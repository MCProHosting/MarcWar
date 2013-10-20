package com.mcprohosting.plugins.marcwar.entities;

import com.mcprohosting.plugins.marcwar.MarcWar;
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
	double maxPlayersOnTeam;

	public Team(String color) {
		this.color = color;
		this.players = new HashMap<String, Participant>();
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
		maxPlayersOnTeam = maxPlayersOnTeam + 1.0;
	}

	public void removePlayer(String name) {
		this.players.remove(name);
		TeamHandler.removePlayer(name);

		if (MarcWar.getGameProgress().equalsIgnoreCase("starting")) {
			maxPlayersOnTeam = maxPlayersOnTeam - 1.0;
		}
	}

	public boolean lossByKills() {
		if ((players.size() / maxPlayersOnTeam) < 0.1) {
			return true;
		}
		return false;
	}

}
