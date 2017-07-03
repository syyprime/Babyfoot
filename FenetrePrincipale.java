package com.tse.kamoulox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.opencv.core.Core;

import com.tse.kamoulox.InterfaceVideo.RepaintEvent;
import com.tse.kamoulox.InterfaceVideo.RepaintListener;
import com.tse.kamoulox.Menu.MenuEvent;
import com.tse.kamoulox.Menu.MenuListener;

/**
 * Interface graphique totale.
 */
public class FenetrePrincipale extends JFrame implements MenuListener, RepaintListener {
	private static final long serialVersionUID = 1L;
	private InterfaceVideo interfaceVideo;
	private Menu menu = new Menu();
	private VideoCharger vid = new VideoCharger();  
	
	/**
	 * Cree l'interface graphique.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public FenetrePrincipale() throws IOException, InterruptedException { // Cree et affiche la fenetre
		interfaceVideo = new InterfaceVideo();
		menu.addMenuListener(this);
		interfaceVideo.addRepaintListener(this);
		
		Container content = this.getContentPane();
		content.setBackground(new Color(171, 169, 170));
		content.add(menu.getMenubar(),BorderLayout.NORTH);
		content.add(interfaceVideo, BorderLayout.SOUTH);
	}
	
	/**
	 * Repaint l'interface lorsque qu'il y a eu un changement. Tout repeindre permet de ne pas avoir le menu sous le reste.
	 */
	public void RepaintReceived(RepaintEvent event) { // repaint de la fenetre
		this.repaint();
	}
	
	/**
	 * Gere les differents events lies au menu en effectuant les actions demandees.
	 * @param event
	 * L'event en question
	 * @throws IOException
	 * @throws HeadlessException
	 */
	public void MenuReceived(MenuEvent event) throws IOException, HeadlessException { // event menu
		if (event.getSource().equals("charger")) {
			try {
				this.vid.charger(event.path());
				this.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erreur de chargement."); 
			}
		    this.interfaceVideo.changeVideo(vid);
		} else if (event.getSource().equals("segmenter")) {
			interfaceVideo.segmenterImage(true);
		} else if (event.getSource().equals("quitter")) {
			if(JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter?", "Quitter", JOptionPane.YES_NO_OPTION)==0) {
				this.setVisible(false);
				this.dispose();
			}
		} else if (event.getSource().equals("fondG") || event.getSource().equals("butG") || event.getSource().equals("butD") || event.getSource().equals("fondD") || event.getSource().equals("toutesLignes")) { //si l'utilisateur veut definir les lignes de but ou de fond une par une
			interfaceVideo.defPoint(event.getSource().toString());
		} else if (event.getSource().equals("etatFrame")) {
			Etat etatBalle = EtatBalle.etat(interfaceVideo.segmenterImage(false));
			String etatBalleText = new String();
			if (etatBalle==Etat.BUTD) {
				etatBalleText = "But droite";
			} else if (etatBalle==Etat.BUTG) {
				etatBalleText = "But gauche";
			} else if (etatBalle==Etat.JEU) {
				etatBalleText = "En jeu";
			} else if (etatBalle==Etat.SORTIE) {
				etatBalleText = "Sortie";
			} else {
				etatBalleText = "Balle non trouvée";
			}
			JOptionPane.showMessageDialog(null, etatBalleText);
		} else if (event.getSource().equals("synthese")) {
			FichierSynthese.synthese(vid);
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException{
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); // Charge la librairie pour openCV
		
		FenetrePrincipale fenetre = new FenetrePrincipale();
		fenetre.setTitle("Amiltone video analyzer");
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
