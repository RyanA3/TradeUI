package me.felnstaren.command.trade.deny.player;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeDenyArgumentStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&eYou've denied &7" + args[current] + "&e's trade request!"));
		return false;
	}

}
