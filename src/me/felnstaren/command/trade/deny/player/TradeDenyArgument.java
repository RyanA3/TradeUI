package me.felnstaren.command.trade.deny.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubArgument;
import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.trade.request.TradeRequestHandler;

public class TradeDenyArgument extends SubArgument {

	public TradeDenyArgument() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				Player player = (Player) sender;
				Player denied = Bukkit.getPlayerExact(args[current]);
				TradeRequestHandler thand = TradeRequestHandler.getInstance();
				
				if(denied == null) {
					player.sendMessage(Language.msg("err.player-not-found", new ChatVar("[Player]", args[current])));
					return true;
				}
			
				if(!thand.hasRequestOfSender(denied)) 
					sender.sendMessage(Language.msg("err.player-hasnt-request", new ChatVar("[Player]", denied.getName())));
				else 
					thand.denyRequest(thand.getRequestOfSender(denied));

				return true;
			}
		}, "<player>");
	}

}
