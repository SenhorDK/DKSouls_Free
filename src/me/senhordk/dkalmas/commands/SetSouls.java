package me.senhordk.dkalmas.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.senhordk.dkalmas.utils.ConfigUtils;

public class SetSouls implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		if (sender.hasPermission("dksouls.admin")) {
			if (args.length == 0){
			sender.sendMessage("§cUse: §f/setsouls §7(player) (souls)");
			return true;
			}
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
        	sender.sendMessage("§cThe player must be online so he can set the souls");
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
		me.senhordk.dkalmas.listener.utils.Souls.setSouls(t, valor);
		sender.sendMessage("§bYou set the souls of the player §f" + t.getName() + "§b for §f" + valor);
		}
		}else {
			sender.sendMessage(ca.getConfig().getString("messages.withoutpermission").replace('&', '§'));
		}
		return false;
	}

}
