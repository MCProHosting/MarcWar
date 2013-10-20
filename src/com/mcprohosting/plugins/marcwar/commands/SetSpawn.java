package com.mcprohosting.plugins.marcwar.commands;

import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(ChatColor.RED + "This command may only be performed by a player.");
			return true;
		}

		if (args.length == 1) {
			if (!cs.hasPermission("kotl.setspawn")) {
				cs.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}

			String color = args[0];

			if (!(color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("red"))) {
				cs.sendMessage(ChatColor.RED + "Please enter either red or blue for team color.");
				return true;
			}

			Player player = (Player) cs;
			TeamHandler.setSpawnLocation(color, player.getLocation());
			player.sendMessage("Spawn point for " + color + " team has been set!");
			return true;
		} else {
			return false;
		}
	}

}
