package com.feyberryjam.minecraft.textadventure.capabilities;

public interface IReceivedJoinMessage 
{
	public void set(boolean value);
	public boolean get();
	
	// no nbt boolean tag?
	public byte getAsByte();	
	public void setAsByte(byte zeroIsFalse);	
}
