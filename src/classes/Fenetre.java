package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Cette classe correspond à la fenetre principale du jeu
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Fenetre extends JFrame implements MouseListener, Observer {

    /**
     * Partie du jeu
     */
    private Partie partie;
    /**
     * Image du secteur 1
     */
    private ImagesPlateau Im1;
    /**
     * Image du secteur 2
     */
    private ImagesPlateau Im2;
    /**
     * Image du secteur 3
     */
    private ImagesPlateau Im3;
    /**
     * Image du secteur 4
     */
    private ImagesPlateau Im4;
    /**
     * Image du secteur 5
     */
    private ImagesPlateau Im5;
    /**
     * Image du secteur 6
     */
    private ImagesPlateau Im6;
    /**
     * Image du secteur 7
     */
    private ImagesPlateau Im7;
    /**
     * Image du secteur 8
     */
    private ImagesPlateau Im8;
    /**
     * Image du secteur 9
     */
    private ImagesPlateau Im9;

    /**
     * Image du cadre qui selection un secteur
     */
    private ImagesPlateau ImCadre;
    /**
     * Image du cadre qui selectionne un hexagone
     */
    private ImagesPlateau ImCadreHexagone;
    /**
     * Image du cadre qui selectionne le tritime
     */
    private ImagesPlateau ImCadreTriTime;
    /**
     * Coordonnée X du secteur selectionné
     */
    private int CoordXCadreSecteur = -100;
    /**
     * Coordonnée Y du secteur selectionné
     */
    private int CoordYCadreSecteur = -100;
    /**
     * Cooordonnée X de l'hexagone selectionné
     */
    private int CoordXCadreHexagone = -100;
    /**
     * Coordonnée Y de l'hexagone selectionné
     */
    private int CoordYCadreHexagone = -100;
    /**
     * Liste qui contient les couleurs des joueurs
     */
    private Color[] listeCouleursTextJoueur = new Color[]{new Color(0xffabab), new Color(0xabb4ff), new Color(0xe1c73d)};
    /**
     * Coordonnée X du dernier click
     */
    private int lastClicX = -10;
    /**
     * Coordonnée Y du dernier click
     */
    private int lastClicY = -10;

    /**
     * Boolean qui permet determine si il faut selectionner un secteur
     */
    private boolean SelectionSecteur = false;
    /**
     * Boolean qui permet determine si il faut selectionner un Hexagone
     */
    private boolean SelectionHexagone = false;
    /**
     * Police d'ecriture pour les joueurs
     */
    private Font FontInfosJoueur;
    /**
     * Modele des entrées pour le choix de l'ordre developpement
     */
    private SpinnerModel modelSpinnerChoixDeveloppement = new SpinnerNumberModel(1, 1, 3, 1);
    /**
     * Modele des entrées pour le choix de l'ordre exploration
     */
    private SpinnerModel modelSpinnerChoixExploration = new SpinnerNumberModel(2, 1, 3, 1);
    /**
     * Modele des entrées pour le choix de l'ordre attaque
     */
    private SpinnerModel modelSpinnerChoixAttaque = new SpinnerNumberModel(3, 1, 3, 1);
    /**
     * Entrée utilisateur de l'ordre de developpement
     */
    private JSpinner ChoixOrdreDeveloppement = new JSpinner(modelSpinnerChoixDeveloppement);
    /**
     * Entrée utilisateur de l'ordre de Attaque
     */
    private JSpinner ChoixOrdreAttaque = new JSpinner(modelSpinnerChoixAttaque);
    /**
     * Entrée utilisateur de l'ordre de exploration
     */
    private JSpinner ChoixOrdreExploration = new JSpinner(modelSpinnerChoixExploration);
    /**
     * Bouton pour valider une etape
     */
    private JButton boutonValider = new JButton("Valider choix");
    /**
     * Label du titre
     */
    private JLabel Titre = new JLabel();
    /**
     * label des infos a afficher
     */
    private JLabel labelInfos = new JLabel();
    /**
     * Permet de savoir quel joueur doit faire l'action demandée
     */
    private int compteurJoueur = 0;
    /**
     * Permet de savoir quelle action doit etre gérée lors de l'execution des
     * actions
     */
    private int compteurAction = 0;
    /**
     * Permet de savoir le nombre d'action restante aux joueurs lorsu'ils
     * executent
     */
    private int nbActionRestantes;
    /**
     * Erreur à afficher à l'écran
     */
    private String erreur = "";
    /**
     * Bouton pour continuer la partie entre les tours
     */
    private JButton boutonContinuer = new JButton("Continuer la partie");
    /**
     * Bouton pour sauvegarder la partie entre les tours
     */
    private JButton boutonSauvegarder = new JButton("Sauvegarder et quitter");

    /**
     * Constructeur de la fenetre, qui la configure grace a la methode
     * setFenetre() et l'affiche
     *
     * @param partie
     * @throws IOException
     * @throws FontFormatException
     */
    public Fenetre(Partie partie) throws IOException, FontFormatException {
        this.partie = partie;
        this.partie.getInstance().addObserver(this);
        this.setFenetre();
        this.setVisible(true);

    }

    /**
     * Renvoie le label titre de la fenetre
     *
     * @return JLabel du titre
     */
    public JLabel getTitre() {
        return Titre;
    }

    /**
     * Configure le label titre de la fenetre
     *
     * @param titre
     */
    public void setTitre(JLabel titre) {
        Titre = titre;
    }

    /**
     * Retourne le label infos de la fenetre
     *
     * @return JLabel
     */
    public JLabel getLabelInfos() {
        return labelInfos;
    }

    /**
     * Configure le label infos de la fenetre
     *
     * @param labelInfos
     */
    public void setLabelInfos(JLabel labelInfos) {
        this.labelInfos = labelInfos;
    }

    /**
     * Renvoie la liste des couleurs des joueurs
     *
     * @return Color[][]
     */
    public Color[] getListeCouleursTextJoueur() {
        return listeCouleursTextJoueur;
    }

    /**
     * Configure la liste des couleurs des joueurs
     *
     * @param listeCouleursTextJoueur
     */
    public void setListeCouleursTextJoueur(Color[] listeCouleursTextJoueur) {
        this.listeCouleursTextJoueur = listeCouleursTextJoueur;
    }

    /**
     * Recupere le compteur de joueur
     *
     * @return entier
     */
    public int getCompteurJoueur() {
        return compteurJoueur;
    }

    /**
     * Recupere le compteur d'action
     *
     * @return entier
     */
    public int getCompteurAction() {
        return compteurAction;
    }

    /**
     * Configure le compteur de joueurs
     *
     * @param compteurJoueur
     */
    public void setCompteurJoueur(int compteurJoueur) {
        this.compteurJoueur = compteurJoueur;
    }

    /**
     * Methode appelée a chaque click de la souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int X = e.getX() - 6;//On rectifie les valeurs
        int Y = e.getY() - 29;

        if (X != this.lastClicX || Y != this.lastClicY) {//Si le click est different du précédent

            if (this.SelectionSecteur || this.SelectionHexagone) {
                if (this.SelectionSecteur) {//Si on doit selectionner un secteur
                    this.lastClicX = X;
                    this.lastClicY = Y;
                    int posSecteurX = (X - 10) / 193;//On determine le secteur auquel le click correspond
                    int posSecteurY = (Y - 10) / 275;
                    if (posSecteurX <= 2 && posSecteurX >= 0 && posSecteurY <= 2 && posSecteurY >= 0) {//Si le secteur est valable
                        this.CoordXCadreSecteur = posSecteurX;//On l'enregistre
                        this.CoordYCadreSecteur = posSecteurY;
                    }

                }
                if (this.SelectionHexagone) {//Si on doit selectionner un hexagone
                    this.lastClicX = X;
                    this.lastClicY = Y;
                    if (X >= 11 && X <= 589) { //Si sur le plateau
                        int Yhexagone = this.CoordYCadreHexagone;
                        int YhexagoneTemp = (Y - 66) / 82;//Car sinon on calcul Yhexagone (pour faire des tests) et modifie donc le placement de l'hexagone avant de savoir si tout est bon
                        int Xhexagone = this.CoordXCadreHexagone;
                        if (Y < YhexagoneTemp * 82 + 66 + 53 && YhexagoneTemp >= 0 && YhexagoneTemp <= 8 && Y > 66) { //Si on est dans les rectangles du centres des hexagones

                            //Si ligne paire
                            if (YhexagoneTemp % 2 == 0) {
                                Yhexagone = (Y - 66) / 82;
                                Xhexagone = (X - 11) / 96;
                            } else { //si ligne impaire
                                if (X >= 60 && X <= 541) {
                                    Yhexagone = (Y - 66) / 82;
                                    Xhexagone = (X - 60) / 96;
                                }
                            }
                            this.CoordXCadreSecteur = Xhexagone / 2;
                            this.CoordYCadreSecteur = Yhexagone / 3;

                            this.CoordXCadreHexagone = Xhexagone;
                            this.CoordYCadreHexagone = Yhexagone;
                        } else { //Si on est sur le haut / bas d'un hexagone 
                            int YhexagoneHautTemp = (Y - 66) / 82;	//On recupere l ligne du dessus et du dessous
                            int YhexagoneBasTemp = YhexagoneHautTemp + 1;
                            String pente = "";//Permet d'enregostre si on est sur un coté 'descendant' ou 'montant' de l'hexagone
                            if (YhexagoneHautTemp % 2 == 0) {//Si la ligne du dessus est paire
                                if (((X - 10) / 48) % 2 == 0) { //Si on est sur un cote gauche (pente descendante)
                                    pente = "Descendante";
                                } else { //Si on est sur un coté droit
                                    pente = "Montante";
                                }
                            } else { //Si la ligne du dessus est impaire
                                if (((X - 58) / 48) % 2 == 0) { //Si on est sur un cote gauche (pente descendante)
                                    pente = "Descendante";
                                } else { //Si on est sur un coté droit
                                    pente = "Montante";
                                }
                            }

                            int Xrelatif = (X - 10) % 48; //X et Y relatifs au rectangle contenant la pente a étudiaer (pour savoir si le cli est au dessu ou en dessous)
                            int Yrelatif = (Y - 119) % 82;
                            if (pente == "Descendante") {//Si on est sur une pente descendante FONCTIONNE MAIS INVERSE
                                if (Yrelatif <= 0.58 * Xrelatif) {
                                    YhexagoneTemp = YhexagoneHautTemp;
                                } else {
                                    YhexagoneTemp = YhexagoneBasTemp;
                                }
                            } else {
                                Yrelatif = 28 - Yrelatif; //Car dasn ce sens l'axe y est inversé
                                if (Yrelatif >= 0.58 * Xrelatif) {
                                    YhexagoneTemp = YhexagoneHautTemp;
                                } else {
                                    YhexagoneTemp = YhexagoneBasTemp;
                                }
                            }

                            if (YhexagoneTemp >= 0 && YhexagoneTemp <= 8 && Y > 66) {//Avant d'assigner les nouvelles coordonnées, on verifie que l'hexagone est valide
                                //Si ligne paire
                                if (YhexagoneTemp % 2 == 0) {
                                    Yhexagone = YhexagoneTemp;
                                    Xhexagone = (X - 11) / 96;
                                } else { //si ligne impaire
                                    if (X >= 60 && X <= 541) {
                                        Yhexagone = YhexagoneTemp;
                                        Xhexagone = (X - 60) / 96;
                                    }
                                }

                                this.CoordXCadreSecteur = Xhexagone / 2;
                                this.CoordYCadreSecteur = Yhexagone / 3;

                                this.CoordXCadreHexagone = Xhexagone;
                                this.CoordYCadreHexagone = Yhexagone;

                            }

                        }

                    }
                }
                try {//on tente de mettre a jour la fenetre
                    this.updateFenetre();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Methode qui configure la fenetre
     *
     * @throws IOException
     * @throws FontFormatException
     */
    public void setFenetre() throws IOException, FontFormatException {
        //On charge la police
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("AUTRES RESSOURCES" + File.separator + "IMP2.otf"));
        customFont = customFont.deriveFont(24f); // Taille de la police
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Font customFontClassique = new Font("Arial", Font.BOLD, 26);

        //On recupere les infos a afficher
        String nomJ1 = this.partie.getInstance().getNomJoueur(0);
        String nomJ2 = this.partie.getInstance().getNomJoueur(1);
        String nomJ3 = this.partie.getInstance().getNomJoueur(2);

        int nbPointsJ1 = this.partie.getInstance().getNbPoints(0);
        int nbPointsJ2 = this.partie.getInstance().getNbPoints(1);
        int nbPointsJ3 = this.partie.getInstance().getNbPoints(2);

        int nbVaisseauxJ1 = this.partie.getInstance().getNbVaisseaux(0);
        int nbVaisseauxJ2 = this.partie.getInstance().getNbVaisseaux(1);
        int nbVaisseauxJ3 = this.partie.getInstance().getNbVaisseaux(2);

        JLabel LabelInfosJ1 = new JLabel(nomJ1 + " : " + nbVaisseauxJ1 + " vaisseaux | " + nbPointsJ1 + " pts");
        JLabel LabelInfosJ2 = new JLabel(nomJ2 + " : " + nbVaisseauxJ2 + " vaisseaux | " + nbPointsJ2 + " pts");
        JLabel LabelInfosJ3 = new JLabel(nomJ3 + " : " + nbVaisseauxJ3 + " vaisseaux | " + nbPointsJ3 + " pts");
        LabelInfosJ1.setBounds(780, 60, 700, 200);
        LabelInfosJ1.setFont(customFont);
        LabelInfosJ1.setForeground(new Color(0xffabab));
        LabelInfosJ1.setBackground(Color.red);

        LabelInfosJ2.setBounds(780, 90, 700, 200);
        LabelInfosJ2.setFont(customFont);
        LabelInfosJ2.setForeground(new Color(0xabb4ff));

        LabelInfosJ3.setBounds(780, 120, 700, 200);
        LabelInfosJ3.setFont(customFont);
        LabelInfosJ3.setForeground(new Color(0xe1c73d));
        LabelInfosJ3.setBackground(Color.red);

        this.add(LabelInfosJ1);
        this.add(LabelInfosJ2);
        this.add(LabelInfosJ3);

        setTitle("Partie en cours");
        setBounds(10, 10, 1430, 900);
        this.addMouseListener(this);
        this.setResizable(false);
        this.setLayout(null); // Désactive le LayoutManager
        ImagesPlateau ImFond = new ImagesPlateau("Fond", "Fond", 0);

        //Affichage des secteurs
        for (int i = 1; i < 10; i++) {
            Secteur secteur = this.partie.getSecteurByNum(i);

            String sensCarte = "Secteur";
            if (i >= 7) {
                sensCarte = "Secteur Inverse"; //On le repere pour retourner (symetrie axiale) l'image avant de l'afficher
                //On inverse les points du secteur (le test pour pas l,inverser a chaque tour se fait dans la methode inverser)
                secteur.inverser();
            }

            switch (i) {//on charge les images du plateau
                case 1:
                    this.Im1 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 2:
                    this.Im2 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;

                case 3:
                    this.Im3 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 4:
                    this.Im4 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 5:
                    this.Im5 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 6:
                    this.Im6 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 7:
                    this.Im7 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 8:
                    this.Im8 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
                case 9:
                    this.Im9 = new ImagesPlateau(secteur.getNomImage(), sensCarte, i - 1);
                    break;
            }

        }

        //On gere le cadre modulable (elements qui changent en fonction de la phase)
        this.boutonValider.setBackground(Color.white);
        this.boutonValider.setBounds(940, 750, 150, 50);

        this.labelInfos.setBounds(600, 250, 800, 400);
        this.labelInfos.setFont(customFontClassique);
        this.labelInfos.setPreferredSize(new Dimension(800, 400));
        this.labelInfos.setVerticalAlignment(SwingConstants.TOP);
        this.labelInfos.setBackground(new Color(0, 0, 0, 0));

        this.add(this.labelInfos);

        System.out.println("moment" + Partie.getInstance().getMoment());

        System.out.println("joueur numéro" + this.compteurJoueur);
        System.out.println("nb action" + this.nbActionRestantes);

        // *************** Si on est au Premier placemement :  placement de 2 vaisseaux par joueurs ***************
        if (this.partie.getInstance().getMoment() == Moment.PremierPlacement) { //Si c'est le moment du premier placement
            this.SelectionHexagone = true; //On met la selection d'hexagone active
            this.SelectionSecteur = false;

            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]); // On met la couleur du texte à celle du joueur en cours
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez un hexagone pour y déplacer 2 vaisseaux<br><br>" + this.erreur + "</html>");//Ecrire le texte

            this.add(this.boutonValider);//On place le bouton

            //On definit l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                if (this.CoordXCadreHexagone != -100 && this.CoordYCadreHexagone != -100) {//Si un hexagone est selectioné
                    try {
                        //On déplace 2x la flotte du joueur
                        this.partie.getInstance().deplacerFlotteJoueur(this.compteurJoueur, this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone, 0);
                        this.partie.getInstance().deplacerFlotteJoueur(this.compteurJoueur, this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone, 1);

                        //On met le cadre de selection hors champs
                        this.CoordXCadreHexagone = -100;
                        this.CoordYCadreHexagone = -100;
                        this.compteurJoueur += 1;//On place aux joueurs suivants
                        this.erreur = "";//On vide l'affichage de l'erreur
                        if (this.compteurJoueur == 3) {//Si on a fait tous les joueurs
                            this.compteurJoueur = 2;//On retourne au dernier
                            this.partie.getInstance().setMoment(Moment.SecondPlacement);//On passe au second placement
                        }
                    } catch (ExceptionZone e1) {//Si il y a un problème
                        // TODO Auto-generated catch block
                        this.erreur = e1.getMessage(); //On charge l'affichae de l'erreur
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    this.erreur = "Erreur : Veuillez sélectionner un hexagone";
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            // *************** Si on est au Second placmement :  placement de 2 vaisseaux par joueurs  dans l'autre sens ***************
        } else if (this.partie.getInstance().getMoment() == Moment.SecondPlacement) {//Si second placement
            this.SelectionHexagone = true;//On met la selection d'hexagone sur active
            this.SelectionSecteur = false;

            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]);//On definit la couleur du texte en fct du joueur actuel
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez un hexagone pour y déplacer 2 vaisseaux<br><br>" + this.erreur + "</html>");

            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                if (this.CoordXCadreHexagone != -100 && this.CoordYCadreHexagone != -100) {
                    try {//Si un hexagone est selectionné
                        //On deplace la flotte du joueur
                        this.partie.getInstance().deplacerFlotteJoueur(this.compteurJoueur, this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone, 2);
                        this.partie.getInstance().deplacerFlotteJoueur(this.compteurJoueur, this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone, 3);
                        //On masque le cadre de l'hexagone
                        this.CoordXCadreHexagone = -100;
                        this.CoordYCadreHexagone = -100;
                        //on passe au joueur precedent
                        this.compteurJoueur -= 1;
                        this.erreur = "";//on supprime les potentielles erreurs
                        if (this.compteurJoueur == -1) { //Si on a fait tous les joueurs
                            this.compteurJoueur = 0; //On remet le J1 en actuel
                            this.partie.getInstance().setTour(1);//On met le numero du tour sur 1
                            this.partie.getInstance().setMoment(Moment.ChoixOrdre);//On definit le moment sur le choix des ordres

                        }
                    } catch (ExceptionZone e1) {
                        // TODO Auto-generated catch block
                        this.erreur = e1.getMessage();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    this.erreur = "Erreur : Veuillez sélectionner un hexagone";
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            // *************** Si on est au choix de l'ordre :  Chaque joueur humain choisit l'odre de ses cartes***************
        } else if ((this.partie.getInstance().getMoment() == Moment.ChoixOrdre) && (this.partie.getInstance().getListeJoueurs().get(compteurJoueur).getType() == TypeJoueur.HUMAIN)) {//Si c'est le moment de choisir l'ordre des cartes et que le joueur est un humain
            this.SelectionHexagone = false;//On définit les selections sur inactives
            this.SelectionSecteur = false;

            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]);//On définit la couleur du texte sur celle du joueur actif
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez l'ordre de vos cartes :<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Développement :<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exploration :<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Attaque : <br><br>" + this.erreur + "</html>");

            //On affiche le choix de l'ordre
            this.ChoixOrdreDeveloppement.setBounds(870, 350, 50, 20);
            this.add(ChoixOrdreDeveloppement);

            this.ChoixOrdreExploration.setBounds(810, 412, 50, 20);
            this.add(ChoixOrdreExploration);

            this.ChoixOrdreAttaque.setBounds(770, 475, 50, 20);
            this.add(ChoixOrdreAttaque);

            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                try {//On recupere les valeurs
                    int choixDeveloppement = (int) this.ChoixOrdreDeveloppement.getValue();
                    int choixExploration = (int) this.ChoixOrdreExploration.getValue();
                    int choixAttaque = (int) this.ChoixOrdreAttaque.getValue();
                    this.partie.phase1(compteurJoueur, choixDeveloppement, choixExploration, choixAttaque);//On lance la phase 1 de ce joeuur avec les valeurs
                    this.compteurJoueur += 1;//On passe au joueur suivant
                    this.erreur = "";//On supprimle les erreurs
                    if (this.compteurJoueur == 3) {//Si on a fait tous les joeuurs
                        this.compteurJoueur = 0;//On remet le joueur 1 en actuel
                        this.partie.getInstance().setMoment(Moment.RevelerCarte);//On passe a la revelation des cartes
                    }
                } catch (ExceptionChoixOrdre e1) {//En cas d'erreur, on l'ecrit
                    // TODO Auto-generated catch block
                    this.erreur = "Attention : Les choix doivent être tous différents, et compris entre 1 et 3";
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    this.updateFenetre();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        } else if ((this.partie.getInstance().getMoment() == Moment.ChoixOrdre) && (this.partie.getInstance().getListeJoueurs().get(this.compteurJoueur).getType() == TypeJoueur.VIRTUEL)) {//Si c'est le moment de choisir l'ordre des cartes et que le joueur est un joueur virtuel

            try {//On recupere les valeurs
                this.compteurJoueur += 1;//On passe au joueur suivant
                this.erreur = "";//On supprimle les erreurs
                if (this.compteurJoueur == 3) {//Si on a fait tous les joeuurs
                    this.compteurJoueur = 0;//On remet le joueur 1 en actuel
                    this.partie.getInstance().setMoment(Moment.RevelerCarte);//On passe a la revelation des cartes

                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (FontFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                this.updateFenetre();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (FontFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            // *************** Si on est a la revelation des cartes :  on affiche l'ordre des cartes a effectuer***************
        } else if (this.partie.getInstance().getMoment() == Moment.RevelerCarte) {//Si c'est le moment de reveler les cartes

            this.SelectionHexagone = false; //On met les selections sur inactives
            this.SelectionSecteur = false;
            this.partie.getInstance().resetListeActions();//On reset la liste des actions
            this.partie.getInstance().setListeAction();//On remplit et trie les actions a faire
            ArrayList<Action> listeActions = this.partie.getInstance().getListeActions();//On recupere la liste des actions
            this.labelInfos.setForeground(Color.white);//On met le texte en blanc
            String affichage = "<html>Voici l'odre des actions à effectuer :";//On affiche l'ordre a effectuer
            for (int i = 0; i < 9; i++) {
                if (i % 3 == 0) {
                    affichage = affichage + "<br>";
                }
                affichage = affichage + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + listeActions.get(i).getIntitule();
            }

            affichage = affichage + "</html>";
            this.labelInfos.setText(affichage);

            this.boutonValider.setText("Commencer les actions");
            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                try {
                    this.erreur = "";
                    this.compteurAction = 0;//on renseigne qu'on est a la première action
                    this.partie.getInstance().setMoment(Moment.nul);//On met le moment sur indéfini
                    // Copie chaque ligne de la matrice nbJoueursCarte pour chaque joueur
                    this.compteurJoueur = 0;
                    this.partie.getInstance().InitialiserNbActionsJoueurs();

                    this.partie.getInstance().phase2(this.compteurAction);//On lance la phase 2 sur la premiere action 

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    this.updateFenetre();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            // *************** Si on est a la fin d'un tour : on demande aux joueurs si ils veulent continuer ou enregistrer la partie***************
        } else if (this.partie.getInstance().getMoment() == Moment.DemandeEnregistrement) {//Si c'est le moment de reveler les cartes

            this.SelectionHexagone = false; //On met les selections sur inactives
            this.SelectionSecteur = false;
            this.labelInfos.setForeground(Color.white);//On met le texte en blanc
            String affichage = "<html>Le tour de jeu n°" + this.partie.getInstance().getTour() + " est terminé.<br><br> Souhaitez-vous continuer et passer au tour suivant, ou quitter et sauvegarder l'état actuel de la partie ?";
            this.labelInfos.setText(affichage);

            this.boutonContinuer.setBackground(Color.white);
            this.boutonContinuer.setBounds(1050, 750, 200, 50);
            this.add(this.boutonContinuer);

            this.boutonSauvegarder.setBackground(Color.white);
            this.boutonSauvegarder.setBounds(790, 750, 200, 50);
            this.add(this.boutonSauvegarder);

            //On defint l'action du bouton continuer
            if (this.boutonContinuer.getActionListeners().length != 0) {
                this.boutonContinuer.removeActionListener(this.boutonContinuer.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonContinuer.addActionListener(e -> {
                try {
                    this.compteurJoueur = 0; //On remet le J1 en actuel
                    this.partie.getInstance().setTour(this.partie.getInstance().getTour() + 1);//On met incrémente le numéro de tour
                    this.partie.getInstance().setMoment(Moment.ChoixOrdre);//On definit le moment sur le choix des ordres

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    this.updateFenetre();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            //On defint l'action du bouton sauvegarder
            if (this.boutonSauvegarder.getActionListeners().length != 0) {
                this.boutonSauvegarder.removeActionListener(this.boutonContinuer.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonSauvegarder.addActionListener(e -> {
                try {
                    this.partie.sauvegarderPartie();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    this.updateFenetre();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });

            // *************** Si on est au dernier scoring : on Effectue le scoring et affiche le classement***************
        } else if (this.partie.getInstance().getMoment() == Moment.DernierScoring) {//Si c'est le moment de reveler les cartes

            this.SelectionHexagone = false; //On met les selections sur inactives
            this.SelectionSecteur = false;
            this.labelInfos.setForeground(Color.white);//On met le texte en blanc
            String affichage = "<html>Le partie est terminée !.<br><br> Les scores finaux vont être calculés...";
            this.labelInfos.setText(affichage);

            this.boutonValider.setText("Afficher le classement");
            this.add(this.boutonValider);

            //On defint l'action du bouton continuer
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                this.setVisible(false);
                this.partie.getInstance().dernierScoring();
                this.partie.getInstance().effectuerClassement();
                try {
                    this.updateFenetre();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });

            // *************** Si on est au developpement :  on choisi la zone qui va developper ***************            
        } else if (this.partie.getInstance().getMoment() == Moment.Developpement) {
            System.out.println("\n Developpement");

            //le joeuur peut agir 4-N avec n qui est le nombre de joueur ayant choisi la même carte au même moment
            switch (this.compteurJoueur) {
                case 0:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur1()[0][this.partie.getInstance().getPrioEnCours()];

                    break;
                case 1:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur2()[0][this.partie.getInstance().getPrioEnCours()];
                    break;
                case 2:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur3()[0][this.partie.getInstance().getPrioEnCours()];
                    break;
            }

            this.SelectionHexagone = true;
            this.SelectionSecteur = false;
            ArrayList<Action> listeActions = this.partie.getInstance().getListeActions();//On recupere la liste des actions
            compteurJoueur = listeActions.get(compteurAction).getNumJoueur();

            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]);
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez l'hexagone à développer <br><br>" + this.erreur + "</html>");

            this.boutonValider.setText("Appliquer");
            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                if (this.CoordXCadreHexagone != -100 && this.CoordYCadreHexagone != -100) {
                    try {
                        this.partie.getInstance().developperVaisseauJoueur(this.compteurJoueur, this.partie.getInstance().getPrioEnCours(), this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone);

                        this.CoordXCadreHexagone = -100;
                        this.CoordYCadreHexagone = -100;
                        this.erreur = "";

                        //mise à jour du nombre d'actions par carte
                        switch (this.compteurJoueur) {
                            case 0:
                                this.partie.getInstance().diminuerNbActionsJoueur1(0, this.partie.getInstance().getPrioEnCours());

                                break;
                            case 1:
                                this.partie.getInstance().diminuerNbActionsJoueur2(0, this.partie.getInstance().getPrioEnCours());

                                break;
                            case 2:
                                this.partie.getInstance().diminuerNbActionsJoueur3(0, this.partie.getInstance().getPrioEnCours());

                                break;
                        }

                        //enlever une valeur au nb de cartes
                        if (this.nbActionRestantes == 1) {//on a fait les nb actions que le joueur doit faire

                            this.compteurAction++;
                            //System.out.println("compteur action"+compteurAction);
                            compteurJoueur = 0;
                            this.partie.getInstance().phase2(compteurAction); // on passe au prochain
                        }

                    } catch (ExceptionZone e1) {
                        // TODO Auto-generated catch block
                        this.erreur = e1.getMessage();
                    }
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    this.erreur = "Erreur : Veuillez sélectionner un hexagone";
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            // *************** Si on est a l'exploration : on choisi la zone à explorer ***************    
        } else if (this.partie.getInstance().getMoment() == Moment.Exploration) {
            System.out.println("Exploration");

            //le joueur peut agir 4-N avec n qui est le nombre de joueur ayant choisi la même carte au même moment
            switch (this.compteurJoueur) {
                case 0:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur1()[1][this.partie.getInstance().getPrioEnCours()];

                    break;
                case 1:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur2()[1][this.partie.getInstance().getPrioEnCours()];
                    break;
                case 2:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur3()[1][this.partie.getInstance().getPrioEnCours()];
                    break;
            }

            this.SelectionHexagone = true;
            this.SelectionSecteur = false;
            ArrayList<Action> listeActions = this.partie.getInstance().getListeActions();//On recupere la liste des actions
            compteurJoueur = listeActions.get(compteurAction).getNumJoueur();

            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]);
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez un hexagone pour explorer <br>Veuillez aller en ligne de commande pour choisir l'hexagone à explorer <br><br>" + this.erreur + "</html>");

            this.boutonValider.setText("Appliquer");
            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                if (this.CoordXCadreHexagone != -100 && this.CoordYCadreHexagone != -100) {
                    try {
                        this.partie.getInstance().explorerFlotteJoueur(this.compteurJoueur, this.partie.getInstance().getPrioEnCours(), this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone);

                        this.CoordXCadreHexagone = -100;
                        this.CoordYCadreHexagone = -100;
                        this.erreur = "";

                        //mise à jour du nombre d'actions par carte
                        switch (this.compteurJoueur) {
                            case 0:
                                this.partie.getInstance().diminuerNbActionsJoueur1(1, this.partie.getInstance().getPrioEnCours());

                                break;
                            case 1:
                                this.partie.getInstance().diminuerNbActionsJoueur2(1, this.partie.getInstance().getPrioEnCours());

                                break;
                            case 2:
                                this.partie.getInstance().diminuerNbActionsJoueur3(1, this.partie.getInstance().getPrioEnCours());
                                break;
                        }

                        //enlever une valeur au nb de cartes
                        if (this.nbActionRestantes == 1) {//on a fait les nb actions que le joueur doit faire

                            this.compteurAction++;
                            this.partie.getInstance().phase2(compteurAction); // on passe au prochain
                        }

                    } catch (ExceptionZone e1) {
                        // TODO Auto-generated catch block

                        this.erreur = e1.getMessage(); //On charge l'affichae de l'erreur

                    } catch (ExceptionManqueVaisseauxDisponibles e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    this.erreur = "Erreur : Veuillez sélectionner un hexagone";
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            // *************** Si on est a l'attaque : on choisi la zone à attaquer ***************  
        } else if (this.partie.getInstance().getMoment() == Moment.Attaque) {
            System.out.println("Attaque");

            //le joueur peut agir 4-N avec n qui est le nombre de joueur ayant choisi la même carte au même moment
            switch (this.compteurJoueur) {
                case 0:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur1()[2][this.partie.getInstance().getPrioEnCours()];

                    break;
                case 1:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur2()[2][this.partie.getInstance().getPrioEnCours()];
                    break;
                case 2:

                    this.nbActionRestantes = this.partie.getInstance().getNbActionsJoueur3()[2][this.partie.getInstance().getPrioEnCours()];
                    break;
            }

            this.SelectionHexagone = true;
            this.SelectionSecteur = false;
            ArrayList<Action> listeActions = this.partie.getInstance().getListeActions();//On recupere la liste des actions
            compteurJoueur = listeActions.get(compteurAction).getNumJoueur();

            
            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]);
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez un hexagone pour attaquer <br>Veuillez aller en ligne de commande pour choisir l'hexagone à attquer <br><br>" + this.erreur + "</html>");

            this.boutonValider.setText("Appliquer");
            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                if (this.CoordXCadreHexagone != -100 && this.CoordYCadreHexagone != -100) {
                    try {
                        this.partie.getInstance().attaquerFlotteJoueur(this.compteurJoueur, this.partie.getInstance().getPrioEnCours(), this.CoordXCadreSecteur, this.CoordYCadreSecteur, this.CoordXCadreHexagone, this.CoordYCadreHexagone);

                        this.CoordXCadreHexagone = -100;
                        this.CoordYCadreHexagone = -100;
                        this.erreur = "";

                        //mise à jour du nombre d'actions par carte
                        switch (this.compteurJoueur) {
                            case 0:
                                this.partie.getInstance().diminuerNbActionsJoueur1(2, this.partie.getInstance().getPrioEnCours());

                                break;
                            case 1:
                                this.partie.getInstance().diminuerNbActionsJoueur2(2, this.partie.getInstance().getPrioEnCours());

                                break;
                            case 2:
                                this.partie.getInstance().diminuerNbActionsJoueur3(2, this.partie.getInstance().getPrioEnCours());
                                break;
                        }

                        //enlever une valeur au nb de cartes
                        if (this.nbActionRestantes == 1) {//on a fait les nb actions que le joueur doit faire

                            this.compteurAction++;
                            this.partie.getInstance().phase2(compteurAction); // on passe au prochain
                        }

                    } catch (ExceptionZone e1) {
                        // TODO Auto-generated catch block

                        this.erreur = e1.getMessage(); //On charge l'affichae de l'erreur
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    this.erreur = "Erreur : Veuillez sélectionner un hexagone";
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            // *************** Scoring :  Choix des secteurs et accumulation des points ***************
        } else if (this.partie.getInstance().getMoment() == Moment.Scoring) {
            this.SelectionHexagone = false;
            this.SelectionSecteur = true;

            //this.partie.getInstance().sustainable();
            if (this.partie.getInstance().getJoueurTriTime() != 1) { //Si un joueur a le tri time

            }
            this.labelInfos.setForeground(this.listeCouleursTextJoueur[compteurJoueur]);
            this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.compteurJoueur) + "<br>Choisissez un secteur à comptabiliser<br><br>" + this.erreur + "</html>");

            this.add(this.boutonValider);

            //On defint l'action du bouton
            if (this.boutonValider.getActionListeners().length != 0) {
                this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
            }
            this.boutonValider.addActionListener(e -> {
                if (this.CoordXCadreSecteur != -100 && this.CoordYCadreSecteur != -100) {
                    try {
                        this.partie.getInstance().scoring(this.compteurJoueur, CoordXCadreSecteur, CoordYCadreSecteur);
                        this.CoordXCadreHexagone = -100;
                        this.CoordYCadreHexagone = -100;
                        this.compteurJoueur += 1;
                        this.erreur = "";
                        if (this.compteurJoueur == 3) {
                            this.compteurJoueur = 0;
                            this.partie.getInstance().setMoment(Moment.ScoringTriTime);

                        }
                    } catch (ExceptionScoring e1) {
                        // TODO Auto-generated catch block
                        this.erreur = "Impossible : " + e1.getMessage();
                        if (e1.getMessage() == "Plus de secteur disponible") {
                            this.compteurJoueur = 0;
                            try {
                                this.partie.getInstance().setMoment(Moment.ScoringTriTime);
                            } catch (IOException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            } catch (FontFormatException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    this.erreur = "Erreur : Veuillez sélectionner un secteur";
                    try {
                        this.updateFenetre();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (FontFormatException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            // *************** Scoring Tritime :  Choix d'un secteur supplementaire pour le joueur qui a le tritime ***************
        } else if (this.partie.getInstance().getMoment() == Moment.ScoringTriTime) {
            this.SelectionHexagone = false;
            this.SelectionSecteur = true;

            if (this.partie.getInstance().getJoueurTriTime() != -1) {//Si un joueur possede le tritime
                this.labelInfos.setForeground(this.listeCouleursTextJoueur[this.partie.getInstance().getJoueurTriTime()]);
                this.labelInfos.setText("<html>" + this.partie.getInstance().getNomJoueur(this.partie.getInstance().getJoueurTriTime()) + "<br>Vous possédez le Tri-Time. Selectionnez un second secteur<br><br>" + this.erreur + "</html>");

                this.add(this.boutonValider);

                //On defint l'action du bouton
                if (this.boutonValider.getActionListeners().length != 0) {
                    this.boutonValider.removeActionListener(this.boutonValider.getActionListeners()[0]); // Supprime le listener existant
                }
                this.boutonValider.addActionListener(e -> {
                    if (this.CoordXCadreSecteur != -100 && this.CoordYCadreSecteur != -100) {
                        try {
                            this.partie.getInstance().scoring(this.partie.getInstance().getJoueurTriTime(), CoordXCadreSecteur, CoordYCadreSecteur);
                            this.CoordXCadreHexagone = -100;
                            this.CoordYCadreHexagone = -100;
                            this.compteurJoueur = 0;
                            this.erreur = "";
                            if (this.partie.getInstance().getTour() > 8) {
                                this.partie.getInstance().debutTour();
                                this.partie.getInstance().setMoment(Moment.DernierScoring);
                            } else {
                                this.partie.getInstance().setMoment(Moment.DemandeEnregistrement);
                            }

                        } catch (ExceptionScoring e1) {
                            // TODO Auto-generated catch block
                            this.erreur = "Impossible : " + e1.getMessage();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (FontFormatException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try {
                            this.updateFenetre();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (FontFormatException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    } else {
                        this.erreur = "Erreur : Veuillez sélectionner un secteur";
                        try {
                            this.updateFenetre();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (FontFormatException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });
            } else {//Si pas de joueur au tritime
                if (this.partie.getInstance().getTour() > 8) {
                    this.partie.getInstance().setMoment(Moment.DernierScoring);
                } else {
                    this.partie.getInstance().debutTour();
                    this.partie.getInstance().setMoment(Moment.DemandeEnregistrement);
                }
            }

        }
        //On charge l'image des cadres de selection
        this.ImCadre = new ImagesPlateau("Cadre", "Cadre", 0);
        this.ImCadreHexagone = new ImagesPlateau("CadreHexagone", "Cadre hexagone", 0);
        this.ImCadreTriTime = new ImagesPlateau("CadreTriTime", "Cadre TriTime", 0);

        this.ImCadre.setCoordCadreSecteur(CoordXCadreSecteur, CoordYCadreSecteur);
        this.ImCadreHexagone.setCoordCadreHexagone(this.CoordXCadreHexagone, this.CoordYCadreHexagone);

        Font customFontIndicationTourEtPhase = Font.createFont(Font.TRUETYPE_FONT, new File("AUTRES RESSOURCES" + File.separator + "IMP2.otf"));
        customFontIndicationTourEtPhase = customFont.deriveFont(38f); // Taille de la police
        GraphicsEnvironment ge3 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //on affiche les infos de la partie
        String indicationTour = "Tour actuel : " + this.partie.getInstance().getTour() + "/9";
        JLabel labelTourActuel = new JLabel(indicationTour);

        labelTourActuel.setBounds(622, 2, 700, 50);
        labelTourActuel.setFont(customFontIndicationTourEtPhase);
        labelTourActuel.setForeground(new Color(0xe1c73d));

        this.add(labelTourActuel);

        String indicationPhase = "Phase actuelle : ";
        if (this.partie.getInstance().getMoment() == Moment.PremierPlacement || this.partie.getInstance().getMoment() == Moment.SecondPlacement) {
            indicationPhase = indicationPhase + "- - - - -";
        } else {
            if (this.partie.getInstance().getPhase() == 1) {
                indicationPhase = indicationPhase + "choix des ordres";
            } else if (this.partie.getInstance().getPhase() == 2) {
                indicationPhase = indicationPhase + "execution";
            } else { //pahse 3
                indicationPhase = indicationPhase + "scoring";
            }
        }
        JLabel labelPhaseActuelle = new JLabel(indicationPhase);

        labelPhaseActuelle.setBounds(622, 60, 800, 50);
        labelPhaseActuelle.setFont(customFontIndicationTourEtPhase);
        labelPhaseActuelle.setForeground(new Color(0xe1c73d));

        this.add(labelPhaseActuelle);
        //On affiche les cadres qui doivent l'etre
        if (this.SelectionHexagone) {
            if ((CoordXCadreHexagone == 2 && (CoordYCadreHexagone == 3 || CoordYCadreHexagone == 4 || CoordYCadreHexagone == 5)) || (CoordXCadreHexagone == 3 && CoordYCadreHexagone == 4)) {
                this.add(this.ImCadreTriTime.getLabel());
            } else {
                this.add(this.ImCadreHexagone.getLabel());
            }
        }
        if (this.SelectionSecteur) {
            this.add(this.ImCadre.getLabel());
        }

        //Affichage des vaisseaux
        for (int i = 0; i < 3; i++) {
            Joueur j = this.partie.getInstance().getListeJoueurs().get(i);
            List<Hexagone> listePlanete = j.getListePlanetesControlees();
            for (Hexagone hex : listePlanete) {

                int nb = hex.getNbVaisseaux();
                int x = hex.getPositionHexagone()[0];
                int y = hex.getPositionHexagone()[1];
                String[] listeCouleur = {"Rouge", "Bleu", "Jaune"};
                String couleur = listeCouleur[i];

                BufferedImage imgVaisseau = ImageIO.read(new File("AUTRES RESSOURCES" + File.separator + "ImagesPlateau" + File.separator + "Redimensionnees" + File.separator + "" + nb + couleur + ".png"));
                JLabel labelImgVaisseau = new JLabel(new ImageIcon(imgVaisseau));
                int newX;
                int newY;
                if (x % 2 == 0) {
                    newX = 36 + x * 82 + (x / 2);
                    newY = 9 + y * 96 + (y + 1) / 3;
                } else {
                    newX = 36 + x * 82 + ((x - 1) / 2);
                    newY = 57 + y * 96 + (y + 1) / 3;
                }
                labelImgVaisseau.setBounds(newY, newX, imgVaisseau.getWidth(), imgVaisseau.getHeight());
                this.add(labelImgVaisseau);
            }
        }

        this.add(Im1.getLabel());
        this.add(Im2.getLabel());
        this.add(Im3.getLabel());
        this.add(Im4.getLabel());
        this.add(Im5.getLabel());
        this.add(Im6.getLabel());
        this.add(Im7.getLabel());
        this.add(Im8.getLabel());
        this.add(Im9.getLabel());

        this.add(ImFond.getLabel());

    }

    public void updateFenetre() throws IOException, FontFormatException {
        // Reconstruire et redessiner la fenêtre
        this.getContentPane().removeAll(); // Supprimer les anciens composants
        this.setFenetre();                 // Reconstruire
        this.revalidate();                 // Informer Swing des modifications
        this.repaint();                    // Redessiner
    }

    public static void main(String[] args) throws IOException, FontFormatException {
    }

    @Override
    public void update(Observable o, Object arg) {
        // Reconstruire et redessiner la fenêtre

        this.getContentPane().removeAll(); // Supprimer les anciens composants
        try {
            this.setFenetre();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                 // Reconstruire
        this.revalidate();                 // Informer Swing des modifications
        this.repaint();                    // Redessiner

    }
}
