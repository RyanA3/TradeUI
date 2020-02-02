package me.felnstaren.util.logger;

import org.bukkit.ChatColor;

public enum Level {

	SEVERE(ChatColor.RED, 3),
	WARNING(ChatColor.YELLOW, 2),
	INFO(ChatColor.GRAY, 1),
	DEBUG(ChatColor.GRAY, 0);
	
	public final ChatColor color;
	public final int weight;
	
	private Level(ChatColor color, int weight) {
		this.color = color;
		this.weight = weight;
	}
	
	
	
	public boolean hasPriority(Level level) {
		return this.weight >= level.weight;
	}
	
	public boolean hasPriority(int level) {
		return this.weight >= level;
	}
	
}
