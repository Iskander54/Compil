package Views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Interface.Observable;
import Interface.Observer;

import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import Controlers.Controler;
import IFCfile.Commentaire;
import IFCfile.Facade;
import IFCfile.ligneIFC;

import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class AccueilView extends ImagePanel implements Observer {
	JButton browser = new JButton("Importer le fichier IFC");
	JButton handwrinting= new JButton("Ecrire Commentaire à la main");
	JButton browsercomm = new JButton("Importer les commentaires");
	JButton reset = new JButton("Reset");
	JButton match = new JButton("Lier");
	JPanel pan = new JPanel(new GridLayout(1,3));
	JPanel panGrid = new JPanel(new GridLayout(1,2));
	JPanel bottompan = new JPanel(new GridLayout(1,5));
	JPanel PartLeft = new JPanel();
	JPanel PartRight = new JPanel();
	int csv_file_load=0;
	int ifc_file_load=0;

	Controler controler;
	private Component frame;
	String path;
	
	ArrayList<Facade> listeFacade = new ArrayList<Facade>();
	ArrayList<ligneIFC> fichier = new ArrayList<ligneIFC>();
	String debut="";
	String fin="";
	String []entete;
	
	JScrollPane scrollRight;
	JScrollPane scrollLeft;
	JList<String> list;
	JList<String> list1;



	


	public AccueilView(Controler c) {
		
		super("./images/fond");
		controler = c;

		browser.setName("part1");
		browser.setOpaque(false);
		browser.setContentAreaFilled(false);
		browser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					if(ifc_file_load==0){
					File IFCfile= browser("IFC file");
					path=IFCfile.getAbsolutePath();
					entete=Controler.readerIFC(fichier, debut, fin, IFCfile);
					System.out.println(entete[1]+entete[0]);

					DefaultListModel<String> model = new DefaultListModel<>();
					for(ligneIFC j : fichier){
						model.addElement("Fonction : "+j.getNomFonction()+" Arguments :"+j.getArgument());
						
					}
					
					list = new JList<>( model );
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list.setLayoutOrientation(JList.VERTICAL);
					list.setVisibleRowCount(-1);
					scrollLeft = new JScrollPane(list);
					
						panGrid.add(scrollLeft,BorderLayout.EAST);
						System.out.println("all guuuuud");
						update();
						ifc_file_load++;
						
					}else{
						JOptionPane.showMessageDialog(frame, " Un fichier IFC à déjà été charger. Si vous voulez le changer, cliquer sur resets");



					
					}
				}catch(java.lang.NullPointerException | IOException i){}

			}
		});
		
		browsercomm.setName("part2");
		browsercomm.setOpaque(false);
		browsercomm.setContentAreaFilled(false);
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
							

			            	model1.addElement("\t : " + c.getCommentaire() + " ( "+ c.getPositionX()+ " , " + c.getPositionY() + " )\n");
						   }
		           
		        }

					list1 = new JList<>( model1 );
					list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		handwrinting.setOpaque(false);
		handwrinting.setContentAreaFilled(false);
		handwrinting.addMouseListener(null);
		
		reset.setName("part4");
		reset.setOpaque(false);
		reset.setContentAreaFilled(false);
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
		
		match.setName("part5");
		match.setOpaque(false);
		match.setContentAreaFilled(false);
		match.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				try{
		           
		        
					
				System.out.println(fichier.get(list.getSelectedIndex()).getNomFonction());
				System.out.println(Controler.indexcom(listeFacade, list1.getSelectedIndex()-2).getCommentaire());
				Controler.modifIFC2(fichier, listeFacade, list.getSelectedIndex(), list1.getSelectedIndex()-2);
				panGrid.remove(scrollLeft);
				list.removeAll();
				list.clearSelection();
				

		
					Controler.ecriture(fichier, entete[0],entete[1],path);
					File IFCfile=new File(path);
					entete=Controler.readerIFC(fichier, debut, fin,IFCfile );
					System.out.println(entete[1]+entete[0]);

					DefaultListModel<String> model = new DefaultListModel<>();
					
					list = new JList<>( model );
					for(ligneIFC j : fichier){
						model.addElement("Fonction : "+j.getNomFonction()+" Arguments :"+j.getArgument());
						
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
	
		

		bottompan.add(match,BorderLayout.CENTER);
		
		bottompan.setOpaque(false);
		bottompan.add(reset,BorderLayout.EAST);
		add(bottompan,BorderLayout.SOUTH);
		
		pan.setOpaque(false);
		pan.add(browser);
		pan.add(browsercomm);
		pan.add(handwrinting);
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
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		return selectedFile;
	}



	@Override
	public void update() {
		controler.GetMainWindow().setContentPane(this);
		controler.GetMainWindow().update();
	}
}
