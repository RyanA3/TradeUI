package me.felnstaren.event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.felnstaren.trade.request.TradeRequest;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;

public class TradeEvent implements Listener {
	private TradeRequestHandler rhandler;
	private TradeSessionHandler shandler;
	private FileConfiguration config;
	private FileConfiguration language;
	
	public TradeEvent(TradeRequestHandler rhandler, TradeSessionHandler shandler, FileConfiguration config, FileConfiguration language) {
		this.rhandler = rhandler;
		this.shandler = shandler;
		this.config = config;
		this.language = language;
	}

	@EventHandler
	public void rightClickPlayer(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() == null) return;
		if(!(event.getRightClicked() instanceof Player)) return;
		Player receiver = (Player) event.getRightClicked();
		Player sender = event.getPlayer();
		
		if(sender.getName().equals(receiver.getName())) return;
		if(!event.getPlayer().isSneaking()) return;
		
		if(!sender.hasPermission("trade_ui.trade")) return;
		if(!receiver.hasPermission("trade_ui.trade")) return;
		
		if(rhandler.hasRequest(sender.getName())) return;
		if(rhandler.hasRequest(receiver.getName())) {
			rhandler.acceptRequest(receiver.getName());
			return;
		}
		
		if(shandler.isTrading(sender.getName()) || shandler.isTrading(receiver.getName())) return;
		
		TradeRequest request = new TradeRequest(sender.getName(), receiver.getName(), rhandler.getDefaultRequestTimeout(), config.getBoolean("use-commands"), language);
		rhandler.addRequest(request);
	}
	
	//Skript
	public void invClose(InventoryCloseEvent event) {
		event.getView().getTitle();
	}
}