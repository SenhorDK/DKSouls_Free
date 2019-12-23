package me.senhordk.dkalmas.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.senhordk.dkalmas.utils.ConfigUtils;

public class AddSouls implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		if (sender.hasPermission("dksouls.admin")) {
			if (args.length == 0){
			sender.sendMessage("§cUse: §f/addsouls §7(player) (souls)");
			return true;
			}
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
        	sender.sendMessage("§cThe friend must be online to add as souls");
        }
		if (args.length == 2){
		String numero = args[1];
		Integer valor;
		try {
			valor = Integer.parseInt(numero);
		} catch (NumberFormatException e) {
			sender.sendMessage("§cPlease enter a valid value!");
			return true;
		}
		me.senhordk.dkalmas.listener.utils.Souls.addSouls(t, +valor);
		sender.sendMessage("§bYou added §f" + valor + "§b souls for the player §f" + t.getName());
		}
		}else {
			sender.sendMessage(ca.getConfig().getString("messages.withoutpermission").replace('&', '§'));
		}
		return false;
	}

}