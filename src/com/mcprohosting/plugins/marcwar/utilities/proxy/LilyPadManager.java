package com.mcprohosting.plugins.marcwar.utilities.proxy;

import com.mcprohosting.plugins.marcwar.MarcWar;
import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResult;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LilyPadManager {

	private static Connect connect;

	public LilyPadManager() {
		init();
	}

	private void init() {
		connect = MarcWar.getPlugin().getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}

	private static Connect getConnect() {
		return connect;
	}

	public static void redirectToServer(String server, final Player player) {
		try {
			FutureResult<RedirectResult> futureResult = LilyPadManager.getConnect().request(new RedirectRequest(server, player.getName()));
			RedirectResult result = futureResult.await(5000L);
			if (!result.getStatusCode().equals(StatusCode.SUCCESS)) {
				player.sendMessage(ChatColor.RED.toString() + "That server is current not available: !");
				Bukkit.getLogger().warning("RedirectRequest failed for player: " + player.getName());
			}
		} catch (RequestException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
		} catch (InterruptedException e) {
			player.sendMessage(ChatColor.RED.toString() + "That server is current not available: " + e.getCause() + "!");
			Bukkit.getLogger().warning("RedirectRequest interrupted for player: " + player.getName());
		}
	}

}
