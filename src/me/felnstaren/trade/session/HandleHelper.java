package me.felnstaren.trade.session;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.inventory.ClickType;

public class HandleHelper {

	public static List<Integer> illegal_slots = Arrays.asList(
			4, 5, 6, 7, 8,
			13, 14, 15, 16, 17,
			22, 23, 24, 25, 26,
			31, 32, 33, 34, 35,
			40, 41, 42, 43, 44,
			49, 50, 51, 52
	);
	
	public static List<ClickType> illegal_clicks = Arrays.asList(
			ClickType.DOUBLE_CLICK,
			ClickType.SHIFT_LEFT,
			ClickType.SHIFT_RIGHT,
			ClickType.WINDOW_BORDER_LEFT,
			ClickType.WINDOW_BORDER_RIGHT
			);
			
	
}
