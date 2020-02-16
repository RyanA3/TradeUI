package me.felnstaren.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.felnstaren.trade.request.TradeRequest;
import me.felnstaren.trade.request.TradeRequestHandler;

public class TradeCommand implements CommandExecutor {
	private TradeRequestHandler handler;
	private FileConfiguration config;
	private FileConfiguration language;
	
	public TradeCommand(TradeRequestHandler handler, FileConfiguration config, FileConfiguration language) {
		this.handler = handler;
		this.config = config;
		this.language = language;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1 || args[0] == null) {
			sender.sendMessage(ChatColor.RED + "Usage: <player || cancel || deny || accept> [player]");
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + language.getString("not-a-player"));
			return true;
		}
		
		if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
			sender.sendMessage(ChatColor.RED + language.getString("player-not-online").replace("[player]", args[0]));
			return true;
		}
		
		if(sender.getName().equalsIgnoreCase(args[0])) {
			sender.sendMessage(ChatColor.RED + language.getString("cant-trade-with-self"));
			return true;
		}
	
		if(handler.hasRequest(sender.getName())) {
			sender.sendMessage(ChatColor.RED + language.getString("cant-have-multiple-requests"));
			return true;
		}
		
		if(config.getDouble("max-trade-distance") > 0) {
			if(!((Player) sender).getWorld().equals(Bukkit.getPlayer(args[0]).getWorld())) {
				sender.sendMessage(ChatColor.RED + language.getString("player-too-far-away").replace("[player]", args[0]));
				return true;
			} else if(((Player) sender).getLocation().distance(Bukkit.getPlayer(args[0]).getLocation()) > config.getDouble("max-trade-distance")) {
				sender.sendMessage(ChatColor.RED + language.getString("player-too-far-away").replace("[player]", args[0]));
				return true;
			}
		}
		
		if(handler.hasRequest(args[0])) {
			handler.acceptRequest(args[0]);
			return true;
		}
		
		TradeRequest request = new TradeRequest(sender.getName(), args[0], handler.getDefaultRequestTimeout(), true, language);
		handler.addRequest(request);
		
		return true;
	}
}
