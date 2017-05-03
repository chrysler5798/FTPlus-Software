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
			byte[] encoded = Files.readAllBytes(Paths.get(PC_PATH+"new_title.txt"));
			String txtFileNew = new String(encoded, Charset.defaultCharset());
			
			if(txtFileNew != "" && txtFileNew.contains((CharSequence) value))
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