package Controlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import IFCfile.Commentaire;
import IFCfile.Facade;
import IFCfile.Token;
import IFCfile.ligneIFC;
import Main.Fenetre;
import Model.Model;

public class Controler {
	Model model;
	ArrayList<Facade> listeFacade = new ArrayList<Facade>();
	ArrayList<ligneIFC> fichier = new ArrayList<ligneIFC>();
	String debut="";
	String fin="";
	
	public Controler(Model m)
	{
		model = m;
	}
	
	public Fenetre GetMainWindow()
	{
		return model.getF();
	}
	
	public static String[] readerIFC(ArrayList<ligneIFC> file,String debut, String fin,File fileR) throws IOException{
		file.clear();
		FileReader fileIFC = new FileReader(fileR);
        @SuppressWarnings("resource")
		BufferedReader bfileIFC = new BufferedReader(fileIFC);
        String ligneIFC = bfileIFC.readLine();
        int i=0;
        
        // On parcours les lignes
        while(ligneIFC!=null) {
            @SuppressWarnings("resource")
			Scanner scIFC = new Scanner(ligneIFC);
            scIFC.useDelimiter(" ");


            // On parcours les mots d'une ligne 
            ArrayList<String> ligne= new ArrayList<String>();
            while(scIFC.hasNext()){
            	// On cree un tableau qui va contenir tout les mots de la ligne
                String mot = scIFC.next();
                ligne.add(mot);    
          
            }
            if(ligne.size()>2 && ligne.get(0).contains("#")){
            	ligneIFC lign = new ligneIFC(ligne);
            	file.add(lign);
            }else{
            	if(i<20){
            	debut=debut+ligneIFC+"\n";
            	}else{
            		fin=fin+ligneIFC+"\n";
            	}
            }
            i++;
            ligneIFC=bfileIFC.readLine();
            
        }
        /*
        for(ligneIFC e : file){

            	System.out.println(file.indexOf(e)+" Hashtag : "+e.getHashTag() + " , Fonction : "+ e.getNomFonction() + " , Argument : "+ e.getArgument());
            	System.out.println();
            
        	
        }
		*/
        String entete[]={debut,fin};
      //  System.out.println("Fichier IFC téléchargé avec succès !");
        return(entete);

       
	}
	public static int maxHashTag(ArrayList<ligneIFC> fichier){
		int max=0;
		String str;
		int tmp=0;
		for(int i=0;i<fichier.size();i++){
			str=fichier.get(i).getHashTag().substring(1);
			tmp=Integer.parseInt(str);
			if(tmp>max){
				max=tmp;
			}
			
		}
		// On cherche le max des htag et on renvoie n+1
		return max;
	}
	
