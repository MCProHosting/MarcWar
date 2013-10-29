package com.mcprohosting.plugins.marcwar.utilities.proxy;

import com.mcprohosting.plugins.marcwar.MarcWar;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCordManager {

	public static void redirectToServer(String server, Player player) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

		try {
			dataOutputStream.writeUTF("Connect");
			dataOutputStream.writeUTF(server);
		} catch (IOException e) {
			MarcWar.getPlugin().getLogger().warning("Failed to connect a user to the specified BungeeCord server!");
		}

		player.sendPluginMessage(MarcWar.getPlugin(), "BungeeCord", byteArrayOutputStream.toByteArray());
	}

}
