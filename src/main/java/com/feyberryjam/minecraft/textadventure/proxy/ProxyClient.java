package com.feyberryjam.minecraft.textadventure.proxy;

import com.feyberryjam.minecraft.textadventure.capabilities.DefaultReceivedJoinMessage;
import com.feyberryjam.minecraft.textadventure.capabilities.IReceivedJoinMessage;
import com.feyberryjam.minecraft.textadventure.capabilities.StorageReceivedJoinMessage;

import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ProxyClient implements IProxy
{
	@Override
	public void preInit() 
	{
	}

	@Override
	public void init() 
	{
		CapabilityManager.INSTANCE.register(IReceivedJoinMessage.class, new StorageReceivedJoinMessage(), DefaultReceivedJoinMessage::new);;
	}

	@Override
	public void postInit() 
	{
	}

}
