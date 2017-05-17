package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import IFCfile.Token;

public class TokenView {
	JFrame frame1 = new JFrame();
	DefaultListModel<String> model = new DefaultListModel<>();
	


	
	public TokenView(Token token) throws IOException{
		
        frame1.setLocationRelativeTo(null);
        frame1.setTitle("Token Handler");
        frame1.setVisible(true);
        frame1.setSize(500, 300);
        token.chargingTokens();
        ArrayList<String> tokens = token.getTokens();

        
        JPanel pan = new JPanel(new GridLayout(3,2));
        JPanel panh = new JPanel(new GridLayout(2,1));

        JButton prop = new JButton("Ajouter");
        JButton rm = new JButton("Supprimer");
        rm.setPreferredSize(new Dimension(10, 10));
        JTextField textFieldProperty = new JTextField(45);
		//DefaultListModel<String> model = new DefaultListModel<>();
		for(int j=0;j<tokens.size();j++){
			model.addElement(tokens.get(j));
		}
		JList<String> list1 = new JList<>( model );
		list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list1.setLayoutOrientation(JList.VERTICAL);
		list1.setVisibleRowCount(-1);
		JScrollPane scroll = new JScrollPane(list1);
		

        panh.add(textFieldProperty,BorderLayout.EAST);

		 panh.add(prop,BorderLayout.WEST);
		pan.add(panh,BorderLayout.NORTH);
			pan.add(scroll,BorderLayout.CENTER);
			pan.add(rm,BorderLayout.SOUTH);

	        frame1.add(pan);
	        
	        rm.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				int []indice=list1.getSelectedIndices();
    				for(int i=0;i<indice.length;i++){
    					tokens.remove(model.getElementAt(indice[i]));
    				}
    				load(token.getTokens());
    				try {
						token.updateFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				update();
    				
    			}
	        });
	        
	        prop.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				tokens.add(textFieldProperty.getText());
    				load(token.getTokens());
    				try {
						token.updateFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				update();
    			}
	        });
	        
	        


	}
	@SuppressWarnings("unused")
	public void load(ArrayList<String> tokens){
		model.clear();
		for(int j=0;j<tokens.size();j++){
			model.addElement(tokens.get(j));
		}
		JList<String> list1 = new JList<>( model );
		list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list1.setLayoutOrientation(JList.VERTICAL);
		list1.setVisibleRowCount(-1);
		JScrollPane scroll = new JScrollPane(list1);
		update();

		
	}
	public void update()
	{
		frame1.setVisible(true);
		frame1.revalidate();
	}

}
