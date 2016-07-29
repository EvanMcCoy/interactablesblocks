package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactables.interactable.InteractSender;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;
import com.qwertyness.interactablesblocks.util.BlockUtil;

public class AddCommand extends CommandLabel {

	public AddCommand(InteractablesBlocks plugin) {
		super("addblockcommand", "Adds a command to specified block.", "<block_name> <-t CONSOLE, PLAYER, or SPECIAL> <command>", plugin);
	}

	public void run(Player player, String[] args) {
		InteractSender interactSender = InteractSender.PLAYER;
		if (args[2].equalsIgnoreCase("-t")) {
			if (args[3].equalsIgnoreCase("player")) {
				if (!player.hasPermission("ib.block.edit.command.player")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "PLAYER" + ChatColor.RED + " type command to a block.");
					return;
				}
				interactSender = InteractSender.PLAYER;
			}
			else if (args[3].equalsIgnoreCase("console")) {
				if (!player.hasPermission("ib.block.edit.command.console")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "CONSOLE" + ChatColor.RED + " type command to a block.");
					return;
				}
				interactSender = InteractSender.CONSOLE;
			}
			else if (args[3].equalsIgnoreCase("special")) {
				if (!player.hasPermission("ib.block.edit.command.special")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "SPECIAL" + ChatColor.RED + " type command to a block.");
					return;
				}
				interactSender = InteractSender.SPECIAL;
			}
			else {
				player.sendMessage(ChatColor.RED + "Invalid block type! Types: player, console, special");
			}
		}
		else {
			if (!player.hasPermission("ib.block.edit.command.player")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "PLAYER" + ChatColor.RED + " type command to a block.");
				return;
			}
			interactSender = InteractSender.PLAYER;
		}
		
		
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1]))) {
			player.sendMessage(ChatColor.RED + "That block does not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof InteractableBlock)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not a block!");
			return;
		}
		
		InteractableBlock block = (InteractableBlock) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		block.addCommand(new InteractCommand(BlockUtil.getBlockCommand(args), interactSender));
		player.sendMessage(ChatColor.GREEN + "Command " + ChatColor.GOLD + BlockUtil.getBlockCommand(args) + ChatColor.GREEN + " added to block " + ChatColor.GOLD + block.getName() + ChatColor.GREEN + "!");
		
	}
}
