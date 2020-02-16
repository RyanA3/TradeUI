package me.felnstaren.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class TradeTabCompleter implements TabCompleter {
	
	private FileConfiguration config;
	private FileConfiguration language;
	
	public TradeTabCompleter(FileConfiguration config, FileConfiguration language) {
		this.config = config;
		this.language = language;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		List<String> done = new ArrayList<String>();

		if(args.length == 1) {
			tabs.add(language.getString("command.sub-commands.accept"));
			tabs.add(language.getString("command.sub-commands.cancel"));
			tabs.add(language.getString("command.sub-commands.deny"));
			if(sender.hasPermission("trade_ui.reload")) tabs.add(language.getString("command.sub-commands.reload"));
			
			for(Player player : Bukkit.getOnlinePlayers()) 
				tabs.add(player.getName());
			
			if(args[0] == null || args[0] == "") return tabs;
			StringUtil.copyPartialMatches(args[0], tabs, done);
		} else if (args.length == 2) {
			if(args[0].equals(language.getString("command.sub-commands.accept")) || args[0].equals(language.getString("command.sub-commands.deny"))) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					tabs.add(player.getName());
				}
			}
			
			if(args[1] == null || args[1] == "") return tabs;
			StringUtil.copyPartialMatches(args[1], tabs, done);
		}
		
		Collections.sort(done);
		
		if(!config.getBoolean("use-commands")) done.add(0, language.getString("commands-disabled-short"));
		else if(!sender.hasPermission("trade_ui.trade") && !sender.isOp()) done.add(0, language.getString("no-permission").replace("[command]", label).replace("[permission]", "trade_ui.trade"));
		
		return done;
	}

}
