package com.feyberryjam.minecraft.textadventure;

public class Constants 
{
    public static final String MODID = "textadventure";
    public static final String NAME = "Text Adventure Commands";
    public static final String VERSION = "goty edition";
    
	// Proxies 
	public static final String PROXY_CLIENT = "com.feyberryjam.minecraft.textadventure.proxy.ProxyClient";
	public static final String PROXY_SERVER = "com.feyberryjam.minecraft.textadventure.proxy.ProxyServer";

	// here was have options that should have been put in a config file instead!
	
	public static final int ENTITY_VIEW_DISTANCE = 10;

	public static final int PLAYER_RUMMAGE_DISTANCE = 3;
	public static final int PLAYER_CHOP_DISTANCE_X = 5;
	public static final int PLAYER_CHOP_DISTANCE_Y = 10;
	
	public static final int UNDERGROUND_Y_LEVEL = 55;
	
	public static final float DARK_ENOUGH_FOR_MOB_SPAWN = 0.18f;
	
	public static final int CLOSE_ENOUGH_TO_VILLAGE = 50;
	
	// probably change to config later
	public static final float DARK_ENOUGH_FOR_GRUE = 0.05f;
	public static final float DARK_ENOUGH_FOR_BLINDNESS = 0.1f;
	
}