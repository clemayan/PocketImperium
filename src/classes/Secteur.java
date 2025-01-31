package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un secteur du plateau. Elle permet de définir les 9
 * zones du plateau.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Secteur implements Serializable {

    private Type type;
    private Coordonees position;
    private boolean dejaInverse=false;
    private Hexagone[][] grilleHexagones; // Une matrice d'hexagones (par exemple, 3x3)
    private int[][] tableauSecteurs = new int[3][3]; //tableau avec les positions des cartes déjà attribuées
    private List<Secteur> secteursAdjacents = new ArrayList<Secteur>();
    private String nomImage;
    private boolean estOccupe;
    private boolean aEteCompte = false;

    /**
     *
     * Constructeur qui instancie un nouvel objet de type {@link Secteur}
     * @param type
     * @param nomImage
     */
    public Secteur(Type type, String nomImage) {
        //Prépare les coordonnées en (0;0) en attente d'une affectation
        this.position = new Coordonees();
        this.type = type;
        this.nomImage=nomImage;
        // Initialiser une grille d'hexagone 
        grilleHexagones = new Hexagone[3][2];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                grilleHexagones[x][y] = new Hexagone(x, y, this, 0); // Crée chaque hexagone avec ses coordonnées

            }
        }
    }

    /**
     * Méthode pour récupérer un hexagone à une position donnée
     */
    public Hexagone getHexagone(int x, int y) {
        if (x >= 0 && x < 3 && y >= 0 && y < 2) {
            return grilleHexagones[x][y];
        }
        return null; // Si la position est hors limites
    }
    
    /**
     * Méthode qui inverse les points d'un secteur (si celui ci est un bord en bas).
     * Le test de savoir si le secteuyr a deja été inversé (pour pas le faire plusieurs fois) se fait dans la méthode.
     */
    public void inverser() {
    	if (this.dejaInverse==false) {
            int c=this.getHexagone(0, 0).getPoints();
            int d=this.getHexagone(0, 1).getPoints();
            int a=this.getHexagone(2, 0).getPoints();
            int b=this.getHexagone(2, 1).getPoints();
            this.getHexagone(0, 0).setPoints(a);
            this.getHexagone(0, 1).setPoints(b);
            this.getHexagone(2, 0).setPoints(c);
            this.getHexagone(2, 1).setPoints(d);
            
            this.dejaInverse=true;
    	}
    }

    public Type getType() {
        return this.type;
    }
    
    public String getNomImage() {
    	return this.nomImage;
    }
    
    /**
     * Permet de définir si le secteur a déja été comptabilisé
     * @param aEteCompte : boolean
     */
    public void setAEteCompte(boolean aEteCompte) {
    	this.aEteCompte=aEteCompte;
    }
    
    /**
     * Permet de savoir si le secteur a déja été comptabilisé
     * @return boolean
     */
    public boolean getAEteCompte() {
    	return this.aEteCompte;
    }
    
    /**
     * Permet de definir si un secteur est occupé
     */
    public void setEstOccupe(boolean estOccupe) {
        this.estOccupe = estOccupe;
    }
    
    /**
     * Permet de savoir si un secteur est occupé
     * @return Boolean
     */
    public boolean isEstOccupe() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                if (this.grilleHexagones[x][y].getControler()!=null){
                    this.estOccupe=true; 
                    return this.estOccupe;
                }
            }
        }
        this.estOccupe = false;
        return this.estOccupe;

    }

    /**
     * Met à jour la matrice des coordonées atribuées.
     *
     * @param positionSecteurs La matrice qui représente le plateau avec les
     * zones déjà prises par un secteur.
     * @return La matrice des psoitions atribuées
     */
    public int[][] choixPlacement(int[][] positionSecteurs) {
        //choix de la position selon le type de Secteur 
        if (this.type == Type.BORD) {
            this.position.setBord(positionSecteurs);
        } else if (this.type == Type.COTE) {
            this.position.setCote(positionSecteurs);
        } else {
            this.position.setTriTime(positionSecteurs);
        }

        return positionSecteurs;
    }

    /**
     * Trouver les secteurs adjacents à ce secteur
     *
     * @param tousLesSecteurs Liste de tous les secteurs du plateau
     * @return Liste des secteurs adjacents
     */
    public List<Secteur> getSecteursAdjacents() {
        List<Secteur> secteursAdjacents = new ArrayList<>();

        // Coordonnées des 6 directions dans une grille hexagonale
        int[][] directions = {
            {1, 0}, // Droite
            {-1, 0}, // Gauche
            {0, 1}, // Haut-droit
            {0, -1}, // Bas-gauche
            {1, -1}, // Bas-droit
            {-1, 1} // Haut-gauche
        };

        // Position actuelle du secteur
        int x = this.position.getPositionX();
        int y = this.position.getPositionY();

        // Parcourir toutes les directions pour trouver les secteurs adjacents
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            // Vérifier si un secteur existe à cette position
            for (Secteur secteur : Partie.getInstance().getListeSecteurs()) {
                if (secteur.position.getPositionX() == nx && secteur.position.getPositionY() == ny) {
                    secteursAdjacents.add(secteur);
                    break;
                }
            }
        }

        return this.secteursAdjacents;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (this.type) {
            case Type.BORD:
                sb.append("bord");
                break;
            case Type.TRITIME:
                sb.append("tritime");
                break;
            case Type.COTE:
                sb.append("coté");
                break;
        }

        sb.append(this);
        return sb.toString();
    }

    public Coordonees getPosition() {
        return this.position;
    }

}
