package me.felnstaren.command.trade.reload;

import org.bukkit.command.CommandSender;

import me.felnstaren.command.CommandStub;
import me.felnstaren.command.SubCommand;
import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Loader;
import me.felnstaren.config.Options;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class TradeReloadSub extends SubCommand {

	public TradeReloadSub() {
		super(new CommandStub() {
			public boolean handle(CommandSender sender, String[] args, int current) {
				sender.sendMessage(Language.msg("ifo.will-reload"));
				Logger.log(Level.INFO, sender.getName() + " reloaded TradeUI's configuration settings");
				Options.load(Loader.loadOrDefault("config.yml", "config.yml"));
				Language.load(Loader.loadOrDefault("lang.yml", "lang.yml"));
				return true;
			}
		}, "reload");
	}
	
	public boolean handle(CommandSender sender, String[] args, int current) {
		if(!sender.hasPermission("trade_ui.reload")) 
			sender.sendMessage(Language.msg("err.no-permission", new ChatVar("[Permission]", "trade_ui.reload")));
		else
			return forward(sender, args, current);
		return true;
	}

}
