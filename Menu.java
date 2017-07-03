package com.tse.kamoulox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Menu en haut de l'interface graphique.
 */
public class Menu {
	JMenuBar menubar = new JMenuBar();
	// 4 components de menu 
	JMenu navigation1 = new JMenu("Fichier");
	JMenu navigation2 = new JMenu("Calibration");
	JMenu navigation3 = new JMenu("Analyse");
	JMenu navigation4 = new JMenu("Synthese");
	JMenu navigation5 = new JMenu("?");
	// sous components
	JMenuItem item1_nav1 = new JMenuItem("Ouvrir video");
	JMenuItem item2_nav1 = new JMenuItem("Quitter");
	JMenuItem item1_nav2 = new JMenuItem("Segmentation de la balle sur l'image courante");
	JMenuItem item2_nav2 = new JMenuItem("Ligne gauche sortie terrain");
	JMenuItem item3_nav2 = new JMenuItem("But gauche");
	JMenuItem item4_nav2 = new JMenuItem("But droite");
	JMenuItem item5_nav2 = new JMenuItem("Ligne droite sortie terrain");
	JMenuItem item6_nav2 = new JMenuItem("Toutes les lignes");
	JMenuItem item1_nav3 = new JMenuItem("Frame actuelle");
	JMenuItem item2_nav3 = new JMenuItem("Tout");
	
	/**
	 * Construit un menu pour l'application.
	 */
	public Menu(){
		//couleur du menu et du texte.
		menubar.setBackground(new Color(26, 35, 126));
		navigation1.setForeground(Color.WHITE);
		navigation2.setForeground(Color.WHITE);
		navigation3.setForeground(Color.WHITE);
		navigation4.setForeground(Color.WHITE);
		//les sous-components de component1
		item1_nav1.addActionListener(new item1_nav1_Listener());
		navigation1.add(item1_nav1);
		item2_nav1.addActionListener(new videListener());
		navigation1.add(item2_nav1);
		
		//les sous-components de component2
		item1_nav2.addActionListener(new videListener());
		navigation2.add(item1_nav2);
		item2_nav2.addActionListener(new videListener());
		navigation2.add(item2_nav2);
		item3_nav2.addActionListener(new videListener());
		navigation2.add(item3_nav2);
		item4_nav2.addActionListener(new videListener());
		navigation2.add(item4_nav2);
		item5_nav2.addActionListener(new videListener());
		navigation2.add(item5_nav2);
		item6_nav2.addActionListener(new videListener());
		navigation2.add(item6_nav2);
		item1_nav3.addActionListener(new videListener());
		navigation3.add(item1_nav3);
		item2_nav3.addActionListener(new videListener());
		navigation3.add(item2_nav3);
		
		//Listeners
		navigation4.addActionListener(new videListener());
		navigation5.addActionListener(new videListener());
		
		//AjouterNavigation
		menubar.add(navigation1);
		menubar.add(navigation2);
		menubar.add(navigation3);
		menubar.add(navigation4);
		menubar.add(navigation5);
	}
	
	public JMenuBar getMenubar(){
		return this.menubar;
	}
	
	/**
	 * Class qui permet de voir quand l'utilisateur veut effectuer une action (implements ActionListener).
	 */
	public class videListener implements ActionListener{
		/**
		 * Regarde d'où vient l'event et utilise _fireMenuEvent.
		 * @param e
		 * Event venant de l'utilisateur
		 */
		public void actionPerformed(ActionEvent e){
			try {
				if (e.getSource()==item1_nav2) {
					_fireMenuEvent("segmenter");
				} else if (e.getSource()==item2_nav1) {
					_fireMenuEvent("quitter");
				} else if (e.getSource()==item2_nav2) { //ligne de fond gauche
					_fireMenuEvent("fondG");
				} else if (e.getSource()==item3_nav2) { //but gauche
					_fireMenuEvent("butG");
				} else if (e.getSource()==item4_nav2) { //but droite
					_fireMenuEvent("butD");
				} else if (e.getSource()==item5_nav2) { //ligne de fond droite
					_fireMenuEvent("fondD");
				} else if (e.getSource()==item6_nav2) { //tracer toutes les lignes
					_fireMenuEvent("toutesLignes");
				} else if (e.getSource()==item1_nav3) { //etat de la balle
					_fireMenuEvent("etatFrame");
				} else if (e.getSource()==navigation4) { //lancer la synthese
					_fireMenuEvent("synthese");
				} else if (e.getSource()==navigation5) { //aide
					//TODO aide pdf
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Erreur de gestion de l'evenement");
				e1.printStackTrace();
			}
	    }
	}
	
    //ListenerNavigation1Item1
	//afficher un explorateur et retourner le nom et l'adresse absolue de fichier choisi.
	static String PATH = "";
	
	/**
	 * Class qui permet de voir quand l'utilisateur veut ouvrir une video pour afficher un explorateur.
	 */
	public class item1_nav1_Listener implements ActionListener{
		/**
		 * Ouvre un explorateur pour permettre a l'utilisateur de charger une video.
		 * @param e
		 * Event du chargement
		 */
		public void actionPerformed(ActionEvent e) { 
	        JFileChooser jfc=new JFileChooser();  
	        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
	        jfc.showDialog(new JLabel(), "charger");
	        File file=jfc.getSelectedFile();
	        if (file!=null) {
		        if(file.isDirectory()){
		        	JOptionPane.showMessageDialog(null, "Selectionnez une video.");
		        }else if(file.isFile()){
		            PATH = file.getAbsolutePath();
			        try {
						_fireMenuEvent("charger");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Erreur de gestion de l'evenement");
						e1.printStackTrace();
					}
		        }
	        }
	    }
	}

	// La suite du code est la création de l'evenement de choix d'une video par l'utilisateur
    private ArrayList<MenuListener> _listeners = new ArrayList<MenuListener>(); // list des listeners
    
    /**
     * Interface pour les events/listeners du menu.
     */
	public interface MenuListener 
	{
		/**
		 * Permet de recevoir les events.
		 * @param event
		 * D'ou vient l'event
		 * @throws IOException
		 */
	    public void MenuReceived( MenuEvent event ) throws IOException;
	}
	
	/**
	 * Sert a etre envoye aux listeners.
	 */
	public class MenuEvent extends EventObject {
		private static final long serialVersionUID = 1L;
		private String path;
		
	    /**
	     * L'event recu par les listeners.
	     * @param source
	     * Objet d'ou vient l'event
	     * @param path
	     * Chemin de la video
	     */
	    public MenuEvent( Object source, String path ) {
	        super( source );
	        this.path = path;
	    }
	    public String path() {
	        return path;
	    }
	    
	}
	
	/**
	 * rajoute des listeners.
	 * @param l
	 * listener a rajouter
	 */
	public synchronized void addMenuListener( MenuListener l ) {
        _listeners.add( l );
    }
	
    /**
     * Envoie l'event a tous les listeners.
     * @param nom
     * Nom de l'event
     * @throws IOException
     */
    private synchronized void _fireMenuEvent(String nom) throws IOException {
        MenuEvent menue = new MenuEvent(nom, PATH );
        Iterator<MenuListener> listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ( (MenuListener) listeners.next() ).MenuReceived( menue );
        }
    }
}