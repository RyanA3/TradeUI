package me.felnstaren.util.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import other.bananapuncher714.NBTEditor;

public class ItemBuild {

	private ItemStack item;
	
	public ItemBuild(Material material) {
		this.item = new ItemStack(material);
	}
	
	public ItemBuild(ItemStack item) {
		this.item = item;
	}
	
	
	
	public ItemBuild setCount(int count) {
		item.setAmount(count);
		return this;
	}
	
	public ItemBuild setType(Material material) {
		item.setType(material);
		return this;
	}
	
	public ItemBuild setName(String name) {
		ItemEditor.setName(item, name);
		return this;
	}
	
	public ItemBuild setLore(String lore, int line) {
		ItemEditor.setLore(item, lore, line);
		return this;
	}
	
	public ItemBuild setUnbreakable(boolean unbreakable) {
		ItemEditor.setUnbreakable(item, unbreakable);
		return this;
	}
	
	public ItemBuild setColor(Color color) {
		ItemEditor.dye(item, color);
		return this;
	}
	
	public ItemBuild damage(int durability) {
		ItemEditor.damage(item, durability);
		return this;
	}
	
	public ItemBuild setTags(Object... tags) {
		NBTEditor.set(item, 1, tags); //ItemNBTEditor.addTag(item, tag);
		return this;
	}
	
	
	
	public ItemStack get() {
		return item;
	}
	
}
