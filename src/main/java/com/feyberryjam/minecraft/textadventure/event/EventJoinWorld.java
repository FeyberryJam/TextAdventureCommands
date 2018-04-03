package com.feyberryjam.minecraft.textadventure.event;

import com.feyberryjam.minecraft.textadventure.capabilities.IReceivedJoinMessage;
import com.feyberryjam.minecraft.textadventure.capabilities.ProviderReceivedJoinMessage;
import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventJoinWorld 
{
	@SubscribeEvent
	public static void onEventJoinWorld(EntityJoinWorldEvent event)
	{
		if( event.getWorld().isRemote )
			return;
		
		if( event.getEntity() instanceof EntityPlayer )
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			
			// dirty gross way that only requires a reasonable number of lines and not tons of extra files and special registration
			// note: makes puppies cry
			/*
			NBTTagCompound nbt = player.getEntityData();
			NBTTagCompound persistent;
			if (!nbt.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) 
			{
				nbt.setTag(EntityPlayer.PERSISTED_NBT_TAG, (persistent = new NBTTagCompound()));
			} 
			else 
			{
				persistent = nbt.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			}

			final String key = "textadventure.firstjoin.message";
			
			if (!persistent.hasKey(key)) 
			{
				persistent.setBoolean(key, true);
				CommandHelper.TextToPlayer(player, "mod.welcome.message");
			}
			*/
			
			// capabilities, which are super good and vastly superior for whatever reason :3
			IReceivedJoinMessage receivedJoinMessage = player.getCapability(ProviderReceivedJoinMessage.CAP_ReceivedJoinMessage, null);
			if( !receivedJoinMessage.get() )
			{
				receivedJoinMessage.set(true);
				CommandHelper.TextToPlayer(player, "mod.welcome.message");
			}
			
		}		
	}

}
