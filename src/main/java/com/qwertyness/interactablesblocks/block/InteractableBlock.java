package com.qwertyness.interactablesblocks.block;

import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import com.qwertyness.interactables.InteractablesPlugin;
import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactables.interactable.Interactable;

public class InteractableBlock extends Interactable {
	
	//Location
	public String world;
	public Vector location;
	public String actionType;
	
	//Data Grabbing
	public String basePath;
	
	public InteractableBlock(InteractablesPlugin plugin) {
		super(plugin);
	}
	
	public InteractableBlock(String name, InteractablesPlugin plugin) {
		super(plugin);
		this.name = name;
		this.basePath = "Blocks." + this.getName();
		this.initialize();
	}
	
	//////////////////////
	// Location Methods //
	//////////////////////
	
	public String getWorld() {
		return this.world;
	}
	
	public void setWorld(String world) {
		this.world = world;
	}
	
	public Vector getLocation() {
		return this.location;
	}
	
	public void setLocation(Vector location) {
		this.location = location;
	}
	
	public String getActionType() {
		return this.actionType;
	}
	
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public void save() {
		this.basePath = "Blocks." + this.getName();
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put(this.basePath + ".commands", InteractCommand.toStringList(this.getCommands()));
		values.put(this.basePath + ".messages", this.messages);
		values.put(this.basePath + ".cooldown", this.cooldown);
		values.put(this.basePath + ".uses", this.uses);
		values.put(this.basePath + ".location", this.location);
		values.put(this.basePath + ".world", this.world);
		values.put(this.basePath + ".actionType", this.actionType);
		this.plugin.getInteractablesAPI().getInteractableManager().saveInteractable(this.plugin, values);
	}
	
	@SuppressWarnings("unchecked")
	public void initialize() {
		FileConfiguration dataFile = this.getPlugin().getInteractablesAPI().dataFiles.get(this.plugin).get();
		this.setCommands(InteractCommand.toCommandList(dataFile.getStringList(this.basePath + ".commands")));
		this.messages = (List<String>) dataFile.getList(this.basePath + ".messages");
		this.cooldown = dataFile.getInt(this.basePath + ".cooldown");
		this.uses = dataFile.getInt(this.basePath + ".uses");
		this.location = dataFile.getVector(this.basePath + ".location");
		this.world = dataFile.getString(this.basePath + ".world");
		this.actionType = dataFile.getString(this.basePath + ".actionType");
	}
}
