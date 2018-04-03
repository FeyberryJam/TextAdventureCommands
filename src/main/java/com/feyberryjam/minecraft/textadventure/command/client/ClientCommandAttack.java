package com.feyberryjam.minecraft.textadventure.command.client;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// here we have an experiment to see if a "client command" would trigger twice (on server and client)
// and/or if a client command by the same name as a server command would fire of both...

public class ClientCommandAttack extends CommandBase 
{
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
		
		if( args.length == 0)
		{
			CommandHelper.TextToPlayer(sender, ">attack");
			CommandHelper.TextToPlayer(sender, "You strike out randomly!");
			
			// right click works perfectly and may wind up being part of my "open door" code, it's cute
			// to shear sheep with text commands.

			// right click?
			KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
			// left
			// nothing I tried for left click worked
			// KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode());
			return;
		}

		
		// what am I hitting?
		String getItem = "";
		for( String string : args)
		{
			getItem += string + " ";
		}
		getItem = getItem.trim();
		
		// display result
		CommandHelper.TextToPlayer(sender, ">attack " + getItem);		
	}

}
