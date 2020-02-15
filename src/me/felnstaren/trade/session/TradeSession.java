package me.felnstaren.trade.session;

import org.bukkit.entity.Player;
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
	
	public TradeSession(Player sender, Player receiver) {
		this.p1 = new PlayerSession(sender, receiver.getName());
		this.p2 = new PlayerSession(receiver, sender.getName());
		open();
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
				p1.update();
				p2.update();
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	public void queueClose() {
		complete = true;
		
		new BukkitRunnable() {
			public void run() {
				p1.cancel();
				p2.cancel();
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	public void forceClose() {
		complete = true;
		p1.cancel();
		p2.cancel();
	}
	
	public void queueAccept() {
		complete = true;
		
		new BukkitRunnable() {
			public void run() {
				if(!p1.isAccepted() || !p2.isAccepted()) {
					queueClose();
					return;
				}
				
				p1.accept(p2.getInputColumn());
				p2.accept(p1.getInputColumn());
			}
		}.runTaskLater(Loader.plugin, 1);
	}
	
	
	
	public void handleEdit(InventoryClickEvent event) {
		if(event.getViewers().contains(p1.getPlayer())) p1.handleEdit(event);
		else if(event.getViewers().contains(p2.getPlayer())) p2.handleEdit(event);
		else return;
		
		if(p1.isResetAcceptStatus()) {
			p1.setResetAcceptStatus(false);
			p2.resetAccept();
		}
		if(p2.isResetAcceptStatus()) {
			p2.setResetAcceptStatus(false);
			p1.resetAccept();
		}

		if(p1.isAccepted() && p2.isAccepted()) 
			queueAccept();
		else 
			queueUpdate();
	}
	
	public void handleDrag(InventoryDragEvent event) {
		if(event.getViewers().contains(p1.getPlayer()) || event.getViewers().contains(p2.getPlayer())) event.setCancelled(true);
	}
	
	public void handleClose(InventoryCloseEvent event) {
		if(!event.getViewers().contains(p1.getPlayer()) && !event.getViewers().contains(p2.getPlayer())) return;
		queueClose();
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
