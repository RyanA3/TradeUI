package me.felnstaren.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.felnstaren.trade.request.TradeRequestHandler;

public class TradeCancelCommand implements CommandExecutor {
	private TradeRequestHandler handler;
	private FileConfiguration language;
	
	public TradeCancelCommand(TradeRequestHandler handler, FileConfiguration language) {
		this.handler = handler;
		this.language = language;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + language.getString("not-a-player"));
			return true;
		}
		
		if(!handler.hasRequest(sender.getName())) {
			sender.sendMessage(ChatColor.RED + language.getString("you-have-no-request"));
			return true;
		}
		
		handler.cancelRequest(sender.getName());
		
		return true;
	}
}
