package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.MasterCommand;
import me.felnstaren.command.trade.accept.TradeAcceptSub;
import me.felnstaren.command.trade.cancel.TradeCancelSub;
import me.felnstaren.command.trade.deny.TradeDenySub;
import me.felnstaren.command.trade.player.TradePlayerArgument;
import me.felnstaren.config.Language;
import me.felnstaren.config.Options;

public class TradeMaster extends MasterCommand {

	public TradeMaster() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				if(Options.use_commands) 
					sender.sendMessage(Language.msg("cmd.trade-master-usage"));
				else 
					sender.sendMessage(Language.msg("err.commands-disabled"));
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
