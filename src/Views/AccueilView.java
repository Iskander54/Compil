package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Controlers.Controler;
import IFCfile.Commentaire;
import IFCfile.Facade;
import IFCfile.Token;
import IFCfile.ligneIFC;
import Interface.Observer;

public class AccueilView extends ImagePanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton browser = new JButton("Importer le fichier IFC");
	JButton handwrinting= new JButton("Ecrire Commentaire à la main");
	Boolean commentManualy = false;
	JButton browsercomm = new JButton("Importer les commentaires");
	JButton reset = new JButton("Reset");
	JButton match = new JButton("Lier");
	JButton token = new JButton("Token");
	JPanel pan = new JPanel(new GridLayout(1,3));
	JPanel panGrid = new JPanel(new GridLayout(1,2));
	JPanel bottompan = new JPanel(new GridLayout(1,5));
	JPanel PartLeft = new JPanel();
	JPanel PartRight = new JPanel();
	int csv_file_load=0;
	int ifc_file_load=0;
	int indice=0;

	Controler controler;
	private Component frame;
	String path;
	
	ArrayList<Facade> listeFacade = new ArrayList<Facade>();
	ArrayList<ligneIFC> fichier = new ArrayList<ligneIFC>();
	ArrayList<String> tmp = new ArrayList<String>();
	ArrayList<String> tmpcom = new ArrayList<String>();


	String debut="";
	String fin="";
	String []entete;
	
	JScrollPane scrollRight;
	JScrollPane scrollLeft;
	JList<String> list;
	JList<String> list1;
	Token t=null;


	


	public AccueilView(Controler c) {
		
		super("./images/fond");
		controler = c;
		browser.setName("part4");
		browser.setBackground(Color.WHITE);
		browser.setContentAreaFilled(false);
		browser.setOpaque(true);
		t = new Token();
        try {
			t.chargingTokens();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		
		browser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					if(ifc_file_load==0){
					File IFCfile= browser("IFC file");
					path=IFCfile.getAbsolutePath();
					entete=Controler.readerIFC(fichier, debut, fin, IFCfile);
					//System.out.println(entete[1]+entete[0]);

					DefaultListModel<String> model = new DefaultListModel<>();
				
					
					for(ligneIFC j : fichier){
						
						tmp.add("Fonction : "+j.getNomFonction()+" Arguments :"+j.getArgument());
						if(t.containToken(j.getNomFonction())){ // PERMET DE VERIFIER SI LA LIGNE POSSEDE UN DES TOKENS
							model.addElement("Fonction : "+j.getNomFonction()+" Arguments :"+j.getArgument());
						}
					}
					
					
					
					list = new JList<>( model );
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list.setLayoutOrientation(JList.VERTICAL);
					list.setVisibleRowCount(-1);
					scrollLeft = new JScrollPane(list);
					
						panGrid.add(scrollLeft,BorderLayout.EAST);
						//System.out.println("all guuuuud");
						update();
						ifc_file_load++;
						
						// On change le boutton
						commentManualy = true;
						handwrinting.setBackground(Color.WHITE);
						handwrinting.setEnabled(true);
						// On change le boutton 
						
					}else{
						JOptionPane.showMessageDialog(frame, " Un fichier IFC à déjà été charger. Si vous voulez le changer, cliquer sur resets");



					
					}
				}catch(java.lang.NullPointerException | IOException i){}

			}
		});
		
		browsercomm.setName("part4");
		browsercomm.setBackground(Color.WHITE);
		browsercomm.setContentAreaFilled(false);
		browsercomm.setOpaque(true);
		browsercomm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					if(csv_file_load==0){
					File comm= browser("CSV file");
					Controler.readerCSV(comm, listeFacade);
					DefaultListModel<String> model1 = new DefaultListModel<>();
					for(Facade j : listeFacade){
						model1.addElement("\t\t\t\t\t\t\t\t               		 "+j.getNom());
						for (Commentaire c : j.getListeCommentaires()){
							tmpcom.add("\t : " + c.getCommentaire() + " ( "+ c.getPositionX()+ " , " + c.getPositionY() + " )\n");

			            	model1.addElement("\t : " + c.getCommentaire() + " ( "+ c.getPositionX()+ " , " + c.getPositionY() + " )\n");
						   }
		           
		        }

					list1 = new JList<>( model1 );
					list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					list1.setLayoutOrientation(JList.VERTICAL);
					list1.setVisibleRowCount(-1);
					scrollRight = new JScrollPane(list1);

					
						panGrid.add(scrollRight,BorderLayout.WEST);
						update();
						csv_file_load++;
						
					}else{
						JOptionPane.showMessageDialog(frame, " Un fichier de Commentaire à déjà été charger. Si vous voulez le changer, cliquer sur resets");


					
					}


					



				}catch(java.lang.NullPointerException i){} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});
		
		handwrinting.setName("part3");

		handwrinting.setBackground(Color.LIGHT_GRAY);
		handwrinting.setContentAreaFilled(false);
		handwrinting.setOpaque(true);
		
		handwrinting.setEnabled(false);
		
		
		handwrinting.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try{
				if(commentManualy){
					/*
					 * On ajoute la vue de commentaire manuelle au model
					 */
					
					indice=Controler.indiceligne(tmp,list.getModel().getElementAt(list.getSelectedIndex()));
					CommentaireView f = new CommentaireView(fichier,indice, tmp, entete,debut,fin, path, list);
					f.update();
				}
				}catch(java.lang.ArrayIndexOutOfBoundsException j){
					JOptionPane.showMessageDialog(frame, " Veuillez selectionner un élément IFC");

				}
			}
			
		});
		
		
		
		reset.setName("part4");
		reset.setBackground(Color.WHITE);
		reset.setContentAreaFilled(false);
		reset.setOpaque(true);
		
		
		reset.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fichier.clear();
				listeFacade.clear();
				panGrid.removeAll();
				csv_file_load=0;
				ifc_file_load=0;
				update();

			}
			
		});
		
		match.setName("part4");
		match.setBackground(Color.WHITE);
		match.setContentAreaFilled(false);
		match.setOpaque(true);
		match.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){


				try{
					
				indice=Controler.indiceligne(tmp,list.getModel().getElementAt(list.getSelectedIndex()));
				//System.out.println(fichier.get(indice)+" ca bug ap bro");
				//System.out.println(Controler.indexcom(listeFacade, list1.getSelectedIndex()-2).getCommentaire());
				//System.out.println(indice);
				int []com=list1.getSelectedIndices();
				int indicecom;
				for(int i=0;i<com.length;i++){
				//System.out.println(com[i]);
				}
				for(int i=0;i<com.length;i++){

				indicecom=Controler.indiceligne(tmpcom, list1.getModel().getElementAt(com[i]));
			//	System.out.println(indicecom);
				Controler.modifIFC2(fichier, listeFacade,indice,indicecom);
				}
				panGrid.remove(scrollLeft);
				list.removeAll();
				list.clearSelection();
				

		
					Controler.ecriture(fichier, entete[0],entete[1],path);
					File IFCfile=new File(path);
					entete=Controler.readerIFC(fichier, debut, fin,IFCfile );
					//System.out.println(entete[1]+entete[0]);
					

					DefaultListModel<String> model = new DefaultListModel<>();

					
					Token t = null;
					try {
						t = new Token();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tmp.clear();
		
					for(ligneIFC j : fichier){
						tmp.add("Fonction : "+j.getNomFonction()+" Arguments :"+j.getArgument());
						if(t.containToken(j.getNomFonction())){ // PERMET DE VERIFIER SI LA LIGNE POSSEDE UN DES TOKENS
							model.addElement("Fonction : "+j.getNomFonction()+" Arguments :"+j.getArgument());
						}
					}

					
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list.setLayoutOrientation(JList.VERTICAL);
					list.setVisibleRowCount(-1);
					scrollLeft = new JScrollPane(list);
					
						panGrid.add(scrollLeft,BorderLayout.EAST);
						update();
						ifc_file_load++;
				
						JOptionPane.showMessageDialog(frame, "IFC file modifié avec succès.");


				}catch(java.lang.NullPointerException | IOException p){
					JOptionPane.showMessageDialog(frame, " Il faut selectionner une ligne dans l'IFC et une ligne dans le CSV");

				}
				

				
				
			}
		});
		
		token.setName("part4");
		token.setBackground(Color.WHITE);
		token.setContentAreaFilled(false);
		token.setOpaque(true);
		
		token.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				TokenView f;
				try {
					f = new TokenView(t);
					f.update();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			});
	
		

		bottompan.add(match,BorderLayout.CENTER);
		
		bottompan.setOpaque(false);
		bottompan.add(reset,BorderLayout.EAST);
		add(bottompan,BorderLayout.SOUTH);
		
		pan.setOpaque(false);
		pan.add(browser);
		pan.add(browsercomm);
		pan.add(handwrinting);
		pan.add(token);
		add(pan,BorderLayout.NORTH);
		
		panGrid.setOpaque(false);
		add(panGrid,BorderLayout.CENTER);	


	}
	
	


	private File browser(String title) {
		File selectedFile=null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this.frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			//System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		return selectedFile;
	}



	@Override
	public void update() {
		controler.GetMainWindow().setContentPane(this);
		controler.GetMainWindow().update();
	}
}
