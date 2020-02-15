package me.felnstaren.command.trade.accept.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;
import me.felnstaren.util.chat.Messenger;

public class TradeAcceptArgumentStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		Player player = (Player) sender;
		Player accepted = Bukkit.getPlayerExact(args[current]);
		TradeRequestHandler thand = TradeRequestHandler.getInstance();
		TradeSessionHandler shand = TradeSessionHandler.getInstance();
		
		if(accepted == null) {
			player.sendMessage(Messenger.color("&7" + args[current] + " &cis not online at the moment!"));
			return true;
		}
	
		if(!thand.hasRequestOfSender(accepted)) 
			sender.sendMessage(Messenger.color("&7" + accepted.getName() + " &chas not requested to trade with you!"));
		else if(shand.hasOpenSession(accepted))
			sender.sendMessage(Messenger.color("&7" + accepted.getName() + " &cis already trading!"));
		else if(shand.hasOpenSession(player)) 
			sender.sendMessage(Messenger.color("&cYou cant accept a request while trading!"));
		else 
			thand.acceptRequest(thand.getRequestOfSender(accepted));
		
		return true;
	}

}
