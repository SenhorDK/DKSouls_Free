package me.senhordk.dkalmas.listener.utils;


import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.senhordk.dkalmas.utils.ConfigUtils;


public class NpcAPI implements Listener{
	
    public final static ConfigUtils ce = ConfigUtils.getConfig(new File("plugins/DKSouls/npc.yml"));
	
		public static void Set(Player p, Integer w) {
		    ce.getConfig().set("npc." + w + ".x", p.getLocation().getX());
		    ce.getConfig().set("npc." + w + ".y", p.getLocation().getY());
		    ce.getConfig().set("npc." + w + ".z", p.getLocation().getZ());
		    ce.getConfig().set("npc." + w + ".Pitch", p.getLocation().getPitch());
		    ce.getConfig().set("npc." + w + ".Yaw", p.getLocation().getYaw());
		    ce.getConfig().set("npc." + w + ".World", p.getLocation().getWorld().getName());
		    ce.save();
		}
		public static void Apagar(Player p, Integer w) {
			ce.getConfig().set("npc." + w, null);
			ce.save();
		}
	}