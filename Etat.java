package com.tse.kamoulox;

/**
 * Enum servant a definir les differents etats de la balle.
 */
public enum Etat {
	SORTIE, // visible mains hors du terrain
	BUTG, // dans le but gauche
	BUTD, // dans le but droite
	JEU, // en jeu
	INVISIBLE; // balle non trouvée
}
