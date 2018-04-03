package com.feyberryjam.minecraft.textadventure.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.Constants;
import com.feyberryjam.minecraft.textadventure.TextAdventure;
import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;
import com.feyberryjam.minecraft.textadventure.util.text.EnglishHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

// Here we have my first command before I knew really what I was doing, which has
// been changed many times and I'm not super happy with!

// This command doesn't work on servers because getBiomeName() is client side only.  
// or at least that's what I believe.  Some research says reflection might help but
// I'm under that reflection is slow and scary!  :(

// later: I, think I've done the reflection. I don't think I've ever successfully done 
// that before now.  I'll never feel clean again....

public class CommandLook extends CommandBase
{
	public enum WhatToLookAt
	{
		EVERYTHING,
		ROOM,
		SUN,
		CREATURES,
		ITEMS
	}

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
		return "look";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
        aliases.add("l");        
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.look.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);

		
		
		// which "look" are we doing here?  i.e. "look at items"
		WhatToLookAt lookAt = WhatToLookAt.EVERYTHING;
		if( args.length == 0)
		{
			// the basic look			
			CommandHelper.TextToPlayer(sender, ">look");
		}
		
		// just ignore the at <.<
		if(args.length == 2)
		{
			if( args[0].equalsIgnoreCase("at") )
				args[0] = args[1];
		}
		
		if( args.length == 1 || args.length == 2 )
		{
			boolean validOneArgument = false;
			if( args[0].equalsIgnoreCase("everything") || args[0].equalsIgnoreCase("all"))
			{
				CommandHelper.TextToPlayer(sender, ">look");
				validOneArgument = true;
			}
			if( args[0].equalsIgnoreCase("location") || args[0].equalsIgnoreCase("place") || args[0].equalsIgnoreCase("room"))
			{
				CommandHelper.TextToPlayer(sender, ">look at location");
				lookAt = WhatToLookAt.ROOM;
				validOneArgument = true;
			}
			if( args[0].equalsIgnoreCase("sun") || args[0].equalsIgnoreCase("sky"))
			{
				CommandHelper.TextToPlayer(sender, ">look at sun");
				lookAt = WhatToLookAt.SUN;
				validOneArgument = true;
			}
			if( args[0].equalsIgnoreCase("items") || args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i"))
			{
				CommandHelper.TextToPlayer(sender, ">look at items");
				lookAt = WhatToLookAt.ITEMS;
				validOneArgument = true;
			}
			if( args[0].equalsIgnoreCase("creatures") || args[0].equalsIgnoreCase("mobs") || args[0].equalsIgnoreCase("c"))
			{
				CommandHelper.TextToPlayer(sender, ">look at creatures");
				lookAt = WhatToLookAt.CREATURES;
				validOneArgument = true;
			}
			
			if( !validOneArgument )
				throw new WrongUsageException("commands.look.usage", new Object[0]);
		}
		
		
		EntityPlayer player = (EntityPlayer)entity;		
		World world = player.getEntityWorld();
		BlockPos playerPosition = player.getPosition();
		
		// Check the lighting
		// this was probably not the best way to get the light level near the player
		// I found all the normal systems problematic and didn't want to take the time to
		// perfect it, given this is a 3 day mode.
		float lighting = world.getLightBrightness(playerPosition);				
		//CommandHelper.TextToPlayer(sender, "*debugging*");
		//CommandHelper.TextToPlayer(sender, Float.toString(lighting));
		
		
		// is player actually blind?
		for( PotionEffect effect : player.getActivePotionEffects() )
		{
			if( effect.getPotion() == MobEffects.BLINDNESS )
			{
				CommandHelper.TextToPlayer(sender, "commands.look.blind");
				return;
			}
		}
				

		// is too dark to see?
		boolean canSee = true;
		if( lighting <= Constants.DARK_ENOUGH_FOR_BLINDNESS )
			canSee = false;

		
		if( lookAt == WhatToLookAt.SUN || lookAt == WhatToLookAt.EVERYTHING )
		{
			// this is making massive lazy assumptions about the surroundings based on y-coord and such
			// This is also using sun positions as it exists on the surface in vanilla, and isn't smart
			// about other dimensions such as The End.
			Long time = world.getWorldTime() % 24000;

			if( !CommandHelper.canReallyReallySeeSky(world, playerPosition) )
			{
				CommandHelper.TextToPlayer(sender, "The sky is not visible directly above you.");				
			}
			else if(player.posY < Constants.UNDERGROUND_Y_LEVEL)
			{
				// crudely "detect" under ground
				// everyone get on the else-if train, choo chooooo
				if( time >= 22500 || time < 1000 )
					CommandHelper.TextToPlayer(sender, "The sky above appears dim.");
				else if( time >= 1000 && time < 11000 )
					CommandHelper.TextToPlayer(sender, "The sun appears to be bright above you.");
				else if( time >= 11000 && time < 13000 )
					CommandHelper.TextToPlayer(sender, "The sky above appears dim.");
				else if( time >= 13000 && time < 22500)
					CommandHelper.TextToPlayer(sender, "The sun is nowhere to be seen.");								
			}
			else
			{
				if( time >= 22500 || time < 1000 )
                    CommandHelper.TextToPlayer(sender, "The morning sun is visible off to the east.");
				else if( time >= 1000 && time < 11000 )
                {
                    CommandHelper.TextToPlayer(sender, "The sun hangs high in the sky.");
                    // don't look directly at the sun! 
                    if( lookAt == WhatToLookAt.SUN )
                    {
                        CommandHelper.TextToPlayer(sender, "commands.look.sunbad");
                        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 250, 1));
                    }

                }
				else if( time >= 11000 && time < 13000 )
                    CommandHelper.TextToPlayer(sender, "It will be night soon...");
				else if( time >= 13000 && time < 22500)
                    CommandHelper.TextToPlayer(sender, "It is currently night.");
			}
		}
		
		if( canSee && (lookAt == WhatToLookAt.EVERYTHING || lookAt == WhatToLookAt.ROOM ) )
		{
			if( player.posY < Constants.UNDERGROUND_Y_LEVEL )
			{
				// crudely "detect" under ground
				CommandHelper.TextToPlayer(sender, "You appear to be deep under ground.");				
			}
			else
			{
				// show biome if "above ground" and enough light
				Biome playerBiome = world.getBiome(playerPosition);
				String biomeName = "strange and wonderful land"; // playerBiome.getBiomeName().toLowerCase();
				
				// ** reflection
				// how do I reflect properly?  is this good?
				Field f = ReflectionHelper.findField(Biome.class, "field_76791_y", "biomeName");
				f.setAccessible(true);

				// Enough try/catch's will fix anything!
				try 
				{
					biomeName = (String) f.get(playerBiome);
				} 
				catch (IllegalArgumentException e) 
				{
				} 
				catch (IllegalAccessException e) 
				{
				}				
				// ** reflection over
				
				biomeName = biomeName.toLowerCase();
				
				String string = "Here we have " + EnglishHelper.prefixWithAorAn(biomeName) + ".";
				CommandHelper.TextToPlayer(player, string);

				if( world.isRainingAt(playerPosition) )
				{
					CommandHelper.TextToPlayer(player, "commands.look.rainedon");;					
				}

			}
						
			// is this a good way to find village?!?
			Village nearVillage = world.getVillageCollection().getNearestVillage(playerPosition, Constants.CLOSE_ENOUGH_TO_VILLAGE);
					
			if(nearVillage != null)
				CommandHelper.TextToPlayer(sender, "You are near a village.");
		}

		// creatures and so on
		if( canSee && (lookAt == WhatToLookAt.EVERYTHING || lookAt == WhatToLookAt.CREATURES) )
		{
			// list entities!
			BlockPos start = playerPosition.add(-Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE);
			BlockPos end = playerPosition.add(Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE);
			List<EntityLiving> listEL = world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(start, end));
			boolean firstSeen = true;
			if( listEL.size() > 0 )
			{
				for(EntityLiving el : listEL)
				{
					if( player.canEntityBeSeen(el) )
					{
						if( firstSeen )
						{
							firstSeen = false;
							CommandHelper.TextToPlayer(sender, "There are creatures nearby:");
						}

						CommandHelper.TextToPlayer(sender, "   " + el.getDisplayName().getUnformattedText());						
					}
				}
			}

			if ( lookAt == WhatToLookAt.CREATURES && firstSeen )
			{
				CommandHelper.TextToPlayer(sender, "You see no nearby creatures.");				
			}
		}
		
		// items and so on
		if( canSee && (lookAt == WhatToLookAt.EVERYTHING || lookAt == WhatToLookAt.ITEMS) )
		{
			// list entities!
			BlockPos start = playerPosition.add(-Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE);
			BlockPos end = playerPosition.add(Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE);
			List<EntityItem> listEI = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(start, end));

			boolean firstSeen = true;
			if( listEI.size() > 0 )
			{
				for(EntityItem ei : listEI)
				{
					if( player.canEntityBeSeen(ei) )
					{
						if( firstSeen )
						{
							firstSeen = false;
							CommandHelper.TextToPlayer(sender, "There are items nearby:");
						}
						CommandHelper.TextToPlayer(sender, "   " + ei.getItem().getDisplayName());
					}
				}
			}
			if ( lookAt == WhatToLookAt.ITEMS && firstSeen)
			{
				CommandHelper.TextToPlayer(sender, "You see no nearby items.");				
			}
		}
		

		// Too dark too see?  This was an interesting idea done poorly.
		
		if( !canSee )
		{
			CommandHelper.TextToPlayer(sender, "You are unable to see much around you without more light.");			
		}
		
		if( lighting <= Constants.DARK_ENOUGH_FOR_MOB_SPAWN )
		{
			String tempString = "It's very dark here";
			
			// fun fact!  Druidry had grues in the Samhain dimensions and I was
			// going to put something like that here as well, but didn't get to it.
			if( lighting <= Constants.DARK_ENOUGH_FOR_GRUE )
				tempString += "!\nJust the way grues like it.";
			else
				tempString += ".";
				
			CommandHelper.TextToPlayer(player, tempString);
		}
			
	}
}
