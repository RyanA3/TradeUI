package me.felnstaren.util.chat;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public class Message {

	private ArrayList<String> components = new ArrayList<String>();
	

	public Message add(String add) {
		components.add("{\"text\":\"" + add + "\"}");
		return this;
	}
	
	public Message add(String add, ChatColor color) {
		components.add("{\"text\":\" + \"" + add + "\",\"color\":\"" + color + "\"");
		return this;
	}
	
	public Message add(String add, String hex_color) {
		components.add("{\"text\":\"" + add + "\",\"color\":\"#" + hex_color + "\"}");
		return this;
	}
	
	public Message addraw(String json) {
		components.add(json);
		return this;
	}
	
	public String build() {
		String message = "[";
		for(int i = 0; i < components.size(); i++) {
			if(i == components.size() - 1) message += components.get(i) + "]";
			else message += components.get(i) + ",";
		}
			
		return message;
	}
	
}
