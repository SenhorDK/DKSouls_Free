package me.senhordk.dkalmas.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import me.senhordk.dkalmas.Main;
import me.senhordk.dkalmas.commands.Souls;
import me.senhordk.dkalmas.listener.player.PlayerDeathListener;
import me.senhordk.dkalmas.listener.player.PlayerJoinListener;

public class Listeners {

	
	public static void setupListeners() {
		Main plugin = Main.getInstance();
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new PlayerJoinListener(), plugin);
		pm.registerEvents(new PlayerDeathListener(), plugin);
		pm.registerEvents(new Souls(), plugin);
	}
}
