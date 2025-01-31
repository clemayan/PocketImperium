package classes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Cette classe permet de gerer les differentes images a afficher dans la fenetre principale du jeu
 * 
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class ImagesPlateau extends JFrame implements Serializable{
    /**
     * Image
     */
	private BufferedImage img;
    /**
     * Label qui contient l'image
     */
	private JLabel label;
    /**
     * Coordonnée x de l'image
     */
	private int x;
    /**
     * Coordonnée y de l'image
     */
	private int y;
    /**
     * type de l'image
     */
	private String type;
	/**
	 * Constructeur de l'image
	 * 
	 * @param image
	 * @param type
	 * @param numero
	 * @throws IOException
	 */
	public ImagesPlateau(String image, String type, int numero) throws IOException {
		//Si c'est une image de scteur
		if (type=="Secteur") {
			this.type=type;
			//On ouvre l'image qui correspond
			this.img = ImageIO.read(new File("AUTRES RESSOURCES"+ File.separator +"ImagesPlateau"+ File.separator +"Redimensionnees"+ File.separator +""+image+".png"));
			//On crée et configure le label
			this.label = new JLabel(new ImageIcon(this.img));
			this.x=10+(numero%3)*(this.img.getWidth());
			this.y=10+(numero/3)*(this.img.getHeight());
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight()); // Position (x, y) et taille
		
		} else if (type=="Secteur Inverse") {//Si le secteur est inversé
			this.type=type;
			this.img=ImageIO.read(new File("AUTRES RESSOURCES"+ File.separator +"ImagesPlateau"+ File.separator +"Redimensionnees"+ File.separator +""+image+".png"));
			
			// Créer une nouvelle image avec les mêmes dimensions que l'originale
			BufferedImage mirroredImg = new BufferedImage(
			    this.img.getWidth(),
			    this.img.getHeight(),
			    this.img.getType()
			);
			
			// Appliquer une transformation pour inverser l'image verticalement
			Graphics2D g2d = mirroredImg.createGraphics();
			AffineTransform transform = new AffineTransform();
			transform.scale(1, -1); // Mise à l'échelle verticale inversée
			transform.translate(0, -this.img.getHeight()); // Translation pour ré-aligner l'image
			g2d.drawImage(this.img, transform, null);
			g2d.dispose();
			
			this.img=mirroredImg;	
			this.label = new JLabel(new ImageIcon(this.img));
			this.x=10+(numero%3)*(this.img.getWidth());
			this.y=10+(numero/3)*(this.img.getHeight());
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
		
		} else if (type=="Cadre") {//Si c'est le cadre de selection du secteur
			this.type=type;
			this.img=ImageIO.read(new File("AUTRES RESSOURCES"+ File.separator +"ImagesPlateau"+ File.separator +"Redimensionnees"+ File.separator +""+image+".png"));
			this.label = new JLabel(new ImageIcon(this.img));
			this.x=8;
			this.y=8;
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
		} else if (type=="Cadre hexagone") {//Si c'est le cadre de seelection d'un hexagone
			this.type=type;
			this.img=ImageIO.read(new File("AUTRES RESSOURCES"+ File.separator +"ImagesPlateau"+ File.separator +"Redimensionnees"+ File.separator +""+image+".png"));
			this.label = new JLabel(new ImageIcon(this.img));
			this.x=250;
			this.y=118;
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
		} else if (type=="Fond") {//Si c'est le fond de la fenetre
			this.type=type;
			this.img=ImageIO.read(new File("AUTRES RESSOURCES"+ File.separator +"ImagesPlateau"+ File.separator +"Redimensionnees"+ File.separator +""+image+".png"));
			this.label = new JLabel(new ImageIcon(this.img));
			this.x=0;
			this.y=0;
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
		} else if (type=="Cadre TriTime") {//Si c'est le cadre de selection du tritime
			this.type=type;
			this.img=ImageIO.read(new File("AUTRES RESSOURCES"+ File.separator +"ImagesPlateau"+ File.separator +"Redimensionnees"+ File.separator +""+image+".png"));
			this.label = new JLabel(new ImageIcon(this.img));
			this.x=202;
			this.y=283;
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
		}
	}
	
	/**
	 * Methode qui permet de configurer les coordonnées du label si c'est le cadre de selection des secteurs
	 * @param SecteurX
	 * @param SecteurY
	 */
	public void setCoordCadreSecteur(int SecteurX,int SecteurY) {
		if (this.type.equals("Cadre")) {
			this.x=8+SecteurX*(this.img.getWidth()-4);
			this.y=8+SecteurY*(this.img.getHeight()-4);
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
			
		}
	}
	/**
	 * Methode qui permet de configurer les coordonnées du label si c'est le cadre de selection des hexagones
	 * @param CadreX
	 * @param CadreY
	 */
	public void setCoordCadreHexagone(int CadreX,int CadreY) {
		if (this.type.equals("Cadre hexagone")) {
			if(CadreY%2==0) {
				this.x=9+CadreX*96 + (CadreX+1)/3;// (CadreX+1)/3 pour décaler d'un pixel a la moitié droite car décalage créé a cause des bordures des hexagones non prises en compte dans les calculs
				this.y=36+CadreY*82 + (CadreY/2);// pareil pour (CadreY/2)
			} else {
				this.x=57+CadreX*96 + (CadreX+1)/3;// (CadreX+1)/3 pour décaler d'un pixel a la moitié droite car décalage créé a cause des bordures des hexagones non prises en compte dans les calculs
				this.y=36+CadreY*82 + ((CadreY-1)/2);// pareil pour (CadreY/2)
			}
			this.label.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
			
		}		

		
	}
	/**
	 * Renvoie le label de l'image
	 * 
	 * @return JLabel
	 */
	public JLabel getLabel() {
		return this.label;
	}
}
