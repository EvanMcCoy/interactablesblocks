package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class RemoveCommand extends CommandLabel {

	public RemoveCommand(InteractablesBlocks plugin) {
		super("removeblockcommand", "Removes a command from specified block.", "<block_name> <command_index>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("ib.block.edit.command.remove")) {
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
		try {
			player.sendMessage(ChatColor.GREEN + "Command " + ChatColor.GOLD + block.getCommands().get(Integer.parseInt(args[2]) - 1).getCommand() + ChatColor.GREEN + " removed from block " + ChatColor.GOLD + block.getName() + ChatColor.GREEN + "!");
			block.removeCommand(Integer.parseInt(args[2]) - 1);
		} catch (Exception e) {
			player.sendMessage("Command index must be a whole number and be within the highest command index!");
		}
	}
}
