package me.felnstaren.util.menu;

import org.bukkit.Material;

import me.felnstaren.util.item.ItemBuild;

public class TradeMenu extends Menu {

	public TradeMenu(String other_player) {
		super("YOU       >>>   " + other_player, 54);
		setItem(new ItemBuild(Material.RED_TERRACOTTA).setName("&aAccept").tag("element").tag("accept_button").get(), 3, 5);
		fillItems(new ItemBuild(Material.RED_STAINED_GLASS_PANE).tag("element").get(), 4, 0, 1, 6);
	}

}
