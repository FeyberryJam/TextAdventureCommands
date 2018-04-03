package com.feyberryjam.minecraft.textadventure.command;

import java.util.ArrayList;
import java.util.List;

import com.feyberryjam.minecraft.textadventure.Constants;
import com.feyberryjam.minecraft.textadventure.util.command.CommandHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.advancements.*;

public class CommandWin extends CommandBase 
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
		return "win";
	}
	
	@Override
	public List<String> getAliases() 
	{
		ArrayList aliases = new ArrayList();
		return aliases;
	}
	
	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "commands.win.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Entity entity = sender.getCommandSenderEntity();
		if(!(entity instanceof EntityPlayer))
			throw new WrongUsageException("commands.general.notplayer", new Object[0]);

		EntityPlayer player = (EntityPlayer)entity;
		World world = player.getEntityWorld();
		
		CommandHelper.TextToPlayer(sender, "You've won!");

		// create a fire work
		// This took me far longer then I care to admit, and I had to do tons of research

		ItemStack firework = new ItemStack(Items.FIREWORKS);
		firework.setTagCompound(new NBTTagCompound());
		NBTTagCompound expl = new NBTTagCompound();
		expl.setBoolean("Flicker", true);
		expl.setBoolean("Trail", true);

		int[] colors = new int[world.rand.nextInt(8) + 1];
		for (int i = 0; i < colors.length; i++) 
		{
			colors[i] = ItemDye.DYE_COLORS[world.rand.nextInt(16)];
		}
		expl.setIntArray("Colors", colors);
		byte type = (byte) (world.rand.nextInt(3) + 1);
		// skips creeper face for whatever reason, which I'm comfortable with
		type = type == 3 ? 4 : type;
		expl.setByte("Type", type);

		NBTTagList explosions = new NBTTagList();
		explosions.appendTag(expl);

		NBTTagCompound fireworkTag = new NBTTagCompound();
		fireworkTag.setTag("Explosions", explosions);
		fireworkTag.setByte("Flight", (byte) 1);
		firework.getTagCompound().setTag("Fireworks", fireworkTag);		
		
		// spawn
        EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(world, player.posX, player.posY, player.posZ, firework);
        world.spawnEntity(entityfireworkrocket);		
	}
}
