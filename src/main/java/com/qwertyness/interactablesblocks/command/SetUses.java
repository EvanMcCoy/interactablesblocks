package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class SetUses extends CommandLabel {

	public SetUses(InteractablesBlocks plugin) {
		super("setblockuses", "Sets the use limit of the specified block.", "<block_name> <uses>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("ib.block.edit.uses")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a block's " + ChatColor.GOLD + "use limit.");
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
			block.setUses(Integer.parseInt(args[2]));
			player.sendMessage(ChatColor.GREEN + "You have set the use limit of "  + ChatColor.GOLD + block.getName() + ChatColor.GREEN + " to " + ChatColor.GOLD + Integer.parseInt(args[2]));
		} catch(Exception e) {
			player.sendMessage(ChatColor.RED + "Uses value must be an integer!");
		}
	}
}
