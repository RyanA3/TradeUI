package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeMasterStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&7 --- &8[&9TradeUI&8] &7--- \n"
				+ "&a/trade cancel\n"
				+ "&a/trade accept <player>\n"
				+ "&a/trade deny <player>\n"
				+ "&a/trade <player>"));
		
		return true;
	}

}
