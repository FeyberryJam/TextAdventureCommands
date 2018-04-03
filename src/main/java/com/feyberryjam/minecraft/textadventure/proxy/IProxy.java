package com.feyberryjam.minecraft.textadventure.proxy;

import net.minecraft.item.Item;

public interface IProxy 
{
	void preInit();

	void init();

	void postInit();
}
