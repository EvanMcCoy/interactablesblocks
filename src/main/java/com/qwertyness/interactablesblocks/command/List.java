package com.qwertyness.interactablesblocks.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactables.interactable.Interactable;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class List extends CommandLabel {
	public List(InteractablesBlocks plugin) {
		super("listblocks", "Lists all of the blocks registered with InteractablesBlocks.", plugin);
	}

	public void run(Player player, String[] args) {
		java.util.List<Interactable> interactables = this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractables();
		java.util.List<InteractableBlock> blocks = new ArrayList<InteractableBlock>();
		for (Interactable interactable : interactables) {
			if (interactable instanceof InteractableBlock) {
				blocks.add((InteractableBlock)interactable);
			}
		}
		java.util.List<String> blockNames = new ArrayList<String>();
		for (InteractableBlock portal : blocks) {
			blockNames.add(portal.getName());
		}
		player.sendMessage(ChatColor.GREEN + "InteractableBlocks: " + blockNames);
	}
}
