package me.felnstaren.item;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBTMan {

	public static ItemStack addTag(ItemStack item, String tag) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TradeUI"), tag), PersistentDataType.STRING, tag);
        item.setItemMeta(meta);
        return item;
	}
	
	public static boolean hasTag(ItemStack item, String tag) {
		return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TradeUI"), tag), PersistentDataType.STRING);
	}
	
	public static ItemStack removeTag(ItemStack item, String tag) {
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().remove(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TradeUI"), tag));
		item.setItemMeta(meta);
		return item;
	}
	
}
