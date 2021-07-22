package de.drmaxnix.featherhome;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Cmd_sethome implements CommandExecutor {

	// This method is called, when somebody uses our command
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		// GET PLAYER LOCATION //
		//get player object
		Player player = (Player)sender;
		
		//get location object
		Location location = player.getLocation();
		
		
		// STORE TO DATABASE //
		boolean success = Data.setHomeLocation(player.getUniqueId(), "home", location);
		
		
		// GIVE FEEDBACK //
		if(success){
			//player.sendMessage("New position for home '" + ChatColor.AQUA + "home" + ChatColor.WHITE + "' was set.");
			player.sendMessage("New " + ChatColor.AQUA + "home" + ChatColor.WHITE + " position was set.");
			
		} else {
			player.sendMessage("Error updating " + ChatColor.AQUA + "home" + ChatColor.WHITE + " position.");
		}
		
		
		// RETURN //
		return true;
	}
}
