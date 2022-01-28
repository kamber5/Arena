package com.serbcraft.arena;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Arena extends JavaPlugin {

	public static Arena plugin;
	
	public void onEnable() {
		this.getCommand("arena").setExecutor(new ArenaCommandExecutor(this));
		plugin = this;
		createFiles();
	}
	
	public void onDisable() {
		
	}

	private File config;
	private FileConfiguration configf;

	private void createFiles() {

		config = new File(getDataFolder(), "config.yml");

		if(!config.exists()) {
			config.getParentFile().mkdirs();
			saveResource("config.yml", false);
		}
		configf = new YamlConfiguration();

		try {
			configf.load(config);

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
