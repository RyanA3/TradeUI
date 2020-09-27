package me.felnstaren.trade.request;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Loader;
import me.felnstaren.config.Options;
import me.felnstaren.trade.session.TradeSession;
import me.felnstaren.trade.session.TradeSessionHandler;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;
import me.felnstaren.util.player.PlayerLocationator;

public class TradeRequestHandler {

	private static TradeRequestHandler instance;
	
	public static TradeRequestHandler getInstance() {
		if(instance == null) instance = new TradeRequestHandler();
		return instance;
	}
	
	public static boolean hasInstance() {
		return instance != null;
	}
	
	
	private ArrayList<TradeRequest> queue_add = new ArrayList<TradeRequest>();
	private ArrayList<TradeRequest> queue_remove = new ArrayList<TradeRequest>();
	private HashMap<TradeRequest, Integer> requests = new HashMap<TradeRequest, Integer>();
	private boolean looping = false;
	
	private BukkitRunnable iterator;
	
	public TradeRequestHandler() {
		Logger.log(Level.DEBUG, "Entering Trade Request timeout loop");
		
		iterator = new BukkitRunnable() {
			public void run() {
				looping = true;
				for(TradeRequest request : requests.keySet()) {
					if(requests.get(request) < 0) {
						queue_remove.add(request);
						request.sendTimeoutMessage();
					} else {
						requests.put(request, requests.get(request) - 1);
					}
				}
				looping = false;
				
				purgeRemoveQueue();
				emptyAddQueue();
			}
		};
		
		iterator.runTaskTimer(Loader.plugin, 0, 20);
	}
	
	
	
	public void purgeRemoveQueue() {
		if(queue_remove.isEmpty()) return;
		Logger.log(Level.DEBUG, "Removing " + queue_remove.size() + " requests from active trade requests");
		for(TradeRequest request : queue_remove) requests.remove(request);
		queue_remove.clear();
	}
	
	public void emptyAddQueue() {
		if(queue_add.isEmpty()) return;
		Logger.log(Level.DEBUG, "Adding " + queue_add.size() + " requests to active trade requests");
		
		for(TradeRequest request : queue_add) {
			if(requests.containsKey(request)) continue;
			requests.put(request, Options.trade_request_timeout);
			request.sendInitialMessage();
		}
		
		queue_add.clear();
	}
	
	
	
	
	public void attemptSendRequest(Player sender, Player receiver) {
		TradeSessionHandler shand = TradeSessionHandler.getInstance();
		
		
		if(sender.equals(receiver) && !Options.allow_trade_self) 
			sender.sendMessage(Language.msg("err.cant-trade-self"));
		else if(!PlayerLocationator.areClose(sender, receiver, Options.trade_max_distance)) 
			sender.sendMessage(Language.msg("err.player-too-far-away", new ChatVar("[Player]", receiver.getName())));
		else if(hasRequestOfSender(receiver)) {
			
			if(shand.hasOpenSession(receiver))
				sender.sendMessage(Language.msg("err.player-is-trading", new ChatVar("[Player]", receiver.getName())));
			 else 
				acceptRequest(getRequestOfSender(receiver));
			
		} else if(hasRequestOfSender(sender)) 
			sender.sendMessage(Language.msg("err.only-one-request"));
		else 
			addRequest(sender, receiver);
		
	}
	
	private void addRequest(Player sender, Player receiver) {
		TradeRequest request = new TradeRequest(sender, receiver, Options.trade_request_timeout);
		
		if(requests.containsKey(request)) return;
		
		if(looping) queue_add.add(request);
		else {
			request.sendInitialMessage();
			requests.put(request, Options.trade_request_timeout);
			Logger.log(Level.DEBUG, "Directly added trade request to active requests");
		}
	}
	
	private void removeRequest(TradeRequest request) {
		if(!requests.containsKey(request)) return;
		
		if(looping) queue_remove.add(request);
		else {
			requests.remove(request);
			Logger.log(Level.DEBUG, "Directly removed trade request from active requests");
		}
	}
	
	
	
	public void acceptRequest(TradeRequest request) {
		if(request.getSender().getGameMode() == GameMode.SPECTATOR || request.getReceiver().getGameMode() == GameMode.SPECTATOR) {
			request.getSender().sendMessage(Language.msg("err.impossible-action"));
			request.getReceiver().sendMessage(Language.msg("err.impossible-action"));
			return;
		}
		
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
	
	public void walkAwayRequest(TradeRequest request, boolean sender_walked) {
		removeRequest(request);
		request.sendWalkAwayMessage(sender_walked);
	}

	
	
	
	public boolean hasRequestOfReceiver(Player player) {
		for(TradeRequest request : requests.keySet()) if(request.getReceiver().equals(player)) return true;
		return false;
	}
	
	public ArrayList<TradeRequest> getRequestsToReceiver(Player player) {
		ArrayList<TradeRequest> out = new ArrayList<TradeRequest>();
		for(TradeRequest request : requests.keySet()) if(request.getReceiver().equals(player)) out.add(request);
		return out;
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
		Logger.log(Level.DEBUG, "Cancelling all active trade requests in TradeRequestHandler");
		if(instance == null) return;
		
		iterator.cancel();
		
		queue_remove.clear();
		queue_add.clear();
		
		for(TradeRequest request : requests.keySet()) request.sendCancelMessage();
		
		requests.clear();
	}
	
}
