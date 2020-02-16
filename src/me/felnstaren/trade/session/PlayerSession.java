package me.felnstaren.trade.session;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.felnstaren.config.Language;
import me.felnstaren.util.Mathy;
import me.felnstaren.util.item.InventoryOrganizer;
import me.felnstaren.util.item.ItemGiver;
import me.felnstaren.util.item.ItemNBTEditor;
import me.felnstaren.util.menu.TradeMenu;
import me.felnstaren.util.sound.NoiseMaker;

public class PlayerSession {
	
	private static List<Material> button_mats = Arrays.asList(Material.RED_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.GREEN_TERRACOTTA);
	
	private Player player;
	private Inventory inventory;
	private boolean accepted = false;
	private boolean reset_accept = false;
	
	public PlayerSession(Player player, String other_player) {
		this.player = player;
		this.inventory = new TradeMenu(other_player).getInventory();
	}
	
	
	
	
	public void update() {
		player.updateInventory();
	}
	
	public void open() {
		player.openInventory(inventory);
	}
	
	public void cancel() {
		player.closeInventory();
		player.sendMessage(Language.msg("ifo.session-cancelled"));
		NoiseMaker.playsound(Sound.BLOCK_ANVIL_LAND, player, 1, 1.5f, 12);
		ItemGiver.giveItems(player, getInputColumn());
	}
	
	public void accept(ItemStack[] others_items) {
		player.closeInventory();
		player.sendMessage(Language.msg("ifo.session-succeeded"));
		NoiseMaker.playsound(Sound.ENTITY_PLAYER_LEVELUP, player, 1, 1.5f, 10);
		ItemGiver.giveItems(player, others_items);
	}
	
	
	
	public void handleEdit(InventoryClickEvent event) {
		if(HandleHelper.illegal_clicks.contains(event.getClick())) event.setCancelled(true);
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		if(!event.getClickedInventory().equals(inventory)) return;
		if(HandleHelper.illegal_slots.contains(event.getSlot())) event.setCancelled(true);
		if(event.isCancelled()) {
			NoiseMaker.playsound(Sound.BLOCK_CHEST_LOCKED, player, 1, 2, 3);
			return;
		}
		
		ItemStack clicked = event.getCurrentItem();
		
		//Handle the accept button click
		if(clicked == null && event.getCursor().getType() == Material.AIR) return;
		if(ItemNBTEditor.hasTag(clicked, "element")) event.setCancelled(true);
		if(ItemNBTEditor.hasTag(clicked, "accept_button")) {
			int original = button_mats.indexOf(clicked.getType());
			int next = original;
			if(event.getClick() == ClickType.LEFT) next++;
			else if(event.getClick() == ClickType.RIGHT) next--;
			else return;
			next = Mathy.clamp(next, 0, button_mats.size() - 1);

			if(original == next) return;
			else if(next > original) NoiseMaker.playsound(Sound.ENTITY_PLAYER_LEVELUP, player, 1, 1.5f, 5);
			else NoiseMaker.playsound(Sound.ENTITY_ITEM_BREAK, player, 1, 1.5f, 3);
			clicked.setType(button_mats.get(next));
			if(next == button_mats.size() - 1) accepted = true;
		} else {
			InventoryOrganizer.getItem(inventory, 3, 5).setType(button_mats.get(0));
			reset_accept = true;
			accepted = false;
		}
		
		//menu.fillItems(InventoryOrganizer.getItems(event.getClickedInventory(), 0, 0, 4, 6), 0, 0, 4, 6);
	}
	
	
	
	public Player getPlayer() {
		return player;
	}
	
	public ItemStack[] getInputColumn() {
		return InventoryOrganizer.getItems(inventory, 0, 0, 4, 6);
	}
	
	public ItemStack[] getDisplayColumn() {
		return InventoryOrganizer.getItems(inventory, 5, 0, 4, 6);	
	}
	
	public void setInputColumn(ItemStack[] left_column) {
		InventoryOrganizer.fillItems(inventory, left_column, 0, 0, 4, 6);
	}
	
	public void setDisplayColumn(ItemStack[] right_column) {
		InventoryOrganizer.fillItems(inventory, InventoryOrganizer.flip(right_column, 4, 6), 5, 0, 4, 6);
	}

	public boolean isAccepted() {
		return this.accepted;
	}
	
	public void resetAccept() {
		InventoryOrganizer.getItem(inventory, 3, 5).setType(button_mats.get(0));
		this.accepted = false;
	}
	
	public boolean isResetAcceptStatus() {
		return this.reset_accept;
	}
	
	public void setResetAcceptStatus(boolean value) {
		this.reset_accept = value;
	}
	
}
