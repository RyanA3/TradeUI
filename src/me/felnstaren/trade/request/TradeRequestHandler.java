package me.felnstaren.trade.request;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.felnstaren.config.Loader;
import me.felnstaren.config.Options;
import me.felnstaren.trade.session.TradeSession;
import me.felnstaren.trade.session.TradeSessionHandler;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class TradeRequestHandler {

	private static TradeRequestHandler instance;
	
	public static TradeRequestHandler getInstance() {
		if(instance == null) instance = new TradeRequestHandler();
		return instance;
	}
	
	
	private ArrayList<TradeRequest> queue_add = new ArrayList<TradeRequest>();
	private ArrayList<TradeRequest> queue_remove = new ArrayList<TradeRequest>();
	private HashMap<TradeRequest, Integer> requests = new HashMap<TradeRequest, Integer>();
	
	private BukkitRunnable iterator;
	
	public TradeRequestHandler() {
		iterator = new BukkitRunnable() {
			public void run() {
				for(TradeRequest request : requests.keySet()) {
					if(requests.get(request) < 0) {
						queue_remove.add(request);
						request.sendTimeoutMessage();
					} else {
						requests.put(request, requests.get(request) - 1);
					}
				}
				
				purgeRemoveQueue();
				emptyAddQueue();
				
				Logger.log(Level.DEBUG, "Loop " + requests.size() + " active trade requests");
			}
		};
		
		iterator.runTaskTimer(Loader.plugin, 0, 20);
	}
	
	
	
	public void purgeRemoveQueue() {
		for(TradeRequest request : queue_remove) requests.remove(request);
		queue_remove.clear();
	}
	
	public void emptyAddQueue() {
		for(TradeRequest request : queue_add) {
			if(requests.containsKey(request)) continue;
			requests.put(request, Options.trade_request_timeout);
			request.sendInitialMessage();
		}
		
		queue_add.clear();
	}
	
	
	public void addRequest(TradeRequest request) {
		queue_add.add(request);
	}
	
	private void removeRequest(TradeRequest request) {
		queue_remove.add(request);
	}
	
	
	
	public void acceptRequest(TradeRequest request) {
		removeRequest(request);
		request.sendAcceptMessage();
		TradeSessionHandler.getInstance().addSession(new TradeSession(request.getSender(), request.getReceiver()));
	}
	
	public void cancelRequest(TradeRequest request) {
		removeRequest(request);
		request.sendCancelMessage();
	}
	
	public void denyRequest(TradeRequest request) {
		removeRequest(request);
		request.sendDenyMessage();
	}

	
	
	
	public boolean hasRequestOfSender(Player player) {
		for(TradeRequest request : requests.keySet()) if(request.getSender().equals(player)) return true;
		return false;
	}
	
	public TradeRequest getRequestOfSender(Player sender) {
		for(TradeRequest request : requests.keySet()) 
			if(request.getSender().equals(sender)) return request;
		return null;
	}
	
	
	
	
	
	public void close() {
		iterator.cancel();
		
		queue_remove.clear();
		queue_add.clear();
		
		for(TradeRequest request : requests.keySet()) request.sendCancelMessage();
		
		requests.clear();
	}
	
}
