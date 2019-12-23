package me.senhordk.dkalmas.commands;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class ResetSouls implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (sender.hasPermission("dksouls.admin")) {
			for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
		me.senhordk.dkalmas.listener.utils.Souls.setSoulsOf(offline, 0);
		sender.sendMessage("§aAll souls have been successfully reset!");
		return true;
		}
		}
		return false;
	}
}