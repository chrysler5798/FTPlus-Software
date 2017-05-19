package main.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import main.listener.userListener;
import net.miginfocom.swing.MigLayout;

public class userWindow extends JFrame
{
	
	public userWindow()
	{
		Font msgFont = new Font("Tahoma", Font.BOLD, 13);
		String pseudoTxt = "Pseudo :", title = "FTPlus - Connexion", passTxt = "Mot de passe :";
		
		setTitle(title);
		
		setSize(600,200);
		
		setResizable(false);
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(new MigLayout("", "[][][][][][grow][][][][][][][][][][][][grow][]", "[][grow][grow]"));
		
		JPanel panelConn = new JPanel();
		JPanel panelInsc = new JPanel();
		
		JTextPane txtConn = new JTextPane();
		JTextPane txtInsc = new JTextPane();
		JTextPane txtPseudoConn = new JTextPane();
		JTextPane txtPseudoInsc = new JTextPane();
		JTextPane textPassConn = new JTextPane();
		JTextPane textPassInsc = new JTextPane();
		
		JTextField fieldConn = new JTextField();
		JTextField fieldInsc = new JTextField();
		JPasswordField fieldPassConn = new JPasswordField();
		JPasswordField fieldPassInsc = new JPasswordField();
		
		JButton btnConn = new JButton("Connexion");
		JButton btnInsc = new JButton("Inscription");
		
		btnConn.addActionListener(new userListener(this, txtPseudoConn, textPassConn, fieldConn, fieldPassConn));
		btnInsc.addActionListener(new userListener(this, txtPseudoInsc, textPassInsc, fieldInsc, fieldPassInsc));
		
		panelConn.setLayout(new MigLayout("", "[92px,grow][grow][grow]", "[grow][][][][grow][][grow]"));
		panelInsc.setLayout(new MigLayout("", "[92px,grow][grow][grow]", "[grow][][][][grow][][grow]"));
		
		getContentPane().add(panelConn, "cell 1 0 8 3,grow");
		getContentPane().add(panelInsc, "cell 10 0 8 3,grow");
		
		fieldConn.setColumns(10);
		fieldInsc.setColumns(10);
		fieldPassInsc.setColumns(10);
		fieldPassConn.setColumns(10);
		
		txtConn.setFont(msgFont);
		txtInsc.setFont(msgFont);
		
		txtConn.setBackground(SystemColor.menu);
		txtInsc.setBackground(SystemColor.menu);
		txtPseudoConn.setBackground(SystemColor.menu);
		txtPseudoInsc.setBackground(SystemColor.menu);
		textPassInsc.setBackground(SystemColor.menu);
		textPassConn.setBackground(SystemColor.menu);
		
		txtConn.setEditable(false);
		txtInsc.setEditable(false);
		txtPseudoConn.setEditable(false);
		txtPseudoInsc.setEditable(false);
		textPassConn.setEditable(false);
		textPassInsc.setEditable(false);
		
		txtConn.setText("Se connecter");
		txtInsc.setText("S'inscrire");
		txtPseudoConn.setText(pseudoTxt);
		txtPseudoInsc.setText(pseudoTxt);
		textPassConn.setText(passTxt);
		textPassInsc.setText(passTxt);
		
		panelConn.add(txtConn, "cell 0 0 3 2,alignx center,aligny top");
		panelConn.add(txtPseudoConn, "cell 0 2 3 1,alignx center,aligny top");
		panelConn.add(fieldConn, "cell 0 3 3 1,growx");
		panelConn.add(textPassConn, "cell 0 4 3 1,alignx center,aligny center");
		panelConn.add(fieldPassConn, "cell 0 5 3 1,growx");
		panelConn.add(btnConn, "cell 0 6 3 1,alignx center");
		panelInsc.add(txtInsc, "cell 0 0 3 2,alignx center,aligny top");
		panelInsc.add(txtPseudoInsc, "cell 0 2 3 1,alignx center,aligny top");
		panelInsc.add(fieldInsc, "cell 0 3 3 1,growx");
		panelInsc.add(textPassInsc, "cell 0 4 3 1,alignx center,aligny center");
		panelInsc.add(fieldPassInsc, "cell 0 5 3 1,growx");
		panelInsc.add(btnInsc, "cell 0 6 3 1,alignx center");

		setVisible(true);
		
	}
}