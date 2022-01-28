package com.serbcraft.arena;

import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ArenaCommandExecutor implements CommandExecutor {

	private final Arena plugin;
	private int counter = 60;

	public ArenaCommandExecutor(Arena plugin) {
		this.plugin = plugin; 
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Arena.plugin.getConfig().getString("OnlyPlayer")));
		}

		Player p = (Player) sender;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Arena.plugin.getConfig().getString("CommandUsage")));
			return true;
		}

		String firstArg = args[0];
		if (firstArg.equalsIgnoreCase("start")) {
			if (plugin.getConfig().get("Location") == null) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Arena.plugin.getConfig().getString("NoLocation")));
				return true;
			}
			
			new BukkitRunnable() {
        
				@Override
				public void run() {

					if (counter == 0) {
						Location loc;
						double x = plugin.getConfig().getDouble("Location.X");
						double y = plugin.getConfig().getDouble("Location.Y");
						double z = plugin.getConfig().getDouble("Location.Z");
						float yaw = (float) plugin.getConfig().getDouble("Location.Yaw");
						float pitch = (float) plugin.getConfig().getDouble("Location.Pitch");
						String world = plugin.getConfig().getString("Location.World");
						loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
						p.teleport(loc);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Arena.plugin.getConfig().getString("Teleported")));
						this.cancel();
					}else{
						p.sendMessage(ChatColor.RED + "Odbrojavanje " + counter);
						counter--;
					}

				}
				
			}.runTaskTimer(this.plugin, 0, 20);

			return true;
		}
		else if (firstArg.equalsIgnoreCase("setlocation")) {

			Location loc = p.getLocation();
			plugin.getConfig().set("Location.World", loc.getWorld().getName());
			plugin.getConfig().set("Location.X", loc.getX());
			plugin.getConfig().set("Location.Y", loc.getY());
			plugin.getConfig().set("Location.Z", loc.getZ());
			plugin.getConfig().set("Location.Pitch", loc.getPitch());
			plugin.getConfig().set("Location.Yaw", loc.getYaw());
			plugin.saveConfig();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Arena.plugin.getConfig().getString("LocationSet")));
			return true;
			
		}
		else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Arena.plugin.getConfig().getString("CommandUsage")));
			return true;
		}
	}

}
