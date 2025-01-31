package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Cette classe correspond à la première fenetre du jeu,
 * celle correpondant au menu de l'application
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class FenetreMenu extends JFrame {
	/**
	 * La partie ratachée au menu
	 */
    private Partie partie;
    
    /**
     * La zone de texte correposndant au joueur 1
     */
    private JTextField prenom1;
    /**
     * La zone de texte correposndant au joueur 2
     */
    private JTextField prenom2;
    /**
     * La zone de texte correposndant au joueur 3
     */
    private JTextField prenom3;

    /**
     * La liste déroulante correspondant au joueur 1
     */
    private JComboBox ListeStrategie1;
    /**
     * La liste déroulante correspondant au joueur 2
     */
    private JComboBox ListeStrategie2;
    /**
     * La liste déroulante correspondant au joueur 3
     */
    private JComboBox ListeStrategie3;

    /**
     * Chaine de caractère correspondant au pseudo du joueur 1
     */
    private String pseudo1 = "";
    /**
     * Chaine de caractère correspondant au pseudo du joueur 2
     */
    private String pseudo2 = "";
    /**
     * Chaine de caractère correspondant au pseudo du joueur 3
     */
    private String pseudo3 = "";
    
    /**
     * Chaine de caractère corrspondant à la stratégie du joueur 1
     */
    private String strategie1 = "Agressive";
    /**
     * Chaine de caractère corrspondant à la stratégie du joueur 2
     */
    private String strategie2 = "Agressive";
    /**
     * Chaine de caractère corrspondant à la stratégie du joueur 3
     */
    private String strategie3 = "Agressive";

    /**
     * Etat du joueur 1 (true = joueur réel, false = joueur virtuel)
     */
    private boolean J1Reel = true;
    /**
     * Etat du joueur 2 (true = joueur réel, false = joueur virtuel)
     */
    private boolean J2Reel = true;
    /**
     * Etat du joueur 3 (true = joueur réel, false = joueur virtuel)
     */
    private boolean J3Reel = true;

    /**
     * Bouton qui permet de changer le type du joueur 1
     */
    private JButton boutonJ1 = new JButton("Changer Mode");
    /**
     * Bouton qui permet de changer le type du joueur 2
     */
    private JButton boutonJ2 = new JButton("Changer Mode");
    /**
     * Bouton qui permet de changer le type du joueur 3
     */
    private JButton boutonJ3 = new JButton("Changer Mode");

    /**
     * Bouton qui permet de lancer une nouvelle partie
     */
    private JButton boutonValider = new JButton("Nouvelle partie");
    /**
     * Bouton qui permet de reprendre une partie sauvegardée
     */
    private JButton boutonPartieSauvegardee = new JButton("Reprendre partie sauvegardée");

    /**
     * Pop-up qui indique qu'un pseudo est vide
     */
    private JFrame popupPseudoVide = new JFrame("Erreur");
    private JLabel labelPopUpPseudoVide=new JLabel("<html>Erreur : Tous les joueurs réels doivent avoir un pseudo</html>");
    
    /**
     * Pop-up qui indique qu'un pseudo est trop long
     */
    private JFrame popupPseudoLong = new JFrame("Erreur");
    private JLabel labelPopUpPseudoLong=new JLabel("<html>Erreur : Un pseudo est trop long (max. 10 caractères)</html>");

    /**
     * Créé la fenetre du menu, la configure grace à la méthode setFenetre() et la rend visible
     * 
     * @param partie	
     * @throws IOException
     */
    public FenetreMenu(Partie partie) throws IOException {
        this.partie = partie;
        this.setFenetre();
        this.setVisible(true);
    }
    
    /**
     * Configure la fenetre
     * @throws IOException
     */
    public void setFenetre() throws IOException {
        setTitle("Menu Principal");
        setBounds(10, 10, 800, 800);
        this.setLayout(null); // Désactive le LayoutManager
        this.setResizable(false);
        
        //On configure les pop-up
        this.popupPseudoVide.setBounds(300, 300, 260, 150);
        this.popupPseudoVide.getContentPane().setBackground(Color.black);
        this.popupPseudoVide.setLayout(null); // Désactive le LayoutManager
        this.popupPseudoVide.setResizable(false);
        
        
        this.labelPopUpPseudoVide.setBounds(0, 0, 250, 100);
        this.labelPopUpPseudoVide.setFont(new Font("Serif", Font.BOLD, 20));
        this.labelPopUpPseudoVide.setBackground(Color.black);
        this.labelPopUpPseudoVide.setForeground(Color.red);
        this.popupPseudoVide.add(this.labelPopUpPseudoVide);
        
        this.popupPseudoLong.setBounds(300, 300, 260, 150);
        this.popupPseudoLong.getContentPane().setBackground(Color.black);
        this.popupPseudoLong.setLayout(null); // Désactive le LayoutManager
        this.popupPseudoLong.setResizable(false);
        
        
        this.labelPopUpPseudoLong.setBounds(0, 0, 250, 100);
        this.labelPopUpPseudoLong.setFont(new Font("Serif", Font.BOLD, 20));
        this.labelPopUpPseudoLong.setBackground(Color.black);
        this.labelPopUpPseudoLong.setForeground(Color.red);
        this.popupPseudoLong.add(this.labelPopUpPseudoLong);
        
        //On configure et place chaque element
        this.prenom1 = new JTextField();
        this.prenom1.setBounds(360, 332, 180, 30);
        this.prenom1.setText(this.pseudo1);
        this.prenom1.setBackground(new Color(0xffabab));
        this.prenom2 = new JTextField();
        this.prenom2.setBounds(360, 475, 180, 30);
        this.prenom2.setText(this.pseudo2);
        this.prenom2.setBackground(new Color(0xabb4ff));
        this.prenom3 = new JTextField();
        this.prenom3.setBounds(360, 620, 180, 30);
        this.prenom3.setText(this.pseudo3);
        this.prenom3.setBackground(new Color(0xe1c73d));

        this.ListeStrategie1 = new JComboBox(new String[]{"Agressive", "Defensive"});
        this.ListeStrategie1.setBounds(380, 332, 180, 30);
        this.ListeStrategie1.setBackground(new Color(0xffabab));
        this.ListeStrategie1.setSelectedItem(this.strategie1);
        this.ListeStrategie2 = new JComboBox(new String[]{"Agressive", "Defensive"});
        this.ListeStrategie2.setBounds(380, 475, 180, 30);
        this.ListeStrategie2.setBackground(new Color(0xabb4ff));
        this.ListeStrategie2.setSelectedItem(this.strategie2);
        this.ListeStrategie3 = new JComboBox(new String[]{"Agressive", "Defensive"});
        this.ListeStrategie3.setBounds(380, 620, 180, 30);
        this.ListeStrategie3.setBackground(new Color(0xe1c73d));
        this.ListeStrategie3.setSelectedItem(this.strategie3);
        
        //Si le boouton a deja une action
        if (this.boutonJ1.getActionListeners().length != 0) {
            this.boutonJ1.removeActionListener(this.boutonJ1.getActionListeners()[0]); // Supprime le listener existant
        }
        this.boutonJ1.addActionListener(e -> {//On assigne une action au bouton du J1
            try {
                changementTypeJoueur(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        if (this.boutonJ2.getActionListeners().length != 0) {//Si le boouton a deja une action
            this.boutonJ2.removeActionListener(this.boutonJ2.getActionListeners()[0]); // Supprime le listener existant
        }
        this.boutonJ2.addActionListener(e -> {//On assigne une action au bouton du J2
            try {
                changementTypeJoueur(2);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        if (this.boutonJ3.getActionListeners().length != 0) {//Si le boouton a deja une action
            this.boutonJ3.removeActionListener(this.boutonJ3.getActionListeners()[0]); // Supprime le listener existant
        }
        this.boutonJ3.addActionListener(e -> { //On assigne une action au bouton du J2
            try {
                changementTypeJoueur(3);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        //On configure et place chaque boutons
        this.boutonJ1.setBounds(600, 315, 150, 50);
        this.boutonJ1.setBackground(Color.white);
        this.add(boutonJ1);
        this.boutonJ2.setBounds(600, 468, 150, 50);
        this.boutonJ2.setBackground(Color.white);
        this.add(boutonJ2);
        this.boutonJ3.setBounds(600, 603, 150, 50);
        this.boutonJ3.setBackground(Color.white);
        this.add(boutonJ3);

        this.boutonValider.setBounds(150, 691, 200, 50);
        this.boutonValider.setBackground(Color.white);
        this.add(boutonValider);

        this.boutonPartieSauvegardee.setBounds(450, 691, 200, 50);
        this.boutonValider.setBackground(Color.white);
        this.add(boutonPartieSauvegardee);

        if (this.boutonValider.getActionListeners().length != 0) {//Si le boouton a deja une action
            this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
        }
        this.boutonValider.addActionListener(e -> {//On assigne une action au bouton lancer
            try {
                ActionBoutonLancer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        if (this.boutonPartieSauvegardee.getActionListeners().length != 0) {//Si le boouton a deja une action
            this.boutonPartieSauvegardee.removeActionListener(this.boutonPartieSauvegardee.getActionListeners()[0]); // Supprime le listener existant
        }
        this.boutonPartieSauvegardee.addActionListener(e -> { //On assigne une action au bouton qui charge une sauvegarde
            try {
                ActionBoutonLancerPartieSauvegardee();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (FontFormatException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
        });
        
        //On charge toutes nos images
        BufferedImage imgFond = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "FondMenu.png"));
        JLabel labelFond = new JLabel(new ImageIcon(imgFond));
        labelFond.setBounds(0, 0, 800, 800);

        if (J1Reel) { //On charge l'image du joueur 1 reel si il est reel
            BufferedImage imgJ1 = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "J1Reel.png"));
            JLabel labelJ1 = new JLabel(new ImageIcon(imgJ1));
            labelJ1.setBounds(0, -50, 800, 800);
            this.add(prenom1);
            this.add(labelJ1);
        } else {//On charge l'image du joueur 1 virtuel si il est virtuel
            BufferedImage imgJ1 = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "J1Virtuel.png"));
            JLabel labelJ1 = new JLabel(new ImageIcon(imgJ1));
            labelJ1.setBounds(0, -50, 800, 800);
            this.add(ListeStrategie1);
            this.add(labelJ1);
        }

        if (J2Reel) {//On charge l'image du joueur 2 reel si il est reel
            BufferedImage imgJ2 = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "J2Reel.png"));
            JLabel labelJ2 = new JLabel(new ImageIcon(imgJ2));
            labelJ2.setBounds(0, -50, 800, 800);
            this.add(prenom2);
            this.add(labelJ2);
        } else {//On charge l'image du joueur 2 virtuel si il est virtuel
            BufferedImage imgJ2 = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "J2Virtuel.png"));
            JLabel labelJ2 = new JLabel(new ImageIcon(imgJ2));
            labelJ2.setBounds(0, -50, 800, 800);
            this.add(ListeStrategie2);
            this.add(labelJ2);
        }

        if (J3Reel) {//On charge l'image du joueur 3 reel si il est reel
            BufferedImage imgJ3 = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "J3Reel.png"));
            JLabel labelJ3 = new JLabel(new ImageIcon(imgJ3));
            labelJ3.setBounds(0, -50, 800, 800);
            this.add(prenom3);
            this.add(labelJ3);
        } else {//On charge l'image du joueur 3 virtuel si il est virtuel
            BufferedImage imgJ3 = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "J3Virtuel.png"));
            JLabel labelJ3 = new JLabel(new ImageIcon(imgJ3));
            labelJ3.setBounds(0, -50, 800, 800);
            this.add(ListeStrategie3);
            this.add(labelJ3);
        }

        this.add(labelFond);
    }

    /**
     * Cette appelée quand un joueur change de type ou quand la partie se lance,
     * enregistre les infos rentrées et selectionnées et actualise la fenetre
     * 
     * @param joueur
     * @throws IOException
     */
    public void changementTypeJoueur(int joueur) throws IOException {
    	//On recupere les pseudos
        this.pseudo1 = this.prenom1.getText();
        this.pseudo2 = this.prenom2.getText();
        this.pseudo3 = this.prenom3.getText();
        //On recupere les stratégies
        this.strategie1 = (String) this.ListeStrategie1.getSelectedItem();
        this.strategie2 = (String) this.ListeStrategie2.getSelectedItem();
        this.strategie3 = (String) this.ListeStrategie3.getSelectedItem();
        //On change l'état du joueur
        switch (joueur) {
            case 1:
                this.J1Reel = !this.J1Reel;
                break;
            case 2:
                this.J2Reel = !this.J2Reel;
                break;
            case 3:
                this.J3Reel = !this.J3Reel;
                break;
        }

        // Reconstruire et redessiner la fenêtre
        this.getContentPane().removeAll(); // Supprimer les anciens composants
        this.setFenetre();                 // Reconstruire
        this.revalidate();                 // Informer Swing des modifications
        this.repaint();                    // Redessiner
    }
    
    /**
     * Methode appelée lorque le bouton pour lancer la partie est cliqué
     * 
     * @throws IOException
     */
    public void ActionBoutonLancer() throws IOException {
        changementTypeJoueur(0);//On simule un changement de joueur qui n'existe pas pour sauvegarder les informations
        //On creer les variables qui vont recuperer les informations
        TypeJoueur Type1 = null;
        TypeJoueur Type2 = null;
        TypeJoueur Type3 = null;
        String Infos1 = null;
        String Infos2 = null;
        String Infos3 = null;
        //On mets les erreurs potentielles a false
        boolean erreurPseudoVide = false;
        boolean erreurPseudoTropLong=false;
        //On recupere les informations du joueur 1 en fonction de son type
        if (this.J1Reel) {
            if (this.pseudo1.equals("")) {//Si le pseudo est vide
                erreurPseudoVide = true;//On passe l'erreur à true
            } else if (this.pseudo1.length()>10){//Si le pseudo est trop long
                erreurPseudoTropLong=true;//On passe l'erreur à true
            }else {
                Type1 = classes.TypeJoueur.HUMAIN;
                Infos1 = this.pseudo1;
            }
        } else {
            Type1 = classes.TypeJoueur.VIRTUEL;
            Infos1 = (String) this.ListeStrategie1.getSelectedItem();
        }
        
      //On recupere les informations du joueur 2 en fonction de son type
        if (this.J2Reel) {
            if (this.pseudo2.equals("")) {//Si le pseudos est vide
                erreurPseudoVide = true;//On passe l'erreur a true
            } else if (this.pseudo2.length()>10){//Si le pseudo est trop long
                erreurPseudoTropLong=true;//On passe l'erreur a true
            }else {
                Type2 = classes.TypeJoueur.HUMAIN;
                Infos2 = this.pseudo2;
            }
        } else {
            Type2 = classes.TypeJoueur.VIRTUEL;
            Infos2 = (String) this.ListeStrategie2.getSelectedItem();
        }
        
      //On recupere les informations du joueur 3 en fonction de son type
        if (this.J3Reel) {
            if (this.pseudo3.equals("")) {//Si le pseudo est vide
                erreurPseudoVide = true;//On passe l'erreur a true
            } else if (this.pseudo3.length()>10){//Si le pseudo est trop long
                erreurPseudoTropLong=true;//On passe l'erreur a true
            }else {
                Type3 = classes.TypeJoueur.HUMAIN;
                Infos3 = this.pseudo3;
            }
        } else {
            Type3 = classes.TypeJoueur.VIRTUEL;
            Infos3 = (String) this.ListeStrategie3.getSelectedItem();
        }
        if (erreurPseudoVide) {
            this.popupPseudoVide.setVisible(true);
        } else if (erreurPseudoTropLong){
            this.popupPseudoLong.setVisible(true);
        }else {
            this.dispose();
            this.partie.commencerNouvellePartie(new TypeJoueur[]{Type1, Type2, Type3}, new String[]{Infos1, Infos2, Infos3});
        }

    }
    /**
     * Methode executée lorsque le bouton pour charger une sauvegarde est cliqué
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws FontFormatException
     */
    public void ActionBoutonLancerPartieSauvegardee() throws IOException, ClassNotFoundException, FontFormatException {
        File fichierSauvegarde = new File("AUTRES RESSOURCES" + File.separator + "sauvegarde_partie.ser");
        if (!fichierSauvegarde.exists()) {
            JOptionPane.showMessageDialog(this, "Aucune partie sauvegardée n'est disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            this.partie.commencerPartieSauvegardee();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la partie sauvegardée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


}
