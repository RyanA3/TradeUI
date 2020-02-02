package me.felnstaren.util.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.felnstaren.util.item.InventoryOrganizer;
public class Menu {

	private Player viewer;
	private Inventory inventory;
	
	public Menu(Player viewer, String title, int size) {
		this.viewer = viewer;
		
		this.inventory = Bukkit.createInventory(viewer, size, title);
	}
	
	
	
	public void open() {
		viewer.openInventory(inventory);
	}
	
	public void close() {
		viewer.closeInventory();
	}
	
	
	
	public void clear() {
		inventory.clear();
	}
	
	public void setItem(ItemStack item, int x, int y) {
		InventoryOrganizer.setItem(inventory, item, x, y);
	}
	
	public ItemStack getItem(int x, int y) {
		return InventoryOrganizer.getItem(inventory, x, y);
	}
	
	public void fillItems(ItemStack[] items, int offX, int offY, int width, int height) {
		InventoryOrganizer.fillItems(inventory, items, offX, offY, width, height);
	}
	
	public void fillItems(ItemStack repeat, int offX, int offY, int width, int height) {
		InventoryOrganizer.fillItems(inventory, repeat, offX, offY, width, height);
	}
	
	public ItemStack[] getItems(int offX, int offY, int width, int height) {
		return InventoryOrganizer.getItems(inventory, offX, offY, width, height);
	}

}
