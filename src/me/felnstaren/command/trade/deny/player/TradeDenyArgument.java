package me.felnstaren.command.trade.deny.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubArgument;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.util.chat.Messenger;

public class TradeDenyArgument extends SubArgument {

	public TradeDenyArgument() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				Player player = (Player) sender;
				Player denied = Bukkit.getPlayerExact(args[current]);
				TradeRequestHandler thand = TradeRequestHandler.getInstance();
				
				if(denied == null) {
					player.sendMessage(Messenger.color("&7" + args[current] + " &cis not online at the moment!"));
					return true;
				}
			
				if(!thand.hasRequestOfSender(denied)) 
					sender.sendMessage(Messenger.color("&7" + denied.getName() + " &chas not requested to trade with you!"));
				else 
					thand.denyRequest(thand.getRequestOfSender(denied));

				return true;
			}
		}, "<player>");
	}

}
