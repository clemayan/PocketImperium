package classes;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Cette classe correspond à la fenetre de classement du jeu
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class FenetreClassement extends JFrame implements MouseListener {
	/**
	 * Pseudo du premier 
	 */
    private String pseudo1er;
    /**
     * Pseudo du deuxieme 
     */
    private String pseudo2eme;
    /**
     * Pseudo du troiseme 
     */
    private String pseudo3eme;
    
    /**
     * Points du premier 
     */
    private int points1er;
    /**
     * Points du deuxieme 
     */
    private int points2eme;
    /**
     * Points du troisieme
     */
    private int points3eme;
    
    /**
     * Coordonnée X du dernier clik enregistré
     */
    private int lastClicX=-1;
    /**
     * Coordonée Y du dernier click enregisté
     */
    private int lastClicY=-1;
    
    /**
     * Bouton pour relancer une partie
     */
    private JButton boutonRelancer = new JButton("Relancer une partie");
    
    /**
     * Couleur du texte pour le premier
     */
    private Color couleurOr=new Color(0xffd700);
    /**
     * Couleur du texte pour le deuxieme
     */
    private Color couleurArgent=new Color(0xc0c0c0);
    /**
     * Couleur du texte pour le troisieme
     */
    private Color couleurBronze=new Color(0xcd7f32);
    
    /**
     * Label qui accueillera le nom du premier
     */
    private JLabel labelPremierJoueur=new JLabel();
    /**
     * Label qui accueillera le nom du deuxieme
     */
    private JLabel labelDeuxiemeJoueur=new JLabel();
    /**
     * Label qui accueillera le nom du troisieme
     */
    private JLabel labelTroisiemeJoueur=new JLabel();
    
    /**
     * Label qui accueillera les points du premier
     */
    private JLabel labelPremierJoueurScore=new JLabel();
    /**
     * Label qui accueillera les points du deuxieme
     */
    private JLabel labelDeuxiemeJoueurScore=new JLabel();
    /**
     * Label qui accueillera les points du troisieme
     */
    private JLabel labelTroisiemeJoueurScore=new JLabel();
    
    /**
     * Constructeur de la fenetre qui recupêre les infos en parametre et construit 
     * la fenetre avec la methode setFenetre()
     * 
     * @param pseudo1
     * @param points1
     * @param pseudo2
     * @param points2
     * @param pseudo3
     * @param points3
     * @throws IOException
     */
    public FenetreClassement(String pseudo1, int points1, String pseudo2, int points2, String pseudo3, int points3) throws IOException {
		this.pseudo1er=pseudo1;
		this.pseudo2eme=pseudo2;
		this.pseudo3eme=pseudo3;
		this.points1er=points1;
		this.points2eme=points2;
		this.points3eme=points3;
		try {
			this.setFenetre();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVisible(true);
    	
	}
    
    @Override
    /**
     * Methode qui permet d'ouvrir les crédits en fonction du click de la souris
     */
	public void mouseClicked(MouseEvent e) {
		int X = e.getX()-6;//On rectifie nos coordonnées
		int Y = e.getY()-29;
		if (X!=this.lastClicX || Y!=this.lastClicY) {//Si le click est différent du précédent
			this.lastClicX=X;
			this.lastClicY=Y;
			if (Y>=758 && X<=282) {//Si on est dans la zone pour ouvrir les credits
				
				String filePath = "AUTRES RESSOURCES" + File.separator + "Credits.pdf";
		        try {//On tente d'ouvrir le fichier PDF
		            File pdfFile = new File(filePath);

		            // Vérifiez si le fichier existe
		            if (!pdfFile.exists()) {
		                System.out.println("Le fichier n'existe pas : " + filePath);
		                return;
		            }

		            // Vérifiez si Desktop est pris en charge
		            if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                desktop.open(pdfFile); // Ouvre le fichier dans l'application par défaut
		            } else {
		                System.out.println("La fonctionnalité Desktop n'est pas prise en charge sur ce système.");
		            }
		        } catch (IOException e1) {
		            System.err.println("Erreur lors de l'ouverture du fichier PDF : " + e1.getMessage());
		        }
			}
		}	
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
    
    /**
     * Methode qui configure la fenetre
     * 
     * @throws IOException
     * @throws FontFormatException
     */
    public void setFenetre() throws IOException, FontFormatException {
        setTitle("Classement");
        setBounds(10, 10, 810, 830);
        this.setLayout(null); // Désactive le LayoutManager
        this.setResizable(false);
        
        this.addMouseListener(this);
        
        //on place le bouton pour relancer
        this.boutonRelancer.setBounds(590, 740, 200, 50);
        this.boutonRelancer.setBackground(Color.white);
        this.add(boutonRelancer);
        
        if (this.boutonRelancer.getActionListeners().length != 0) {//Si le bouton a deja une action
            this.boutonRelancer.removeActionListener(this.boutonRelancer.getActionListeners()[0]); // Supprime le listener existant
        }
        this.boutonRelancer.addActionListener(e -> {
            try {//On assigne des actions au bouton relancer
                Partie partie = new Partie();
                FenetreMenu menu = new FenetreMenu(partie);
                this.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //On charge la police d'ecriture
		Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("AUTRES RESSOURCES"+File.separator+"IMP2.otf"));
        customFont = customFont.deriveFont(36f); // Taille de la police
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //On configue les labels
        this.labelPremierJoueur.setText(pseudo1er);
        this.labelPremierJoueur.setBounds(180, 265, 700, 200);
        this.labelPremierJoueur.setFont(customFont);
        this.labelPremierJoueur.setForeground(this.couleurOr);
        
        this.labelDeuxiemeJoueur.setText(pseudo2eme);
        this.labelDeuxiemeJoueur.setBounds(180, 400, 700, 200);
        this.labelDeuxiemeJoueur.setFont(customFont);
        this.labelDeuxiemeJoueur.setForeground(this.couleurArgent);
        
        this.labelTroisiemeJoueur.setText(pseudo3eme);
        this.labelTroisiemeJoueur.setBounds(180, 535, 700, 200);
        this.labelTroisiemeJoueur.setFont(customFont);
        this.labelTroisiemeJoueur.setForeground(this.couleurBronze);
        
        this.add(this.labelPremierJoueur);
        this.add(this.labelDeuxiemeJoueur);
        this.add(this.labelTroisiemeJoueur);
        
        this.labelPremierJoueurScore.setText(" | "+points1er+" pts");
        this.labelPremierJoueurScore.setBounds(580, 265, 700, 200);
        this.labelPremierJoueurScore.setFont(customFont);
        this.labelPremierJoueurScore.setForeground(this.couleurOr);
        
        this.labelDeuxiemeJoueurScore.setText(" | "+points2eme+" pts");
        this.labelDeuxiemeJoueurScore.setBounds(580, 400, 700, 200);
        this.labelDeuxiemeJoueurScore.setFont(customFont);
        this.labelDeuxiemeJoueurScore.setForeground(this.couleurArgent);
        
        this.labelTroisiemeJoueurScore.setText(" | "+points3eme+" pts");
        this.labelTroisiemeJoueurScore.setBounds(580, 535, 700, 200);
        this.labelTroisiemeJoueurScore.setFont(customFont);
        this.labelTroisiemeJoueurScore.setForeground(this.couleurBronze);
        
        this.add(this.labelPremierJoueurScore);
        this.add(this.labelDeuxiemeJoueurScore);
        this.add(this.labelTroisiemeJoueurScore);
        
        //On place l'image de fond
        BufferedImage imgFond = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "FondClassement.png"));
        JLabel labelFond = new JLabel(new ImageIcon(imgFond));
        labelFond.setBounds(0, 0, 800, 800);
        this.add(labelFond);
        
    }
    
    
    
}
