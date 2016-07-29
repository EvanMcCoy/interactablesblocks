package com.qwertyness.interactablesblocks.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactables.interactable.InteractSender;
import com.qwertyness.interactables.interactable.Interactable;
import com.qwertyness.interactablesblocks.InteractablesBlocks;
import com.qwertyness.interactablesblocks.block.InteractableBlock;

public class BlockListener implements Listener {
	private InteractablesBlocks plugin;
	
	public BlockListener(InteractablesBlocks plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getClickedBlock() == null) {
			return;
		}
		String world = event.getClickedBlock().getWorld().getName();
		Vector location = event.getClickedBlock().getLocation().toVector();
		Player player = event.getPlayer();
		
		for (Interactable interactable : this.plugin.getInteractablesAPI().getInteractableManager().getInteractables()) {
			if (!(interactable instanceof InteractableBlock)) {
				continue;
				
			}
			if (!interactable.getPlugin().getInteractablesAPI().getInteractableManager().canUse(event.getPlayer(), interactable)) {
				continue;
			}
			InteractableBlock block = (InteractableBlock) interactable;
			if (!block.getWorld().equals(world) || block.getLocation().getBlockX() != location.getBlockX() || block.getLocation().getBlockY() != location.getBlockY() ||
					block.getLocation().getBlockZ() != location.getBlockZ()) {
				continue;
			}
			if (block.getActionType().equalsIgnoreCase("left_click") && event.getAction() != Action.LEFT_CLICK_BLOCK) {
				return;
			}
			if (block.getActionType().equalsIgnoreCase("right_click") && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
				return;
			}
			if (block.getActionType().equalsIgnoreCase("both") && (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
				return;
			}
			if (!event.getPlayer().hasPermission("ib.block.use." + block.getName()) || !event.getPlayer().hasPermission("ib.block.use.*")) {
				return;
			}
			try {
				for (String message : block.getMessages()) {
					message = message.replaceAll("@p", player.getName());
					player.sendMessage(message);
				}
			} catch (NullPointerException e) {}
			
			for (int commandCounter = 0;commandCounter < block.getCommands().size();commandCounter++) {
				InteractCommand command = block.getCommands().get(commandCounter);
				String executeCommand = command.getCommand().replaceAll("@p", player.getName());
				if (command.getSender() == InteractSender.CONSOLE) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), executeCommand);
				}
				else if (command.getSender() == InteractSender.SPECIAL) {
					player.chat("/" + executeCommand);
				}
				else {
					Bukkit.dispatchCommand(player, executeCommand);
				}
			}
			interactable.getPlugin().getInteractablesAPI().getInteractableManager().useInteractable(player, interactable);
			event.setCancelled(true);
			break;
		}
		
		
	}
}
