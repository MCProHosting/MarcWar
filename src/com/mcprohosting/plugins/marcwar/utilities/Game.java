package com.mcprohosting.plugins.marcwar.utilities;

import com.mcprohosting.plugins.marcwar.MarcWar;
import com.mcprohosting.plugins.marcwar.entities.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;

public class Game implements Runnable {

	private Date lastMsg = new Date(0);
	private Date starting = null;
	private boolean gameOver = false;

	@Override
	public void run() {
		if (Bukkit.getOnlinePlayers().length < 10 && MarcWar.getGameProgress().equalsIgnoreCase("starting")) {
			Date now = new Date();

			if (now.getTime() - lastMsg.getTime() > 20000L) {
				lastMsg = now;
				String message = FontFormat.BOLD + "A minimum of 10 players are required to start the game. Wating for more players...";
				Bukkit.broadcastMessage(FontFormat.GREEN + message);
			}
		} else {
			if (MarcWar.getGameProgress().equalsIgnoreCase("starting")) {
				if (starting == null) {
					String message = FontFormat.BOLD + "Minimum number of players reached, game starting in 2 minutes.";
					Bukkit.broadcastMessage(FontFormat.GREEN + message);
					starting = new Date();
				}

				Date now = new Date();

				if (now.getTime() - starting.getTime() > 1000 * 60 * 2) {
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
				}
			} else if (MarcWar.getGameProgress().equalsIgnoreCase("started")) {
				if (TeamHandler.getTeam("blue").lossByKills()) {
					String message = FontFormat.BOLD + "Red team has defended their flag! Congratulations!";
					Bukkit.broadcastMessage(FontFormat.RED + message);
					MarcWar.setGameProgress("gameover");
					lastMsg = new Date();
				}
				if (TeamHandler.getTeam("red").lossByKills()) {
					String message = FontFormat.BOLD + "Blue team has slain the enemy force! Congratulations!";
					Bukkit.broadcastMessage(FontFormat.BLUE + message);
					MarcWar.setGameProgress("gameover");
					lastMsg = new Date();
				}
			} else {
				Date now = new Date();

				if (now.getTime() - lastMsg.getTime() < 30000L && !gameOver) {
					lastMsg = now;
					String message = FontFormat.BOLD + "The game will restart in 30 seconds";
					Bukkit.broadcastMessage(FontFormat.GREEN + message);
					gameOver = !gameOver;
				} else if (now.getTime() - lastMsg.getTime() > 30000L) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (MarcWar.getProxyEnabled()) {
							LilyPadManager.redirectToServer("lobby", player);
						} else {
							player.kickPlayer("The game is restarting!");
						}
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
				}
			}
		}
	}

}
