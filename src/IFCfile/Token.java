package IFCfile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Token {
	
	ArrayList<String> tokens = new ArrayList<String>();
	String path = "token_ajout_commentaire/token_function";
	
	public Token() throws IOException{
		try{
			String pathFileHasToken = this.path;
			BufferedReader fileToken = new BufferedReader(new FileReader(pathFileHasToken));
			String reader;
			while((reader = fileToken.readLine())!= null){
				if(reader != "" || reader != "/n"){
					this.tokens.add(reader);
				}
			}

			}
			catch (FileNotFoundException e)
			{
			   System.out.println("Le fichier contenant les Tokens est introuvable");
			}
	}
	
	public ArrayList<String> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}
	
	public boolean containToken(String mot){
		boolean contain = false;
		for(String e : this.tokens){
			if(e.equals(mot)){
				contain = true;
			}
		}
		return contain;
	}
}
