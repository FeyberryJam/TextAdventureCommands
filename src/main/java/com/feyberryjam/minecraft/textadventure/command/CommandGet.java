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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandGet extends CommandBase 
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
		return "get";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
        aliases.add("take");        
        aliases.add("grab");        
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.get.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);

		if( args.length == 0)
		{
			// not enough arguments
			// stretch goal, get item directly in front of player? just get everything?
			CommandHelper.TextToPlayer(sender, ">get");
			CommandHelper.TextToPlayer(sender, "What would you like to get?");
			return;
		}
		
		// what am I getting?
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
		CommandHelper.TextToPlayer(sender, ">get " + getItem);


		EntityPlayer player = (EntityPlayer)entity;		
		World world = player.getEntityWorld();
		BlockPos playerPosition = player.getPosition();

		// do eet!
		boolean foundAtLeastOne = false;
		BlockPos start = playerPosition.add(-Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE);
		BlockPos end = playerPosition.add(Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE);
		List<EntityItem> listEI = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(start, end));
		if( listEI.size() > 0 )
		{
			for(EntityItem ei : listEI)
			{
				if( getAll || ei.getItem().getDisplayName().equalsIgnoreCase(getItem) )
				{
					if( player.canEntityBeSeen(ei))
					{
						foundAtLeastOne = true;
						ei.moveToBlockPosAndAngles(playerPosition, 0, 0);
						
					}
				}
			}
		}
		
		if(!foundAtLeastOne)
		{
			if( getAll )
			{
				CommandHelper.TextToPlayer(sender, "There is nothing nearby!" );
			}			
			else
			{
				CommandHelper.TextToPlayer(sender, "I don't see " + EnglishHelper.prefixWithAorAn(getItem) + " nearby." );				
			}
		}	
		
	}

}
