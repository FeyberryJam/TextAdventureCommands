package com.feyberryjam.minecraft.textadventure.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageReceivedJoinMessage implements IStorage<IReceivedJoinMessage>
{
	// notice write is get, and read is set (sort of the opposite, really) since I have to get what to write, and set what is read

	// from a quick scan I didn't see a getAsBoolean/setAsBoolean so I gues this is fine...

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
