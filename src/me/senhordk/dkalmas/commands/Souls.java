package me.senhordk.dkalmas.commands;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.senhordk.dkalmas.Main;
import me.senhordk.dkalmas.utils.ConfigUtils;
import me.senhordk.dkalmas.utils.Criar;

public class Souls implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		DecimalFormat formatador = new DecimalFormat("#,###.##");
		final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cYou need to be a player to execute this command!");
		} else {
			Player p = (Player) sender;
			int valor = me.senhordk.dkalmas.listener.utils.Souls.getSouls(p);
			if (args.length == 0) {
				p.sendMessage(ca.getConfig().getString("messages.Souls").replace('&', '§').replace("{souls}",
						"" + formatador.format(valor)));
			}

			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("shop")) {
					abrirShop(p);
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static void abrirShop2(Player p) {
		final ConfigUtils sh = ConfigUtils.getConfig(new File("plugins/DKSouls/shop.yml"));
		int tamanho = sh.getConfig().getInt("Config.InventorySize");
		String titulo = sh.getConfig().getString("Config.InventoryTitle").replace("&", "§");

		Inventory inv = Bukkit.createInventory(null, tamanho, titulo);

		for (String material : sh.getConfig().getConfigurationSection("Items").getKeys(false)) {
			String Name = sh.getConfig().getString("Items." + material + ".Name");
			String display = sh.getConfig().getString("Items." + material + ".Display").replace("&", "§");
			int slot = sh.getConfig().getInt("Items." + material + ".Slot") - 1;
			int item = sh.getConfig().getInt("Items." + material + ".Item");

			final ArrayList<String> lore = new ArrayList<String>();
			for (String str : sh.getConfig().getStringList("Items." + material + ".Lore")) {
				lore.add(str.replace('&', '§'));
			}
			inv.setItem(slot, Criar.add1(item, display, lore));
		}
		p.openInventory(inv);
	}

	public static void abrirShop(Player p) {
		DecimalFormat formatador = new DecimalFormat("#,###.##");
		final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		int tamanho = ca.getConfig().getInt("Config.InventorySize");
		String titulo = ca.getConfig().getString("Config.InventoryTitle").replace("&", "§");

		Inventory inv = Bukkit.createInventory(null, tamanho, titulo);

		for (String material : ca.getConfig().getConfigurationSection("Items").getKeys(false)) {
			String display = ca.getConfig().getString("Items." + material + ".Display").replace("&", "§");
			int slot = ca.getConfig().getInt("Items." + material + ".Slot") - 1;
			int item = ca.getConfig().getInt("Items." + material + ".Item");

			final ArrayList<String> lore = new ArrayList<String>();
			for (String str : ca.getConfig().getStringList("Items." + material + ".Lore")) {
				int souls = me.senhordk.dkalmas.listener.utils.Souls.getSouls(p);
				lore.add(str.replace('&', '§').replace("{souls}", "" + formatador.format(souls)).replace("{player}",
						p.getName()));
			}
			inv.setItem(slot, Criar.add2(item, display, lore));
		}
		p.openInventory(inv);

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerCLickInventry(final InventoryClickEvent e) {
		final ConfigUtils sh = ConfigUtils.getConfig(new File("plugins/DKSouls/shop.yml"));
		final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		final Player p = (Player) e.getWhoClicked();
		String titulo = sh.getConfig().getString("Config.InventoryTitle").replace("&", "§");
		if (e.getInventory().getName().equalsIgnoreCase(titulo) && e.getCurrentItem() != null
				&& e.getCurrentItem().getTypeId() != 0) {
			e.setCancelled(true);
			for (String material : sh.getConfig().getConfigurationSection("Items").getKeys(false)) {
				String display = sh.getConfig().getString("Items." + material + ".Display").replace("&", "§");
				String sucess = sh.getConfig().getString("Items." + material + ".Success").replace("&", "§")
						.replace("{player}", p.getName());
				int souls = sh.getConfig().getInt("Items." + material + ".Souls") - 1;
				int souls2 = sh.getConfig().getInt("Items." + material + ".Souls");
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(display)) {
					e.setCancelled(true);
					if (me.senhordk.dkalmas.listener.utils.Souls.getSouls(p) > souls) {
						p.sendMessage(sucess);
						me.senhordk.dkalmas.listener.utils.Souls.removeSouls(p, souls2);
						for (int i = 0; i < sh.getConfig().getList("Items." + material + ".Commands").size(); i++) {
							ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();
							Bukkit.getServer().dispatchCommand(cs,
									sh.getConfig().getList("Items." + material + ".Commands").get(i).toString()
											.replace("{player}", p.getName()));
							Main.getInstance().save();
							p.closeInventory();
						}
					} else {
						p.sendMessage(ca.getConfig().getString("messages.withoutsouls").replace('&', '§')
								.replace("{player}", p.getName()));
						p.closeInventory();
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		Player p = (Player) e.getWhoClicked();
		String titulo = ca.getConfig().getString("Config.InventoryTitle").replace("&", "§");
		if (e.getInventory().getName().equalsIgnoreCase(titulo) && e.getCurrentItem() != null
				&& e.getCurrentItem().getTypeId() != 0) {
			e.setCancelled(true);
			for (String material : ca.getConfig().getConfigurationSection("Items").getKeys(false)) {
				String display = ca.getConfig().getString("Items." + material + ".Display").replace("&", "§");
				boolean menu = ca.getConfig().getBoolean("Items." + material + ".OpenShopInventory");

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(display)) {
					e.setCancelled(true);
					if (menu == true) {
						abrirShop2(p);
					}
				}
			}
		}
	}
}
