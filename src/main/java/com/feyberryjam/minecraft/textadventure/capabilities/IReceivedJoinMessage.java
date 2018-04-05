package com.feyberryjam.minecraft.textadventure.capabilities;

public interface IReceivedJoinMessage 
{
	public void set(boolean value);
	public boolean get();
	
	// no nbt boolean tag?  Looking into it, it seems like the byte tag IS generally used for true/false flags and I did fine here.
	// Stop updating your comments and pushing to github for everyone to see your mistakes <3
	
	public byte getAsByte();	
	public void setAsByte(byte zeroIsFalse);	
}
