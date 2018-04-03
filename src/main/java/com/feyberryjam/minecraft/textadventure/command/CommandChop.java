package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.Constants;
import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;

// This command should probably be more complex, it could apply damage to a held axe for example

public class CommandChop extends CommandBase 
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
		return "chop";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
		aliases.add("cut");
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.chop.usage";
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
		
		// Totally inadequate command, just murders everything
		CommandHelper.TextToPlayer(sender, ">chop");		

		int count = 0;
		
		for(int x = -Constants.PLAYER_CHOP_DISTANCE_X; x < Constants.PLAYER_CHOP_DISTANCE_X; x++)
		{
			for(int y = -Constants.PLAYER_CHOP_DISTANCE_Y; y < Constants.PLAYER_CHOP_DISTANCE_Y; y++)
			{
				for(int z = -Constants.PLAYER_CHOP_DISTANCE_X; z < Constants.PLAYER_CHOP_DISTANCE_X; z++)
				{
					BlockPos thisPos = new BlockPos(playerPosition.getX() + x, playerPosition.getY() + y, playerPosition.getZ() + z );
					IBlockState thisBlock = world.getBlockState(thisPos);
					if( thisBlock.getBlock() instanceof BlockLog )
					{
						count++;
						world.destroyBlock(thisPos, true);
					}
				}							
			}			
		}		

		String display = "You cut down " + count + " log";
		if( count != 1 )
			display +="s";
		display +=".";
		CommandHelper.TextToPlayer(sender, display);		
		
	}
}
