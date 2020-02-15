package me.felnstaren.trade.session;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class TradeSessionHandler implements Listener {
	
	private static TradeSessionHandler instance;
	
	public static TradeSessionHandler getInstance() {
		if(instance == null) instance = new TradeSessionHandler();
		return instance;
	}
	
	public static boolean hasInstance() {
		return instance != null;
	}
	
	
	private ArrayList<TradeSession> sessions = new ArrayList<TradeSession>();
	
	
	@EventHandler
	public void onInventoryEdit(InventoryClickEvent event) {
		for(TradeSession session : sessions)
			session.handleEdit(event);
		
		purgeEndedSessions();
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		for(TradeSession session : sessions) 
			session.handleDrag(event);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		for(TradeSession session : sessions)
			session.handleClose(event);
		
		purgeEndedSessions();
	}
	
	
	
	public void addSession(TradeSession session) {
		sessions.add(session);
	}
	
	public boolean hasOpenSession(Player player) {
		for(TradeSession session : sessions) 
			if(session.getParticipator1().equals(player) || session.getParticipator2().equals(player)) return true;
		return false;
	}
	
	public void purgeEndedSessions() {
		ArrayList<TradeSession> remove = new ArrayList<TradeSession>();
		
		for(TradeSession session : sessions) 
			if(session.isComplete()) remove.add(session);
		
		sessions.removeAll(remove);
	}
	
	public void closeAll() {
		if(instance == null) return;
		
		for(TradeSession session : sessions)
			session.forceClose();
		
		purgeEndedSessions();
	}

}
