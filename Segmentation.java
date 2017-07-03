package com.tse.kamoulox;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Class dont la seul methode (static) permet de trouver le centre de la balle par segmentation.
 */
public class Segmentation{
	private static Mat image_a_segmenter;
	private static Point centre;

	private static final int h=96;
	private static final int s=189;
	private static final int v=188;
	private static final int v_cage=111;

	private static int width;
	private static int height;

	// La conversion de RGB vers HSV est sans perte. Toutes les couleurs RGB ont une correspondance en HSV. 
	// (Attention, ce n’est pas le cas pour l’opération inverse, HSV vers RGB.). 

	// Remarque : sur OpenCV, la composante Hue n’est pas codée sur 8 bits (0 à 255) 
	// comme les autres mais de 0 à 180 car c'est un angle (donc perte de précision).

	/**
	 * Methode static permettant de trouver le centre de la balle d'une image et de "colorier" la balle si ont analyse une unique image.
	 * @param image
	 * Image a segmenter
	 * @param imageSeul
	 * Si on veut "colorier" la balle ou prevenir a l'utilisateur qu'on ne trouve pas de balle
	 * @return
	 * Centre de la balle (retourne null si non trouve)
	 */
	public static double[] Traitement(Mat image, boolean imageSeul) {
		image_a_segmenter = image;
		height = image.height();
		width = image.width();

		//______________Transformation HSV________________________

		//		// On transforme l'image en matrice
		//		Mat monImage_Mat = img2Mat(monImage_Buf);

		// Création nouvelle Matrice_HSV
		Mat monImage_Mat_HSV = new Mat(height,width,CvType.CV_8UC3);

		//Passage de la matrice de RGB à HSV
		Imgproc.cvtColor(image_a_segmenter, monImage_Mat_HSV, Imgproc.COLOR_RGB2HSV);


		// ______________Algorithme pour trouver les points de l'image qui correspondent_____________
		double H_Seuil_Haut = h + 55;
		double H_Seuil_Bas = h - 55;
		double S_Seuil_Haut = s + 55;
		double S_Seuil_Bas = s - 55;
		double V_Seuil_Haut = v + 55;
		double V_Seuil_Bas = v - 55;
		double V_Seuil_Haut_cage = v_cage + 15;
		double V_Seuil_Bas_cage = v_cage - 15;
		int Nombre_Pixel = 0;


		List<Integer> indice_Balle_ligne = new ArrayList<Integer>();
		List<Integer> indice_Balle_colonne = new ArrayList<Integer>();
		int x_moyenne = 0;
		int y_moyenne = 0;



		for (int i = 0; i < height-1; i++) {
			for (int j = 0; j < width-1; j++) {
				double[] data = monImage_Mat_HSV.get(i, j);
				if (data[0] < H_Seuil_Haut && data[0] > H_Seuil_Bas && data[1] < S_Seuil_Haut && data[1] > S_Seuil_Bas && (data[2] < V_Seuil_Haut && data[2] > V_Seuil_Bas || data[2] < V_Seuil_Haut_cage && data[2] > V_Seuil_Bas_cage)){
					Nombre_Pixel++;
					indice_Balle_ligne.add(i);
					indice_Balle_colonne.add(j);
					y_moyenne = y_moyenne + i; 
					x_moyenne = x_moyenne + j; 
				}
			}
		}
		
		//___________Moyenne Spaciale________________
		if (indice_Balle_colonne.size()!=0 && indice_Balle_ligne.size()!=0) {
			x_moyenne = x_moyenne/(indice_Balle_colonne.size());
			y_moyenne = y_moyenne/(indice_Balle_ligne.size());
	
			centre = new Point(x_moyenne,y_moyenne);

			// _____On change BGR en RGB ______
			//Imgproc.cvtColor(image_a_segmenter, image_a_segmenter, Imgproc.COLOR_BGR2RGB);
	
			//___________Création du cercle_______________
			if (imageSeul) {
			Scalar color= new Scalar(0, 255, 0);
	
			int rayon = 9; //indice_Balle_ligne.get(indice_Balle_ligne.size()-1)- y_moyenne ;
	
			Core.circle(image_a_segmenter, centre, rayon, color, -1);
			}
			double[] pt = {centre.x, centre.y};
			return pt;
		} else if (imageSeul) {
			JOptionPane.showMessageDialog(null, "Balle non trouvée"); 
			return null;
		} else {
			return null;
		}
	}
}
