package me.felnstaren.util.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import other.bananapuncher714.NBTEditor;

public class ItemGiver {

	public static void giveItems(Player player, ItemStack... items) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null || items[i].getType() == Material.AIR) continue;
			if(NBTEditor.contains(items[i], "element")) continue; //if(ItemNBTEditor.hasTag(items[i], "element")) continue;
			if(hasConventionalInventorySpace(player)) player.getInventory().addItem(items[i]);
			else dropItem(player.getLocation(), items[i]);
		}
	}
	
	public static void dropItem(Location location, ItemStack item) {
		location.getWorld().dropItem(location, item);
	}
	
	
	
	public static boolean isFull(Inventory inventory) {
		ItemStack[] items = inventory.getContents();
		
		for(int i = 0; i < items.length; i++) 
			if(items[i] == null || items[i].getType() == Material.AIR) return false;
		
		return true;
	}
	
	public static boolean hasConventionalInventorySpace(Player player) {
		ItemStack[] items = player.getInventory().getContents();
		
		for(int i = 0; i < 35; i++)
			if(items[i] == null || items[i].getType() == Material.AIR) return true;
		
		return false;
	}
	
}
