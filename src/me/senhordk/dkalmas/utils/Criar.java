package me.senhordk.dkalmas.utils;
 
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
 
public class Criar {
    @SuppressWarnings("deprecation")
	public static ItemStack add1(int m, String nome , List<String> lore){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(nome);
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("deprecation")
	public static ItemStack add2(int m, String nome , List<String> lore){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(nome);
        item.setItemMeta(meta);
        return item;
    }
}