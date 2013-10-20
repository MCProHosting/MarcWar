package com.mcprohosting.plugins.marcwar.commands;

import com.mcprohosting.plugins.marcwar.entities.Team;
import com.mcprohosting.plugins.marcwar.utilities.TeamHandler;
import com.mcprohosting.plugins.marcwar.utilities.UtilityMethods;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCapture implements CommandExecutor{

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

		if (!cs.hasPermission("marcwar.setcapture")) {
			cs.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		Team team = TeamHandler.getTeam("blue");

		if (team == null) {
			cs.sendMessage(ChatColor.RED + "Blue team is null.");
			return true;
		}

		Player player = (Player) cs;
		Block block = UtilityMethods.getBlockLookedAt(player);

		if (block == null) {
			cs.sendMessage(ChatColor.RED + "You must be looking at a block.");
			return true;
		}

		block.getLocation().setY(block.getY());

		TeamHandler.setCaptureLocation(block.getLocation());

		player.sendMessage("Capture point for blue team has been set!");
		return true;
	}

}
