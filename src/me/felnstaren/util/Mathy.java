package me.felnstaren.util;

public class Mathy {

	public static int max(int value, int max) {
		if(value > max) return max;
		else return value;
	}
	
	public static int min(int value, int min) {
		if(value < min) return min;
		else return value;
	}
	
	public static int clamp(int value, int min, int max) {
		return max(min(value, min), max);
	}
	
}
