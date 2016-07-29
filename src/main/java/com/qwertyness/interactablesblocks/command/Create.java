package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactables.interactable.InteractSender;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;
import com.qwertyness.interactablesblocks.util.BlockUtil;
public class Create extends CommandLabel {

	public Create(InteractablesBlocks plugin) {
		super("createblock", "Creates a block.", "<block_name> <-t CONSOLE, PLAYER, or SPECIAL> <command>", plugin);
		this.plugin = plugin;
	}

	public void run(Player player, String[] args) {
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (args[1].equalsIgnoreCase("-t")) {
			player.sendMessage(ChatColor.RED + "Cannot use " + ChatColor.GOLD + "-t" + ChatColor.RED + " as a block name!");
			return;
		}
		if (this.plugin.getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That block already exists!");
			return;
		}
		
		InteractableBlock block = new InteractableBlock(plugin);
		block.name = args[1];
		if (args[2].equalsIgnoreCase("-t")) {
			if (args[3].equalsIgnoreCase("player")) {
				if (player.hasPermission("ib.block.create.player")) {
					BlockUtil.create(player, block);
					block.addCommand(new InteractCommand(BlockUtil.getBlockCommand(args), InteractSender.PLAYER));
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to create blocks of this type!");
					return;
				}
			}
			else if (args[3].equalsIgnoreCase("console")) {
				if (player.hasPermission("ib.block.create.console")) {
					BlockUtil.create(player, block);
					block.addCommand(new InteractCommand(BlockUtil.getBlockCommand(args), InteractSender.CONSOLE));
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to create blocks of this type!");
					return;
				}
			}
			else if (args[3].equalsIgnoreCase("special")) {
				if (player.hasPermission("ib.block.create.special")) {
					BlockUtil.create(player, block);
					block.addCommand(new InteractCommand(BlockUtil.getBlockCommand(args), InteractSender.SPECIAL));
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to create blocks of this type!");
					return;
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Invalid block type! Types: player, console, special");
			}
		}
		else {
			if (player.hasPermission("ib.block.create.player")) {
				BlockUtil.create(player, block);
				block.addCommand(new InteractCommand(BlockUtil.getBlockCommand(args), InteractSender.PLAYER));
			}
			else {
				player.sendMessage(ChatColor.RED + "You do not have permission to create blocks of this type!");
				return;
			}
		}
	}
}
