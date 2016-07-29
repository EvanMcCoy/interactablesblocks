package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;
import com.qwertyness.interactablesblocks.util.BlockUtil;

public class AddMessage extends CommandLabel {

	public AddMessage(InteractablesBlocks plugin) {
		super("addblockmessage", "Adds a message to the specified block.", "<block_name> <message>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("ib.block.edit.message")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a block's " + ChatColor.GOLD + "messages");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That block does not exist!");
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof InteractableBlock)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not a Portal!");
			return;
		}
		
		InteractableBlock block = (InteractableBlock) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);;
		block.addMessage(BlockUtil.getBlockCommand(args));
		player.sendMessage(ChatColor.GREEN + "Message " + ChatColor.GOLD + BlockUtil.getBlockCommand(args) + ChatColor.GREEN + " added to block " + ChatColor.GOLD + block.getName() + ChatColor.GREEN + "!");
	}
}
