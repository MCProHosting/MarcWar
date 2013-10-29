package com.mcprohosting.plugins.marcwar.commands;

import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobby implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(ChatColor.RED + "This command may only be performed by a player.");
			return true;
		}

		if (!cs.hasPermission("marcwar.setlobby")) {
			cs.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		if (args.length > 0) {
			cs.sendMessage(ChatColor.RED + "This command does not accept parameters.");
			return true;
		}

		Player player = (Player) cs;
		TeamHandler.setLobbyLocation(player.getLocation());
		player.sendMessage("Spawn point the for lobby has been set!");
		return true;
	}

}
