package com.tse.kamoulox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class FichierSynthese {

	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ";";
	private static final String LINE_SEPARATOR = "\n";
	private static String fileName = "_Synthèse.csv";
	private static int ScoreD, ScoreG = 0;

	//CSV file header
	private static final String FILE_HEADER = "N° de Frame;Temps;Evenement;Score Gauche;Score Droit";

	public static void WriteCsvFile(String NomVideo, Etat Evenement, String TempsFrame, String NumeroFrame) throws IOException {

		NomVideo = NomVideo + fileName;
		File file = new File(NomVideo);  

		if(!file.exists()){
			file.createNewFile();
			FileWriter writerDebut = new FileWriter(NomVideo);
			//Write the CSV file header
			writerDebut.append(FILE_HEADER.toString());
			//Add a new line separator after the header
			writerDebut.append(LINE_SEPARATOR);
			writerDebut.flush();
			writerDebut.close();
		}
		
		FileWriter writer = new FileWriter(NomVideo,true);

		try {

			//Write a new line event to the CSV file
			if (Evenement==Etat.BUTD || Evenement==Etat.BUTG || Evenement==Etat.SORTIE) {
				
				//score
				if (Evenement==Etat.BUTD ){
					ScoreD++;
				}

				else if (Evenement==Etat.BUTG ){
					ScoreG++;
				}
				writer.append(String.valueOf(NumeroFrame));
				writer.append(COMMA_DELIMITER);
				writer.append(String.valueOf(TempsFrame));
				writer.append(COMMA_DELIMITER);
				writer.append(String.valueOf(Evenement));
				writer.append(COMMA_DELIMITER);
				writer.append(String.valueOf(ScoreG));
				writer.append(COMMA_DELIMITER);
				writer.append(String.valueOf(ScoreD));
				writer.append(LINE_SEPARATOR);

			}

			System.out.println("CSV file a été crée/modifié !");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
		NomVideo = null;

		System.out.println("done!");

	}
	
	public static void synthese(VideoCharger vid) {
		VideoCapture cap = vid.getCap(); //video
		Mat frame = new Mat();
		cap.read(frame); //met la prochaine frame dans frame
		Etat etatBalle = EtatBalle.etat(Segmentation.Traitement(frame, false)); //avoir l'etat de la balle sur la frame
	}
}
