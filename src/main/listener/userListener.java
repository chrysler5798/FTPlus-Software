package main.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class userListener implements ActionListener
{
	JTextPane txtPane;
	JTextField txtField;
	
	public userListener(JTextPane txtPane, JTextField txtField)
	{
		this.txtPane = txtPane;
		this.txtField = txtField;
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
			PostMethod post = new PostMethod("");
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
				if(client.executeMethod(post) == 200 && !post.getResponseBodyAsString(1).equals("0"))
				{
					setText("Pseudo :", Color.BLACK);
					
					String reponse = post.getResponseBodyAsString(1);
					
					if(typeCommand.equals("Inscription"))
					{
						switch(reponse)
						{
						case "1":
							
							break;
							
						case "3":
							setText("Ce pseudo existe deja !", Color.RED);
							break;
						}
					}
					else if(typeCommand.equals("Connexion"))
					{
						switch(reponse)
						{
						case "1":
							
							break;
							
						case "3":
							setText("Ce pseudo n'existe pas !", Color.RED);
							break;
						}
					}
				}
				else
				{
					setText("Erreur serveur !", Color.RED);
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

}