	public static void readerCSV(File path,ArrayList<Facade> listeFacade) throws IOException{
		
		
		  // String chemin = "/home/etudiants/cluchagu1u/2A/PIDR/export_commentaire_2017-01-31.csv";
		   FileReader fileCSV = new FileReader(path);
		   BufferedReader fichierCSV = new BufferedReader(fileCSV);
		   String chaine;
		   int n;
		 
		   Facade facade =null;
		   
		   while((chaine = fichierCSV.readLine())!= null){
			   String[] ligne = chaine.split(";");
			   n = ligne.length;
			   if(n<3){
				   if(facade != null){
					   listeFacade.add(facade);
				   }
				   facade = new Facade(ligne[1]);
			   }
			   else{
				   if(!ligne[2].contains("Texte")){
					   String nomCom = ligne[2];
					   Double x = Double.parseDouble(ligne[0]);
					   Double y = Double.parseDouble(ligne[1]);
					   Commentaire commentaire = new Commentaire(nomCom,x,y);
					   //System.out.println("commentaire : " + commentaire.getCommentaire() + " | x = " + commentaire.getPositionX()+" | y = " +commentaire.getPositionY());
					   facade.add(commentaire);
				   }
			   }
		   }listeFacade.add(facade);
		   /*
		   for ( Facade f : listeFacade){
			   System.out.print(listeFacade.indexOf(f)+f.nom+" : \n");
			   
			   for (Commentaire c : f.getListeCommentaires()){
				   System.out.print("\t Commentaire : " + c.getCommentaire() + " ( "+ c.getPositionX()+ " , " + c.getPositionY() + " )\n");
			   }
		   }
		   */
		   fichierCSV.close();    
		  // System.out.println("Liste des commentaires téléchargée avec succès !"+"\n");
	
		
		
	
	}
	public static void modifIFC2(ArrayList<ligneIFC> fichier,ArrayList<Facade> listeFacade,int valIFC, int valcom) throws IOException{
        new ArrayList<ligneIFC>();
        ArrayList<ligneIFC> tmp = new ArrayList<ligneIFC>();
		String tmp1 = null;
		ligneIFC modifline=null;
    	try {
			new Token();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String Hstr=fichier.get(valIFC).getHashTag();
		//System.out.println(fichier.get(valIFC).toString());
        
        Commentaire commentaire=indexcom(listeFacade, valcom);
        
        String com1=commentaire.getCommentaire();
        String com=commentaire.getCommentaire();
      //  System.out.println(com1+com);
        ArrayList<String> commentaireliste = new ArrayList<String>();
        commentaireliste.add("'"+com1+"'");
        commentaireliste.add("'"+com1+"'");
        commentaireliste.add("IFCTEXT('"+com+"')");
        String hash="#"+Integer.toString(maxHashTag(fichier)+1);
        ligneIFC newline = new ligneIFC(hash,"IFCPROPERTYSINGLEVALUE",commentaireliste);
        
        for (ligneIFC e : fichier){
        	if (e.getNomFonction().contains("IFCRELDEFINESBYPROPERTIES")){
        		tmp.add(e);
        	}
        }
		 for (ligneIFC l:tmp){
	        	for (String s: l.argument){

	        		if(s.contains("("+Hstr+")")){
	        			tmp1=l.getArgument().get(l.argument.size()-1);
	        			//System.out.println(tmp1);
	        		}
	        	}
	        }
		 if(tmp1!=null){
	        for(ligneIFC t : fichier){
	        	if(t.getHashTag().contains(tmp1)){
	        		modifline=t;
	        		//System.out.println(modifline);
	        	}
	        }

        
        fichier.remove(modifline);

        String lastArg = modifline.getArgument().get(modifline.getArgument().size()-1);
        lastArg = lastArg.substring(0, lastArg.length()-1);
        lastArg = lastArg+","+hash+")";
        modifline.remove(modifline.getArgument().size()-1);
        modifline.add(lastArg);
        fichier.add(newline);
        fichier.add(modifline);
       // System.out.println(newline.toString());
      //  System.out.println(modifline.toString());
        
	 }else{
		 String id1= tmp.get(0).getArgument().get(0);
		 String hashRELDEFINES="#"+Integer.toString(maxHashTag(fichier)+2);
		 String hashPROPERTYSET="#"+Integer.toString(maxHashTag(fichier)+3);
		 String id1_propertyset = null;

		 ArrayList<String> ArgIFCPROPERTYSET = new ArrayList<String>();
		 ArrayList<String> ArgIFCRELDEFINES = new ArrayList<String>();
		 ArgIFCRELDEFINES.add(id1);
		 ArgIFCRELDEFINES.add("#2");
		 ArgIFCRELDEFINES.add("$");
		 ArgIFCRELDEFINES.add("$");
		 ArgIFCRELDEFINES.add("("+Hstr+")");
		 ArgIFCRELDEFINES.add(hashPROPERTYSET);
		 ligneIFC lines_RELDEFINES = new ligneIFC(hashRELDEFINES,"IFCRELDEFINESBYPROPERTIES",ArgIFCRELDEFINES);
		 
		 for (ligneIFC e : fichier){
	        	if (e.getNomFonction().contains("IFCPROPERTYSET")){
	        		id1_propertyset=e.getArgument().get(0);
	        		break;
	        	}
	        	}
		 ArgIFCPROPERTYSET.add(id1_propertyset);
		 ArgIFCPROPERTYSET.add("#2");
		 ArgIFCPROPERTYSET.add("'Propriete'");
		 ArgIFCPROPERTYSET.add("$");
		 ArgIFCPROPERTYSET.add("("+hash+")");
		 ligneIFC lines_PROPERTYSET= new ligneIFC(hashPROPERTYSET,"IFCPROPERTYSET",ArgIFCPROPERTYSET);
		 
		 
		 fichier.add(lines_RELDEFINES);
		 fichier.add(lines_PROPERTYSET);
		 fichier.add(newline);

	 }

		
	}
	
	public static void ecriture(ArrayList<ligneIFC> file,String debut,String fin,String path) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(path, "UTF-8");
    	writer.println(debut);

        for (ligneIFC e : file){
        	String argumentfinal =e.getArgument().toString();
        	argumentfinal=argumentfinal.substring(1,argumentfinal.length()-1);
        	writer.println(e.getHashTag()+" = "+e.getNomFonction()+"("+argumentfinal+");");	
        }
    	writer.println(fin);
        writer.close();
      //  System.out.println("Ecriture dans un new IFC effectué avec un succes certain !"+"\n"+"Le nouveau fichier se situe dans le même dossier que le jar");
		
	}
	public static Commentaire indexcom(ArrayList<Facade> listeFacade,int val){
		int j=-1;
		Commentaire com=null;
			for(Facade e : listeFacade)
				for (Commentaire c : e.getListeCommentaires()){
					j++;
					if(j==val){
						com=c;
						break;
					}
			}
			return com;
		}
	
