package me.felnstaren.command.trade;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.felnstaren.command.MasterCommand;
import me.felnstaren.command.trade.accept.TradeAcceptSub;
import me.felnstaren.command.trade.cancel.TradeCancelSub;
import me.felnstaren.command.trade.deny.TradeDenySub;
import me.felnstaren.command.trade.player.TradePlayerArgument;
import me.felnstaren.util.chat.Messenger;

public class TradeMaster extends MasterCommand {

	public TradeMaster() {
		super(new TradeMasterStub(), "trade", "tradeui.trade");
		
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


	public ArrayList<String> tab(CommandSender sender, String[] args, int current) {
		ArrayList<String> tabs = forwardTab(sender, args, current);
		if(tabs.contains("<player>")) {
			ArrayList<String> remove = new ArrayList<String>();
			for(String str : tabs) if(str.equals("<player>")) remove.add(str);
			tabs.removeAll(remove);
			
			for(Player player : Bukkit.getOnlinePlayers()) tabs.add(player.getName());
		}
		
		ArrayList<String> done = new ArrayList<String>();
		StringUtil.copyPartialMatches(args[args.length - 1], tabs, done);
		return done;
	}

}
