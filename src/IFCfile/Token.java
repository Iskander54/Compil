package IFCfile;

import java.io.IOException;
import java.util.ArrayList;

public class Token {
	
	ArrayList<String> tokens = new ArrayList<String>();
	
	public Token() throws IOException{
		tokens.add("IFCWALLSTANDARDCASE");
		tokens.add("IFCBUILDING");
		tokens.add("IFCBUILDINGELEMENTPROXY");
		tokens.add("IFCRELAGGREGATES");
		tokens.add("IFCSITE");


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
