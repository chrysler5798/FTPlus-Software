package main.listener;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ouvrirListener implements ActionListener
{
	private final String PC_PATH = System.getenv("APPDATA")+File.separator+"FTPlus"+File.separator;
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			
			byte[] encoded = Files.readAllBytes(Paths.get(PC_PATH+"setup.txt"));
			String pathToMusic = new String(encoded, Charset.defaultCharset());
			Desktop.getDesktop().open(new File(pathToMusic));
			
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}