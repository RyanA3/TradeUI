package me.felnstaren.trade.session;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.felnstaren.config.Loader;

public class TradeSession {

	private boolean complete = false;
	
	private PlayerSession p1;
	private PlayerSession p2;
	
	public TradeSession(PlayerSession p1, PlayerSession p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	
	public void open() {
		p1.open();
		p2.open();
		queueUpdate();
	}
	
	public void queueUpdate() {
		new BukkitRunnable() {
			public void run() {
				p1.setDisplayColumn(p2.getInputColumn());
				p2.setDisplayColumn(p1.getInputColumn());
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	public void queueClose() {
		new BukkitRunnable() {
			public void run() {
				p1.cancel();
				p2.cancel();
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	
	
	public void handleEdit(InventoryClickEvent event) {
		if(event.getViewers().contains(p1.getPlayer())) p1.handleEdit(event);
		else if(event.getViewers().contains(p2.getPlayer())) p2.handleEdit(event);
		else return;

		queueUpdate();
	}
	
	public void handleDrag(InventoryDragEvent event) {
		if(event.getViewers().contains(p1.getPlayer()) || event.getViewers().contains(p2.getPlayer())) event.setCancelled(true);
	}
	
	public void handleClose(InventoryCloseEvent event) {
		if(!event.getViewers().contains(p1.getPlayer()) && !event.getViewers().contains(p2.getPlayer())) return;
		queueClose();
		complete = true;
	}
	
	
	
	public boolean isComplete() {
		return complete;
	}
	
}
