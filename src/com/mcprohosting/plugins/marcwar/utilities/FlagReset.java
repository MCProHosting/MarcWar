package com.mcprohosting.plugins.marcwar.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;

public class FlagReset implements Runnable {

	private static Item flag;

	@Override
	public void run() {
		if (flag != null) {
			Bukkit.broadcastMessage("The flag has been reset");
			flag.remove();
			UtilityMethods.addFlag(TeamHandler.getTeam("red").getFlag());
		}
	}

	public static void setFlag(Item stack) {
		flag = stack;
	}

}
