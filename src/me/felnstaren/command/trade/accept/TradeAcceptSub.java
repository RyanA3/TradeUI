package me.felnstaren.command.trade.accept;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubCommand;
import me.felnstaren.command.trade.accept.player.TradeAcceptArgument;
import me.felnstaren.config.Language;

public class TradeAcceptSub extends SubCommand {

	public TradeAcceptSub() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				sender.sendMessage(Language.msg("cmd.accept-usage"));
				return true;
			}
		}, "accept");
		arguments.add(new TradeAcceptArgument());
	}

}
