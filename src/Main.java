import Grammar.AdaGrammar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // Spécifiez le chemin du dossier contenant les fichiers
        String folderPath = "Examples";

        // Créez une liste pour stocker le contenu des fichiers
        List<String> filesContent = new ArrayList<>();

        // Obtenez la liste des fichiers dans le dossier
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // Parcourez les fichiers et lisez leur contenu
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    // Lisez le contenu du fichier
                    String content = readFileContent(file);
                    // Ajoutez le contenu à la liste
                    filesContent.add(content);
                }
            }
        }

        // Vérifiez si la liste n'est pas vide avant d'appeler la méthode grammar1
        if (!filesContent.isEmpty()) {
            AdaGrammar grammar = new AdaGrammar();
            grammar.grammar1(filesContent.get(0)); // Vous pouvez également parcourir la liste si nécessaire
        } else {
            System.out.println("La liste de contenu des fichiers est vide.");
        }
    }

    // Méthode pour lire le contenu d'un fichier
    private static String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }




}
