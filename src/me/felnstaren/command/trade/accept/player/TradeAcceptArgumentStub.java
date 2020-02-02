package me.felnstaren.command.trade.accept.player;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeAcceptArgumentStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&eYou've accepted " + args[current] + "'s trade request!"));
		return true;
	}

}
