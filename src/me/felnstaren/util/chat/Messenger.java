package me.felnstaren.util.chat;

import org.bukkit.entity.Player;

import me.felnstaren.util.Reflector;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class Messenger {

	public static String color(String message) {
		return message.replace('&', '§');
	}
	
	public static String uncolor(String message) {
		message = color(message);
		char[] msg = message.toCharArray();
		String newmsg = "";
		
		for(int i = 0; i < msg.length; i++) {
			if(i > 0 && msg[i] != '§' && msg[i - 1] != '§') newmsg += msg[i];
			if(i == 0 && msg[i] != '§') newmsg += msg[i];
		}
		
		return newmsg;
	}
	
	public static void sendJSON(Player player, String message) {
		try {
			//Create the packet
			Object component = Reflector.METHOD_CACHE.get("b").invoke(null , message);
			Object type[] = Reflector.getNMSClass("ChatMessageType").getEnumConstants();
			Object packet = Reflector.CONSTRUCTOR_CACHE.get(Reflector.getNMSClass("PacketPlayOutChat")).newInstance(component, type[0], player.getUniqueId());
			
			//Get the player connection
			Object craft_player = Reflector.getNMSClass("CraftPlayer").cast(player);
			Object entity_player = Reflector.METHOD_CACHE.get("getHandle").invoke(craft_player);
			Object player_connection = Reflector.FIELD_CACHE.get(Reflector.getNMSClass("EntityPlayer")).get(entity_player);
			
			//Send the packet to the player connection
			Reflector.METHOD_CACHE.get("sendPacket").invoke(player_connection, packet);
		} catch(Exception e) {
			e.printStackTrace();
			Logger.log(Level.WARNING, "Error sending JSON message, is your plugin up to date?");
		}
	}
	
	public static String prefix(String message, String prefix) {
		return prefix.concat(message);
	}
	
	public static String permission(String permission) {
		return color("&cYou do not have permission to &7" + permission + "&c!");
	}
	
}
