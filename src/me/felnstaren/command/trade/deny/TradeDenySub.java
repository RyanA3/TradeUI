package me.felnstaren.command.trade.deny;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubCommand;
import me.felnstaren.command.trade.deny.player.TradeDenyArgument;

public class TradeDenySub extends SubCommand {

	public TradeDenySub() {
		super(new TradeDenyStub(), "deny");
		arguments.add(new TradeDenyArgument());
	}

	public boolean handle(CommandSender sender, String[] args, int current) {
		forward(sender, args, current);
		return true;
	}

}
