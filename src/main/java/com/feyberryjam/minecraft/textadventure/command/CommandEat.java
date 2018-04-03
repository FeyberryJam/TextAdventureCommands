package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandEat  extends CommandBase
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
		return "eat";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
        aliases.add("devour");        
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.eat.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);

		EntityPlayer player = (EntityPlayer)entity;
		World world = player.getEntityWorld();

		CommandHelper.TextToPlayer(sender, ">eat");
		
		if( player.getHeldItemMainhand().isEmpty() )
		{
			CommandHelper.TextToPlayer(sender, "Eat what? There is nothing in your hand!");				
			return;
		}
		
		if( !(player.getHeldItemMainhand().getItem() instanceof ItemFood) )
		{
			CommandHelper.TextToPlayer(sender, "You lick the " + player.getHeldItemMainhand().getDisplayName().toLowerCase() +".");				
			return;			
		}
		
		ItemFood food = (ItemFood)player.getHeldItemMainhand().getItem();
		
		CommandHelper.TextToPlayer(sender, "You eat the " + player.getHeldItemMainhand().getDisplayName().toLowerCase() +".");				
		food.onItemUseFinish(player.getHeldItemMainhand(), world, player);
		
	}
}
