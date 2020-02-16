package me.felnstaren.trade.session;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class TradeSessionHandler implements Listener {

	private List<TradeSession> sessions = new ArrayList<TradeSession>();
	private FileConfiguration language;

	
	public TradeSessionHandler(FileConfiguration language) {
		this.language = language;
	}
	
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		for(TradeSession session : sessions) {
			session.handleTradeClick(event);
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event) {
		for(TradeSession session : sessions) {
			session.handleTradeClose(event);
		}
		
		//purgeInactiveSessions();
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		for(TradeSession session : sessions) {
			session.handleDragEvent(event);
		}
	}
	
	
	
	public void addTradeSession(TradeSession session) {
		purgeInactiveSessions();
		
		Player p1 = Bukkit.getPlayer(session.getPlayer1());
		Player p2 = Bukkit.getPlayer(session.getPlayer2());
		if(p1.getGameMode() == GameMode.SPECTATOR || p2.getGameMode() == GameMode.SPECTATOR) {
			p1.sendMessage(ChatColor.RED + language.getString("no-spectator-mode"));
			p2.sendMessage(ChatColor.RED + language.getString("no-spectator-mode"));
			return;
		}
		
		sessions.add(session);
	}
	
	public boolean isTrading(String player) {
		purgeInactiveSessions();
		
		if(sessions.isEmpty()) return false;
		
		for(TradeSession session : sessions) {
			if(session.getPlayer1().equals(player) || session.getPlayer2().equals(player)) return true;
		}
		
		return false;
	}
	
	
	public void closeAllSessions() {
		purgeInactiveSessions();
		
		if(sessions.isEmpty()) return;
		
		for(TradeSession session : sessions) {
			session.close();
		}
		
		sessions.clear();
	}
	
	public void purgeInactiveSessions() {
		if(sessions.isEmpty()) return;
		
		List<TradeSession> remove = new ArrayList<TradeSession>();
		
		for(TradeSession session : sessions) {
			if(session.isClosed()) remove.add(session);
		}
		
		if(remove.isEmpty()) return;
		for(TradeSession session : remove) {
			sessions.remove(session);
		}
	}
}
