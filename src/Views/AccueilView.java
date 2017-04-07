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
import java.util.ArrayList;

import Interface.Observable;
import Interface.Observer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import Controlers.Controler;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;

public class AccueilView extends ImagePanel implements Observer {
	JButton browser = new JButton("Importer le fichier IFC");
	JButton handwrinting= new JButton("Ecrire Commentaire à la main");
	JButton browsercomm = new JButton("Importer les commentaires");
	JPanel pan = new JPanel(new GridLayout(1,3));
	JPanel panGrid = new JPanel(new GridLayout(1,2));
	JPanel PartLeft = new JPanel();
	JPanel PartRight = new JPanel();
	JButton test1 = new JButton("test1");
	JButton test2 = new JButton("test2");
	

	Controler controler;
	private Component frame;
	private final JSplitPane splitPane = new JSplitPane();
	private JList list = new JList();
	private final JList list_1 = new JList();
	ArrayList<String> fichier = new ArrayList<String>();
	


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
					FileReader IFcfile= browser();

				}catch(java.lang.NullPointerException i){}

			}
		});
		
		browsercomm.setName("part2");
		browsercomm.setOpaque(false);
		browsercomm.setContentAreaFilled(false);
		browsercomm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					FileReader Comfile= browser();
					

				}catch(java.lang.NullPointerException i){}
			}
			});
		
		handwrinting.setName("part3");
		handwrinting.setOpaque(false);
		handwrinting.setContentAreaFilled(false);
		handwrinting.addMouseListener(null);
		
		pan.setOpaque(false);
		pan.add(browser);
		pan.add(browsercomm);
		pan.add(handwrinting);
		add(pan,BorderLayout.NORTH);
		
		panGrid.setOpaque(false);
		PartLeft.setOpaque(false);
		PartRight.setOpaque(false);
		
		// Panel de gauche
		PartRight.setOpaque(true);
		DefaultListModel<String> model = new DefaultListModel<>();
		for(int i=1;i<150;i++){
			model.addElement("droite pd n° "+i);
		}
		JList<String> list = new JList<>( model );
		JList<String> list1 = new JList<>( model );


		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane scrollRight = new JScrollPane(list);
		JScrollPane scrollLeft = new JScrollPane(list1);

		panGrid.add(scrollRight);
		panGrid.add(scrollLeft);
		// Panel de droite

		add(panGrid,BorderLayout.CENTER);

		
		splitPane.setRightComponent(list_1);

	}
	
	private void addIFCligne(FileReader file){
		
	}
	
	


	private FileReader browser() {
		FileReader IFCfile=null;
		File selectedFile=null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this.frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		try {
			return IFCfile=new FileReader(selectedFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	class BouttonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (((JButton) e.getSource()).getName()) {
			case "browser": 
			{
				FileReader IFcfile= browser();
				fichier.add("prout");
				DefaultListModel listModel = new DefaultListModel();
				listModel.addElement("Jane Doe");
				listModel.addElement("John Smith");
				listModel.addElement("Kathy Green");
				list = new JList(listModel);
				add(splitPane, BorderLayout.CENTER);
				
				splitPane.setLeftComponent(list);
				System.out.println("done");
			}
			case "part2":
			case "part3":

				break;
			default:
				break;
			}
		}
	}

	@Override
	public void update() {
		controler.GetMainWindow().setContentPane(this);
		controler.GetMainWindow().update();
	}
}
