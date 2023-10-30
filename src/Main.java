import Grammar.GrammarTest;
import Lexer.FileReaderUtil;
import Lexer.Lexer;
import Error.LexerError;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static void main(String[] args) throws LexerError {
        // Obtenir le répertoire de travail courant
        String repertoireCourant = System.getProperty("user.dir");

        // Créer le chemin complet du fichier en utilisant le répertoire courant
        String cheminComplet = repertoireCourant + "/" + "src/Lexer/lexerTextToRead.txt";
        System.out.println(cheminComplet);

        String sourceCode = FileReaderUtil.lireFichier(cheminComplet);
        System.out.println(sourceCode);

        Lexer lexer = new Lexer(sourceCode);
        lexer.tokenize();
        lexer.printTokensTypes();


        // Test de la grammaire
        GrammarTest grammarTest = new GrammarTest();
        grammarTest.test();

    }
}