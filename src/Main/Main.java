package Main;

import Controlers.Controler;
import Interface.Observer;
import Model.Model;
import Views.AccueilView;

public class Main 
{
	public static void main (String [] args)
	{
		Fenetre f = new Fenetre();
		
	    Model model = new Model(f);

	    Controler controler = new Controler(model);
	    
	    AccueilView vue = new AccueilView(controler);
	    
	    model.addObserver(vue);
	}
}
