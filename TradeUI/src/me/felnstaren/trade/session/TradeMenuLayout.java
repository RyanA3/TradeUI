package me.felnstaren.trade.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.felnstaren.item.ItemMan;
import me.felnstaren.item.NBTMan;

public class TradeMenuLayout {
	
	public static ItemStack siding;
	private static ItemStack p1_accept_btn = ItemMan.addName(NBTMan.addTag(NBTMan.addTag(NBTMan.addTag(new ItemStack(Material.RED_TERRACOTTA, 1), "menu-element"), "accept-btn"), "p1-accept-btn"), ChatColor.GREEN + "Accept");
	private static ItemStack p2_accept_btn = ItemMan.addName(NBTMan.addTag(NBTMan.addTag(NBTMan.addTag(new ItemStack(Material.RED_TERRACOTTA, 1), "menu-element"), "accept-btn"), "p2-accept-btn"), ChatColor.GREEN + "Accept");
	
	static {
		ItemStack sid1 = NBTMan.addTag(new ItemStack(Material.RED_STAINED_GLASS_PANE), "menu-element");
		sid1.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		ItemMeta meta1 = sid1.getItemMeta();
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		sid1.setItemMeta(meta1);
		siding = sid1;
	}
	
	
	
	private static List<Integer> p1_slots = Arrays.asList(
			0, 1, 2, 3,
			9, 10, 11, 12,
			18, 19, 20, 21,
			27, 28, 29, 30,
			36, 37, 38, 39,
			    46, 47, 48
	);
	private static List<Integer> p2_slots = Arrays.asList(
			5, 6, 7, 8,
			14, 15, 16, 17,
			23, 24, 25, 26,
			32, 33, 34, 35,
			41, 42, 43, 44,
			50, 51, 52
	);
	
	
	private static Map<Integer, ItemStack> menu_gui = new HashMap<Integer, ItemStack>();
	static {
		menu_gui.put(4, siding);
		menu_gui.put(13, siding);
		menu_gui.put(22, siding);
		menu_gui.put(31, siding);
		menu_gui.put(40, siding);
		menu_gui.put(49, siding);
		
		menu_gui.put(53, p2_accept_btn);
		menu_gui.put(45, p1_accept_btn);
	}
	
	
	
	public static void addLayout(Inventory inventory) {
		for(Integer index : menu_gui.keySet()) {
			if(index > inventory.getSize() - 1) break;
			inventory.setItem(index, menu_gui.get(index).clone());
		}
	}
	
	public static List<ItemStack> getP1Slots(Inventory inventory) {
		List<ItemStack> items = new ArrayList<ItemStack>();

		for(Integer index : p1_slots) {
			try { if(inventory.getItem(index).getType() == Material.AIR); }
			catch (Exception e) { continue; }
			items.add(inventory.getItem(index));
		}
		
		return items;
	}
	
	public static List<ItemStack> getP2Slots(Inventory inventory) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		for(Integer index : p2_slots) {
			try { if(inventory.getItem(index).getType() == Material.AIR); }
			catch (Exception e) { continue; }
			items.add(inventory.getItem(index));
		}
		
		return items;
	}
	
	public static boolean isP1Slot(Integer slot) {
		return p1_slots.contains(slot);
	}
	
	public static boolean isP2Slot(Integer slot) {
		return p2_slots.contains(slot);
	}
	
	
	public static boolean isFull(Inventory inventory) {
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) return false;
		}
		
		return true;
	}
	
	public static boolean hasSpace(Inventory inventory, int slots) {
		int open = 0;
		
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) open++;
		}
		
		return open >= slots;
	}
}
