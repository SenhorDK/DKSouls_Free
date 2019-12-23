package me.senhordk.dkalmas.listener.player;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.senhordk.dkalmas.Main;
import me.senhordk.dkalmas.commands.Souls;
import me.senhordk.dkalmas.utils.ConfigUtils;
import me.senhordk.dkalmas.utils.MySQL;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		Player p = evt.getPlayer();
		String storage = ca.getConfig().getString("storage");
		int value = ca.getConfig().getInt("configs.souls");
		if (!(storage.equalsIgnoreCase("MySQL"))) {
			if (!Main.getInstance().souls.contains(p.getName() + "")) {
				Main.getInstance().souls.set("players" + p.getName() + ".Souls", Integer.valueOf(value));
				Main.getInstance().save();
			}
		} else {
			if (!MySQL.hasJogador(p)) {
				MySQL.addJogador(p);
				MySQL.addSouls(p, Integer.valueOf(value));
			}
		}
	}
	@EventHandler
	public void onInteractNPC(NPCRightClickEvent e) {
		final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
	    if (e.getNPC().getName().equals(ca.getConfig().getString("configs.npc.name").replace('&', '§'))) {
	        Player p = e.getClicker();
	        Souls.abrirShop(p);
	    }
	}
}
