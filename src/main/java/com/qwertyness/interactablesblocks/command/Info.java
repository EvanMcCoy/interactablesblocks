package com.qwertyness.interactablesblocks.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class Info extends CommandLabel {

	public Info(InteractablesBlocks plugin) {
		super("blockinfo", "gives the info of the specified block.", "<block_name>", plugin);
	}

	public void run(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That block doe not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof InteractableBlock)) {
			player.sendMessage(ChatColor.RED + "The selected interactable is not a InteractableBlock!");
		}
		InteractableBlock block = (InteractableBlock) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		java.util.List<String> commands = new ArrayList<String>();
		for (int counter = 0;counter < block.getCommands().size();counter++) {
			commands.add(ChatColor.WHITE + block.getCommands().get(counter).getCommand() + ChatColor.GOLD + ":" + ChatColor.WHITE + block.getCommands().get(counter).getSender());
		}
				
		player.sendMessage(ChatColor.GREEN + "----- InteractableBlock Info: " + block.getName() + " -----");
		player.sendMessage(ChatColor.GREEN + "Commands: " + commands);
		player.sendMessage(ChatColor.GREEN + "Messages: " + block.getMessages());
		player.sendMessage(ChatColor.GREEN + "Cooldown: " + block.getCooldown());
		player.sendMessage(ChatColor.GREEN + "Location: World - " + block.getWorld() + 
				", X - " + block.getLocation().getBlockX() + 
				", Y - " + block.getLocation().getBlockY() + 
				", Z - " + block.getLocation().getBlockZ());
		player.sendMessage(ChatColor.GREEN + "Action Type: " + block.getActionType());
	}
}
