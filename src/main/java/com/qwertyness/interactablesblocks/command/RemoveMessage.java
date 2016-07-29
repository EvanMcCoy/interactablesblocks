package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class RemoveMessage extends CommandLabel {

	public RemoveMessage(InteractablesBlocks plugin) {
		super("removeblockmessage", "Removes a message from the specified block.", "<block_name> <message_index>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("ib.block.edit.message")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a block's " + ChatColor.GOLD + "messages");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That block does not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof InteractableBlock)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not a InteractableBlock!");
			return;
		}
		
		InteractableBlock block = (InteractableBlock) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		try {
			player.sendMessage(ChatColor.GREEN + "Message " + ChatColor.GOLD + block.getMessages().get(Integer.parseInt(args[2]) - 1) + ChatColor.GREEN + " removed from block " + ChatColor.GOLD + block.getName() + ChatColor.GREEN + "!");
			block.removeMessage(Integer.parseInt(args[2]) - 1);
		} catch (Exception e) {
			player.sendMessage(ChatColor.RED + "Message index must be a whole number and be within the highest message index!");
		}
	}

}
