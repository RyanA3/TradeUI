package me.felnstaren.command.trade.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubArgument;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.util.chat.Messenger;

public class TradePlayerArgument extends SubArgument {
	
	public TradePlayerArgument() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				Player player = (Player) sender;
				Player receiver = Bukkit.getPlayerExact(args[current]);

				if(receiver == null) {
					player.sendMessage(Messenger.color("&7" + args[current] + " &cis not online at the moment!"));
					return true;
				}
				
				TradeRequestHandler.getInstance().attemptSendRequest(player, receiver);

				return true;
			}
		}, "<player>");
	}

}
