package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.Constants;
import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandRummage extends CommandBase
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
		return "rummage";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
        aliases.add("rum");        
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.rummage.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		// verify we're a player
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);
		

		EntityPlayer player = (EntityPlayer)entity;		
		World world = player.getEntityWorld();
		BlockPos playerPosition = player.getPosition();

		
		
		// look in nearby area for first usable tall grass typoe of block
		// todo: randomly pick one tall grass
		
		BlockPos rummagePos = new BlockPos(playerPosition.getX(), playerPosition.getY(), playerPosition.getZ());
		
		for(int x = -Constants.PLAYER_RUMMAGE_DISTANCE; x < Constants.PLAYER_RUMMAGE_DISTANCE; x++)
		{
			for(int y = -Constants.PLAYER_RUMMAGE_DISTANCE; y < Constants.PLAYER_RUMMAGE_DISTANCE; y++)
			{
				for(int z = -Constants.PLAYER_RUMMAGE_DISTANCE; z < Constants.PLAYER_RUMMAGE_DISTANCE; z++)
				{
					BlockPos thisPos = new BlockPos(playerPosition.getX() + x, playerPosition.getY() + y, playerPosition.getZ() + z );
					IBlockState thisBlock = world.getBlockState(thisPos);
					if( thisBlock.getBlock() instanceof BlockTallGrass )
					{
						rummagePos = new BlockPos(thisPos.getX(), thisPos.getY(), thisPos.getZ());
					}
				}							
			}			
		}		
		
		
		
		// actually rummage		
		if( world.getBlockState(rummagePos).getBlock() instanceof BlockTallGrass )
		{
			CommandHelper.TextToPlayer(player, "You rummage around.");
			int youFind = world.rand.nextInt(15);
			if( youFind < 2 )
			{
				CommandHelper.TextToPlayer(player, "You find a stick!");
				world.spawnEntity(new EntityItem(world, (double)rummagePos.getX(), (double)rummagePos.getY(), (double)rummagePos.getZ(), new ItemStack(Items.STICK)));				
				world.destroyBlock(rummagePos, false);
			}
			else if (youFind < 5 )
			{
				CommandHelper.TextToPlayer(player, "You find a flint!");
				world.spawnEntity(new EntityItem(world, (double)rummagePos.getX(), (double)rummagePos.getY(), (double)rummagePos.getZ(), new ItemStack(Items.FLINT)));				
				world.destroyBlock(rummagePos, false);
			}
			else if (youFind < 10 )
			{
				CommandHelper.TextToPlayer(player, "You find a seed!");
				world.spawnEntity(new EntityItem(world, (double)rummagePos.getX(), (double)rummagePos.getY(), (double)rummagePos.getZ(), new ItemStack(Items.WHEAT_SEEDS)));				
				world.destroyBlock(rummagePos, false);
			}
			else
			{
				CommandHelper.TextToPlayer(player, "You find nothing!");				
			}

			
		}
		else
		{
			CommandHelper.TextToPlayer(player, "You are not near any tall tall grass or anything else suitable to rummage through.");			
		}		
		
	} 
}
