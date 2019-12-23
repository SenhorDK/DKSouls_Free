package me.senhordk.dkalmas.listener.utils;

import java.io.File;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.senhordk.dkalmas.Main;
import me.senhordk.dkalmas.utils.ConfigUtils;
import me.senhordk.dkalmas.utils.MySQL;

public class Souls {
	
	public static void setSouls(Player p, int i) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
		Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(i));
		Main.getInstance().save();
	    }else {

		MySQL.setJogador(p, i);
	    }
	}
	public static void setSoulsOf(OfflinePlayer offline, int i) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
		Main.getInstance().souls.set("players."+offline.getName() + ".Souls", Integer.valueOf(i));
		Main.getInstance().save();
	    }else {

		MySQL.setJogadorOf(offline, i);
	    }
	}
	public static void addSouls(Player p) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
		int Value = Main.getInstance().souls.getInt("players."+p.getName() + ".Souls");
		Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(Value + 100));
		Main.getInstance().save();
	    }else {

		MySQL.addSouls(p, +100);
	    }
	}
	    public static void addSouls(Player p, int i) {
		    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
			String storage = ca.getConfig().getString("storage");
		    if (!(storage.equalsIgnoreCase("MySQL"))) {
	    	int Value = Main.getInstance().souls.getInt("players."+p.getName() + ".Souls");
			Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(Value + i));
			Main.getInstance().save();
		    }else {

			MySQL.addSouls(p, +i);
		    }
		}
public static void removeSouls(Player p) {
    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
	String storage = ca.getConfig().getString("storage");
    if (!(storage.equalsIgnoreCase("MySQL"))) {
	int Value = Main.getInstance().souls.getInt("players."+p.getName() + ".Souls");
    if ((Value - 50) > 0) {
    	Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(Value - 50));
    }else {
    	Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(0));
		Main.getInstance().save();
    }
    }else {
    	MySQL.removeSouls(p, - 50);
    }
}
    public static void removeSouls(Player p, int i) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
    	int Value = Main.getInstance().souls.getInt("players."+p.getName() + ".Souls");
        if ((Value - i) > 0) {
        	Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(Value - i));
        }else {
        	Main.getInstance().souls.set("players."+p.getName() + ".Souls", Integer.valueOf(0));
    		Main.getInstance().save();
        }
    }else {
    	MySQL.removeSouls(p, i);
    }
    }
 public static int getSouls(Player p) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
	return Main.getInstance().souls.getInt("players."+p.getName() + ".Souls");
	    }else {
	    	return (int) MySQL.getSouls(p);
	    }
	    	
}
 public static int getSoulsOf(OfflinePlayer offline) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
	return Main.getInstance().souls.getInt("players."+offline.getName() + ".Souls");
	    }else {
	    	return (int) MySQL.getSoulsOf(offline);
	    }
	    	
}

}

