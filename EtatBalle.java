package com.tse.kamoulox;

import javax.swing.JOptionPane;

/**
 * Class servant a garder en mémoire la position des points saisies par l'utilisateur et a donner l'etat de la balle.
 * Il est inutile de creer une instance car tout y est static.
 */
public class EtatBalle {
	private static double[] pointButGH = {0,0}; // point haut du but gauche
	private static double[] pointButGB = {0,0}; // point bas du but gauche
	private static double[] pointButDH = {0,0}; // point haut du but droite
	private static double[] pointButDB = {0,0}; // point bas du but droite
	private static double[] pointFondGH = {0,0}; // point haut de la ligne de fond gauche
	private static double[] pointFondGB = {0,0}; // point bas de la ligne de fond gauche
	private static double[] pointFondDH = {0,0}; // point haut de la ligne de fond droite
	private static double[] pointFondDB = {0,0}; // point bas de la ligne de fond droite
	
	/**
	 * Vise a faciliter la lisibilite du code.
	 * Cherche a voir si l'absisse de la balle se trouve bien au niveau des buts.
	 * @param
	 * balle
	 * Centre de la balle dont on veut connaitre l'etat.
	 * @param
	 * verticale
	 * Si le babyfoot est vertical dans la video.
	 * @return
	 * Boolean disant si l'absisse de la balle est au niveau des buts.
	*/
	private static boolean niveauBut(double[] balle, boolean verticale) {
		int x = 0;
		int y = 1;
		if (verticale) {
			x = 1;
			y = 0;
		}
		return (
		balle[y]>((pointFondDH[y]-pointFondGH[y])/(pointFondDH[x]-pointFondGH[x]))*balle[x] + (pointFondGH[y]*pointFondDH[x]-pointFondDH[y]*pointFondGH[x])/(pointFondDH[x]-pointFondGH[x]) &&
		balle[y]<((pointFondDB[y]-pointFondGB[y])/(pointFondDB[x]-pointFondGB[x]))*balle[x] + (pointFondGB[y]*pointFondDB[x]-pointFondDB[y]*pointFondGB[x])/(pointFondDB[x]-pointFondGB[x])
		);
	}

