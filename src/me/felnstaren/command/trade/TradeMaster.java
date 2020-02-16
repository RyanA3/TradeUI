package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.MasterCommand;
import me.felnstaren.command.trade.accept.TradeAcceptSub;
import me.felnstaren.command.trade.cancel.TradeCancelSub;
import me.felnstaren.command.trade.deny.TradeDenySub;
import me.felnstaren.command.trade.player.TradePlayerArgument;
import me.felnstaren.config.Options;
import me.felnstaren.util.chat.Messenger;

public class TradeMaster extends MasterCommand {

	public TradeMaster() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				if(Options.use_commands) 
					sender.sendMessage(Messenger.color("&7 --- &8[&9TradeUI&8] &7--- \n"
							+ "&a/trade cancel\n"
							+ "&a/trade accept <player>\n"
							+ "&a/trade deny <player>\n"
							+ "&a/trade <player>"));
				else 
					sender.sendMessage(Messenger.color("&cThis command has been disabled by an administrator. \n" 
							+ "&cmaybe you should &7try shift-right-clicking someone if you wish to trade with them&c."));
				return true;
			}
		}, "trade", "tradeui.trade");
		
		commands.add(new TradeAcceptSub());
		commands.add(new TradeCancelSub());
		commands.add(new TradeDenySub());
		
		arguments.add(new TradePlayerArgument());
	}
	
	@Override public boolean handle(CommandSender sender, String[] args, int current) {
		if(Options.use_commands) return forward(sender, args, current);
		else return stub(sender, args);
	}

}
