package com.feyberryjam.minecraft.textadventure.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageReceivedJoinMessage implements IStorage<IReceivedJoinMessage>
{
	// being now far less tired and over worked, I see why the byte/boolean conversion I did here was silly and I'll be fixing this later

	@Override
	public NBTBase writeNBT(Capability<IReceivedJoinMessage> capability, IReceivedJoinMessage instance, EnumFacing side) 
	{
		return new NBTTagByte (instance.getAsByte());
	}

	@Override
	public void readNBT(Capability<IReceivedJoinMessage> capability, IReceivedJoinMessage instance, EnumFacing side, NBTBase nbt) 
	{
		instance.setAsByte( ((NBTPrimitive)nbt).getByte() );
	}
	

}
