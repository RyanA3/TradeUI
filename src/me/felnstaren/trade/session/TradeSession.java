package me.felnstaren.trade.session;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.felnstaren.config.Loader;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class TradeSession {

	private boolean complete = false;
	private boolean updating = false;
	private boolean closing = false;
	
	private PlayerSession p1;
	private PlayerSession p2;
	
	public TradeSession(PlayerSession p1, PlayerSession p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public TradeSession(Player sender, Player receiver) {
		this.p1 = new PlayerSession(sender, receiver.getName());
		this.p2 = new PlayerSession(receiver, sender.getName());
		open();
	}
	
	
	
	public void open() {
		Logger.log(Level.DEBUG, "Trade session opened");
		p1.open();
		p2.open();
		queueUpdate();
	}
	
	public void queueUpdate() {
		if(updating) return;
		updating = true;
		
		new BukkitRunnable() {
			public void run() {
				Logger.log(Level.DEBUG, "Trade session updated");
				p1.setDisplayColumn(p2.getInputColumn());
				p2.setDisplayColumn(p1.getInputColumn());
				p1.update();
				p2.update();
				updating = false;
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	public void queueClose() {
		if(closing) return;
		closing = true;
		
		new BukkitRunnable() {
			public void run() {
				close();
				closing = false;
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	public void close() {
		Logger.log(Level.DEBUG, "Trade session closed");
		complete = true;
		p1.cancel();
		p2.cancel();
	}
	
	public void queueAccept() {
		if(closing) return;
		closing = true;
		complete = true;
		
		new BukkitRunnable() {
			public void run() {
				Logger.log(Level.DEBUG, "Trade session accepted");
				if(!p1.isAccepted() || !p2.isAccepted()) {
					queueClose();
					return;
				}
				
				p1.accept(p2.getTradeable());
				p2.accept(p1.getTradeable());
				closing = false;
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	
	
	public void handleEdit(InventoryClickEvent event) {
		if(event.getViewers().contains(p1.getPlayer())) p1.handleEdit(event, p2);
		else if(event.getViewers().contains(p2.getPlayer())) p2.handleEdit(event, p1);
		else return;

		if(p1.isAccepted() && p2.isAccepted()) 
			queueAccept();
		else if(!updating)
			queueUpdate();
	}
	
	public void handleDrag(InventoryDragEvent event) {
		if(event.getViewers().contains(p1.getPlayer()) || event.getViewers().contains(p2.getPlayer())) event.setCancelled(true);
	}
	
	public void handleClose(InventoryCloseEvent event) {
		if(event.getViewers().contains(p1.getPlayer()) || event.getViewers().contains(p2.getPlayer())) queueClose();
	}
	
	
	
	public boolean isComplete() {
		return complete;
	}
	
	public Player getParticipator1() {
		return p1.getPlayer();
	}
	
	public Player getParticipator2() {
		return p2.getPlayer();
	}
	
}
