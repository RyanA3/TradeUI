package me.felnstaren.trade.session;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class TradeSessionHandler implements Listener {
	
	private ArrayList<TradeSession> sessions = new ArrayList<TradeSession>();
	
	public TradeSessionHandler() {
		
	}
	
	
	
	private static TradeSessionHandler handler;
	public static TradeSessionHandler getInstance() {
		if(handler == null) handler = new TradeSessionHandler();
		return handler;
	}
	
	
	
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
	
	public void purgeEndedSessions() {
		ArrayList<TradeSession> remove = new ArrayList<TradeSession>();
		
		for(TradeSession session : sessions) 
			if(session.isComplete()) remove.add(session);
		
		sessions.removeAll(remove);
	}

}
