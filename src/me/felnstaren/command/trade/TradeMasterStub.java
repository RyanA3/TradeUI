package me.felnstaren.command.trade;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeMasterStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&7 --- &8[&9TradeUI&8] &7--- \n"
				+ "&aThis is TradeUI's main command!"));
		
		return true;
	}

}
