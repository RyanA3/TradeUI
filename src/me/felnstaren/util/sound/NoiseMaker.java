package me.felnstaren.util.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class NoiseMaker {

	public static void playsound(Sound sound, Location location, float radius, float pitch) {
		location.getWorld().playSound(location, sound, radius, pitch);
	}
	
	public static void playsound(Sound sound, Player player, float radius, float pitch, float volume) {
		player.playSound(player.getLocation().add(0, volume, 0), sound, radius, pitch);
	}
	
}
