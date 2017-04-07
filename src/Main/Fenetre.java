package Main;

import javax.swing.JFrame;

import Model.Model;
import Views.*;
import javax.swing.JButton;
import java.awt.BorderLayout;

public class Fenetre extends JFrame {
	
	//SearchModel m ;
	
	public Fenetre()
	{
		// Fenetre
		this.setTitle(" IFC File Modifier");
	    this.setSize(1280, 740);        
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
	    JButton btnNewButton = new JButton("New button");
	    getContentPane().add(btnNewButton, BorderLayout.NORTH);
	    this.setVisible(true);
	    
	}
	public void update()
	{
		this.setVisible(true);
		this.revalidate();
	}
}
