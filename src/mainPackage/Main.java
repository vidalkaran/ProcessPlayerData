package mainPackage;

import javax.swing.JOptionPane;

public class Main 
{
	public static void main(String[]args)
	{
	JOptionPane.showMessageDialog(null, "It can take a couple minutes to process... "
			+ "\nthere will be another dialog box when it is completed... click ok to start... "
			+ "\n(Once day I'll learn how to use progress bars in java :D :D)");

	Main main = new Main();
	
	try {
		JSONProcessor jp = new JSONProcessor();
		ExcelProcessor ep = new ExcelProcessor(jp.getPlayerData());
		ep.execute();
		JOptionPane.showMessageDialog(null, "Complete!.");
	}
	catch(Exception e)
	{
		System.out.println("Fail: "+e);
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "File not found. Check the data directory.");
	}
	}
}