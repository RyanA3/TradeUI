package me.felnstaren.command.trade.deny;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.util.chat.Messenger;

public class TradeDenyStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		sender.sendMessage(Messenger.color("&eUse /trade deny <player> to deny a specific player's trade request"));
		return true;
	}

}
