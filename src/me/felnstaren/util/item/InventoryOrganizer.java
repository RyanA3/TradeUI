package me.felnstaren.util.item;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class InventoryOrganizer {
	
	public static Inventory setItem(Inventory inventory, ItemStack item, int x, int y) {
		if(y > 6 || y < 0) return inventory;
		if(x > 9 || x < 0) return inventory;
		ItemStack[] contents = inventory.getContents();
		contents[y * 9 + x] = item;
		inventory.setContents(contents);
		return inventory;
	}
	
	public static ItemStack getItem(Inventory inventory, int x, int y) {
		if(y > 6 || y < 0) return null;
		if(x > 9 || x < 0) return null;
		return inventory.getContents()[y * 9 + x];
	}
	
	public static Inventory fillItems(Inventory inventory, ItemStack[] items, int offX, int offY, int width, int height) {
		Logger.log(Level.DEBUG, "Fill items from (" + offX + "," + offY + ") to (" + offX + width + "," + offY + height + ")");
		
		for(int x = 0; x < width; x++) 
			for(int y = 0; y < height; y++) 
				setItem(inventory, items[y * width + x], x + offX, y + offY);
			
		return inventory;
	}
	
	public static Inventory fillItems(Inventory inventory, ItemStack repeat, int offX, int offY, int width, int height) {
		Logger.log(Level.DEBUG, "Fill items from (" + offX + "," + offY + ") to (" + offX + width + "," + offY + height + ")");
		
		for(int x = 0; x < width; x++) 
			for(int y = 0; y < height; y++)
				setItem(inventory, repeat.clone(), x + offX, y + offY);
		
		return inventory;
	}
	
	public static ItemStack[] getItems(Inventory inventory, int offX, int offY, int width, int height) {
		ItemStack[] items = new ItemStack[width * height];
		
		for(int x = 0; x < width; x++) 
			for(int y = 0; y < height; y++) 
				items[y * width + x] = getItem(inventory, x + offX, y + offY);
		
		return items;
	}
	
	public static ItemStack[] flip(ItemStack[] items, int width, int height) {
		Logger.log(Level.DEBUG, " - FLIP OPERATION - ");
		ItemStack[] flipped = new ItemStack[width * height];
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int fx = (width - 1) - x;
				//int fy = height - y;
				flipped[y * width + x] = items[y * width + fx];
				//Logger.log(Level.DEBUG, "Flipped point: (" + x + ", " + y + ") to (" + fx + ", " + y + ")");
			}
		}
				
		return flipped;
	}

}
