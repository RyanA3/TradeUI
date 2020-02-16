package me.felnstaren.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.felnstaren.config.Options;
import me.felnstaren.trade.request.TradeRequestHandler;

public class TradeRequestListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEntityEvent event) {
		if(!Options.use_shifting) return;
		if(!(event.getRightClicked() instanceof Player)) return;
		if(event.getHand() != EquipmentSlot.HAND) return;
		Player clicked = (Player) event.getRightClicked();
		Player clicker = event.getPlayer();
		
		if(!clicker.isSneaking()) return;
		
		TradeRequestHandler.getInstance().attemptSendRequest(clicker, clicked);
	}
	
}
