package me.felnstaren.command.trade.accept;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubCommand;
import me.felnstaren.command.trade.accept.player.TradeAcceptArgument;

public class TradeAcceptSub extends SubCommand {

	public TradeAcceptSub() {
		super(new TradeAcceptStub(), "accept");
		arguments.add(new TradeAcceptArgument());
	}

	public boolean handle(CommandSender sender, String[] args, int current) {
		forward(sender, args, current);
		return true;
	}

}
