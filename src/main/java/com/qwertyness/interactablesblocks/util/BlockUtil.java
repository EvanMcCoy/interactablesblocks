package com.qwertyness.interactablesblocks.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class BlockUtil implements Listener {
	public static HashMap<String, InteractableBlock> waitingBlocks = new HashMap<String, InteractableBlock>();
	
	/////////////////////////
	// Create              //
	/////////////////////////
	
	@EventHandler
	public void onBlockClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (!waitingBlocks.containsKey(event.getPlayer().getUniqueId().toString())) {
			return;
		}
		event.setCancelled(true);
		InteractableBlock block = waitingBlocks.get(event.getPlayer().getUniqueId().toString());
		waitingBlocks.remove(event.getPlayer().getUniqueId().toString());
		block.setWorld(event.getClickedBlock().getWorld().getName());
		block.setLocation(event.getClickedBlock().getLocation().toVector());
		block.setMessages(new ArrayList<String>());
		block.setCooldown(0);
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			block.setActionType("LEFT_CLICK");
		}
		else {
			block.setActionType("RIGHT_CLICK");
		}
		block.save();
		event.getPlayer().sendMessage(ChatColor.GREEN + "You have created the block " + ChatColor.GOLD + block.getName());
		InteractablesBlocks.instance().getInteractablesAPI().getInteractableManager().registerInteractable(block);
	}

	public static void create(Player player, InteractableBlock block) {
		waitingBlocks.put(player.getUniqueId().toString(), block);
		player.sendMessage(ChatColor.GREEN + "Left or right click the desired block. (How you click will determine the default action type of the block.)");
	}

	public static String getBlockCommand(String[] args) {
		int startingArgument = 2;
		if (args[startingArgument].equalsIgnoreCase("-t")) {
			startingArgument = startingArgument + 2;
		}
		if (args[startingArgument].charAt(0) == '/') {
			char[] chars = args[startingArgument].toCharArray();
			args[startingArgument] = "";
			for (int counter = 1;counter < chars.length;counter++) {
				args[startingArgument] += chars[counter];
			}
		}
		String command = args[startingArgument];
		for (int counter = startingArgument + 1;counter < args.length;counter++) {
			command += " " + args[counter];
		}
		return command;
	}
}
