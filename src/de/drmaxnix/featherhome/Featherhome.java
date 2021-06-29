package de.drmaxnix.featherhome;
import org.bukkit.plugin.java.JavaPlugin;

public class Featherhome extends JavaPlugin {
	@Override
	public void onEnable(){
		System.out.println("enabled");
	}
	
	@Override
	public void onDisable(){
		System.out.println("disbaled");
	}
}
