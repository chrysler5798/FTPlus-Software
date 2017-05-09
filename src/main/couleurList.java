package main;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class couleurList extends DefaultListCellRenderer
{
	private final String PC_PATH = System.getenv("APPDATA")+File.separator+"FTPlus"+File.separator;
	
	
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		try
		{
			byte[] encodedTitle = Files.readAllBytes(Paths.get(PC_PATH+"new_title.txt"));
			byte[] encodedSetup = Files.readAllBytes(Paths.get(PC_PATH+"setup.txt"));
			
			String txtFileNew = new String(encodedTitle, Charset.defaultCharset());
			String txtSetup = new String(encodedSetup, Charset.defaultCharset());
			
			File fileValue = new File(txtSetup+File.separator+value+".mp3");
			
			if(txtFileNew != "" && txtFileNew.contains((CharSequence) value) || !(fileValue.exists()))
	        {
				c.setBackground(new Color(255,196,196));
	        }
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        return c;
    }
}