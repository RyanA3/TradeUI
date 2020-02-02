package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.MasterCommand;
import me.felnstaren.command.trade.accept.TradeAcceptSub;
import me.felnstaren.command.trade.cancel.TradeCancelSub;
import me.felnstaren.command.trade.deny.TradeDenySub;
import me.felnstaren.command.trade.player.TradePlayerArgument;
import me.felnstaren.util.chat.Messenger;

public class TradeMaster extends MasterCommand {

	public TradeMaster() {
		super(new TradeMasterStub(), "tradeui.trade");
		
		commands.add(new TradeAcceptSub());
		commands.add(new TradeCancelSub());
		commands.add(new TradeDenySub());
		
		arguments.add(new TradePlayerArgument());
	}
	
	
	
	public boolean handle(CommandSender sender, String[] args, int current) {
		if(!sender.hasPermission(this.permission)) {
			sender.sendMessage(Messenger.color("&cYou do not have permission to &7" + permission + "&c!"));
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messenger.color("&cOnly players can use this command!"));
			return true;
		}
		
		return forward(sender, args, current);
	}

}
