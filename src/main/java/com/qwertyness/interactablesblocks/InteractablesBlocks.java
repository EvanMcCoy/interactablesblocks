package com.qwertyness.interactablesblocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.qwertyness.interactables.Interactables;
import com.qwertyness.interactables.InteractablesPlugin;
import com.qwertyness.interactablesblocks.block.InteractableBlock;
import com.qwertyness.interactablesblocks.command.AddCommand;
import com.qwertyness.interactablesblocks.command.AddMessage;
import com.qwertyness.interactablesblocks.command.Create;
import com.qwertyness.interactablesblocks.command.Delete;
import com.qwertyness.interactablesblocks.command.Info;
import com.qwertyness.interactablesblocks.command.ListCommands;
import com.qwertyness.interactablesblocks.command.ListMessages;
import com.qwertyness.interactablesblocks.command.RemoveCommand;
import com.qwertyness.interactablesblocks.command.RemoveMessage;
import com.qwertyness.interactablesblocks.command.SetAction;
import com.qwertyness.interactablesblocks.command.SetCooldown;
import com.qwertyness.interactablesblocks.command.SetUses;
import com.qwertyness.interactablesblocks.listener.BlockListener;
import com.qwertyness.interactablesblocks.util.BlockUtil;

public class InteractablesBlocks extends JavaPlugin implements InteractablesPlugin {
	public Interactables interactablesPlugin;
	private static InteractablesBlocks instance;
	
	public void onEnable() {
		instance = this;
		
		//Register plugin with Interactables API.
		this.interactablesPlugin = this.getServer().getServicesManager().load(Interactables.class);
		this.interactablesPlugin.registerPlugin(this);
		
		//Register commands
		this.interactablesPlugin.getCommandHandler().registerCommand(new AddCommand(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new AddMessage(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new Create(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new Delete(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new Info(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new com.qwertyness.interactablesblocks.command.List(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new ListCommands(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new ListMessages(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new RemoveCommand(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new RemoveMessage(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new SetAction(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new SetCooldown(this));
		this.interactablesPlugin.getCommandHandler().registerCommand(new SetUses(this));
		
		//Register listeners
		this.getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockUtil(), this);
		
		try {
	    	List<String> blocks = new ArrayList<String>(this.interactablesPlugin.dataFiles.get(this).get().getConfigurationSection("Blocks").getKeys(false));
	    	for (String block : blocks) {
	        	this.interactablesPlugin.getInteractableManager().registerInteractable(new InteractableBlock(block, this));
	        }
	    } catch(NullPointerException e){}
	}

	public String getPluginDescription() {
		return this.getDescription().getDescription();
	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}

	public Interactables getInteractablesAPI() {
		return this.interactablesPlugin;
	}
	
	public static InteractablesBlocks instance() {
		return instance;
	}
}
