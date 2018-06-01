package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import domainPackage.Player;

public class JSONProcessor
{			
	//Map of filenames to playerdata
	Map<String, Player> playerData = new TreeMap<String, Player>();

	public JSONProcessor() throws FileNotFoundException
	{
		//Always load json on instantiation
		loadJSON();
	}
			
	public Map<String, Player> getPlayerData()
	{
		return playerData;
	}
		
	//Loading data from data folder
	private void loadJSON() throws FileNotFoundException
	{

		GsonBuilder builder= new GsonBuilder();
		Gson gson = builder.create();
				
		//get a list of folders in the data directory
		
		File[] directories = new File("data").listFiles();

		//Loop through folders in data directory
		for(File folder : directories)
			{
				//get a list of files inside the folder
				File[] jsonItems = new File(folder.toString()).listFiles();
				
				//Loop through files inside the folder 
				for(File file : jsonItems)
				{
					//Store in directory map... substring to remove the "data/" portion... placed by filename to foldername
					String dir = file.toString().substring(5);
					
					//Generate player data from gson
					JsonReader reader = new JsonReader(new FileReader(file));
					Player player = gson.fromJson(reader, Player.class);
					
					//Store it in the map tied to it's directory
					playerData.put(dir, player);
				}
			}
		}
	}
