package me.felnstaren.trade.session;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.util.Mathy;
import me.felnstaren.util.item.InventoryOrganizer;
import me.felnstaren.util.item.ItemGiver;
import me.felnstaren.util.item.ItemNBTEditor;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;
import me.felnstaren.util.menu.TradeMenu;
import me.felnstaren.util.sound.NoiseMaker;

public class PlayerSession {
	
	private static List<Material> button_mats = Arrays.asList(Material.RED_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.GREEN_TERRACOTTA);
	
	private Player player;
	private Inventory inventory;
	private boolean accepted = false;
	
	public PlayerSession(Player player, String other_player) {
		this.player = player;
		this.inventory = new TradeMenu(Language.msg("menu.header", new ChatVar("[Player]", other_player))).getInventory();
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
	
	
	
	public void handleEdit(InventoryClickEvent event, PlayerSession other) {
		if(event.getAction() == InventoryAction.NOTHING) return;
		if(event.getClickedInventory() == null) return;
		if(HandleHelper.ILLEGAL_CLICKS.contains(event.getClick())) event.setCancelled(true);
		if(HandleHelper.ILLEGAL_SLOTS.contains(event.getRawSlot())) event.setCancelled(true);
		if(event.isCancelled()) {
			NoiseMaker.playsound(Sound.BLOCK_CHEST_LOCKED, player, 1, 2, 3);
			return;
		}
		
		ItemStack clicked = event.getCurrentItem();
		if(clicked == null) return;
		
		if(event.getClick() == ClickType.SHIFT_LEFT) {
			event.setCancelled(true);
			shiftOn(event.getView().getTopInventory(), event.getView().getBottomInventory(), clicked, event.getRawSlot());
			event.setCurrentItem(clicked);
			return;
		}
		
		if(!event.getClickedInventory().equals(inventory)) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		
		//Handle the accept button click
		if(ItemNBTEditor.hasTag(clicked, "element")) event.setCancelled(true);
		if(ItemNBTEditor.hasTag(clicked, "accept_button")) {
			incAcceptButton(clicked, event.getClick());
		} else {
			other.resetAccept();
			resetAccept();
		}
	}
	
	
	
	private void incAcceptButton(ItemStack button, ClickType click) {
		int original = button_mats.indexOf(button.getType());
		int next = original;
		if(click == ClickType.LEFT) next++;
		else if(click == ClickType.RIGHT) next--;
		else return;
		
		next = Mathy.clamp(next, 0, button_mats.size() - 1);

		if(original == next) return;
		else if(next > original) NoiseMaker.playsound(Sound.ENTITY_PLAYER_LEVELUP, player, 1, 1.5f, 5);
		else NoiseMaker.playsound(Sound.ENTITY_ITEM_BREAK, player, 1, 1.5f, 3);
		
		button.setType(button_mats.get(next));
		if(next == button_mats.size() - 1) accepted = true;
	}
	
	private void shiftOn(Inventory top, Inventory bottom, ItemStack clicked, int slot) {
		Logger.log(Level.DEBUG, "Shifting item from slot " + slot);
		
		if(slot < 54) { //Shift out of top
			for(int i = 0; i < bottom.getSize(); i++) {
				if(bottom.getItem(i) != null) continue;
			
				bottom.setItem(i, clicked.clone());
				clicked.setType(Material.AIR);
				return;
			}
		} else { //Shift in to top
			for(int i = 0; i < top.getSize(); i++) {
				if(HandleHelper.ILLEGAL_SLOTS.contains(i)) continue;
				if(top.getItem(i) != null) continue;
			
				top.setItem(i, clicked.clone());
				clicked.setType(Material.AIR);
				return;
			}
		}
	}
	
	
	
	public Player getPlayer() {
		return player;
	}
	
	public ItemStack[] getInputColumn() {
		return InventoryOrganizer.getItems(inventory, 0, 0, 4, 6);
	}
	
	public void setDisplayColumn(ItemStack[] right_column) {
		InventoryOrganizer.fillItems(inventory, InventoryOrganizer.flip(right_column, 4, 6), 5, 0, 4, 6);
	}

	
	public boolean isAccepted() {
		return accepted;
	}
	
	public void resetAccept() {
		ItemStack button = InventoryOrganizer.getItem(inventory, 3, 5);
		if(button.getType() != button_mats.get(0)) NoiseMaker.playsound(Sound.BLOCK_ANVIL_USE, player, 1, 1.5f, 3);
		button.setType(button_mats.get(0));
		this.accepted = false;
	}
	
}
