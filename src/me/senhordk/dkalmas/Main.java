package me.senhordk.dkalmas;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.senhordk.dkalmas.listener.Listeners;
import me.senhordk.dkalmas.utils.ConfigUtils;
import me.senhordk.dkalmas.utils.MySQL;
import me.senhordk.dkalmas.Main;
import me.senhordk.dkalmas.commands.AddSouls;
import me.senhordk.dkalmas.commands.Removesoulsshop;
import me.senhordk.dkalmas.commands.RemoveSouls;
import me.senhordk.dkalmas.commands.ResetSouls;
import me.senhordk.dkalmas.commands.Setsoulsshop;
import me.senhordk.dkalmas.commands.SetSouls;
import me.senhordk.dkalmas.commands.Souls;

public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	
	public static HashMap<String, Double> contas = new HashMap<>();

	public static List<Entry<String, Double>> valores;
	
	public Main() {
		instance = this;
	}
	public File souls1;
	public YamlConfiguration souls;
	public File shop1;
	public YamlConfiguration shop;
	
	public void onLoad() {
		Bukkit.getConsoleSender().sendMessage("§6[DKSouls]§cThe plugin is loading");
	}
	
	public void onEnable() {
		saveDefaultConfig();
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (!(storage.equalsIgnoreCase("MySQL"))) {
		File souls = new File(getDataFolder(), "souls.yml");
		if (!souls.exists())
			 saveResource("souls.yml", false);
		souls1 = new File(getDataFolder(), "souls.yml");
		this.souls = YamlConfiguration.loadConfiguration(souls1);
	    }else {
			MySQL.criarTabela();
	    }
		File shop = new File(getDataFolder(), "shop.yml");
		if (!shop.exists())
			 saveResource("shop.yml", false);
		shop1 = new File(getDataFolder(), "shop.yml");
		this.shop = YamlConfiguration.loadConfiguration(shop1);
		Bukkit.getPluginManager().registerEvents(this, this);
		save();
		Comandos();
		Bukkit.getConsoleSender().sendMessage("§6[DKSouls]§aThe plugin is activated");
		Listeners.setupListeners();
		
		new BukkitRunnable() {

			@Override
			public void run() {
				atualizador();
			}
		}.runTaskTimer(this, 20, 300*20);


	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/souls top") || e.getMessage().startsWith("/almas top")) {
			e.setCancelled(true);
            Player p = e.getPlayer();
			p.sendMessage("§b → §aSouls top rank §b← ");
			Main.mostrarTop(e.getPlayer());
		}
	}
	public static void atualizador() {
	    final ConfigUtils st = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
			String list;
			list = String.valueOf(offline);
            if (!st.getConfig().contains(list)) {
			double dinheiro = me.senhordk.dkalmas.listener.utils.Souls.getSoulsOf(offline);
			contas.put(offline.getName(), dinheiro);
            }
		}

		Stream<Entry<String, Double>> streamOrdenada = contas.entrySet().stream()
				.sorted((x, y) -> y.getValue().compareTo(x.getValue()));

		valores = streamOrdenada.collect(Collectors.toList());

	}
	public static void mostrarTop(Player p) {
		DecimalFormat formatador = new DecimalFormat("#,###.##");
		int id = 1;
		for (Entry<String, Double> entrada : valores) {
			String jogador = entrada.getKey();
			Double valor = entrada.getValue();
			
			p.sendMessage("§a  "+id+"º §2"+jogador +" §f: §7"+formatador.format(valor));
			id++;
			if (id >10) {
				break;
			}
			
			
		}
		
		
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§6[DKSouls]§cThe plugin is desactivated");
	}

	public static Main getInstance() {
		return instance;
	}
	public void save() {
	      try {
	  	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
			String storage = ca.getConfig().getString("storage");
		    if (!(storage.equalsIgnoreCase("MySQL"))) {
	    	  this.souls.save(this.souls1);
		    }
	    	  this.shop.save(this.shop1);
	      } catch (IOException evt) {
	    	  evt.printStackTrace();
	      
	      }
		}
	public void Comandos() {
		this.getCommand("Souls").setExecutor(new Souls());
		this.getCommand("Setsoulsshop").setExecutor(new Setsoulsshop());
		this.getCommand("Removesoulsshop").setExecutor(new Removesoulsshop());
		this.getCommand("ResetSouls").setExecutor(new ResetSouls());
		this.getCommand("SetSouls").setExecutor(new SetSouls());
		this.getCommand("AddSouls").setExecutor(new AddSouls());
		this.getCommand("RemoveSouls").setExecutor(new RemoveSouls());
	}
	}

