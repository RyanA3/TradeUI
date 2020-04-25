package me.felnstaren.trade.request;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Options;
import net.minecraft.server.v1_15_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;

public class TradeRequest115 extends TradeRequest {
	
	public TradeRequest115(Player sender, Player receiver) {
		super(sender, receiver, Options.trade_request_timeout);
	}
	
	public void sendInitialMessage() {
		sender.sendMessage(Language.msg("ifo.sent-request", new ChatVar("[Player]", receiver.getName()), new ChatVar("[Timeout]", timeout + "")));
		if(Options.use_commands) {
			String sjson = "[\"\",{\"text\":\"" + Language.msg("cmd.cancel-button") + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade cancel\"}}]";
			PacketPlayOutChat rpacket = new PacketPlayOutChat(ChatSerializer.b(sjson));
			((CraftPlayer) sender).getHandle().playerConnection.sendPacket(rpacket);
		}
		
		receiver.sendMessage(Language.msg("ifo.request-received", new ChatVar("[Player]", sender.getName()), new ChatVar("[Timeout]", timeout + "")));
		if(Options.use_commands) {
			String rjson = "[\"\",{\"text\":\"" + Language.msg("cmd.accept-button") + "\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade accept " + sender.getName() + "\"}},{\"text\":\"   \",\"color\":\"gray\"},{\"text\":\"" + Language.msg("cmd.deny-button") + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade deny " + sender.getName() + "\"}}]";
			PacketPlayOutChat spacket = new PacketPlayOutChat(ChatSerializer.b(rjson));
			((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(spacket);
		}
	}
	
}
