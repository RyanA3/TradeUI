package me.felnstaren.util.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.felnstaren.config.Loader;

public class ItemNBTEditor {
	
	public static final int TAG_LIMIT = 20;
	public static String key = "Horsia";
	
	
	//Edit NBT Tags by value
	public static void addTag(ItemStack item, String value) {
		if(item == null) return;
		if(!hasOpenSlot(item)) return;
		
		setTag(item, value, getNextSlot(item));
	}
	
	public static void removeTag(ItemStack item, String value) {
		if(item == null) return;
		int remove = -1;
		
		for(int i = 0; i < TAG_LIMIT; i++) {
			if(!hasTag(item, i)) return;
			if(getTag(item, i).equals(value)) {
				remove = i;
				break;
			}
		}
		
		removeTag(item, remove);
	}
	
	public static boolean hasTag(ItemStack item, String value) {
		if(item == null) return false;
		
		for(int i = 0; i < TAG_LIMIT; i++) {
			if(!hasTag(item, i)) return false;
			if(getTag(item, i).equals(value)) return true;
		}
		
		return false;
	}
	
	public static List<String> getTags(ItemStack item) {
		if(item == null) return null;
		
		List<String> tags = new ArrayList<String>();

		for(int i = 0; i < TAG_LIMIT; i++) {
			if(!hasTag(item, i)) continue;
			tags.add(getTag(item, i));
		}
		
		return tags;
	}
	
	public static List<String> getNiceTags(ItemStack item) {
		if(item == null) return null;
		
		List<String> tags = new ArrayList<String>();

		for(int i = 0; i < TAG_LIMIT; i++) {
			if(restAreNull(item, i)) return tags;
			if(!hasTag(item, i)) tags.add("<none>");
			else tags.add(getTag(item, i));
		}
		
		return tags;
	}
	
	
	
	
	
	
	
	//Edit NBT Tags by index
	public static String getTag(ItemStack item, int index) {
		if(item == null) return null;
		
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		return container.get(new NamespacedKey(Loader.plugin, key(index)), PersistentDataType.STRING);
	}
	
	public static void removeTag(ItemStack item, int index) {
		if(item == null) return;
		
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		container.remove(new NamespacedKey(Loader.plugin, key(index)));
		
		item.setItemMeta(meta);
	}
	
	public static boolean hasTag(ItemStack item, int index) {
		if(item == null) return false;
		
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		return container.has(new NamespacedKey(Loader.plugin, key(index)), PersistentDataType.STRING);
	}
	
	public static void setTag(ItemStack item, String value, int index) {
		if(item == null) return;
		
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		container.set(new NamespacedKey(Loader.plugin, key(index)), PersistentDataType.STRING, value);
		
		item.setItemMeta(meta);
	}
	
	
	
	
	
	
	
	//Make my life easier
	private static int getNextSlot(ItemStack item) {
		if(item == null) return 0;
		
		for(int i = 0; i < TAG_LIMIT; i++) {
			if(!hasTag(item, i)) return i;
		}
		
		return -1;
	}
	
	private static boolean hasOpenSlot(ItemStack item) {
		if(item == null) return false;
		return getNextSlot(item) != -1;
	}
	
	private static String key(int index) {
		return key + "." + index;
	}
	
	private static boolean restAreNull(ItemStack item, int index) {
		if(item == null) return false;
		
		for(int i = index; i < TAG_LIMIT; i++) {
			if(hasTag(item, i)) return false;
		}
		
		return true;
	}

}
