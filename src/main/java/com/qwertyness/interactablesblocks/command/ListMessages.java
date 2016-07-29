package com.qwertyness.interactablesblocks.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;;

public class ListMessages extends CommandLabel {

	public ListMessages(InteractablesBlocks plugin) {
		super("listblockmessages", "Lists the messages of the specified block.", "<block_name>", plugin);
	}

	public void run(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That block does not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof InteractableBlock)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not an InteractableBlock!");
			return;
		}
		
		InteractableBlock block = (InteractableBlock) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		player.sendMessage(ChatColor.GREEN + "------- " + ChatColor.GOLD + block.getName() + " - Messages" + ChatColor.GREEN + " -------");
		for (int counter = 0;counter < block.getMessages().size();counter++) {
			player.sendMessage(ChatColor.GREEN + new Integer(counter + 1).toString() + ": " + ChatColor.GOLD + block.getMessages().get(counter));
		}
	}
}
