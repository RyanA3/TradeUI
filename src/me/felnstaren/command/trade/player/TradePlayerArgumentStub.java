package me.felnstaren.command.trade.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.trade.request.TradeRequest;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;
import me.felnstaren.util.chat.Messenger;

public class TradePlayerArgumentStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		Player player = (Player) sender;
		Player receiver = Bukkit.getPlayerExact(args[current]);
		TradeRequestHandler thand = TradeRequestHandler.getInstance();
		TradeSessionHandler shand = TradeSessionHandler.getInstance();
		
		if(receiver == null) {
			player.sendMessage(Messenger.color("&7" + args[current] + " &cis not online at the moment!"));
			return true;
		}
		
		if(thand.hasRequestOfSender(receiver)) {
			
			if(shand.hasOpenSession(receiver))
				player.sendMessage(Messenger.color("&7" + receiver.getName() + " &cis currently trading!"));
			 else 
				thand.acceptRequest(thand.getRequestOfSender(receiver));
			
		} else if(thand.hasRequestOfSender(player)) {
			player.sendMessage(Messenger.color("&cYou can only have one active trade request!"));
		} else {
			player.sendMessage(Messenger.color("&eSent trade request to &7" + args[current] + "&e!"));
			thand.addRequest(new TradeRequest(player, receiver));
		}
		
		return true;
	}

}
