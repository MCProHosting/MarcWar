package com.mcprohosting.plugins.marcwar.utilities;

import com.mcprohosting.plugins.marcwar.MarcWar;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResult;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class UtilityMethods {

	public static void redirectToServer(String server, final Player player) {
		try {
			FutureResult<RedirectResult> futureResult = MarcWar.getConnect().request(new RedirectRequest(server, player.getName()));
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

	public static Block getBlockLookedAt(Player player) {
		return player.getTargetBlock(null, 200);
	}

	public static void addFlag(Location location) {
		Block block = location.getBlock();
		block.setType(Material.WOOL);
		block.setData((byte) 14);
		FlagMeta meta = new FlagMeta();
		meta.setFlag();
		block.setMetadata("flag", meta);
	}

}
