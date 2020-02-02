package me.felnstaren.command.trade.cancel;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeCancelStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&eYou cancelled your trade request!"));
		return true;
	}

}
