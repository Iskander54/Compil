package IFCfile;

import java.util.ArrayList;

public class Facade {
	String nom;
	ArrayList<Commentaire> listeCommentaires;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public ArrayList<Commentaire> getListeCommentaires() {
		return listeCommentaires;
	}
	public void setListeCommentaires(ArrayList<Commentaire> listeCommentaires) {
		this.listeCommentaires = listeCommentaires;
	}
	
	public Facade(String nom){
		this.nom = nom;
		this.listeCommentaires = new ArrayList<Commentaire>();
	}
	
	public void add(Commentaire commentaire){
		this.listeCommentaires.add(commentaire);
	}
	
	public void remove(Commentaire commentaire){
		this.listeCommentaires.remove(commentaire);
	}
}
