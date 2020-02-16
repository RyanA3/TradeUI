package me.felnstaren.trade.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import me.felnstaren.trade.session.TradeSession;
import me.felnstaren.trade.session.TradeSessionHandler;

public class TradeRequestHandler {

	private HashMap<String, TradeRequest> requests = new HashMap<String, TradeRequest>();
	private TradeSessionHandler session_handler;
	private FileConfiguration config;
	private FileConfiguration language;
	
	public TradeRequestHandler(TradeSessionHandler session_handler, FileConfiguration config, FileConfiguration language) {
		this.config = config;
		this.session_handler = session_handler;
		this.language = language;
		
		new BukkitRunnable() {
			List<String> remove = new ArrayList<String>();
			
			public void run() {
				for(String sender : requests.keySet()) {
					TradeRequest request = requests.get(sender);
					request.setTime(request.getTime() - 1);
					
					if(request.getTime() <= 0) {
						remove.add(sender);
					}
				}
				
				for(String sender : remove) {
					timeoutRequest(sender);
					requests.remove(sender);
				}
				
				remove.clear();
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("TradeUI"), 0, 20);
	}
	
	
	
	public void addRequest(TradeRequest request) {
		if(hasRequest(request.getSender())) return;
		requests.put(request.getSender(), request);
	}
	
	public void removeRequest(String sender) {
		if(!hasRequest(sender)) return;
		requests.remove(sender);
	}
	
	public boolean hasRequest(String sender) {
		return requests.containsKey(sender);
	}
	
	public TradeRequest getRequest(String sender) {
		if(!hasRequest(sender)) return null;
		return requests.get(sender);
	}
	
	public int getDefaultRequestTimeout() {
		return config.getInt("request-timeout");
	}
	
	
	
	public void cancelRequest(String sender) {
		if(!hasRequest(sender)) return;
		String receiver = requests.get(sender).getReceiver();
		if(Bukkit.getPlayer(sender) != null) Bukkit.getPlayer(sender).sendMessage(ChatColor.GREEN + language.getString("cancelled-own-request").replace("[player]", receiver));
		if(Bukkit.getPlayer(receiver) != null) Bukkit.getPlayer(receiver).sendMessage(ChatColor.YELLOW + language.getString("request-to-you-got-cancelled").replace("[player]", sender));
		requests.remove(sender);
	}
	
	public void denyRequest(String sender) {
		if(!hasRequest(sender)) return;
		String receiver = requests.get(sender).getReceiver();
		if(Bukkit.getPlayer(sender) != null) Bukkit.getPlayer(sender).sendMessage(ChatColor.YELLOW + language.getString("your-request-got-denied").replace("[player]", receiver));
		if(Bukkit.getPlayer(receiver) != null) Bukkit.getPlayer(receiver).sendMessage(ChatColor.GREEN + language.getString("denied-other-request").replace("[player]", sender));
		requests.remove(sender);
	}
	
	public void timeoutRequest(String sender) {
		if(!hasRequest(sender)) return;
		String receiver = requests.get(sender).getReceiver();
		if(Bukkit.getPlayer(sender) != null) Bukkit.getPlayer(sender).sendMessage(ChatColor.YELLOW + language.getString("your-request-timed-out").replace("[player]", receiver));
		if(Bukkit.getPlayer(receiver) != null) Bukkit.getPlayer(receiver).sendMessage(ChatColor.YELLOW + language.getString("request-to-you-timed-out").replace("[player]", sender));
		requests.remove(sender);
	}
	
	public void acceptRequest(String sender) {
		if(!hasRequest(sender)) return;
		TradeRequest request = requests.get(sender);
		String receiver = request.getReceiver();
		
		if(Bukkit.getPlayer(sender) == null) {
			cancelRequest(sender);
		} else if(Bukkit.getPlayer(receiver) == null) {
			denyRequest(sender);
		}
		
		Bukkit.getPlayer(sender).sendMessage(ChatColor.GREEN + language.getString("your-request-was-accepted").replace("[player]", receiver));
		Bukkit.getPlayer(receiver).sendMessage(ChatColor.GREEN + language.getString("you-accepted-a-request").replace("[player]", sender));
		requests.remove(sender);
		
		TradeSession session = new TradeSession(sender, receiver, language);
		session_handler.addTradeSession(session);
	}
	
	
	
	public void closeAllRequests() {
		List<String> remove = new ArrayList<String>();
		
		for(String sender : requests.keySet()) {
			remove.add(sender);
		}
		
		for(String sender : remove) {
			cancelRequest(sender);
		}
	}
}
