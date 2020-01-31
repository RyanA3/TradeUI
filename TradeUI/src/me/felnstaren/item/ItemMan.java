package me.felnstaren.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMan {

	public static ItemStack addGlow(ItemStack item) {
		ItemStack glowing = item.clone();
		glowing.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		ItemMeta meta = glowing.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		glowing.setItemMeta(meta);
		return glowing;
	}
	
	public static ItemStack addName(ItemStack item, String name) {
		ItemStack named = item.clone();
		ItemMeta meta = named.getItemMeta();
		meta.setDisplayName(name);
		named.setItemMeta(meta);
		return named;
	}
	
	public static boolean isItem(ItemStack item) {
		if(item == null) return false;
		if(item.getType() == Material.AIR) return false;
		return true;
	}
	
}
