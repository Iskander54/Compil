package Model;

import java.util.ArrayList;

import Interface.Observer;
import Main.Fenetre;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import Interface.Observable;
import Interface.Observer;
import Interface.Observer;
import Main.Fenetre;

public class Model {
	Fenetre f;
	ArrayList<Observer> views = new ArrayList<Observer>();

	public Model(Fenetre f)
	{
		this.f = f;
	}
	public void addObserver(Observer obs) {
		if(!views.contains(obs))
			views.add(obs);
		notifyObserver(obs);
	}

	public void notifyObserver(Observer obs) {
		if(obs == null)
		{
			for(Observer o : views)
			      o.update();
		}
		else
			obs.update();
	}
	
	public void removeObserver(Observer obs) {
		if(obs == null)
			views = new ArrayList<Observer>();
		else
		{
			if(views.contains(obs))
				views.remove(obs);
		}
	}
	public Fenetre getF() {
		return f;
	}
	
	public void setF(Fenetre f) {
		this.f = f;
	}
	public ArrayList<Observer> getViews() {
		return views;
	}
	public void setViews(ArrayList<Observer> views) {
		this.views = views;
	}
}
