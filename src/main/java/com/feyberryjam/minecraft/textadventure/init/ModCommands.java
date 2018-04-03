package com.feyberryjam.minecraft.textadventure.init;

import com.feyberryjam.minecraft.textadventure.command.*;
import com.feyberryjam.minecraft.textadventure.command.client.*;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ModCommands 
{
	public static void preInit()
	{
		// ClientCommandHandler.instance.registerCommand(new ClientCommandAttack());
	}
	
	public static void serverLoad(FMLServerStartingEvent event)
	{
        // register server commands (as opposed to client commands...)

    	event.registerServerCommand(new CommandTextAdventure());
    	event.registerServerCommand(new CommandLook());
    	event.registerServerCommand(new CommandGet());
    	event.registerServerCommand(new CommandInventory());
    	event.registerServerCommand(new CommandDrop());
    	event.registerServerCommand(new CommandRummage());
    	event.registerServerCommand(new CommandChop());
    	event.registerServerCommand(new CommandEat());
    	
    	// attacks might need a packet sent to the client, I can't figure out a way to simulate attacks on server
    	// and KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode()) wasn't working
    	// for attacks!  I don't know why.
    	event.registerServerCommand(new CommandAttack());
    	
    	// wasn't able to figure out how to make this a credits-warp joke unfortunately, but the lone firework
    	// is almost funnier
    	event.registerServerCommand(new CommandWin());
	}
}
