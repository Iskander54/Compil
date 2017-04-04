package IFCfile;

import java.util.ArrayList;
import java.util.Scanner;

public class ligneIFC {
	String hashTag;
	String nomFonction;
	ArrayList<String> argument=new ArrayList<String>();
	
	public String getHashTag() {
		return hashTag;
	}
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	public String getNomFonction() {
		return nomFonction;
	}
	public void setNomFonction(String nomFonction) {
		this.nomFonction = nomFonction;
	}
	public ArrayList<String> getArgument() {
		return argument;
	}
	public void setArgument(ArrayList<String> argument) {
		this.argument = argument;
	}
	public ligneIFC(String hashTag, String nomFonction, ArrayList<String> argument) {
		super();
		this.hashTag = hashTag;
		this.nomFonction = nomFonction;
		this.argument = argument;
	}

	
	
	public ligneIFC(ArrayList<String> ligne){
		super();
		String arg="";
		this.hashTag=ligne.get(0);
		
		if(!ligne.get(2).contains("(")){
			this.nomFonction=ligne.get(2);
		}
		else {
			int index = ligne.get(2).indexOf('(');
			String str1 = ligne.get(2).substring(0, index);
			this.nomFonction=str1;
			String str2 = ligne.get(2).substring(index+1,ligne.get(2).length());
			ligne.remove(ligne.get(2));
			ligne.add(2, str1);
			ligne.add(3,"(");
			ligne.add(4,str2);
		}
		

		if(ligne.get(3).length()!=1){
			//System.out.println(ligne.get(3));
			String str3=ligne.get(3).substring(0,1);
			//System.out.println(str3);
			String str4=ligne.get(3).substring(1,ligne.get(3).length());
			//System.out.println(str4);
			ligne.remove(ligne.get(3));
			ligne.add(3, str3);
			ligne.add(4, str4);
		}
		// On commence à 4 car on supprime la 1ere parenthese 
		for(int i=4;i<ligne.size();i++){
			arg = arg.concat(ligne.get(i)+" ");
		
		}
		
		
		if(arg.endsWith("); ")==true){
			
			arg=arg.substring(0,arg.length()-3);

		}
		if(arg.endsWith(") ; ")==true){
			arg=arg.substring(0, arg.length()-4);

		}
		
		Scanner arguments = new Scanner(arg);
		arguments.useDelimiter(",");
		while(arguments.hasNext()){
			String mot = arguments.next();
			
			int nbespacegauche = 0;
			int nbespacedroite = mot.length();
			while(mot.charAt(nbespacegauche)==' '){
				nbespacegauche+=1;
			}
		while(mot.charAt(nbespacedroite-1)==' '){
			nbespacedroite-=1;
			}
			mot=mot.substring(nbespacegauche,nbespacedroite);
			this.argument.add(mot);
		}
		
		
	}
	public boolean add(String e) {
		return argument.add(e);
	}
	public void add(int index, String element) {
		argument.add(index, element);
	}
	public String remove(int index) {
		return argument.remove(index);
	}
	
	
	
}
