package me.felnstaren.command.trade.accept;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeAcceptStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&aUse /trade accept <player> to accept a specific player's trade request!"));
		return true;
	}

}
