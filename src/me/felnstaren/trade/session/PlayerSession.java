package me.felnstaren.trade.session;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import me.felnstaren.util.chat.Messenger;
import me.felnstaren.util.item.InventoryOrganizer;
import me.felnstaren.util.menu.TradeMenu;
import me.felnstaren.util.sound.NoiseMaker;

public class PlayerSession {
	
	private Player player;
	private TradeMenu menu;
	
	public PlayerSession(Player player, String other_player) {
		this.player = player;
		this.menu = new TradeMenu(player, other_player);
	}
	
	
	
	
	public void open() {
		menu.open();
	}
	
	public void cancel() {
		menu.close();
		player.sendMessage(Messenger.color("&eThe trade was cancelled!"));
		NoiseMaker.playsound(Sound.BLOCK_ANVIL_LAND, player, 1, 1.5f, 12);
	}
	
	public void accept() {
		menu.close();
		player.sendMessage(Messenger.color("&aThe trade was successful!"));
		NoiseMaker.playsound(Sound.ENTITY_PLAYER_LEVELUP, player, 1, 1.5f, 10);
	}
	
	
	
	public void handleEdit(InventoryClickEvent event) {
		if(HandleHelper.illegal_clicks.contains(event.getClick())) event.setCancelled(true);
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		if(HandleHelper.illegal_slots.contains(event.getSlot())) event.setCancelled(true);
		if(event.isCancelled()) return;
		menu.fillItems(InventoryOrganizer.getItems(event.getClickedInventory(), 0, 0, 4, 6), 0, 0, 4, 6);
	}
	
	
	
	public Player getPlayer() {
		return player;
	}
	
	public ItemStack[] getInputColumn() {
		return menu.getItems(0, 0, 4, 6);
	}
	
	public ItemStack[] getDisplayColumn() {
		return menu.getItems(5, 0, 4, 6);
	}
	
	public void setInputColumn(ItemStack[] left_column) {
		menu.fillItems(left_column, 0, 0, 4, 6);
	}
	
	public void setDisplayColumn(ItemStack[] right_column) {
		menu.fillItems(InventoryOrganizer.flip(right_column, 4, 6), 5, 0, 4, 6);
	}

}
