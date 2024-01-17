import Grammar.AdaGrammar;
import Grammar.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Desktop;
//import guru.nidi.graphviz.engine.Format;
//import guru.nidi.graphviz.engine.Graphviz;
//import guru.nidi.graphviz.model.MutableGraph;
//import static guru.nidi.graphviz.model.Factory.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Spécifiez le chemin du dossier contenant les fichiers
        String folderPath = "Examples";

        // Créez une liste pour stocker le contenu des fichiers
        List<String> filesContent = new ArrayList<>();

        // Obtenez la liste des fichiers dans le dossier
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    String content = readFileContent(file);
                    filesContent.add(content);
                }
            }
        }

        if (!filesContent.isEmpty()) {
            AdaGrammar grammar = new AdaGrammar();
            grammar.grammar1(filesContent.get(0)); // Vous pouvez également parcourir la liste si nécessaire
        } else {
            System.out.println("La liste de contenu des fichiers est vide.");
        }

        // à décommenter
        //AdaGrammar grammar = new AdaGrammar();
        //grammar.grammar1(filesContent.get(0)); // Lancer l'analyse syntaxique

        //grammar.finishDotFile(); // Finaliser et écrire le fichier .dot

        //monArbre();

    }

    private static void monArbre() {
        try {
            String dotPath = "src/monArbre.dot";
            String outputPath = "src/monArbre.png";
            String[] cmd = new String[]{"dot", "-Tpng", "-o", outputPath, dotPath}; // créeation de la commande

            Runtime rt = Runtime.getRuntime(); // Obtenir l'instance de Runtime
            Process proc = rt.exec(cmd);

            // Lire la sortie standard et d'erreur
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String s;
            while ((s = stdInput.readLine()) != null) { // essentiel
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) { // essentiel
                System.err.println(s);
            }
            proc.waitFor();
            Desktop.getDesktop().open(new File(outputPath));
        } catch (Exception e) {
            e.printStackTrace();
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