package me.felnstaren.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import me.felnstaren.TradeMain;

public class TradeReloadCommand implements CommandExecutor {

	private TradeMain main;
	private FileConfiguration language;
	
	public TradeReloadCommand(TradeMain main, FileConfiguration language) {
		this.main = main;
		this.language = language;
	}
	
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("trade_ui.reload")) {
			sender.sendMessage(ChatColor.RED + language.getString("no-permission").replace("[command]", "reload TradeUI").replace("[permission]", "trade_ui.reload"));
			return true;
		}
		
		sender.sendMessage(ChatColor.GREEN + "Reloading TradeUI");
		
		main.onDisable();
		main.onEnable();
		
		return true;
	}
	
}
