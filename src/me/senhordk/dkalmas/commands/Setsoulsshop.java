package me.senhordk.dkalmas.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.senhordk.dkalmas.listener.utils.NpcAPI;
import me.senhordk.dkalmas.utils.ConfigUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.SkinnableEntity;

public class Setsoulsshop implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String args, String[] lb) {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
	    final ConfigUtils ce = ConfigUtils.getConfig(new File("plugins/DKSouls/npc.yml"));
		if (sender.hasPermission("dksouls.admin")) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cThis command can only be executed by players!");
		}else {
			Player p = (Player)sender;
			if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
				if (lb.length == 0){
					p.sendMessage("§cUse: §f/setsoulsshop §7(id)");
				}
				if (lb.length == 1){
					String numero = lb[0];
					Integer valor;
					try {
						valor = Integer.parseInt(numero);
					} catch (NumberFormatException e) {
						sender.sendMessage("§cPlease enter a valid value!");
						return true;
					}
					if (ce.getConfig().getString("npc." + numero) == null) {
				NPCRegistry registry = CitizensAPI.getNPCRegistry();

		        NPC hNpc = registry.createNPC(EntityType.PLAYER, ca.getConfig().getString("configs.npc.name").replace('&', '§'));
		        hNpc.spawn(p.getLocation());
		        NpcAPI.Set(p, valor);
		        ce.getConfig().set("npc." + valor + ".npcid", hNpc.getId());
		        ce.save();
		        
		        // set skin properties
		        hNpc.data().setPersistent(NPC.PLAYER_SKIN_UUID_METADATA, ca.getConfig().getString("configs.npc.skin"));
		        hNpc.data().setPersistent(NPC.PLAYER_SKIN_USE_LATEST, false);

		        // send skin change to online players
		        Entity npcEntity = hNpc.getEntity();
		        if (npcEntity instanceof SkinnableEntity) {
		            ((SkinnableEntity) npcEntity).getSkinTracker().notifySkinChange(hNpc.data().get(NPC.PLAYER_SKIN_USE_LATEST));
		       p.sendMessage("§aNpc successfully set!");
		        }
			}else {
				p.sendMessage("§cThis npc is already stored in the npc.yml file");
			}
				}
			}else {
				p.sendMessage("§cHey little friend it seems that your server does not have citizens or it is disabled so it will not be possible for you to use this command!");
		}
		}
		}else {
			sender.sendMessage(ca.getConfig().getString("messages.withoutpermission").replace('&', '§'));
		}
		
		return false;
	}

}

