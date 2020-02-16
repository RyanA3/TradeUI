package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.MasterCommand;
import me.felnstaren.command.trade.accept.TradeAcceptSub;
import me.felnstaren.command.trade.cancel.TradeCancelSub;
import me.felnstaren.command.trade.deny.TradeDenySub;
import me.felnstaren.command.trade.player.TradePlayerArgument;
import me.felnstaren.command.trade.reload.TradeReloadSub;
import me.felnstaren.config.ChatVar;
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
		commands.add(new TradeReloadSub());
		
		arguments.add(new TradePlayerArgument());
	}
	
	@Override 
	public boolean handle(CommandSender sender, String[] args, int current) {
		if(!sender.hasPermission(this.permission) && Options.require_trade_permission) {
			sender.sendMessage(Language.msg("err.no-permission", new ChatVar("[Permission]", permission)));
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Language.msg("err.impossible-action"));
			return true;
		}
		
		if(Options.use_commands || (args.length > 0 && args[0].toLowerCase().equals("reload"))) {
			if(!forward(sender, args, current)) 
				sender.sendMessage(Language.msg("err.invalid-command"));
		} else stub(sender, args);
		
		return true;
	}

}
