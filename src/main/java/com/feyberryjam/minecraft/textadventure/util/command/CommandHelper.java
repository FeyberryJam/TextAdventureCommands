package com.feyberryjam.minecraft.textadventure.util.command;

import com.feyberryjam.minecraft.textadventure.TextAdventure;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

// here we have things I felt like I was going to use frequently in my commands,
// regardless of how often I really did use them >.<

public class CommandHelper 
{
	public static void TextToPlayer(ICommandSender player, String text)
	{
		player.sendMessage(new TextComponentTranslation(text));
	}

	public static void TextToPlayer(EntityPlayer player, String text)
	{
		player.sendMessage(new TextComponentTranslation(text));
	}
			
	// Minecraft's world.canSeeSkye was giving me terrible results!
	public static boolean canReallyReallySeeSky(World world, BlockPos pos)
	{
		int maxY = world.getHeight();

		// Im stupid...
		//TextAdventure.logger.info("IS THIS even happening?");
		
		BlockPos newPos = pos.up();

		for(int y = pos.getY(); y < maxY; y++)
		{
			if( world.getBlockState(newPos).isOpaqueCube() )
				return false;
			
			newPos = newPos.up();
		}
		
		return true;
	}
	
}
