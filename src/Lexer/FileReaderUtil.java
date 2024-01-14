package Lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtil {

    public static String lireFichier(String nomFichier) {
        StringBuilder contenu = new StringBuilder();

        try {
            // Créez un BufferedReader pour lire le fichier
            BufferedReader lecteur = new BufferedReader(new FileReader(nomFichier));
            String ligne;

            // Lisez le fichier ligne par ligne et ajoutez-le au contenu
            while ((ligne = lecteur.readLine()) != null) {
                contenu.append(ligne);
                contenu.append(System.lineSeparator()); // Ajoute un saut de ligne pour chaque ligne lue
            }

            // Fermez le BufferedReader après utilisation
            lecteur.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contenu.toString();
    }

    public static void main(String[] args) {
        String nomFichier = "./src/Lexer/lexerTextToRead3.txt";
        String contenuFichier = lireFichier(nomFichier);

        if (contenuFichier.isEmpty()) {
            System.out.println("Le fichier est vide.");
        } else {
            System.out.println("Contenu du fichier :");
            System.out.println(contenuFichier);
        }
    }
}
