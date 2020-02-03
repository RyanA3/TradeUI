package me.felnstaren.command.trade.accept.player;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubArgument;

public class TradeAcceptArgument extends SubArgument {

	public TradeAcceptArgument() {
		super(new TradeAcceptArgumentStub(), "<player>");
	}

	public boolean handle(CommandSender sender, String[] args, int current) {
		//stub(sender, args);
		forward(sender, args, current);
		return false;
	}

}
