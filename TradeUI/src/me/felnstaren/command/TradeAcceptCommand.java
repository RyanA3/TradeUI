package me.felnstaren.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;

public class TradeAcceptCommand implements CommandExecutor {
	private TradeRequestHandler rhandler;
	private TradeSessionHandler shandler;
	private FileConfiguration config;
	private FileConfiguration language;
	
	public TradeAcceptCommand(TradeRequestHandler rhandler, TradeSessionHandler shandler, FileConfiguration config, FileConfiguration language) {
		this.rhandler = rhandler;
		this.shandler = shandler;
		this.config = config;
		this.language = language;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Usage: /trade accept <player>");
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + language.getString("not-a-player"));
			return true;
		}
		
		if(sender.getName().equalsIgnoreCase(args[1])) {
			sender.sendMessage(ChatColor.RED + language.getString("cant-accept-own-trade"));
			return true;
		}
		
		if(!rhandler.hasRequest(args[1])) {
			sender.sendMessage(ChatColor.RED + language.getString("player-has-no-request").replace("[player]", args[1]));
			return true;
		}
		
		if(shandler.isTrading(args[1])) {
			sender.sendMessage(ChatColor.RED + language.getString("player-already-trading").replace("[player]", args[1]));
			return true;
		}
		
		if(config.getDouble("max-trade-distance") > 0) {
			if(!((Player) sender).getWorld().equals(Bukkit.getPlayer(args[1]).getWorld())) {
				sender.sendMessage(ChatColor.RED + language.getString("player-too-far-away").replace("[player]", args[0]));
				return true;
			} else if(((Player) sender).getLocation().distance(Bukkit.getPlayer(args[1]).getLocation()) > config.getDouble("max-trade-distance")) {
				sender.sendMessage(ChatColor.RED + language.getString("player-moved-away").replaceAll("[player]", args[1]));
				return true;
			}
		}
		
		rhandler.acceptRequest(args[1]);
		
		return true;
	}
}
