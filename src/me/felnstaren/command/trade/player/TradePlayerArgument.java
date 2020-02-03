package me.felnstaren.command.trade.player;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubArgument;

public class TradePlayerArgument extends SubArgument {
	
	public TradePlayerArgument() {
		super(new TradePlayerArgumentStub(), "<player>");
	}
	
	

	public boolean handle(CommandSender sender, String[] args, int current) {
		stub(sender, args);
		return true;
	}

}
