package de.drmaxnix.featherhome;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;

public class Data {
	/*
		Get storage path of home-data-file
	*/
	private static String homeFilePath(){
		return Featherhome.getInstance().getDataFolder().getAbsolutePath() + "/home.json";
	}
	
	/*
		Save home-data to file
	*/
	private static void saveHome(HashMap<String, HashMap<String, HashMap<String, String>>> home) throws Exception {
		//encode json
		Gson gson = new Gson();
		String json = gson.toJson(home);
		
		//write to file
		FileWriter fileWriter = new FileWriter(homeFilePath());
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.print(json);
	    printWriter.close();
	}
	
	/*
		Read home-data from file
	*/
	private static HashMap<String, HashMap<String, HashMap<String, String>>> loadHome() throws Exception {
		//read file
		String json = "";
		
		File myObj = new File(homeFilePath());
	    Scanner myReader = new Scanner(myObj);
	    while(myReader.hasNextLine()){
	    	if(json.length() > 0){
	    		json += "\n";
	    	}
	    	
	    	String data = myReader.nextLine();
	    	json += data;
	    }
	    myReader.close();
		
		//decode json
		Gson gson = new Gson();
		Type empMapType = new TypeToken<HashMap<String, HashMap<String, HashMap<String, String>>>>(){}.getType();
		HashMap<String, HashMap<String, HashMap<String, String>>> home = gson.fromJson(json, empMapType);
		return home;
	}
	
	/*
		Ensure the home-data-file exists
	*/
	private static void firstCreateHome() throws Exception {
		// CREATE FILE IF IT DOESN'T EXIST //
		//generate empty home-list
		HashMap<String, HashMap<String, HashMap<String, String>>> home = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		
		//check if existing
		File f = new File(homeFilePath());
		if(!f.exists()){
			try {
				//create parent folder
				f.getParentFile().mkdirs();
				
				//store empty home-list
				saveHome(home);
				
			} catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static boolean setHomeLocation(UUID uuid, String homeName, Location location){
		// ENSURE THE FILE EXISTS //
		try {
			firstCreateHome();
			
		} catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		//create home object
		HashMap<String, HashMap<String, HashMap<String, String>>> home;
		
		
		// LOAD OLD DATA //
		try {
			home = loadHome();
			
		} catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		
		// SET DATA IN HOME-OBJECT //
		//maybe create new empty uuid-map
		if(!home.containsKey(uuid.toString())){
			home.put(uuid.toString(), new HashMap<String, HashMap<String, String>>());
		}
		
		//store location
		home.get(uuid.toString()).put(homeName, new HashMap<String, String>());
		
		//coordinates
		home.get(uuid.toString()).get(homeName).put("x", String.valueOf(location.getX()));
		home.get(uuid.toString()).get(homeName).put("y", String.valueOf(location.getY()));
		home.get(uuid.toString()).get(homeName).put("z", String.valueOf(location.getZ()));
		
		//rotation
		home.get(uuid.toString()).get(homeName).put("yaw", String.valueOf(location.getYaw()));
		home.get(uuid.toString()).get(homeName).put("pitch", String.valueOf(location.getPitch()));
		
		//world
		home.get(uuid.toString()).get(homeName).put("world", location.getWorld().getName());
		
		
		
		// SAVE NEW DATA //
		try {
			saveHome(home);
			
		} catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		// RETURN //
		return true;
	}
	
	public static Location getHomeLocation(UUID uuid, String homeName){
		// ENSURE THE FILE EXISTS //
		try {
			firstCreateHome();
			
		} catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//create home object
		HashMap<String, HashMap<String, HashMap<String, String>>> home;
		
		
		// LOAD DATA //
		try {
			home = loadHome();
			
		} catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		// CHECK IF THIS HOME EXISTS //
		if(!home.containsKey(uuid.toString()) || !home.get(uuid.toString()).containsKey(homeName)){
			return null;
		}
		
		
		// GET LOCATION DATA //
		//coordinates
		double locX = Double.parseDouble(home.get(uuid.toString()).get(homeName).get("x"));
		double locY = Double.parseDouble(home.get(uuid.toString()).get(homeName).get("y"));
		double locZ = Double.parseDouble(home.get(uuid.toString()).get(homeName).get("z"));
		
		//rotation
		float locYaw = Float.parseFloat(home.get(uuid.toString()).get(homeName).get("yaw"));
		float locPitch = Float.parseFloat(home.get(uuid.toString()).get(homeName).get("pitch"));
		
		//world
		String locWorldName = home.get(uuid.toString()).get(homeName).get("world");
		World locWorld = Bukkit.getServer().getWorld(locWorldName);
		
		//make it a location object
		Location location = new Location(locWorld, locX, locY, locZ, locYaw, locPitch);
		
		
		// RETURN //
		return location;
	}
}
