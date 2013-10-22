package com.mcprohosting.plugins.marcwar.commands;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Team;
import com.mcprohosting.plugins.marcwar.utilities.FontFormat;
import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartGame implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(ChatColor.RED + "This command may only be performed by a player.");
			return true;
		}

		if (!(args.length == 0)) {
			cs.sendMessage(ChatColor.RED + "This command does not have any arguments.");
			return true;
		}

		if (!cs.hasPermission("marcwar.startgame")) {
			cs.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			MarcWar.setGameProgress("started");
			Team team = TeamHandler.getPlayerTeam(player.getName());
			player.teleport(team.getSpawn());

			if (team.getColor().equalsIgnoreCase("blue")) {
				String message = FontFormat.BOLD + "Storm the enemy castle, take their red wool flag, and place atop the diamond at home to win!";
				player.sendMessage(FontFormat.BLUE + message);
			} else {
				String message = FontFormat.BOLD + "Close the gates! Protect the red wool flag at all cost! Route the enemy to win!";
				player.sendMessage(FontFormat.RED + message);
			}
		}

		MarcWar.setGameProgress("started");
		return true;
	}
}
