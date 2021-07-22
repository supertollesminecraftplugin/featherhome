package de.drmaxnix.featherhome;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Cmd_home implements CommandExecutor {

	// This method is called, when somebody uses our command
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		// GET PLAYER LOCATION //
		//get player object
		Player player = (Player)sender;
		
		//get location object
		Location location = Data.getHomeLocation(player.getUniqueId(), "home");
		
		
		// PRINT INFO IF NO LOCATION WAS FOUND //
		if(location == null){
			//player.sendMessage("The home '" + ChatColor.AQUA + "home" + ChatColor.WHITE + "' could not be found.");
			player.sendMessage("The " + ChatColor.AQUA + "home" + ChatColor.WHITE + " could not be found.");
			return true;
		}
		
		
		// TP PLAYER //
		player.teleport(location);
		
		
		// GIVE FEEDBACK //
		//player.sendMessage("Moved to home '" + ChatColor.AQUA + "home" + ChatColor.WHITE + "'.");
		player.sendMessage("Moved to " + ChatColor.AQUA + "home" + ChatColor.WHITE + ".");
		
		//return
		return true;
	}
}
