package me.senhordk.dkalmas.listener.player;

import java.io.File;
import java.util.Random;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.senhordk.dkalmas.listener.utils.Souls;
import me.senhordk.dkalmas.utils.ConfigUtils;


public class PlayerDeathListener implements Listener{

	@EventHandler
	private void aoMorrer(PlayerDeathEvent evt) {
		evt.setDeathMessage(null);
		if (evt.getEntity().getKiller() instanceof Player) {
	        final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
			for(String material : ca.getConfig().getConfigurationSection("vip").getKeys(false)) {
				String permission = ca.getConfig().getString("vip." + material + ".permission");
				int souls = ca.getConfig().getInt("vip." + material + ".souls");
			Player p = evt.getEntity();
			String message = ca.getConfig().getString("vip." + material + ".message").replace("&", "§").replace("{player}", p.getName());
			Player k = p.getKiller();
			double chance = new Random().nextDouble();
			if (chance <= ca.getConfig().getDouble("configs.playerchance")) {
			  if (!k.hasPermission(permission)) {
				  Souls.addSouls(k, +ca.getConfig().getInt("configs.playersouls"));
				  k.sendMessage(ca.getConfig().getString("messages.playermessage").replace('&', '§').replace("{player}", p.getName()));
			  }else {
				  
			  }
			}
			double chance2 = new Random().nextDouble();
			if (chance2 <= ca.getConfig().getDouble("vip." + material + ".chance")) {
				  if (k.hasPermission(permission)) {
					  Souls.addSouls(k, +souls);
					  k.sendMessage(ca.getConfig().getString(message).replace('&', '§').replace("{player}", p.getName()));
				  }
			}
		}
		}
	}
	@EventHandler
	public void onEntityDeath(final EntityDeathEvent event) {  
        final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		Boolean config = ca.getConfig().getBoolean("configs.ofsoulswhilekillingmob");
		if (config == false) {
			
		}else {
	     if(event.getEntity() instanceof Monster) {       
	         Monster monsterEnt = (Monster) event.getEntity();
	         Player p = monsterEnt.getKiller();
	         if(p == null)
	             return;
				String message = ca.getConfig().getString("configs.playermessagemob").replace("&", "§").replace("{player}", p.getName());
				double chance = new Random().nextDouble();
				if (chance <= ca.getConfig().getDouble("configs.playermobchance")) {
				p.sendMessage(message);
			  Souls.addSouls(p, +ca.getConfig().getInt("configs.playermobsouls"));
	     }
	     }
		}
	}
	@EventHandler
	public void onEntityDeath2(final EntityDeathEvent event) {  
        final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		Boolean config = ca.getConfig().getBoolean("configs.ofsoulswhilekillingmob");
		if (config == false) {
			
		}else {
	     if(event.getEntity() instanceof Animals) {       
	         Animals animals = (Animals) event.getEntity();
	         Player p = animals.getKiller();
	         if(p == null)
	             return;
				String message = ca.getConfig().getString("configs.playermessagemob").replace("&", "§").replace("{player}", p.getName());
				double chance = new Random().nextDouble();
				if (chance <= ca.getConfig().getDouble("configs.playermobchance")) {
				p.sendMessage(message);
			  Souls.addSouls(p, +ca.getConfig().getInt("configs.playermobsouls"));
	     }
		}
		}
	}
}
