package me.felnstaren.command;

import org.bukkit.command.CommandSender;

public interface CommandElement {

	public boolean handle(CommandSender sender, String[] args, int current);
	
}
