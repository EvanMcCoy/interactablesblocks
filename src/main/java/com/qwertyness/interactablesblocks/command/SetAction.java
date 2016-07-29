package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class SetAction extends CommandLabel {

	public SetAction(InteractablesBlocks plugin) {
		super("setblockaction", "Sets the action of the block.", "<block_name> <LEFT_CLICK/RIGHT_CLICK/BOTH>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("ib.block.edit.action")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a block's " + ChatColor.GOLD + "commands");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable removeblockcommand <name> <command_index>");
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
		
		if (!(args[2].equalsIgnoreCase("right_click") || args[2].equalsIgnoreCase("left_click") || args[2].equalsIgnoreCase("both"))) {
			player.sendMessage(ChatColor.RED + "Invalid action type! Use LEFT_CLICK, RIGHT_CLICK, or BOTH");
			return;
		}
		block.setActionType(args[2]);
		player.sendMessage(ChatColor.GREEN + "You have set the action of "  + ChatColor.GOLD + block.getName() + ChatColor.GREEN + " to " + ChatColor.GOLD + args[2]);
	}
}
