package main.window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;

public class testFrame extends JFrame {

	static private final String newline = "\n";
	JTextArea textArea;
	int i = 0;
	JList<String> list;
	JButton btnNewButton_1;
	boolean isTrue;
	
	public testFrame() {
		
		setTitle("FTPlus");
		
		setSize(1400,600);
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new MigLayout("", "[][][384px,grow][]", "[][130px][][grow][]"));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		list = new JList<>(model);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String value = list.getSelectedValue();
				String mp3Value = value.substring(value.length() - 3);
				if(mp3Value.equals("mp3"))
				{
					
				}
				else
				{
				}
			}
		});
		scrollPane.setMinimumSize(new Dimension(100, 100));
		scrollPane.setViewportView(list);
		
		textArea = new JTextArea();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btnNewButton = new JButton("SYNC");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setIcon(new ImageIcon(mainWindow.class.getResource("/main/img/sync.png")));
		getContentPane().add(btnNewButton, "cell 2 0,grow");
		
		JFXPanel jfxPanel = new JFXPanel();
		getContentPane().add(jfxPanel, "cell 0 0 1 5,growy");
		getContentPane().add(scrollPane, "cell 2 1,aligny top,grow");
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(testFrame.class.getResource("/main/img/add.png")));
		getContentPane().add(btnNewButton_2, "cell 1 0 1 5,grow");
		isTrue = true;
		
		getContentPane().add(scrollPane_1, "cell 2 3,aligny top,grow");
		btnNewButton_1 = new JButton("Save");
		btnNewButton_1.setEnabled(false);
		getContentPane().add(btnNewButton_1, "cell 2 4,grow");
		
		textArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				btnNewButton_1.setEnabled(true);
			}
		});
		scrollPane_1.setViewportView(textArea);
		textArea.setText("Select a file...");

		// Creation of scene and future interactions with JFXPanel
		// should take place on the JavaFX Application Thread
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    webView.getEngine().load("http://www.youtube.com/");
		});
		
		setVisible(true);
	}
}