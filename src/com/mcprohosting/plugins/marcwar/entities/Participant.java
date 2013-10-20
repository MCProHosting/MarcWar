package com.mcprohosting.plugins.marcwar.entities;

public class Participant {

	private String name;
	private String team;
	private boolean hasFlag;

	public Participant(String name, String team) {
		this.name = name;
		this.team = team;
		this.hasFlag = false;
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

}
