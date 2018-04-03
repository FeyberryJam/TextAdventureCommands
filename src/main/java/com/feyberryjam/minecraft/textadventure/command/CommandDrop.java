package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.Constants;
import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;
import com.feyberryjam.minecraft.textadventure.util.text.EnglishHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

// this command should probably be changed to allow you to specify a number
// such as >drop 25 leather
// in order to drop the first 25 found in the inventory

public class CommandDrop extends CommandBase 
{
	@Override
	public int getRequiredPermissionLevel() 
	{
		return 0;
	}

	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

	@Override
	public String getName() 
	{
		return "drop";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
		aliases.add("ditch");
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.drop.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);

		EntityPlayer player = (EntityPlayer)entity;

			
		// if no args/target
		if( args.length == 0)
		{
			CommandHelper.TextToPlayer(sender, ">drop");
			
			if( player.getHeldItemMainhand().isEmpty() )
			{
				CommandHelper.TextToPlayer(sender, "Drop what? There is nothing in your hand!");				
				return;
			}
			
			String dropWhat = player.getHeldItemMainhand().getDisplayName();
			CommandHelper.TextToPlayer(sender, "You drop the " + dropWhat);
			
			player.dropItem(false);			
			return;
		}		

		
		// what am I dropping?
		boolean getAll = false;
		String getItem = "";
		for( String string : args)
		{
			getItem += string + " ";
		}
		getItem = getItem.trim();

		if(getItem.equalsIgnoreCase("all") || getItem.equalsIgnoreCase("everything"))
		{
			getAll = true;
		}
		
		// display result
		CommandHelper.TextToPlayer(sender, ">drop " + getItem);
			
		int count = 0;

		// actually drop
		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			if( !player.inventory.getStackInSlot(i).isEmpty())
			{
				if( getAll || player.inventory.getStackInSlot(i).getDisplayName().equalsIgnoreCase(getItem) )
				{
					count++;
					player.dropItem(player.inventory.removeStackFromSlot(i), true);
				}
			}			
		}
		
		if( count == 0)
		{
			if( getAll )
			{
				CommandHelper.TextToPlayer(sender, "You have nothing to drop!" );
			}			
			else
			{
				CommandHelper.TextToPlayer(sender, "You don't have any " + getItem + "." );				
			}			
		}
		else
		{
			String display = "You drop " + count + " item";
			if( count != 1 )
				display +="s";
			display +=".";
			CommandHelper.TextToPlayer(sender, display);		
		}

	}
}
