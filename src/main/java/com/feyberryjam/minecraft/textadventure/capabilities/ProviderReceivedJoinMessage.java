package com.feyberryjam.minecraft.textadventure.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderReceivedJoinMessage implements ICapabilitySerializable<NBTBase> 
{
	@CapabilityInject(IReceivedJoinMessage.class)
	public static final Capability<IReceivedJoinMessage> CAP_ReceivedJoinMessage = null;
	private IReceivedJoinMessage instance = CAP_ReceivedJoinMessage.getDefaultInstance();
	
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		return capability == CAP_ReceivedJoinMessage;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		return capability == CAP_ReceivedJoinMessage ? CAP_ReceivedJoinMessage.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() 
	{
		return CAP_ReceivedJoinMessage.getStorage().writeNBT(CAP_ReceivedJoinMessage, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) 
	{
		CAP_ReceivedJoinMessage.getStorage().readNBT(CAP_ReceivedJoinMessage, this.instance, null, nbt);
	}
}
