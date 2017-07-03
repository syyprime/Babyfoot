package com.tse.kamoulox;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Interface graphique sans le menu.
 */
public class InterfaceVideo extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private VideoCharger vid;
	private int nbImage = 0;
	private double fpsVid = 1;
	private int debutImages = 1;
	
	private JPanel topPanel = new JPanel();
	private JPanel midPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel numPanel = new JPanel();
	private JPanel nbImgPanel = new JPanel();
	private JLabel nomVideo = new JLabel("Aucune vidéo chargée", SwingConstants.CENTER);
	private JLabel logoLabel = new JLabel();
	private DessinLabel picLabel = new DessinLabel();
	private JLabel nbImgLabel = new JLabel("/ 0", SwingConstants.CENTER);
	private JLabel tpsLabel = new JLabel("0 / 0", SwingConstants.CENTER);
	private ImageIcon icon = new ImageIcon();
	private JProgressBar progressBar = new JProgressBar();
	private ImageIcon logo = new ImageIcon();
	private JButton precS = new JButton();
	private JButton prec = new JButton();
	private NumberFormat nf = NumberFormat.getNumberInstance();
	private JFormattedTextField numPage;
	private JButton suiv = new JButton();
	private JButton suivS = new JButton();
	
	private String prochainPoint = new String(); // le prochain point à définir
	private boolean toutesLignes = false; // si on fait toutes les lignes à la suite
	
	MouseAdapter mouseAdapter = new MouseAdapter(){  //gestion du clique de l'utilisateur pour placer les lignes
		/**
		 * Permet de capturer les cliques de l'utilisateur lorsqu'il veut tracer les buts et les fonds.
		 */
		public void mouseClicked(MouseEvent e){  
			if (prochainPoint.equals("fondGH")) { //ligne de fond gauche
				double[] p = cliqueLignes(e);
				EtatBalle.setPointFondGH(p);
				prochainPoint = "fondGB";
			} else if (prochainPoint.equals("fondGB")) {
				double[] p = cliqueLignes(e);
				EtatBalle.setPointFondGB(p);
				if (toutesLignes) {
					prochainPoint = "butGH";					
				} else {
					removeMouseL();
					if (vid!=null) {
						nomVideo.setText(vid.getPath());
					} else {
						nomVideo.setText("Aucune vidéo chargée");
					}
				}
				_fireRepaintEvent(this);
			} else if (prochainPoint.equals("butGH")) { //ligne de but gauche
				double[] p = cliqueLignes(e);
				EtatBalle.setPointButGH(p);
				prochainPoint = "butGB";
			} else if (prochainPoint.equals("butGB")) {
				double[] p = cliqueLignes(e);
				EtatBalle.setPointButGB(p);
				if (toutesLignes) {
					prochainPoint = "butDH";					
				} else {
					removeMouseL();
					if (vid!=null) {
						nomVideo.setText(vid.getPath());
					} else {
						nomVideo.setText("Aucune vidéo chargée");
					}
				}
				_fireRepaintEvent(this);
			} else if (prochainPoint.equals("butDH")) { //ligne de but droite
				double[] p = cliqueLignes(e);
				EtatBalle.setPointButDH(p);
				prochainPoint = "butDB";
			} else if (prochainPoint.equals("butDB")) {
				double[] p = cliqueLignes(e);
				EtatBalle.setPointButDB(p);
				if (toutesLignes) {
					prochainPoint = "fondDH";					
				} else {
					removeMouseL();
					if (vid!=null) {
						nomVideo.setText(vid.getPath());
					} else {
						nomVideo.setText("Aucune vidéo chargée");
					}
				}
				_fireRepaintEvent(this);
			} else if (prochainPoint.equals("fondDH")) { //ligne de fond droite
				double[] p = cliqueLignes(e);
				EtatBalle.setPointFondDH(p);
				prochainPoint = "fondDB";
			} else if (prochainPoint.equals("fondDB")) {
				double[] p = cliqueLignes(e);
				EtatBalle.setPointFondDB(p);
				removeMouseL();
				if (vid!=null) {
					nomVideo.setText(vid.getPath());
				} else {
					nomVideo.setText("Aucune vidéo chargée");
				}
				_fireRepaintEvent(this);
			}
		}
    };
	
    /**
     * Construit l'interface sous le menu.
     */
	public InterfaceVideo() {		
		this.setLayout(new BorderLayout(0,0));
		topPanel.setLayout(new BorderLayout(0,0));
		midPanel.setLayout(new BorderLayout(0,2));
		bottomPanel.setLayout(new FlowLayout());
		numPanel.setLayout(new GridLayout(0, 1));
		nbImgPanel.setLayout(new FlowLayout());
		// couleur de l'interface
		nbImgPanel.setBackground(new Color(171, 169, 170));
		numPanel.setBackground(new Color(171, 169, 170));
		topPanel.setBackground(new Color(171, 169, 170));
		midPanel.setBackground(new Color(171, 169, 170));
		bottomPanel.setBackground(new Color(171, 169, 170));
		this.setBackground(new Color(171, 169, 170));
		
		// Nom video
		topPanel.add(nomVideo, BorderLayout.CENTER);
		
		// Logo
		try {
			logo.setImage(ImageIO.read(new File("Files/logo.png")));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur: ressource manquante dans Files.");
			e.printStackTrace();
		}
		logoLabel.setIcon(logo);
		topPanel.add(logoLabel, BorderLayout.EAST);
		
		// Image
		try {
			icon.setImage(ImageIO.read(new File("Files/video.png")));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur: ressource manquante dans Files.");
			e.printStackTrace();
		}
		picLabel.setIcon(icon);
		midPanel.add(picLabel, BorderLayout.NORTH);
		
		// Progress bar
		progressBar.setValue(100);
		midPanel.add(progressBar, BorderLayout.SOUTH);
		
		// Bouton précédent seconde
		try {
			precS.setIcon(new ImageIcon(ImageIO.read(new File("Files/precS.png"))));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur: ressource manquante dans Files.");
			e.printStackTrace();
		}
		precS.addActionListener(this);
		bottomPanel.add(precS);
		
		// Bouton précédent
		try {
			prec.setIcon(new ImageIcon(ImageIO.read(new File("Files/prec.png"))));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur: ressource manquante dans Files.");
			e.printStackTrace();
		}
		prec.addActionListener(this);
		bottomPanel.add(prec);
		
		// Numéro de la page et temps
		nf.setParseIntegerOnly(true);
		nf.setGroupingUsed(false);
		numPage = new JFormattedTextField(nf);
		numPage.addActionListener(this);
		numPage.setPreferredSize(new Dimension(100, 20));
		nbImgPanel.add(numPage);
		nbImgPanel.add(nbImgLabel);
		numPanel.add(nbImgPanel);
		numPanel.add(tpsLabel);
		bottomPanel.add(numPanel);
		
		// Bouton suivant
		try {
			suiv.setIcon(new ImageIcon(ImageIO.read(new File("Files/suiv.png"))));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur: ressource manquante dans Files.");
			e.printStackTrace();
		}
		suiv.addActionListener(this);
		bottomPanel.add(suiv);
		
		// Bouton suivant seconde
		try {
			suivS.setIcon(new ImageIcon(ImageIO.read(new File("Files/suivS.png"))));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur: ressource manquante dans Files.");
			e.printStackTrace();
		}
		suivS.addActionListener(this);
		bottomPanel.add(suivS);
		
		// Placement des panels
		this.add(topPanel, BorderLayout.NORTH);
		this.add(midPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Permet de changer la frame à afficher.
	 * @param image
	 * Image a afficher
	 */
	public void changeImage(BufferedImage image) {
		icon.setImage(image);
		this.repaint();
		_fireRepaintEvent(this);
	}
	
	/**
	 * Permet de changer la frame à afficher.
	 * @param image
	 * Matrice correspondant a l'image a afficher
	 */
	public void changeImage(Mat image) {
		icon.setImage(mat2Img(image));
		this.repaint();
		_fireRepaintEvent(this);
	}
	
	/**
	 * Permet de changer de video sur laquelle travailler.
	 * @param video
	 * Nouvelle video
	 */
	public void changeVideo(VideoCharger video) {
		vid = video;
		nomVideo.setText(video.getPath());
		nbImage = video.getNbFrame();
		nbImgLabel.setText("/ "+Integer.toString(nbImage));
		fpsVid = video.getFps();
		tpsLabel.setText(new DecimalFormat("#.##").format(1/fpsVid)+" / "+new DecimalFormat("#.##").format(nbImage/fpsVid)+"s");
		numPage.setText("1");
		debutImages = 1;
		this.changeImage(vid.getSeries().get(0));
	}
	
	/**
	 * Segmente l'image actuellement affiche pour detecter le centre de la balle.
	 * @param imageSeul
	 * Si on veut "colorier" la balle ou prevenir a l'utilisateur qu'on ne trouve pas de balle
	 * @return
	 * Centre de la balle (null si non trouvee)
	 */
	public double[] segmenterImage(boolean imageSeul) {
		if (vid!=null) {
			double[] point = Segmentation.Traitement(vid.getSeries().get(getNumPage()-debutImages), imageSeul);
			changeImage(vid.getSeries().get(getNumPage()-debutImages));
			return point;
		} else {
			JOptionPane.showMessageDialog(null, "Chargez une vidéo avant de segmenter.");
			return null;
		}
	}
	
	/**
	 * Active le tracage de lignes
	 */
	public void addMouseL() {
		picLabel.addMouseListener(mouseAdapter);
	}
	
	/**
	 * Desactive le tracage de lignes
	 */
	public void removeMouseL() {
		picLabel.removeMouseListener(mouseAdapter);
	}
	
	/**
	 * Prend les coordonees du clique de l'utilisateur.
	 * @param e
	 * Event de clique
	 * @return
	 * Coordonnees du clique
	 */
	public double[] cliqueLignes(MouseEvent e){  
		double x = 0;
		double y = 0;
        if(e.getButton() == MouseEvent.BUTTON1){ 
            x = e.getX();  
            y = e.getY();
        }
		double[] z = {x,y};
		return z;
    }
	
	/**
	 * Dit quels sont les points a definir en fonction de l'event de demande de l'utilisateur. Lance l'acquisition des points.
	 * @param s
	 * Demande de l'utilisateur.
	 */
	public void defPoint(String s) {
		double[] point = {0,0};
		if (s.equals("fondG")) {
			toutesLignes = false;
			prochainPoint = "fondGH";
			nomVideo.setText("Cliquez sur deux points pour tracer la ligne de fond gauche.");
			EtatBalle.setPointFondGH(point);
			EtatBalle.setPointFondGB(point);
			_fireRepaintEvent(this);
			this.addMouseL();
		} else if (s.equals("butG")) {
			toutesLignes = false;
			prochainPoint = "butGH";
			nomVideo.setText("Cliquez sur deux points pour tracer la ligne de but gauche.");
			EtatBalle.setPointButGH(point);
			EtatBalle.setPointButGB(point);
			_fireRepaintEvent(this);
			this.addMouseL();
		} else if (s.equals("butD")) {
			toutesLignes = false;
			prochainPoint = "butDH";
			nomVideo.setText("Cliquez sur deux points pour tracer la ligne de but droite.");
			EtatBalle.setPointButDH(point);
			EtatBalle.setPointButDB(point);
			_fireRepaintEvent(this);
			this.addMouseL();
		} else if (s.equals("fondD")) {
			toutesLignes = false;
			prochainPoint = "fondDH";
			nomVideo.setText("Cliquez sur deux points pour tracer la ligne de fond droite.");
			EtatBalle.setPointFondDH(point);
			EtatBalle.setPointFondDB(point);
			_fireRepaintEvent(this);
			this.addMouseL();
		} else if (s.equals("toutesLignes")) {
			toutesLignes = true;
			prochainPoint = "fondGH";
			nomVideo.setText("Cliquez sur 8 points pour tracer les lignes. Dans l'ordre: fond gauche, but gauche, but droite, fond droite.");
			EtatBalle.setPointFondGH(point);
			EtatBalle.setPointFondGB(point);
			EtatBalle.setPointButGH(point);
			EtatBalle.setPointButGB(point);
			EtatBalle.setPointButDH(point);
			EtatBalle.setPointButDB(point);
			EtatBalle.setPointFondDH(point);
			EtatBalle.setPointFondDB(point);
			_fireRepaintEvent(this);
			this.addMouseL();
		}
	}
	
	/**
	 * Avoir le numero de la frame ou l'on se trouve.
	 * @return
	 * un entier correspondant au text dans numPage
	 */
	public int getNumPage() {
		return Integer.parseInt(numPage.getText());
	}
	
	/**
	 * Gere la navigation entre les frames en fonction des actions de l'utilisateur.
	 * @param e
	 * Action de l'utilisateur
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// bouton precS
		if (source == precS && getNumPage() > 1) {
			numPage.setText(String.valueOf(Math.max(getNumPage()-(int)fpsVid,1)));
			tpsLabel.setText(new DecimalFormat("#.##").format(getNumPage()/fpsVid)+" / "+new DecimalFormat("#.##").format(nbImage/fpsVid)+"s");
			if (getNumPage() < debutImages) {
				vid.update(Math.max(getNumPage()-1,0));
				debutImages = Math.max(1, getNumPage()-300);
			}
			this.changeImage(vid.getSeries().get(getNumPage()-debutImages));
			
		// bouton prec
		} else if (source == prec && getNumPage() > 1) {
			numPage.setText(String.valueOf(getNumPage()-1));
			tpsLabel.setText(new DecimalFormat("#.##").format(getNumPage()/fpsVid)+" / "+new DecimalFormat("#.##").format(nbImage/fpsVid)+"s");
			this.changeImage(vid.getSeries().get(getNumPage()-debutImages));
			if (getNumPage() < debutImages+1 && debutImages!=1) {
				vid.update(getNumPage()-1);
				debutImages = getNumPage()-300;
			}
			
		// numPage
		} else if (source == numPage && getNumPage() > 0 && getNumPage() <= nbImage) {
			tpsLabel.setText(new DecimalFormat("#.##").format(getNumPage()/fpsVid)+" / "+new DecimalFormat("#.##").format(nbImage/fpsVid)+"s");
			if (getNumPage() > debutImages+599 || getNumPage() < debutImages+1) {
				vid.update(Math.max(getNumPage()-1,0));
				debutImages = Math.max(1, getNumPage()-300);
			}
			this.changeImage(vid.getSeries().get(getNumPage()-debutImages));
			
		// bouton suiv
		} else if (source == suiv && getNumPage() < nbImage-1) {
			numPage.setText(String.valueOf(getNumPage()+1));
			tpsLabel.setText(new DecimalFormat("#.##").format(getNumPage()/fpsVid)+" / "+new DecimalFormat("#.##").format(nbImage/fpsVid)+"s");
			this.changeImage(vid.getSeries().get(getNumPage()-debutImages));
			if (getNumPage() > debutImages+598 && debutImages+599!=nbImage) {
				vid.update(getNumPage()-1);
				debutImages = getNumPage()-300;
			}
			
		// bouton suiS
		} else if (source == suivS && getNumPage() < nbImage-1) {
			numPage.setText(String.valueOf(Math.min(getNumPage()+(int)fpsVid,nbImage)));
			tpsLabel.setText(new DecimalFormat("#.##").format(getNumPage()/fpsVid)+" / "+new DecimalFormat("#.##").format(nbImage/fpsVid)+"s");
			if (getNumPage() > debutImages+599) {
				vid.update(Math.max(getNumPage()-1,0));
				debutImages = Math.max(1, getNumPage()-300);
			}
			this.changeImage(vid.getSeries().get(getNumPage()-debutImages));
		}
	}
	
	// La suite du code est la création de l'evenement de repaint de la fenetre pour eviter la disparition du menu.
    private ArrayList<RepaintListener> _listeners = new ArrayList<RepaintListener>(); // list des listeners
    
    /**
     * Interface pour actualise l'interface graphique en fonction de changements.
     */
	public interface RepaintListener 
	{
	    public void RepaintReceived( RepaintEvent event );
	}
	
	/**
	 * Class qui gere l'actualisation de l'interface graphique
	 */
	public class RepaintEvent extends EventObject {
		private static final long serialVersionUID = 1L;
	    /**
	     * Cree un event d'actualisation de l'interface graphique.
	     * @param source
	     * D'ou vient la demande
	     */
	    public RepaintEvent( Object source) {
	        super( source );
	    }
	    
	}
	
	/**
	 * Ajoute des listeners a l'event de repaint.
	 * @param l
	 * Nouveau listener
	 */
	public synchronized void addRepaintListener( RepaintListener l ) {
        _listeners.add( l );
    }
     
	/**
	 * Dit au listeners qu'il y a un event de repaint.
	 * @param o
	 * Qui demande a repaint
	 */
    private synchronized void _fireRepaintEvent(Object o) {
        RepaintEvent repainte = new RepaintEvent(o);
        Iterator<RepaintListener> listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ( (RepaintListener) listeners.next() ).RepaintReceived( repainte );
        }
    }
	
	// passage d'une matrice en image et vice versa
    /**
     * Transforme une matrice (opencv.core.mat) en BufferedImage.
     * @param m
     * Matrice a transformer
     * @return
     * Image correspondant a la matrice
     */
	public static BufferedImage mat2Img(Mat m){
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);  
        return image;

    }
	
	/**
	 * Transforme une BufferedImage en matrice (opencv.core.mat).
	 * @param bi
	 * Image a transformer
	 * @return
	 * Matrice correspondant a l'image
	 */
	public static Mat img2Mat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
}