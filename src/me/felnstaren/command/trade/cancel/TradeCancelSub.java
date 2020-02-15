package me.felnstaren.command.trade.cancel;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubCommand;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.util.chat.Messenger;

public class TradeCancelSub extends SubCommand {

	public TradeCancelSub() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				Player player = (Player) sender;
				TradeRequestHandler thand = TradeRequestHandler.getInstance();
			
				if(!thand.hasRequestOfSender(player)) 
					sender.sendMessage(Messenger.color("&cYou do not have a trade request!"));
				else 
					thand.cancelRequest(thand.getRequestOfSender(player));

				return true;
			}
		}, "cancel");
	}

}
