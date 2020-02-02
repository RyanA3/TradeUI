package me.felnstaren.util.chat;

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
	
	public static String prefix(String message, String prefix) {
		return prefix.concat(message);
	}
	
	public static String permission(String permission) {
		return color("&cYou do not have permission to &7" + permission + "&c!");
	}
	
}
