package IFCfile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

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
	
	@SuppressWarnings("resource")
	public static String[] readerIFC(ArrayList<ligneIFC> file,String debut, String fin,FileReader fileIFC) throws IOException{
        BufferedReader bfileIFC = new BufferedReader(fileIFC);
        String ligneIFC = bfileIFC.readLine();
        int i=0;
        
        // On parcours les lignes
        while(ligneIFC!=null) {
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
        System.out.println("Fichier IFC téléchargé avec succès !");
        return(entete);

       
	}
	
	
	public static void loader(FileReader ifcpath,String debut,String fin,ArrayList<ligneIFC> fichier ) throws IOException, URISyntaxException{

        try {
        	readerIFC(fichier,debut,fin,ifcpath);
    		modifIFC(fichier);
            ecriture(fichier,debut,fin);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	@SuppressWarnings("resource")
	public static void modifIFC(ArrayList<ligneIFC> fichier) throws IOException, URISyntaxException{
        ArrayList<ligneIFC> travail = new ArrayList<ligneIFC>();
		Scanner sc = new Scanner(System.in);
        ArrayList<ligneIFC> tmp = new ArrayList<ligneIFC>();
		String str=null;
		String tmp1 = null;
		ligneIFC modifline=null;
		int numeroligne=0;
		int nbl=0;
        int cond1=0;
        
        Token token = new Token();
       
        while( cond1==0){
		System.out.println("A quel élément IFC voulez-vous ajouter un commentaire ? "+token.getTokens() +"\n");
		str=sc.next();
		if(token.containToken(str)){
			cond1++;
		}else{
			System.out.println("Saisie incorrecte, vous avez le choix entre les élement entre crochet");
		}
        }

		
		
        for (ligneIFC e : fichier){
        	if (e.getNomFonction().contains(str)){
        		travail.add(e);
        	}
        	if (e.getNomFonction().contains("IFCRELDEFINESBYPROPERTIES")){
        		tmp.add(e);
        	}
        }
        do{
            for (ligneIFC e : travail){
            	System.out.println(travail.indexOf(e)+1+" Hashtag : "+e.getHashTag() + " , Fonction : "+ e.getNomFonction() + " , Argument : "+ e.getArgument());
            	System.out.println();
            	System.out.println(travail.size());
            	nbl++;
            }
            
            System.out.println("Quel est l'indice du mur auquel voulez-vous ajouter un commentaire ? \n");
            Scanner sc1 = new Scanner(System.in);
            try{
    		numeroligne=sc1.nextInt();
    		if(numeroligne>0 && numeroligne<=nbl){
    		cond1++;
    		}else {
    			System.out.println("Veuillez saisir un chiffre entre 1 et "+nbl);
    		}
            }catch(java.util.InputMismatchException e){
            	System.out.println("Veuillez saisir un chiffre");
            }
            sc1.reset();
            nbl=0;
            }while(cond1==1);
        
            numeroligne=numeroligne-1;
		String Hstr=travail.get(numeroligne).getHashTag();
	
		System.out.println("Commentaire [Property] :");
		sc.nextLine();
		String com1=sc.nextLine();
		System.out.println("Commentaire [Value] :");
		String com=sc.nextLine();

        
        for (ligneIFC l:tmp){
        	for (String s: l.argument){
        		if(s.contains("("+Hstr+")")){
        			tmp1=l.getArgument().get(l.argument.size()-1);
        		}
        	}
        }
        for(ligneIFC t : fichier){
        	if(t.getHashTag().contains(tmp1)){
        		modifline=t;
        	}
        }
        fichier.remove(modifline);


        ArrayList<String> commentaire = new ArrayList<String>();
        commentaire.add("'"+com1+"'");
        commentaire.add("'"+com1+"'");
        commentaire.add("IFCTEXT('"+com+"')");
        String hash="#"+Integer.toString(maxHashTag(fichier)+1);
        ligneIFC newline = new ligneIFC(hash,"IFCPROPERTYSINGLEVALUE",commentaire);
        
        String lastArg = modifline.getArgument().get(modifline.getArgument().size()-1);
        lastArg = lastArg.substring(0, lastArg.length()-1);
        lastArg = lastArg+","+hash+")";
        modifline.remove(modifline.getArgument().size()-1);
        modifline.add(lastArg);
        fichier.add(newline);
        fichier.add(modifline);
		
	}
	
	@SuppressWarnings("resource")
	public static void modifIFC2(ArrayList<ligneIFC> fichier,ArrayList<Facade> listeFacade) throws IOException, URISyntaxException{
        ArrayList<ligneIFC> travail = new ArrayList<ligneIFC>();
		Scanner sc = new Scanner(System.in);
        ArrayList<ligneIFC> tmp = new ArrayList<ligneIFC>();
		String str=null;
		String tmp1 = null;
		ligneIFC modifline=null;
        int cond1=1;
        int numeroligne=0;
        int nfacade=0;
        int ncom=0;
        int cond=0;
        int nbl=0;
        
        
    	System.out.println("--------- Choix du commentaire ----------");
    	System.out.println();
    	do{
    		Scanner sc5 = new Scanner(System.in);
		System.out.println("Quelle est le numéro de la façade concernée ?");
		for (Facade f : listeFacade){
			   System.out.println(f.nom);
		}
		try{
		nfacade=sc5.nextInt();

		if(nfacade>0 && nfacade<=listeFacade.size()){
			cond1=2;

		}else{
			System.out.println("Saisie incorrect : vous devez saisir un chiffre entre 1 et "+listeFacade.size());
			System.out.println(cond1);
		}
		}catch (java.util.InputMismatchException e){
			System.out.println("Saisie incorrecte, veuillez saisir un chiffre");
		} sc5.reset();

    	}while(cond1==1);
    	
    	nfacade=nfacade-1;
    	
    	do{
    		Scanner sc5 = new Scanner(System.in);
		System.out.println("Quelle commentaire voulez-vous ajouter ? ");	
		for(Commentaire c: listeFacade.get(nfacade).getListeCommentaires()){
			System.out.println(listeFacade.get(nfacade).getListeCommentaires().indexOf(c)+1+" Commentaires : "+c.getCommentaire());
		}
    	try {
		ncom=sc5.nextInt();
		if(ncom>0 && ncom<=listeFacade.get(nfacade).getListeCommentaires().size()){
			cond1=3;
			
		}else{
			System.out.println("Saisie incorrect : vous devez saisir un chiffre entre 1 et "+listeFacade.get(nfacade).getListeCommentaires().size());
		}
    	}catch(java.util.InputMismatchException e){
			System.out.println("Saisie incorrecte, veuillez saisir un chiffre");
    		
    	}sc5.reset();

		}while(cond1==2);
    	ncom=ncom-1;
    	
    	
    	System.out.println("--------- Choix de l'élement IFC à modifié ----------");
    	System.out.println();
    	
    	Token token = new Token();
    	
        while( cond==0){
		System.out.println("A quel élément IFC voulez-vous ajouter un commentaire ?" + token.getTokens() + "\n");
		str=sc.next();
		if(token.containToken(str)){
			cond++;
		}else{
			System.out.println("Saisie incorrecte, vous avez le choix entre les élement entre crochet");
		}

        }
		
        for (ligneIFC e : fichier){
        	if (e.getNomFonction().contains(str)){
        		travail.add(e);
        	}
        	if (e.getNomFonction().contains("IFCRELDEFINESBYPROPERTIES")){
        		tmp.add(e);
        	}
        }
        do{
        for (ligneIFC e : travail){
        	System.out.println(travail.indexOf(e)+1+" Hashtag : "+e.getHashTag() + " , Fonction : "+ e.getNomFonction() + " , Argument : "+ e.getArgument());
        	nbl++;
        }
        
        System.out.println("Quel est l'indice du mur auquel voulez-vous ajouter un commentaire ? \n");
        Scanner sc1 = new Scanner(System.in);
        try{
		numeroligne=sc1.nextInt();
		if(numeroligne>=1 && numeroligne<=nbl){
		cond1++;
		System.out.println("On passe le if "+cond1);
		}else {
			System.out.println("Veuillez saisir un chiffre entre 1 et "+travail.size());
		}
        }catch(java.util.InputMismatchException e){
        	System.out.println("Veuillez saisir un chiffre");
        }
        sc1.reset();

        }while(cond1==3);
        System.out.println("valeur cond1 "+cond1);
        
        numeroligne=numeroligne-1;
		String Hstr=travail.get(numeroligne).getHashTag();
		

        
        for (ligneIFC l:tmp){
        	for (String s: l.argument){
        		if(s.contains("("+Hstr+")")){
        			tmp1=l.getArgument().get(l.argument.size()-1);
        		}
        	}
        }
        for(ligneIFC t : fichier){
        	if(t.getHashTag().contains(tmp1)){
        		modifline=t;
        	}
        }
        fichier.remove(modifline);
        
        Commentaire commentaire=listeFacade.get(nfacade).listeCommentaires.get(ncom);
        
        String com1=commentaire.getCommentaire();
        String com=commentaire.getCommentaire();
        ArrayList<String> commentaireliste = new ArrayList<String>();
        commentaireliste.add("'"+com1+"'");
        commentaireliste.add("'"+com1+"'");
        commentaireliste.add("IFCTEXT('"+com+"')");
        String hash="#"+Integer.toString(maxHashTag(fichier)+1);
        ligneIFC newline = new ligneIFC(hash,"IFCPROPERTYSINGLEVALUE",commentaireliste);
        
        String lastArg = modifline.getArgument().get(modifline.getArgument().size()-1);
        lastArg = lastArg.substring(0, lastArg.length()-1);
        lastArg = lastArg+","+hash+")";
        modifline.remove(modifline.getArgument().size()-1);
        modifline.add(lastArg);
        fichier.add(newline);
        fichier.add(modifline);
		
	}
	
	public static void ecriture(ArrayList<ligneIFC> file,String debut,String fin) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter("IFCmodif.ifc", "UTF-8");
    	writer.println(debut);

        for (ligneIFC e : file){
        	String argumentfinal =e.getArgument().toString();
        	argumentfinal=argumentfinal.substring(1,argumentfinal.length()-1);
        	writer.println(e.getHashTag()+" = "+e.getNomFonction()+"("+argumentfinal+");");	
        }
    	writer.println(fin);
        writer.close();
        System.out.println("Ecriture dans un new IFC effectué avec un succes certain !"+"\n"+"Le nouveau fichier se situe dans le même dossier que le jar");
		
	}
	
	public static void readerCSV(String path,ArrayList<Facade> listeFacade) throws IOException{
		
		
		  // String chemin = "/home/etudiants/cluchagu1u/2A/PIDR/export_commentaire_2017-01-31.csv";
		   BufferedReader fichierCSV = new BufferedReader(new FileReader(path));
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
		   System.out.println("Liste des commentaires téléchargée avec succès !"+"\n");
	
		
		
	
	}
	
	

		
	
	/*public static void main(String args[]) throws IOException{
		ArrayList<Facade> listeFacade = new ArrayList<Facade>();
		ArrayList<ligneIFC> fichier = new ArrayList<ligneIFC>();
		String debut="";
		String fin="";
		int cond1=0; //valeur qui check les entrée clavier
		String IFCpath="";
		int entre=0; //choix numero1 
		int path=0;
		String CSVpath="";
		String en_tete[] = null;
//Scanner sc1= new Scanner(System.in);
		


		System.out.println("---------------------------------");
		System.out.println("|                               |");
		System.out.println("|                               |");
		System.out.println("|                               |");
		System.out.println("|   Welcome into IFC modifier   |");
		System.out.println("|                               |");
		System.out.println("|                               |");
		System.out.println("|                               |");
		System.out.println("---------------------------------");
		
		do{
		System.out.println("Veuillez entrer le chemin du fichier IFC à modifier :");
		Scanner sc1 = new Scanner(System.in);
		try{
		IFCpath=sc1.nextLine();
		
		//String IFCpath="/Users/AlexLo/Downloads/ITE+.IFC";
		/*en_tete=readerIFC(fichier,debut,fin,IFCpath);
		System.out.println(debut);
		System.out.println(fin);
		path++;
		} catch(java.io.FileNotFoundException e){
			System.out.println("Le chemin inséré n'est pas correcte, réessayez");
		}
		
	}while(path==0);
	
		do{
			Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous :\n"+" 1) entrer un commentaire à la main \n"+" 2) le charger à partir d'un fichier .csv ?");
		entre=sc.nextInt();
		if(entre==1 || entre==2){
			cond1++;
			//entre=Integer.parseInt(Sentre);
		}else{
			System.out.println("Saisie incorrect : vous devez saisir 1 ou 2");
		}
		sc.reset();
		}while(cond1==0);
		
		if(entre==1){
    		modifIFC(fichier);
            ecriture(fichier,en_tete[0],en_tete[1]);	
		} else if (entre==2){
			
		do{
			System.out.println("Veuillez entrer le chemin du fichier des commentaires à ajouter :");
			Scanner sc2 = new Scanner(System.in);
			try{
				CSVpath=sc2.next();
				readerCSV(CSVpath,listeFacade);

				modifIFC2(fichier,listeFacade);
		        ecriture(fichier,debut,fin);
		        path++;
			}catch(java.io.FileNotFoundException e){
				System.out.println("Le chemin inséré n'est pas correcte, réessayez");

			}
		}while(path==1);
		
		
		
	
	


	}else{}
		}
	}*/
}

