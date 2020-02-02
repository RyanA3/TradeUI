package me.felnstaren.command.trade.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.trade.session.PlayerSession;
import me.felnstaren.trade.session.TradeSession;
import me.felnstaren.trade.session.TradeSessionHandler;
import me.felnstaren.util.chat.Messenger;

public class TradePlayerArgumentStub extends CommandStub {

	public boolean handle(CommandSender sender, String[] args, int current) {
		Player player = (Player) sender;
		player.sendMessage(Messenger.color("&eSent trade request to &7" + args[current] + " &e!"));
		TradeSession session = new TradeSession(new PlayerSession(player, args[current]), new PlayerSession(Bukkit.getPlayer(args[current]), player.getName()));
		session.open();
		
		TradeSessionHandler.getInstance().addSession(session);
		return true;
	}

}
