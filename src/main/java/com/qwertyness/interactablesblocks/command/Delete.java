package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactables.interactable.Interactable;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class Delete extends CommandLabel {

	public Delete(InteractablesBlocks plugin) {
		super("deleteblock", "<block_name>", "Deletes a block.", plugin);
	}

	public void run(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!player.hasPermission("ib.block.delete")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to " + ChatColor.GOLD + "delete" + ChatColor.GREEN + "blocks.");
			return;
		}
		if (!this.plugin.getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That block does not exist!");
			return;
		}
		Interactable interactable = this.plugin.getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		if (!(interactable instanceof InteractableBlock)) {
			player.sendMessage(ChatColor.RED + "The selected interactable is not a block!");
			return;
		}
		InteractableBlock block = (InteractableBlock) interactable;
		player.sendMessage(ChatColor.GOLD + "You have deleted the block " + ChatColor.RED + args[1] + ChatColor.RED + "!");
		this.plugin.getInteractablesAPI().getInteractableManager().unregisterInteractable(block);
		this.plugin.getInteractablesAPI().getInteractableManager().deleteInteractable(block.getPlugin(), block.basePath);
	}
}
