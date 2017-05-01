package main.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.commons.net.ftp.FTPClient;

import net.miginfocom.swing.MigLayout;

public class folderWindow extends JFrame
{
	public folderWindow(FTPClient fClient)
	{
		setSize(400,200);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setTitle("Installation");
		
		getContentPane().setLayout(new MigLayout("", "[400px]", "[200px][191px][]"));
		
		JTextPane msgSelectFolder = new JTextPane();
		JButton selectButton = new JButton();
		JButton validButton = new JButton("Valider");
		JTextField fieldFolder = new JTextField();
		
		msgSelectFolder.setBackground(SystemColor.menu);
		msgSelectFolder.setText("Sélectionner un dossier pour les musiques");
		msgSelectFolder.setEditable(false);
		
		fieldFolder.setColumns(10);
		
		selectButton.setIcon(new ImageIcon(getClass().getResource("/main/img/seach.png")));
		
		selectButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
		        JFileChooser fc = new JFileChooser();
		        fc.setCurrentDirectory(new java.io.File(""));
		        fc.setDialogTitle("Sélectionner un dossier");
		        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        fc.setAcceptAllFileFilterUsed(false);

		        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		        {
		        	File directory = fc.getSelectedFile();
		        	String pathToDir = directory.getAbsolutePath();
		        	fieldFolder.setText(pathToDir);
		        	msgSelectFolder.setForeground(Color.BLACK);
		        	msgSelectFolder.setText("Sélectionner un dossier pour les musiques");
		        }
			}
		});
		
		validButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String field = fieldFolder.getText();
				if(field.isEmpty())
				{
					msgSelectFolder.setText("Dossier introuvable !");
					msgSelectFolder.setForeground(Color.RED);
				}
				else if(new File(field).exists())
				{
					try
					{
						FileWriter fileWriter = new FileWriter(new File(System.getenv("APPDATA")+File.separator+"FTPlus"+File.separator+"setup.txt"));
						fileWriter.write(field);
						fileWriter.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					
					dispose();
					new mainWindow(fClient);
				}
			}
		});
		
		getContentPane().add(msgSelectFolder, "cell 0 0,alignx center");
		getContentPane().add(fieldFolder, "flowx,cell 0 1,growx,aligny top");
		getContentPane().add(selectButton, "cell 0 1,alignx center,aligny top");
		getContentPane().add(validButton, "cell 0 2,alignx right");
		
		setVisible(true);
		
		fieldFolder.setMinimumSize(new Dimension(10, selectButton.getHeight()));
	}
}