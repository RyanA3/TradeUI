package me.felnstaren.util.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemEditor {
	
	public static ItemStack addLore(ItemStack item, String str) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		
		lore.add(str.replace('&', '§'));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, String str, int line) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		
		if(line < 0) line = 0;
		if(lore.size() <= line) lore.add(str.replace('&', '§'));
		else lore.set(line, str.replace('&', '§'));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, String str) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = Arrays.asList(str.replace('&', '§').split("/n/"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack insertLore(ItemStack item, String str, int line) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		
		if(line < 0) line = 0;
		if(lore.size() <= line) lore.add(str.replace('&', '§'));
		else lore.add(line, str.replace('&', '§'));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack removeLore(ItemStack item, int line) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		
		if(line < 0) line = 0;
		if(lore.size() <= line) return item;
		lore.remove(line);
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	
	
	
	
	public static ItemStack setName(ItemStack item, String str) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(str.replace('&', '§'));
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static String getName(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		return meta.getDisplayName();
	}
	
	
	
	
	
	
	public static ItemStack dye(ItemStack item, Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		
		return item;
	}
	
	
	
	
	
	
	public static ItemStack setUnbreakable(ItemStack item, boolean unb) {
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(unb);
		item.setItemMeta(meta);
		
		return item;
	}
	
	
	
	
	
	
	public static ItemStack damage(ItemStack item, int durability) {
		ItemMeta meta = item.getItemMeta();
		Damageable dam = (Damageable) meta;
		dam.setDamage(durability);
		item.setItemMeta((ItemMeta) dam);
		
		return item;
	}
	
	
	
	
	
	
	public static int getDamage(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		Damageable dam = (Damageable) meta;
		return dam.getDamage();
	}
	
}
