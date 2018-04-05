package com.feyberryjam.minecraft.textadventure.capabilities;

public class DefaultReceivedJoinMessage implements IReceivedJoinMessage 
{
	private boolean received = false;

	@Override
	public void set(boolean value) 
	{
		this.received = value;
	}

	@Override
	public boolean get() 
	{
		return this.received;
	}

	@Override
	public byte getAsByte() 
	{
		return (byte) (this.received ? 1 : 0);
	}

	@Override
	public void setAsByte(byte zeroIsFalse) 
	{
		if( zeroIsFalse == 0 )
			this.received = false;
		else
			this.received = true;
	}
}
