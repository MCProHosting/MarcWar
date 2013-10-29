package com.mcprohosting.plugins.marcwar.utilities;

import org.bukkit.Bukkit;

public class Shutdown implements Runnable {

	@Override
	public void run() {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
	}

}
