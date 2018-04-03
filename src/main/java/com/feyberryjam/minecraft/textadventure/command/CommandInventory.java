package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandInventory extends CommandBase 
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
		return "inventory";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
		aliases.add("inv");
		aliases.add("i");
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.inventory.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{

		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);

		EntityPlayer player = (EntityPlayer)entity;
		
		CommandHelper.TextToPlayer(sender, ">inventory");
		
		boolean foundAtLeastOne = false;
		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			if( !player.inventory.getStackInSlot(i).isEmpty() )
			{
				foundAtLeastOne = true;

				String itemDisplay = player.inventory.getStackInSlot(i).getDisplayName();
				String itemCount = Integer.toString(player.inventory.getStackInSlot(i).getCount()); 
				String itemDamaged = "";
				
				if(player.inventory.getStackInSlot(i).isItemStackDamageable())
				{
					// number gets bigger as more broken, up 0 to 1, because my math is awkward!
					if((float)player.inventory.getStackInSlot(i).getItemDamage() / player.inventory.getStackInSlot(i).getMaxDamage() < 0.95f )
						itemDamaged = "A Pristine ";
					if((float)player.inventory.getStackInSlot(i).getItemDamage() / player.inventory.getStackInSlot(i).getMaxDamage() > 0.5f )
						itemDamaged = "Damaged ";
					if((float)player.inventory.getStackInSlot(i).getItemDamage() / player.inventory.getStackInSlot(i).getMaxDamage() > 0.8f )
						itemDamaged = "Nearly Broken ";					
				}
				
				CommandHelper.TextToPlayer(player, "   " + itemDamaged + itemDisplay + " x " + itemCount);
			}
		}
		
		if(!foundAtLeastOne)
		{
			CommandHelper.TextToPlayer(sender, "You're carrying nothing but a smile." );				
		}

	}
}
