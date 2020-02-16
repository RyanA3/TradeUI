package me.felnstaren.command.trade.deny;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubCommand;
import me.felnstaren.command.trade.deny.player.TradeDenyArgument;
import me.felnstaren.config.Language;

public class TradeDenySub extends SubCommand {

	public TradeDenySub() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				sender.sendMessage(Language.msg("cmd.deny-usage"));
				return true;
			}
		}, "deny");
		arguments.add(new TradeDenyArgument());
	}

}