	// Fonction qui sera appelé lorsque l'utilisateur veut modifier un commentaire manuelement
	public static void modifIFCManuellement(ArrayList<ligneIFC> fichier,int valIFC, String property,String value) throws IOException{
        new ArrayList<ligneIFC>();
        ArrayList<ligneIFC> tmp = new ArrayList<ligneIFC>();
		String tmp1 = null;
		ligneIFC modifline=null;
    	try {
			new Token();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String Hstr=fichier.get(valIFC).getHashTag();
		//System.out.println(fichier.get(valIFC).toString());
		 ArrayList<String> commentaireliste = new ArrayList<String>();
	        commentaireliste.add("'"+property+"'");
	        commentaireliste.add("'"+value+"'");
	        commentaireliste.add("IFCTEXT('"+value+"')");
	        String hash="#"+Integer.toString(maxHashTag(fichier)+1);
	        ligneIFC newline = new ligneIFC(hash,"IFCPROPERTYSINGLEVALUE",commentaireliste);
        for (ligneIFC e : fichier){
        	if (e.getNomFonction().contains("IFCRELDEFINESBYPROPERTIES")){
        		tmp.add(e);
        	}
        }
		 for (ligneIFC l:tmp){
	        	for (String s: l.argument){

	        		if(s.contains("("+Hstr+")")){
	        			tmp1=l.getArgument().get(l.argument.size()-1);
	        			//System.out.println(tmp1);
	        		}
	        	}
	        }
		 if(tmp1!=null){
	        for(ligneIFC t : fichier){
	        	if(t.getHashTag().equals(tmp1)){
	        		modifline=t;
	        		//System.out.println(modifline);
	        	}
	        }
		 
        fichier.remove(modifline);   
        String lastArg = modifline.getArgument().get(modifline.getArgument().size()-1);
        lastArg = lastArg.substring(0, lastArg.length()-1);
        lastArg = lastArg+","+hash+")";
        modifline.remove(modifline.getArgument().size()-1);
        modifline.add(lastArg);
        fichier.add(newline);
        fichier.add(modifline);
       // System.out.println(newline.toString());
      //  System.out.println(modifline.toString());
		 }else{
			 String id1= tmp.get(0).getArgument().get(0);
			 String hashRELDEFINES="#"+Integer.toString(maxHashTag(fichier)+2);
			 String hashPROPERTYSET="#"+Integer.toString(maxHashTag(fichier)+3);
			 String id1_propertyset = null;

			 ArrayList<String> ArgIFCPROPERTYSET = new ArrayList<String>();
			 ArrayList<String> ArgIFCRELDEFINES = new ArrayList<String>();
			 ArgIFCRELDEFINES.add(id1);
			 ArgIFCRELDEFINES.add("#2");
			 ArgIFCRELDEFINES.add("$");
			 ArgIFCRELDEFINES.add("$");
			 ArgIFCRELDEFINES.add("("+Hstr+")");
			 ArgIFCRELDEFINES.add(hashPROPERTYSET);
			 ligneIFC lines_RELDEFINES = new ligneIFC(hashRELDEFINES,"IFCRELDEFINESBYPROPERTIES",ArgIFCRELDEFINES);
			 
			 for (ligneIFC e : fichier){
		        	if (e.getNomFonction().contains("IFCPROPERTYSET")){
		        		id1_propertyset=e.getArgument().get(0);
		        		break;
		        	}
		        	}
			 ArgIFCPROPERTYSET.add(id1_propertyset);
			 ArgIFCPROPERTYSET.add("#2");
			 ArgIFCPROPERTYSET.add("'Propriete'");
			 ArgIFCPROPERTYSET.add("$");
			 ArgIFCPROPERTYSET.add("("+hash+")");
			 ligneIFC lines_PROPERTYSET= new ligneIFC(hashPROPERTYSET,"IFCPROPERTYSET",ArgIFCPROPERTYSET);
			 
			 
			 fichier.add(lines_RELDEFINES);
			 fichier.add(lines_PROPERTYSET);
			 fichier.add(newline);

		 }

		
	}
	
	public static int indiceligne(ArrayList<String> list,String search){
		for(int i=0;i<list.size();i++){
			if(list.get(i).equals(search))
				return i;
		}
		return -1;

		
	}
	

	

}
