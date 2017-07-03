package com.tse.kamoulox;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * Class fille de JLabel ne redefinissant que le methode paint de maniere a afficher les lignes de but et de fond sur l'image (et a les garder lorsqu'on change d'image).
 */
public class DessinLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Utilise JLabel.paint(Graphics g) puis trace les differentes lignes.
	 * Cette methode est utilise indirectement (par repaint par exemple).
	 */
	@Override public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.RED);
        g.drawLine((int)EtatBalle.getPointFondGH()[0], (int)EtatBalle.getPointFondGH()[1], (int)EtatBalle.getPointFondGB()[0], (int)EtatBalle.getPointFondGB()[1]);
		g.setColor(Color.GREEN);
        g.drawLine((int)EtatBalle.getPointButGH()[0], (int)EtatBalle.getPointButGH()[1], (int)EtatBalle.getPointButGB()[0], (int)EtatBalle.getPointButGB()[1]);
		g.setColor(Color.GREEN);
        g.drawLine((int)EtatBalle.getPointButDH()[0], (int)EtatBalle.getPointButDH()[1], (int)EtatBalle.getPointButDB()[0], (int)EtatBalle.getPointButDB()[1]);
		g.setColor(Color.RED);
        g.drawLine((int)EtatBalle.getPointFondDH()[0], (int)EtatBalle.getPointFondDH()[1], (int)EtatBalle.getPointFondDB()[0], (int)EtatBalle.getPointFondDB()[1]);
    }
}
