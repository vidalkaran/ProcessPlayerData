package mainPackage;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.GroupLayout.Alignment;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import domainPackage.Player;
import domainPackage.VictoryData;


public class ExcelProcessor 
{
	Map<String, Player> playerData;
	String[] folderNames;
	static final String[] playerHeaders = 
		{
				"Chapter", "LevelNum", "LevelName", "Time", "Shifts", "Resets",
				"Chapter", "LevelNum", "LevelName", "Hazard", "Shifts", "Resets",
				"Chapter", "LevelNum", "LevelName", "Originator", "Shifts", "Resets",
				"Chapter", "LevelNum", "LevelName", "isSwipe", "TimeStart", "TimeEnd", "PositionStart", "PositionEnd"
		};
	
	XSSFCellStyle victoryStyle;
	XSSFCellStyle deathStyle;
	XSSFCellStyle resetStyle;
	XSSFCellStyle tapsStyle;

	XSSFCellStyle victoryHeader;
	XSSFCellStyle deathHeader;
	XSSFCellStyle resetHeader;
	XSSFCellStyle tapsHeader;

	XSSFWorkbook workbook;
	XSSFFont font;
	boolean stylesCreated = false;
	
	//Constructor takes in a map players, where the key is the directory location in plain text, and generates a folder array
	public ExcelProcessor(Map<String, Player> inputPlayers)
	{
		playerData = inputPlayers;

		//populate foldernames array... use a set to ensure no duplicates
		Set<String> set = new HashSet<String>();

		for(String key : playerData.keySet())
			set.add(StringUtils.split(key, "\\")[0]);
		
		folderNames = set.toArray(new String[set.size()]);
	}
	
	public void setDefaultStyle(XSSFCellStyle style, boolean isHeader)
	{
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.DASHED);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());		
				
		if(isHeader)
		{
			font.setBold(true);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
		}
	}
	
	public void createStyles()
	{	
			font = workbook.createFont();
			victoryStyle = workbook.createCellStyle();
			deathStyle = workbook.createCellStyle();
			resetStyle = workbook.createCellStyle();
			tapsStyle = workbook.createCellStyle();

			victoryHeader = workbook.createCellStyle();
			deathHeader = workbook.createCellStyle();
			resetHeader = workbook.createCellStyle();
			tapsHeader = workbook.createCellStyle();

			//Define format of all styles
			setDefaultStyle(victoryStyle, false);
			victoryStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			
			setDefaultStyle(deathStyle, false);
			deathStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	
			setDefaultStyle(resetStyle, false);
			resetStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
	
			setDefaultStyle(tapsStyle, false);
			tapsStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			
			//Headers
			
			setDefaultStyle(victoryHeader, true);
			victoryHeader.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			
			setDefaultStyle(deathHeader, true);
			deathHeader.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	
			setDefaultStyle(resetHeader, true);
			resetHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
	
			setDefaultStyle(tapsHeader, true);
			tapsHeader.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			
			System.out.println("STYLESCREATED");
	}
	
	public void execute()
	{
		writeWorkbook();
	}
	
	//Creates the workbook from folder array!
	public void writeWorkbook()
	{
		for(String folderName : folderNames)
		{
			try
			{
			//Create workbook
			workbook = new XSSFWorkbook();
			
			//define cell styles
			createStyles();
			
			//Write sheets
			writeExcelSheet(folderName);
			
	        workbook.write(new FileOutputStream(folderName+".xlsx"));
	        workbook.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		createStyles();
	}
	
	//Generates cells and writes playerdata to cell... also handles some formatting because of lack of foresight :]
	public void writePlayer(XSSFSheet sheet, Player player)
	{		
		ArrayList<ArrayList<String>> playerData = player.getAllData();
		int rows = player.rowMax+1;
		int columns = playerData.size();

		XSSFCell[][] cellArray = new XSSFCell[rows][columns];

		System.out.println(rows + "..." + columns);
		
		//Generate cell array based on rows and columns from playerdata
		for(int i = 0; i<rows;i++)
		{
			XSSFRow row = sheet.createRow(i);
			for(int j = 0; j<columns;j++)
			{
				XSSFCell cell = row.createCell(j);
				cellArray[i][j] = cell;
				setFormatting(cellArray[i][j]);
			}
		}
		
		//Add player data... adding it in reverse order so that it is ordered by column instead of row (shrug) :]
		for(int i = 0; i<playerData.size();i++)
		{
			for(int j = 0; j<playerData.get(i).size();j++)
			{
				cellArray[j+1][i].setCellValue(playerData.get(i).get(j)); //+1 to bring it down 1 :]
			}
		}
		
		//adding merged regions
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));		cellArray[0][0].setCellValue("Victory Data");
		sheet.addMergedRegion(new CellRangeAddress(0,0,6,11));		cellArray[0][6].setCellValue("Death Data");
		sheet.addMergedRegion(new CellRangeAddress(0,0,12,17));		cellArray[0][12].setCellValue("Reset Data");
		sheet.addMergedRegion(new CellRangeAddress(0,0,18,25));		cellArray[0][18].setCellValue("Taps Data");

		//Resize and formatting header columns
		for(int i = 0; i<columns;i++)
		{
			setFormatting(cellArray[0][i]);
			setFormatting(cellArray[1][i]);
		    sheet.autoSizeColumn(i);
		}
		
		System.out.println("DONE");
	}
	
	//Used to format all cells
	public void setFormatting(XSSFCell cell)
	{
		boolean isHeader = false;
		
		//check is cell is a header cell
		if(cell.getRowIndex() <= 1)
			isHeader = true;
			
		if(cell.getColumnIndex() <= 5)
		{
			if(isHeader)
				cell.setCellStyle(victoryHeader);
			else
				cell.setCellStyle(victoryStyle);
		}
		else if(cell.getColumnIndex() >= 6 && cell.getColumnIndex() <= 11)
		{
			if(isHeader)
				cell.setCellStyle(deathHeader);
			else
				cell.setCellStyle(deathStyle);
		}
		else if(cell.getColumnIndex() >= 12 && cell.getColumnIndex() <= 17)
		{
			if(isHeader)
				cell.setCellStyle(resetHeader);
			else
				cell.setCellStyle(resetStyle);
		}
		else if(cell.getColumnIndex() >= 18 && cell.getColumnIndex() <= 25)
		{
			if(isHeader)
				cell.setCellStyle(tapsHeader);
			else
				cell.setCellStyle(tapsStyle);
		}

	}

	//Create sheet
	public void writeExcelSheet(String folderName)
	{
		try
		{		
		//Iterate through each item in the playerdata map and see if the folder matches the attached folder
		for(String key : playerData.keySet())
		{
			if(StringUtils.contains(key, folderName))
			{
				//creates a new sheet from the filename
				XSSFSheet sheet = workbook.createSheet(StringUtils.split((StringUtils.split(key,"\\")[1]), ".")[0]);
				writePlayer(sheet, playerData.get(key));
			}
		}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void test()
	{
		writeWorkbook();
	}
}
