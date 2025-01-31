package classes;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
/**
 * Cette classe permet d'ouvrir le fichier PDF des credits
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class OpenPDF {
    public static void main(String[] args) {
        // Remplacez ce chemin par le chemin absolu du fichier PDF sur votre système
        String filePath = "chemin/vers/votre/fichier.pdf";

        try {
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
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du fichier PDF : " + e.getMessage());
        }
    }
}
