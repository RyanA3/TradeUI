package me.felnstaren.command.trade.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubArgument;
import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Options;
import me.felnstaren.trade.request.TradeRequestHandler;

public class TradePlayerArgument extends SubArgument {
	
	public TradePlayerArgument() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				Player player = (Player) sender;
				Player receiver = Bukkit.getPlayerExact(args[current]);

				if(receiver == null || !Bukkit.getOnlinePlayers().contains(receiver)) {
					player.sendMessage(Language.msg("err.player-not-found", new ChatVar("[Player]", args[current])));
					return true;
				}
				
				if(!receiver.hasPermission("trade_ui.trade") && Options.require_trade_permission) {
					player.sendMessage(Language.msg("err.player-hasnt-permission", new ChatVar("[Player]", receiver.getName()), new ChatVar("[Permission]", "trade_ui.trade")));
					return true;
				}
				
				TradeRequestHandler.getInstance().attemptSendRequest(player, receiver);

				return true;
			}
		}, "<player>");
	}

}
