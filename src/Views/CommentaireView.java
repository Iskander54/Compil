package Views;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controlers.Controler;
import IFCfile.ligneIFC;


	public class CommentaireView {
		
		JFrame frame1 = new JFrame();
		
		ArrayList<ligneIFC> fichier;
		int indice;
		ArrayList<String> tmp;
		String []entete = null;
		String debut="";
		String fin="";
		String path;
		JList<String> list;
		public CommentaireView(ArrayList<ligneIFC> fichier, int indice, ArrayList<String> tmp, String []entete, String debut, String fin, String path, JList<String> list){
			
			this.fichier = fichier;
			this.indice = indice;
			this.tmp = tmp;
			this.entete = entete;
			this.debut = debut;
			this.path = path;
			this.list = list;
			
			
			
            frame1.setLocationRelativeTo(null);
            frame1.setTitle("Beta Test");
            frame1.setVisible(true);
            frame1.setSize(550, 160);
            

            
            
            JPanel pan = new JPanel();
            
            JLabel prop = new JLabel("Property");
            JTextField textFieldProperty = new JTextField(45);
            
            JLabel val = new JLabel("Value");
            JTextField textFieldValue = new JTextField(45);
            
            JButton valider = new JButton("Add comment");
            
            frame1.add(pan);
            
            // Commentaire property
            pan.add(prop,BorderLayout.NORTH);
            pan.add(textFieldProperty,BorderLayout.NORTH);

            
            // Commentaire value
            pan.add(val,BorderLayout.SOUTH);
            pan.add(textFieldValue,BorderLayout.SOUTH);
           
            // Valider
            pan.add(valider,BorderLayout.SOUTH);
                    
            /*
             * LISTENER
             */

            valider.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				String propertyText = textFieldProperty.getText();
    				String valueText = textFieldValue.getText();
    				try {
    					System.out.println(indice);
						Controler.modifIFCManuellement(fichier, Controler.indiceligne(tmp, list.getModel().getElementAt(list.getSelectedIndex())), propertyText, valueText);
						Controler.ecriture(fichier, entete[0],entete[1],path);
						File IFCfile=new File(path);
						Controler.readerIFC(fichier, debut, fin,IFCfile );
						JOptionPane.showMessageDialog(frame1, "Ecriture dans le fichier IFC : Succes");

						frame1.dispose (); 
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame1, "Ecriture dans le fichier IFC : Echec");
					}
    			}
    			
    		});
		    
		}
		public void update()
		{
			frame1.setVisible(true);
			frame1.revalidate();
		}
	}


