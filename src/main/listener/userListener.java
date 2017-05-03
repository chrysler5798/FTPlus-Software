package main.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import main.window.folderWindow;
import main.window.mainWindow;

public class userListener implements ActionListener
{
	JFrame window;
	JTextPane txtPane;
	JTextField txtField;
	
	public userListener(JFrame window, JTextPane txtPane, JTextField txtField)
	{
		this.txtPane = txtPane;
		this.txtField = txtField;
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String txtTyped = txtField.getText();
		String typeCommand = e.getActionCommand();
		
		if(txtTyped.isEmpty())
		{
			setText("Veuillez entrer un pseudo !", Color.RED);
		}
		else
		{
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://164.132.145.12/ftplus/account.php");
			String typePhp = "";
			
			switch(typeCommand)
			{
				case "Inscription":
					typePhp = "1";
					break;
				
				case "Connexion":
					typePhp = "2";
					break;
			}
			
			post.addParameter("type", typePhp);
			post.addParameter("username",txtTyped);
			
			try
			{
				if(client.executeMethod(post) != 200 || post.getResponseBodyAsString(1).equals("0"))
				{
					setText("Erreur serveur !", Color.RED);
				}
				else
				{
					setText("Pseudo :", Color.BLACK);
					
					String response = post.getResponseBodyAsString(1);
					
					if(typeCommand.equals("Inscription"))
					{
						switch(response)
						{
						case "1":
							doneUser(txtTyped);
							break;
							
						case "3":
							setText("Ce pseudo existe deja !", Color.RED);
							break;
						}
					}
					else if(typeCommand.equals("Connexion"))
					{
						switch(response)
						{
						case "1":
							doneUser(txtTyped);
							break;
							
						case "3":
							setText("Ce pseudo n'existe pas !", Color.RED);
							break;
						}
					}
				}
			}
			catch (HttpException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
	}
	
	void setText(String txt, Color color)
	{
		txtPane.setForeground(color);
		txtPane.setText(txt);
	}
	
	void doneUser(String pseudo)
	{
		final String PC_PATH = System.getenv("APPDATA")+File.separator+"FTPlus"+File.separator;
		
		File userFile = new File(PC_PATH+"user.txt");
		
		try
		{
			FileWriter writer = new FileWriter(userFile);
			writer.write(pseudo);
			writer.close();
			
			byte[] encoded = Files.readAllBytes(Paths.get(PC_PATH+"setup.txt"));
			String setupFile = new String(encoded, Charset.defaultCharset());
			
			if(setupFile.isEmpty())
			{
				window.dispose();
				new folderWindow();
			}
			else
			{
				window.dispose();
				new mainWindow();
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}