package me.felnstaren.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.felnstaren.config.Options;
import me.felnstaren.trade.request.TradeRequest;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.util.player.PlayerLocationator;

public class TradeWalkAwayListener implements Listener {

	@EventHandler
	public void onStep(PlayerMoveEvent event) {
		if(Options.trade_max_distance < 0 || !Options.cancel_request_on_walk_away) return;
		if(event.getFrom().getBlockX() == event.getTo().getBlockX()
				&& event.getFrom().getBlockY() == event.getTo().getBlockY()
				&& event.getFrom().getBlockZ() == event.getTo().getBlockZ()) 
			return;
		
		Player walker = event.getPlayer();
		TradeRequestHandler thand = TradeRequestHandler.getInstance();
		
		if(thand.hasRequestOfSender(walker)) {
			if(!PlayerLocationator.areClose(walker, thand.getRequestOfSender(walker).getReceiver(), Options.trade_max_distance))
				thand.walkAwayRequest(thand.getRequestOfSender(walker), true);
		} else if(thand.hasRequestOfReceiver(walker)) {
			for(TradeRequest request : thand.getRequestsToReceiver(walker))
				if(!PlayerLocationator.areClose(walker, request.getSender(), Options.trade_max_distance))
					thand.walkAwayRequest(request, false);
		}
	}
	
}
