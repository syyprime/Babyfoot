package com.tse.kamoulox;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestEtatBalle {
	@Before
	public void setUp() {
		double[] fGH = {10,40};
		EtatBalle.setPointFondGH(fGH);
		double[] fGB = {11,60};
		EtatBalle.setPointFondGB(fGB);
		double[] bGH = {20,10};
		EtatBalle.setPointButGH(bGH);
		double[] bGB = {21,100};
		EtatBalle.setPointButGB(bGB);
		double[] bDH = {70,10};
		EtatBalle.setPointButDH(bDH);
		double[] bDB = {71,100};
		EtatBalle.setPointButDB(bDB);
		double[] fDH = {80,41};
		EtatBalle.setPointFondDH(fDH);
		double[] fDB = {81,59};
		EtatBalle.setPointFondDB(fDB);
    }
	
	@Test
	public void testBourrinGaucheHaut() {
		double[] balle = {11, 39};
		assertTrue(EtatBalle.etat(balle)==Etat.JEU);
	}
	
	@Test
	public void testBourrinGaucheBas() {
		double[] balle = {12, 61};
		assertTrue(EtatBalle.etat(balle)==Etat.JEU);
	}
	
	@Test
	public void testBourrinDroiteHaut() {
		double[] balle = {79, 39};
		assertTrue(EtatBalle.etat(balle)==Etat.JEU);
	}
	
	@Test
	public void testBourrinDroiteBas() {
		double[] balle = {79, 61};
		assertTrue(EtatBalle.etat(balle)==Etat.JEU);
	}
	
	@Test
	public void testSortieGauche() {
		double[] balle = {9, 80};
		assertTrue(EtatBalle.etat(balle)==Etat.SORTIE);
	}

	@Test
	public void testButGauche1() {
		double[] balle = {11, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.BUTG);
	}
	
	@Test
	public void testButGauche2() {
		double[] balle = {19, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.BUTG);
	}
	
	@Test
	public void testJeu1() {
		double[] balle = {21, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.JEU);
	}
	
	@Test
	public void testJeu2() {
		double[] balle = {69, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.JEU);
	}
	
	@Test
	public void testButDroite1() {
		double[] balle = {71, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.BUTD);
	}
	
	@Test
	public void testButDroite2() {
		double[] balle = {79, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.BUTD);
	}
	
	@Test
	public void testSortieDroite() {
		double[] balle = {81, 50};
		assertTrue(EtatBalle.etat(balle)==Etat.SORTIE);
	}
}
