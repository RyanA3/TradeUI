package me.felnstaren.util.player;

import org.bukkit.entity.Player;

public class PlayerLocationator {

	public static boolean areClose(Player p1, Player p2, int min_distance) {
		if(min_distance < 0) return true;
		if(!p1.getWorld().equals(p2.getWorld())) return false;
		return p1.getLocation().distance(p2.getLocation()) < min_distance;
	}
	
}
