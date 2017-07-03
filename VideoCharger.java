package com.tse.kamoulox;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;  

/**
 * Gere le chargement de la video et les frames a charger.
 */
public class VideoCharger{
    private ArrayList<Mat> series;
    private Mat frame;
    private double fps;
    private int nbFrame = 0;
    private String path = new String();
    private VideoCapture cap = new VideoCapture(); 
	
    /**
     * Charge la video et met les premieres frames dans this.series.
     * @param path
     * Chemin d'acces a la video
     * @throws InterruptedException
     */
	public void charger(String path) throws InterruptedException{  
	      
	    //charger le video  
		this.path = path;
	    cap.open(path);
	    //if the video is opened  
	    if(cap.isOpened()){
	          
	        //le nombre total de frame dans le video 
	        nbFrame = (int)cap.get(7);  //Highgui.CV_CAP_PROP_FRAME_COUNT
	          
	        //combien de frame par une seconde 
	        fps = cap.get(5);//opencv_highgui.CV_CAP_PROP_FPS
	        
	        series=new ArrayList<Mat>();
        	frame = new Mat();
	        for(int i=0; i<Math.min(nbFrame, 600); i++){
	            //lire la frame prochaine  
	            if(cap.read(frame)){
	            	Imgproc.resize(frame, frame, new Size(848, 477)); // Image de base en 1280*720
	                //donner valeurs a la liste de images.
	                series.add(frame.clone());
	            }  
	        }
	        frame.release();
	        //fermer le video  
	        //cap.release();
	        //System.out.println(series);
	    }
	    else {
        	JOptionPane.showMessageDialog(null, "Erreur de chargement."); 
	    }
	}
	
	/**
	 * Met a jour this.series pour centrer this.series sur la frame qui nous interesse.
	 * @param i
	 * Frame sur laquelle on veut centrer this.series
	 */
	public void update(int i) {
		//cap.release();
		//cap.open(path);
		cap.set(1, Math.max(i-300, 0));
    	if(cap.isOpened()){  
    		seriesClear();
        	frame = new Mat();
	        for(int j = Math.max(i-300, 0); j<Math.min(nbFrame, Math.max(600, i+300)); j++){
	            //lire la frame prochaine  
	            if(cap.read(frame)){
	            	Imgproc.resize(frame, frame, new Size(848, 477)); // Image de base en 1280*720
	                //donner valeurs a la liste de images.
	                series.add(frame.clone());
	            }  
	        }
	        frame.release();
    	}
	    else {
        	JOptionPane.showMessageDialog(null, "Erreur de chargement."); 
	    }
	}
	
	/**
	 * Permet de liberer la memoire lorsqu'on vide "series".
	 */
	private void seriesClear() {
		for (int i=0; i<series.size(); i++) {
			series.get(i).release();
		}
		series.clear();
	}
	
	public ArrayList<Mat> getSeries() {
		return series;
	}
	
	public double getFps() {
		return fps;
	}
	
	public int getNbFrame() {
		return nbFrame;
	}
	
	public String getPath() {
		return path;
	}
	
	public VideoCapture getCap() {
		return cap;
	}
}