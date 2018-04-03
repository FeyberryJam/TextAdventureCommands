package com.feyberryjam.minecraft.textadventure.event;

import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventDeath 
{
	
	@SubscribeEvent
	public static void onLivingDeathEvent(LivingDeathEvent event)
	{
		if( event.getEntityLiving() instanceof EntityPlayer )
		{
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			
			// explosion message
			if(event.getSource().isExplosion())
			{
				CommandHelper.TextToPlayer(player, "deaths.explosions.general");
				return;				
			}

			// fall
			if(event.getSource() == DamageSource.FALL)
			{
				CommandHelper.TextToPlayer(player, "deaths.fall.general");
				return;				
			}
			
			// drown
			if(event.getSource() == DamageSource.DROWN)
			{
				CommandHelper.TextToPlayer(player, "deaths.drown.general");
				return;				
			}

			// fire
			if(event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.LAVA)
			{
				CommandHelper.TextToPlayer(player, "deaths.fire.general");
				return;				
			}

			// catctus
			if(event.getSource() == DamageSource.CACTUS)
			{
				CommandHelper.TextToPlayer(player, "deaths.cactus.general");
				return;				
			}			
			
			// wolves
			if(event.getSource().getTrueSource() instanceof EntityWolf)
			{
				CommandHelper.TextToPlayer(player, "deaths.wolf.general");
				return;				
			}
			
		}
		
	}
	
	
	
}
