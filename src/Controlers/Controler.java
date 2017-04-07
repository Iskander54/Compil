package Controlers;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import Main.Fenetre;
import Model.Model;

public class Controler {
	Model model;
	
	public Controler(Model m)
	{
		model = m;
	}
	
	public Fenetre GetMainWindow()
	{
		return model.getF();
	}

}
