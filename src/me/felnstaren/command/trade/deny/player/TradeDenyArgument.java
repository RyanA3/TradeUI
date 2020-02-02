package me.felnstaren.command.trade.deny.player;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubArgument;

public class TradeDenyArgument extends SubArgument {

	public TradeDenyArgument() {
		super(new TradeDenyArgumentStub());
	}

	public boolean handle(CommandSender sender, String[] args, int current) {
		stub(sender, args);
		return true;
	}

}