	/**
	 * Regarde l'etat de la balle en fonction des point (attributs static de la class).
	 * @param
	 * balle
	 * Centre de la balle dont on veut connaitre l'etat.
	 * @return
	 * Etat de la balle (voir enum Etat).
	 */
	public static Etat etat(double[] balle) {
		if (pointButGH[1]!=pointButGB[1] && pointButDH[1]!=pointButDB[1] && pointFondGH[1]!=pointFondGB[1] && pointFondDH[1]!=pointFondDB[1] && pointFondGH[0]!=pointFondDH[0] &&pointFondGB[0]!=pointFondDB[0]) {
			if (balle==null) {
				return com.tse.kamoulox.Etat.INVISIBLE;
			} else if (balle[0]<((pointFondGH[0]-pointFondGB[0])/(pointFondGH[1]-pointFondGB[1]))*balle[1] + (pointFondGB[0]*pointFondGH[1]-pointFondGH[0]*pointFondGB[1])/(pointFondGH[1]-pointFondGB[1])) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.SORTIE;
			} else if (balle[0]<((pointButGH[0]-pointButGB[0])/(pointButGH[1]-pointButGB[1]))*balle[1] + (pointButGB[0]*pointButGH[1]-pointButGH[0]*pointButGB[1])/(pointButGH[1]-pointButGB[1]) && niveauBut(balle, false)) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.BUTG;
			} else if (balle[0]<((pointButDH[0]-pointButDB[0])/(pointButDH[1]-pointButDB[1]))*balle[1] + (pointButDB[0]*pointButDH[1]-pointButDH[0]*pointButDB[1])/(pointButDH[1]-pointButDB[1])) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.JEU;
			} else if (balle[0]<((pointFondDH[0]-pointFondDB[0])/(pointFondDH[1]-pointFondDB[1]))*balle[1] + (pointFondDB[0]*pointFondDH[1]-pointFondDH[0]*pointFondDB[1])/(pointFondDH[1]-pointFondDB[1]) && niveauBut(balle, false)) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.BUTD;
			} else if (balle[0]<((pointFondDH[0]-pointFondDB[0])/(pointFondDH[1]-pointFondDB[1]))*balle[1] + (pointFondDB[0]*pointFondDH[1]-pointFondDH[0]*pointFondDB[1])/(pointFondDH[1]-pointFondDB[1])) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.JEU;
			} else {
				return com.tse.kamoulox.Etat.SORTIE;
			}
		} else if (pointButGH[0]!=pointButGB[0] && pointButDH[0]!=pointButDB[0] && pointFondGH[0]!=pointFondGB[0] && pointFondDH[0]!=pointFondDB[0] && pointFondGH[1]!=pointFondDH[1] &&pointFondGB[1]!=pointFondDB[1]) {
			if (balle==null) {
				return com.tse.kamoulox.Etat.INVISIBLE;
			} else if (balle[1]<((pointFondGH[1]-pointFondGB[1])/(pointFondGH[0]-pointFondGB[0]))*balle[0] + (pointFondGB[1]*pointFondGH[0]-pointFondGH[1]*pointFondGB[0])/(pointFondGH[0]-pointFondGB[0])) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.SORTIE;
			} else if (balle[1]<((pointButGH[1]-pointButGB[1])/(pointButGH[0]-pointButGB[0]))*balle[0] + (pointButGB[1]*pointButGH[0]-pointButGH[1]*pointButGB[0])/(pointButGH[0]-pointButGB[0]) && niveauBut(balle, true)) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.BUTG;
			} else if (balle[1]<((pointButDH[1]-pointButDB[1])/(pointButDH[0]-pointButDB[0]))*balle[0] + (pointButDB[1]*pointButDH[0]-pointButDH[1]*pointButDB[0])/(pointButDH[0]-pointButDB[0])) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.JEU;
			} else if (balle[1]<((pointFondDH[1]-pointFondDB[1])/(pointFondDH[0]-pointFondDB[0]))*balle[0] + (pointFondDB[1]*pointFondDH[0]-pointFondDH[1]*pointFondDB[0])/(pointFondDH[0]-pointFondDB[0]) && niveauBut(balle, true)) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.BUTD;
			} else if (balle[1]<((pointFondDH[1]-pointFondDB[1])/(pointFondDH[0]-pointFondDB[0]))*balle[0] + (pointFondDB[1]*pointFondDH[0]-pointFondDH[1]*pointFondDB[0])/(pointFondDH[0]-pointFondDB[0])) { //abscisse du point de la droite aillant la même ordonnée que la balle.
				return com.tse.kamoulox.Etat.JEU;
			} else {
				return com.tse.kamoulox.Etat.SORTIE;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Veuillez saisir des lignes de fond et de but. C'est lignes ne doivent pas être perpendiculaires et ne doivent pas être confondues."); 
			return null;
		}
	}

	public static double[] getPointButGH() {
		return pointButGH;
	}

	public static void setPointButGH(double[] pointButGH) {
		EtatBalle.pointButGH = pointButGH;
	}

	public static double[] getPointButGB() {
		return pointButGB;
	}

	public static void setPointButGB(double[] pointButGB) {
		EtatBalle.pointButGB = pointButGB;
	}

	public static double[] getPointButDH() {
		return pointButDH;
	}

	public static void setPointButDH(double[] pointButDH) {
		EtatBalle.pointButDH = pointButDH;
	}

	public static double[] getPointButDB() {
		return pointButDB;
	}

	public static void setPointButDB(double[] pointButDB) {
		EtatBalle.pointButDB = pointButDB;
	}

	public static double[] getPointFondGH() {
		return pointFondGH;
	}

	public static void setPointFondGH(double[] pointFondGH) {
		EtatBalle.pointFondGH = pointFondGH;
	}

	public static double[] getPointFondGB() {
		return pointFondGB;
	}

	public static void setPointFondGB(double[] pointFondGB) {
		EtatBalle.pointFondGB = pointFondGB;
	}

	public static double[] getPointFondDH() {
		return pointFondDH;
	}

	public static void setPointFondDH(double[] pointFondDH) {
		EtatBalle.pointFondDH = pointFondDH;
	}

	public static double[] getPointFondDB() {
		return pointFondDB;
	}

	public static void setPointFondDB(double[] pointFondDB) {
		EtatBalle.pointFondDB = pointFondDB;
	}
}
