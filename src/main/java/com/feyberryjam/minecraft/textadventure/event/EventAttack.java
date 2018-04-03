package com.feyberryjam.minecraft.textadventure.event;

import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventAttack 
{	
	@SubscribeEvent
	public static void onLivingHurtEvent(LivingHurtEvent event)
	{
		Entity source = event.getSource().getTrueSource();
		if( !(source instanceof EntityPlayer) )
		{
			Entity target = event.getEntity();
			if( target instanceof EntityPlayer )
			{
				EntityPlayer targetPlayer = (EntityPlayer)target;
				
				if( source != null )
					CommandHelper.TextToPlayer(targetPlayer, "The " + source.getDisplayName().getUnformattedComponentText().toLowerCase() + " hits you for " + event.getAmount() + " damage!");				
				else
					CommandHelper.TextToPlayer(targetPlayer, "You take " + event.getAmount() + " damage!");									
			}
			
			return;
		}
		
		EntityPlayer sourcePlayer = (EntityPlayer)source;						
		CommandHelper.TextToPlayer(sourcePlayer, "You hit the " + event.getEntityLiving().getDisplayName().getUnformattedComponentText().toLowerCase() + " for " + event.getAmount() + " damage!");
	}
	
	
	
}
