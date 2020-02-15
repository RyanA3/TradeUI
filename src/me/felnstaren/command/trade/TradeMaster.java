package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.MasterCommand;
import me.felnstaren.command.trade.accept.TradeAcceptSub;
import me.felnstaren.command.trade.cancel.TradeCancelSub;
import me.felnstaren.command.trade.deny.TradeDenySub;
import me.felnstaren.command.trade.player.TradePlayerArgument;
import me.felnstaren.util.chat.Messenger;

public class TradeMaster extends MasterCommand {

	public TradeMaster() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				sender.sendMessage(Messenger.color("&7 --- &8[&9TradeUI&8] &7--- \n"
						+ "&a/trade cancel\n"
						+ "&a/trade accept <player>\n"
						+ "&a/trade deny <player>\n"
						+ "&a/trade <player>"));
				return true;
			}
		}, "trade", "tradeui.trade");
		
		commands.add(new TradeAcceptSub());
		commands.add(new TradeCancelSub());
		commands.add(new TradeDenySub());
		
		arguments.add(new TradePlayerArgument());
	}

}
