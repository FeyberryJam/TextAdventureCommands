package com.feyberryjam.minecraft.textadventure;

import org.apache.logging.log4j.Logger;

import com.feyberryjam.minecraft.textadventure.init.ModCommands;
import com.feyberryjam.minecraft.textadventure.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

// Here we have the main file for this mod!
// When I did this mod it was mostly meant as a light hearted bit of fun.  I didn't 
// expect anyone to see the source code.  I super don't recommend any future generations
// of modder-wannabe's reference this code as it's basically terrible.

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class TextAdventure
{
    public static Logger logger;

    // this can be used to fetch other mods, which is sort of cool!  I always forget
    // that's a thing
	@Instance(Constants.MODID)
	public static TextAdventure instance;
	
	// proxies are a gross but necessary evil!  I'm not sure why I use an interface normally
	// I might switch to using a CommonProxy class
	@SidedProxy(clientSide = Constants.PROXY_CLIENT, serverSide = Constants.PROXY_SERVER)
	public static IProxy proxy;    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();        
        // this didn't wind up being used but could be if I kept working on this.
        ModCommands.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	TextAdventure.proxy.init();
    	
    	// Skye and I didn't wind up making any items.  I wanted a "Ye Flask"
    	// and an Amulet of Yendor, alas :P
    	
    	// ModItems.init();
        // logger.info("I'm in your mods, initializing my lasers!");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	ModCommands.serverLoad(event);
    }
}