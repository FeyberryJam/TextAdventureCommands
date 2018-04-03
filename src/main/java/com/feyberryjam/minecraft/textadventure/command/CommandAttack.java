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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// I feel like if I sent a packet to the client I could make the player perform a swing animation during
// the attack, and that would look better.  I also feel like some ray tracing or something would be nice to 
// make sure the target is in front of the player

public class CommandAttack extends CommandBase 
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
		return "attack";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
        aliases.add("hit");        
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.attack.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);
		
		EntityPlayer player = (EntityPlayer)entity;		
		World world = player.getEntityWorld();
		BlockPos playerPosition = player.getPosition();

		// Skye doesn't seem to know what she's hitting.. with.. oddly.. so let's get the name of that
		String weaponName = "hand";
		if( !player.getHeldItemMainhand().isEmpty() )
		{
			weaponName = player.getHeldItemMainhand().getDisplayName().toLowerCase();
		}

		if( args.length == 0)
		{
			// if no args/target
			CommandHelper.TextToPlayer(sender, ">attack");
			// Legends of the Red Dragon reference
			CommandHelper.TextToPlayer(sender, "You brandish your " + weaponName + " dramatically!");
			CommandHelper.TextToPlayer(sender, "Please type a target to attack.");

			return;
		}
					
		
		// what should I hit?
		String hitTarget = "";
		for( String string : args)
		{
			hitTarget += string + " ";
		}
		hitTarget = hitTarget.trim();
		
		// display result
		CommandHelper.TextToPlayer(sender, ">attack " + hitTarget);

		CommandHelper.TextToPlayer(sender, "You attempt to hit " + EnglishHelper.prefixWithAorAn(hitTarget) + " with your " + weaponName + ".");
						
		// do eet!
		boolean foundAtLeastOne = false;
		BlockPos start = playerPosition.add(-Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE, -Constants.ENTITY_VIEW_DISTANCE);
		BlockPos end = playerPosition.add(Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE, Constants.ENTITY_VIEW_DISTANCE);
		List<EntityLiving> listEL = world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(start, end));
		if( listEL.size() > 0 )
		{
			for(EntityLiving el : listEL)
			{
				if( player.canEntityBeSeen(el) )
				{
					if( el.getDisplayName().getUnformattedText().equalsIgnoreCase(hitTarget))
					{					
						foundAtLeastOne = true;						
						player.attackTargetEntityWithCurrentItem(el);
						// punching everything around is more satisfying then just punching the first thing found.
						// in a finished product, we'd want to punch the nearest creature that's in front of us
						// break;
					}
				}
			}
		}
		
		if(!foundAtLeastOne)
		{
			CommandHelper.TextToPlayer(sender, "I don't see " + EnglishHelper.prefixWithAorAn(hitTarget) + " nearby." );
		}
		
	}

}
