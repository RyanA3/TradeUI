package me.felnstaren.command.trade.accept.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubArgument;
import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Options;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;

public class TradeAcceptArgument extends SubArgument {

	public TradeAcceptArgument() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				Player player = (Player) sender;
				Player accepted = Bukkit.getPlayerExact(args[current]);
				TradeRequestHandler thand = TradeRequestHandler.getInstance();
				TradeSessionHandler shand = TradeSessionHandler.getInstance();
				
				if(accepted == null) {
					player.sendMessage(Language.msg("err.player-not-found", new ChatVar("[Player]", args[current])));
					return true;
				}
				
				if(!accepted.hasPermission("trade_ui.trade") && Options.require_trade_permission) {
					player.sendMessage(Language.msg("err.player-hasnt-permission", new ChatVar("[Player]", accepted.getName()), new ChatVar("[Permission]", "trade_ui.trade")));
					return true;
				}
			
				if(!thand.hasRequestOfSender(accepted)) 
					sender.sendMessage(Language.msg("err.player-hasnt-request", new ChatVar("[Player]", accepted.getName())));
				else if(shand.hasOpenSession(accepted))
					sender.sendMessage(Language.msg("err.player-is-trading", new ChatVar("[Player]", accepted.getName())));
				else if(shand.hasOpenSession(player)) 
					sender.sendMessage(Language.msg("err.impossible-action"));
				else 
					thand.acceptRequest(thand.getRequestOfSender(accepted));
				
				return true;
			}
		}, "<player>");
	}

}
