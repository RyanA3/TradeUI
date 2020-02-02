package me.felnstaren.command.trade.accept.player;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubArgument;

public class TradeAcceptArgument extends SubArgument {

	public TradeAcceptArgument() {
		super(new TradeAcceptArgumentStub());
	}

	public boolean handle(CommandSender sender, String[] args, int current) {
		stub(sender, args);
		return false;
	}

}
