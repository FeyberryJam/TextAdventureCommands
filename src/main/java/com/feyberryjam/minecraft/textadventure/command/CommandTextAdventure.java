package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandTextAdventure extends CommandBase
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
		return "textadventure";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
        aliases.add("ta");        
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.textadventure.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		CommandHelper.TextToPlayer(sender, "commands.textadventure.execute");
	}

}
