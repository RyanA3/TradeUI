package me.felnstaren.trade.request;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;

import net.minecraft.server.v1_15_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;

public class TradeRequest {

	private boolean active = true;
	private String sender;
	private String receiver;
	private int time;
	
	public TradeRequest(String sender, String receiver, int timeout, boolean use_commands, FileConfiguration language) {
		this.sender = sender;
		this.receiver = receiver;
		this.time = timeout;
		
		
		CraftPlayer csender = (CraftPlayer) Bukkit.getPlayer(sender);
		CraftPlayer creceiver = (CraftPlayer) Bukkit.getPlayer(receiver);
		
		csender.sendMessage(ChatColor.GREEN + language.getString("sent-trade-request").replace("[player]", receiver).replace("[time]", timeout + ""));
		creceiver.sendMessage(ChatColor.GREEN + language.getString("received-trade-request").replace("[player]", sender).replace("[time]", timeout + ""));
		
		if(use_commands) {
			String sjson = "[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"" + capFirstLetter(language.getString("command.sub-commands.cancel")) + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade " + language.getString("command.sub-commands.cancel") + "\"}},{\"text\":\"]\",\"color\":\"gray\"}]";
			PacketPlayOutChat rpacket = new PacketPlayOutChat(ChatSerializer.b(sjson));
			csender.getHandle().playerConnection.sendPacket(rpacket);
			
			String rjson = "[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"" + capFirstLetter(language.getString("command.sub-commands.accept")) + "\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade " + language.getString("command.sub-commands.accept") + " " + sender + "\"}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"" + capFirstLetter(language.getString("command.sub-commands.deny")) + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade " + language.getString("command.sub-commands.deny") + " " + sender + "\"}},{\"text\":\"]\",\"color\":\"gray\"}]";
			PacketPlayOutChat spacket = new PacketPlayOutChat(ChatSerializer.b(rjson));
			creceiver.getHandle().playerConnection.sendPacket(spacket);
		}
	}
	
	
	
	public String getSender() {
		return this.sender;
	}
	
	public String getReceiver() {
		return this.receiver;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	
	public int getTime() {
		return this.time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	
	public String capFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
}
