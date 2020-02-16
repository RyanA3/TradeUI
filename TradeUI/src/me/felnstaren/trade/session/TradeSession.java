package me.felnstaren.trade.session;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.felnstaren.item.ItemMan;
import me.felnstaren.item.NBTMan;

public class TradeSession {
	
	//Sound.BLOCK_CHEST_LOCKED at pitch of 2 sounds just like a mechanical keyboard lol
	//Sound.BLOCK_CONDUIT_DEACTIVATE at pitch of 2 sounds just like a laser lolx2

	private Inventory inventory;
	private String name;
	private String player1;
	private String player2;
	private boolean closed = false;
	private FileConfiguration language;
	
	private static List<ClickType> illegal_clicks = Arrays.asList(
			ClickType.DOUBLE_CLICK,
			ClickType.CONTROL_DROP,
			ClickType.DROP,
			ClickType.NUMBER_KEY,
			ClickType.SHIFT_LEFT,
			ClickType.SHIFT_RIGHT,
			ClickType.UNKNOWN
	);
			
	
	public TradeSession(String player1, String player2, FileConfiguration language) {
		this.name = (ChatColor.BLACK + player1.substring(0, Math.min(player1.length(), 10))) + "          " + (player2.substring(0, Math.min(player2.length(), 10)));
		this.player1 = player1;
		this.player2 = player2;
		this.inventory = Bukkit.createInventory(null, 54, this.name);
		this.language = language;
		
		Bukkit.getPlayer(player1).closeInventory();
		Bukkit.getPlayer(player2).closeInventory();
		Bukkit.getPlayer(player1).openInventory(inventory);
		Bukkit.getPlayer(player2).openInventory(inventory);
		
		TradeMenuLayout.addLayout(inventory);
	}
	
	
	public void handleTradeClick(InventoryClickEvent event) {
		if(!event.getInventory().equals(inventory)) return;
		if(event instanceof InventoryCreativeEvent) return;
		if(closed) return;
		
		if(illegal_clicks.contains(event.getClick())) {
			event.setCancelled(true);
			return;
		}
		
		Player clicker = (Player) event.getWhoClicked();
		
		if(event.getRawSlot() <= 53) {
			//Prevents players from editing eachothers' slots
			if(clicker.getName().equals(player1) && TradeMenuLayout.isP2Slot(event.getRawSlot())) {
				clicker.playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1, 0);
				event.setCancelled(true);
				return;
			} else if (clicker.getName().equals(player2) && TradeMenuLayout.isP1Slot(event.getRawSlot())) {
				clicker.playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1, 0);
				event.setCancelled(true);
				return;
			}
			
