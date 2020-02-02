package me.felnstaren.command.trade.cancel;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.SubCommand;

public class TradeCancelSub extends SubCommand {

	public TradeCancelSub() {
		super(new TradeCancelStub(), "cancel");
	}


	public boolean handle(CommandSender sender, String[] args, int current) {
		stub(sender, args);
		return true;
	}

}
