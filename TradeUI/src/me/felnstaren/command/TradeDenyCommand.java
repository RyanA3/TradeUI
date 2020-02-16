package me.felnstaren.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.felnstaren.trade.request.TradeRequestHandler;

public class TradeDenyCommand implements CommandExecutor {
	private TradeRequestHandler handler;
	private FileConfiguration language;
	
	public TradeDenyCommand(TradeRequestHandler handler, FileConfiguration language) {
		this.handler = handler;
		this.language = language;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Usage: /trade deny <player>");
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + language.getString("not-a-player"));
			return true;
		}
		
		if(sender.getName().equalsIgnoreCase(args[1])) {
			sender.sendMessage(ChatColor.RED + language.getString("cant-deny-own-trade"));
			return true;
		}
		
		if(!handler.hasRequest(args[1])) {
			sender.sendMessage(ChatColor.RED + language.getString("player-has-no-request").replace("[player]", args[1]));
			return true;
		}
		
		handler.denyRequest(args[1]);
		
		return true;
	}
}
