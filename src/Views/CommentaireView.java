package Views;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


	public class CommentaireView extends JFrame {
		
		//SearchModel m ;
		
		public CommentaireView(){
			
            this.setLocationRelativeTo(null);
            this.setTitle("Beta Test");
            this.setVisible(true);
            this.setSize(550, 160);
            
            JPanel pan = new JPanel();
            
            JLabel prop = new JLabel("Property");
            JTextField textFieldProperty = new JTextField(45);
            
            JLabel val = new JLabel("Value");
            JTextField textFieldValue = new JTextField(45);
            
            JButton valider = new JButton("Add comment");
            
            this.add(pan);
            
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
    				String propertyText = prop.getText();
    				String valueText = val.getText();
    				
    			}
    			
    		});
		    
		}
		public void update()
		{
			this.setVisible(true);
			this.revalidate();
		}
	}