			//Sets both buttons to red if a player changes their trade
			if(inventory.getItem(45).getType() != Material.RED_TERRACOTTA || inventory.getItem(53).getType() != Material.RED_TERRACOTTA) {
				if ((clicker.getName().equals(player1) && TradeMenuLayout.isP1Slot(event.getRawSlot())) || (clicker.getName().equals(player2) && TradeMenuLayout.isP2Slot(event.getRawSlot()))) {
					if(ItemMan.isItem(event.getCursor()) || ItemMan.isItem(inventory.getItem(event.getRawSlot()))) {
						//Sets items back to red 1 tick later because idk, this apparently needs to be done after the event is over
						new BukkitRunnable() {
							public void run() {
								if(closed) return;
								inventory.getItem(45).setType(Material.RED_TERRACOTTA);
								inventory.getItem(53).setType(Material.RED_TERRACOTTA);
								clicker.playSound(clicker.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 2);
							}
						}.runTaskLater(Bukkit.getPluginManager().getPlugin("TradeUI"), 1);
					}
				}
			}
		}
		
		
		ItemStack currItem = event.getCurrentItem();
		if(currItem == null) return;
		if(NBTMan.hasTag(currItem, "menu-element")) event.setCancelled(true);
		
		//Button press
		if(NBTMan.hasTag(currItem, "accept-btn")) {
			//Checking which button is pressed and incrementing it's color value
			if(clicker.getName().equals(player1) && NBTMan.hasTag(currItem, "p1-accept-btn") && currItem.getType() != Material.GREEN_TERRACOTTA) {
				if(currItem.getType() == Material.YELLOW_TERRACOTTA) currItem.setType(Material.GREEN_TERRACOTTA);
				else if(currItem.getType() == Material.RED_TERRACOTTA) currItem.setType(Material.YELLOW_TERRACOTTA);
				clicker.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
			} else if (clicker.getName().equals(player2) && NBTMan.hasTag(currItem, "p2-accept-btn") && currItem.getType() != Material.GREEN_TERRACOTTA) {
				if(currItem.getType() == Material.YELLOW_TERRACOTTA) currItem.setType(Material.GREEN_TERRACOTTA);
				else if(currItem.getType() == Material.RED_TERRACOTTA) currItem.setType(Material.YELLOW_TERRACOTTA);
				clicker.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
			} else if (currItem.getType() != Material.GREEN_TERRACOTTA) {
				clicker.playSound(event.getWhoClicked().getLocation().add(0, 5, 0), Sound.BLOCK_ANVIL_LAND, 1, 1);
			} else {
				clicker.playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_CHEST_LOCKED, 1, 2);
			}
			
			//Accepts trade if both buttons are green
			if(inventory.getItem(45).getType() == Material.GREEN_TERRACOTTA && inventory.getItem(53).getType() == Material.GREEN_TERRACOTTA) {
				Player p1 = Bukkit.getPlayer(player1);
				Player p2 = Bukkit.getPlayer(player2);
				
				swapItems();
				this.closed = true;
				
				p1.sendMessage(ChatColor.GREEN + language.getString("trade-accepted").replace("[player]", player2));
				p2.sendMessage(ChatColor.GREEN + language.getString("trade-accepted").replace("[player]", player1));
				p1.playSound(p1.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
				p2.playSound(p2.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
				p1.closeInventory();
				p2.closeInventory();
			}
		}
	}
	
	public void handleTradeClose(InventoryCloseEvent event) {
		if(!event.getInventory().equals(inventory)) return;
		if(closed) return;
		this.closed = true;
		
		Player closer = (Player) event.getPlayer();
		Player other;
		if(closer.getName().equals(player1)) other = Bukkit.getPlayer(player2);
		else other = Bukkit.getPlayer(player1);
		
		closer.sendMessage(ChatColor.YELLOW + language.getString("you-cancelled-trade").replace("[player]", other.getName()));
		other.sendMessage(ChatColor.YELLOW + language.getString("trade-cancelled-with-you").replace("[player]", closer.getName()));
		
		other.closeInventory();
		
		closer.playSound(closer.getLocation().add(0, 10, 0), Sound.BLOCK_ANVIL_LAND, 1, 2);
		other.playSound(closer.getLocation().add(0, 10, 0), Sound.BLOCK_ANVIL_LAND, 1, 2);
		
		giveBackItems();
	}
	
	public void handleDragEvent(InventoryDragEvent event) {
		if(event.getInventory().equals(inventory)) event.setCancelled(true);
	}
	
	
	
	public void setContents(ItemStack[] items) {
		this.inventory.setContents(items);
	}
	
	public void setItem(ItemStack item, int index) {
		this.inventory.setItem(index, item);
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public ItemStack[] getContents() {
		return inventory.getContents();
	}
	
	public ItemStack getItem(int index) {
		return inventory.getItem(index);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public String getPlayer1() {
		return this.player1;
	}
	
	public String getPlayer2() {
		return this.player2;
	}
	
	public boolean isClosed() {
		return this.closed;
	}
	
	
	
	public void close() {
		Player p1 = Bukkit.getPlayer(player1);
		Player p2 = Bukkit.getPlayer(player2);
		if(p1 != null) p1.closeInventory();
		if(p2 != null) p2.closeInventory();
		giveBackItems();
	}
	
	private void giveBackItems() {
		Player p1 = Bukkit.getPlayer(player1);
		Player p2 = Bukkit.getPlayer(player2);
		
		
		if(p1.isDead() || TradeMenuLayout.isFull(p1.getInventory())) 
			for(ItemStack item : TradeMenuLayout.getP1Slots(inventory)) 
				p1.getWorld().dropItemNaturally(p1.getLocation(), item);
		else 
			for(ItemStack item : TradeMenuLayout.getP1Slots(inventory)) 
				p1.getInventory().addItem(item);
		
		
		if(p2.isDead() || TradeMenuLayout.isFull(p2.getInventory()))
			for(ItemStack item : TradeMenuLayout.getP2Slots(inventory))
				p2.getWorld().dropItemNaturally(p2.getLocation(), item);
		else
			for(ItemStack item : TradeMenuLayout.getP2Slots(inventory))
				p2.getInventory().addItem(item);
	}
	
	private void swapItems() {
		Player p1 = Bukkit.getPlayer(player1);
		Player p2 = Bukkit.getPlayer(player2);
		
		
		if(p1.isDead() || !TradeMenuLayout.hasSpace(p1.getInventory(), TradeMenuLayout.getP2Slots(inventory).size())) 
			for(ItemStack item : TradeMenuLayout.getP2Slots(inventory)) 
				p1.getWorld().dropItemNaturally(p1.getLocation(), item);
		else 
			for(ItemStack item : TradeMenuLayout.getP2Slots(inventory)) 
				p1.getInventory().addItem(item);
		
		
		if(p2.isDead() || !TradeMenuLayout.hasSpace(p2.getInventory(), TradeMenuLayout.getP1Slots(inventory).size()))
			for(ItemStack item : TradeMenuLayout.getP1Slots(inventory))
				p2.getWorld().dropItemNaturally(p2.getLocation(), item);
		else
			for(ItemStack item : TradeMenuLayout.getP1Slots(inventory))
				p2.getInventory().addItem(item);
	}
}
