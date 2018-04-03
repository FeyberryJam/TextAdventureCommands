package com.feyberryjam.minecraft.textadventure.capabilities;

import com.feyberryjam.minecraft.textadventure.Constants;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class CapabilityEvents
{
	 public static final ResourceLocation CAP_ReceivedJoinMessage = new ResourceLocation(Constants.MODID, "receivedjoinmessage");
	
	 // assuming any of this is.. correct.. that's fairly straight forward
	 
	 @SubscribeEvent
	 public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
	 {
		 if (canHaveAttributes(event.getObject()))
		 {
			 EntityLivingBase entity = (EntityLivingBase) event.getObject();

			 if (entity instanceof EntityPlayer)
				 event.addCapability(CAP_ReceivedJoinMessage, new ProviderReceivedJoinMessage());
		 }
	 }
	 
	 // fairly similar thing would go on here to store data on an item...
	 
	 // And this is to persist data between deaths... because otherwise it just acts like regular old nbt data.
	 // only put capabilities down here that you want to persist	 
	 @SubscribeEvent
	 public static void onPlayerClone(PlayerEvent.Clone event)
	 {

	  EntityPlayer player = event.getEntityPlayer();

	  IReceivedJoinMessage receivedJoinMessage = player.getCapability(ProviderReceivedJoinMessage.CAP_ReceivedJoinMessage, null);
	  IReceivedJoinMessage oldReceivedJoinMessage = event.getOriginal().getCapability(ProviderReceivedJoinMessage.CAP_ReceivedJoinMessage, null);
	  
	  receivedJoinMessage.set(oldReceivedJoinMessage.get());
	 }	 

	 
	 
	 // helper garbage
	 
	 public static boolean canHaveAttributes(Entity entity)
	 {
	 	if (entity instanceof EntityLivingBase)
	 		return true;
	 	return false;
	 }
	 
	 public static boolean canHaveAttributes(Item item)
	 {
	 	if ((item instanceof ItemTool || item instanceof ItemSword || item instanceof ItemBow
	 			|| item instanceof ItemArmor || item instanceof ItemShield))
	 		return true;
	 	return false;
	 }

}
