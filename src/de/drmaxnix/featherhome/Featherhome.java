package de.drmaxnix.featherhome;
import org.bukkit.plugin.java.JavaPlugin;

public class Featherhome extends JavaPlugin {
	private static Featherhome instance;
	
	public Featherhome(){
		if(Featherhome.instance == null){
			Featherhome.instance = this;
		}
	}
	
	public static Featherhome getInstance(){
		return Featherhome.instance;
	}
	
	@Override
	public void onEnable(){
		//bind registered commands
		this.getCommand("home").setExecutor(new Cmd_home());
		this.getCommand("sethome").setExecutor(new Cmd_sethome());
		
		//print debug
		//System.out.println("[FeatherHome] by DrMaxNix | www.drmaxnix.de");
		System.out.println("[FeatherHome] Enabled FeatherHome");
	}
	
	@Override
	public void onDisable(){
		//print debug
		System.out.println("[FeatherHome] Disabled FeatherHome");
	}
}
