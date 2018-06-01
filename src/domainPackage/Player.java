package domainPackage;

import java.util.ArrayList;

public class Player 
{
	VictoryData[] victoryData; 
	DeathData[] deathData;
	TapsData[] tapsData;  
	ResetData[] resetData;
	public int rowMax = 0;
	int count = 0;

	private void updateMax(ArrayList<ArrayList<String>> temp)
	{
		int max = 0;
		
		for(ArrayList<String> data : temp)
		{
			if(data.size() > max)
				max = data.size();
		}
		
		if(max > rowMax)
			rowMax = max;
	}
	
	public ArrayList<ArrayList<String>> getAllData()
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> temp;
		
		temp = getVictoryData();				updateMax(temp);
		for(ArrayList<String> data : temp)		output.add(data);
		
		temp = getDeathData();					updateMax(temp);
		for(ArrayList<String> data : temp)		output.add(data);
		
		temp = getResetData();					updateMax(temp);
		for(ArrayList<String> data : temp)		output.add(data);
		
		temp = getTapsData();					updateMax(temp);
		for(ArrayList<String> data : temp)		output.add(data);

		return output;
	}
	
	public ArrayList<ArrayList<String>> getVictoryData()
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
	//VICTORY DATA-----------------------------------------------
		ArrayList<String> victoryChapters = new ArrayList<String>();	ArrayList<String> victoryLevels = new ArrayList<String>();
		ArrayList<String> victoryNames= new ArrayList<String>();		ArrayList<String> victoryTimes= new ArrayList<String>();
		ArrayList<String> victoryShifts= new ArrayList<String>();		ArrayList<String> victoryResets= new ArrayList<String>();

		victoryChapters.add("Chapter");							victoryLevels.add("Level");
		victoryNames.add("Name");									victoryTimes.add("Time");
		victoryShifts.add("Shifts");								victoryResets.add("Resets");

		for(VictoryData data : victoryData)
		{
		victoryChapters.add(""+data.getChapter());					victoryLevels.add(""+data.getLevelNum());
		victoryNames.add(""+data.getLevelName());					victoryTimes.add(""+data.getTime());
		victoryShifts.add(""+data.getShifts());						victoryResets.add(""+data.getResets());
		}

		output.add(victoryChapters);									output.add(victoryLevels);
		output.add(victoryNames);										output.add(victoryTimes);
		output.add(victoryShifts);										output.add(victoryResets);

		return output;
	}
	
	public ArrayList<ArrayList<String>> getDeathData()
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();

		//DEATH DATA-----------------------------------------------
		ArrayList<String> deathChapters = new ArrayList<String>();	ArrayList<String> deathLevels = new ArrayList<String>();
		ArrayList<String> deathNames= new ArrayList<String>();		ArrayList<String> deathHazards= new ArrayList<String>();
		ArrayList<String> deathTimes= new ArrayList<String>();		ArrayList<String> deathLocation = new ArrayList<String>();

		deathChapters.add("Chapter");							deathLevels.add("Level");
		deathNames.add("Name");									deathHazards.add("Hazards");
		deathTimes.add("Times");								deathLocation.add("Resets");

		for(DeathData data : deathData)
		{
		deathChapters.add(""+data.getChapter());					deathLevels.add(""+data.getLevelNum());
		deathNames.add(""+data.getLevelName());						deathHazards.add(""+data.getHazard());
		deathTimes.add(""+data.getTime());							deathLocation.add(""+data.getLocation());
		}

		output.add(deathChapters);									output.add(deathLevels);
		output.add(deathNames);										output.add(deathHazards);
		output.add(deathTimes);										output.add(deathLocation);
		
		return output;
	}
	
	public ArrayList<ArrayList<String>> getResetData()
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		


	//RESET DATA-----------------------------------------------
		ArrayList<String> resetChapters = new ArrayList<String>();	ArrayList<String> resetLevels = new ArrayList<String>();
		ArrayList<String> resetNames= new ArrayList<String>();		ArrayList<String> resetOriginator= new ArrayList<String>();
		ArrayList<String> resetTimes= new ArrayList<String>();		ArrayList<String> resetLocation = new ArrayList<String>();

		resetChapters.add("Chapter");							resetLevels.add("Level");
		resetNames.add("Name");									resetOriginator.add("Originator");
		resetTimes.add("Times");								resetLocation.add("Location");

		for(ResetData data : resetData)
		{
		resetChapters.add(""+data.getChapter());					resetLevels.add(""+data.getLevelNum());
		resetNames.add(""+data.getLevelName());						resetOriginator.add(""+data.getOriginator());
		resetTimes.add(""+data.getTime());							resetLocation.add(""+data.getLocation());
		}

		output.add(resetChapters);									output.add(resetLevels);
		output.add(resetNames);										output.add(resetOriginator);
		output.add(resetTimes);										output.add(resetLocation);
	
		return output;
	}

	public ArrayList<ArrayList<String>> getTapsData()
	{
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
	//TAPSDATA-----------------------------------------------
		ArrayList<String> tapsChapters = new ArrayList<String>();	ArrayList<String> tapsLevels = new ArrayList<String>();
		ArrayList<String> tapsNames= new ArrayList<String>();		ArrayList<String> tapsSwipes = new ArrayList<String>();
		ArrayList<String> tapsTimeStarts = new ArrayList<String>();	ArrayList<String> tapsTimeEnds= new ArrayList<String>();
		ArrayList<String> tapsPosStart = new ArrayList<String>();	ArrayList<String> tapsPosEnd = new ArrayList<String>();

		tapsChapters.add("Chapter");								tapsLevels.add("Level");
		tapsNames.add("Name");										tapsSwipes.add("Swipes");
		tapsTimeStarts.add("Time Start");							tapsTimeEnds.add("Time Ends");
		tapsPosStart.add("Position Start");							tapsPosEnd.add("Position Ends");

		for(TapsData data : tapsData)
		{
		tapsChapters.add(""+data.getChapter());						tapsLevels.add(""+data.getLevelNum());
		tapsNames.add(""+data.getLevelName());						tapsSwipes.add(""+data.isSwipe());
		tapsTimeStarts.add(""+data.getTimeStart());					tapsTimeEnds.add(""+data.getTimeEnd());
		tapsPosStart.add(""+data.getPositionStart());				tapsPosEnd.add(""+data.getPositionEnd());
		}

		output.add(tapsChapters);									output.add(tapsLevels);
		output.add(tapsNames);										output.add(tapsSwipes);
		output.add(tapsTimeStarts);									output.add(tapsTimeEnds);
		output.add(tapsPosStart);									output.add(tapsPosEnd);

		return output;
	}
	
	
}
