import Grammar.AdaGrammar;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        // Obtenir le répertoire de travail courant
        // String repertoireCourant = System.getProperty("user.dir");


        AdaGrammar grammar = new AdaGrammar();
        grammar.grammar1("with Ada.Text_IO ; use Ada.Text_IO ;\n" +
                "\n" +
                "procedure unDebut is\n" +
                "    function aireRectangle (larg : integer; long : integer) return integer is\n" +
                "    aire: integer;\n" +
                "    begin\n" +
                "        aire := larg * long ;\n" +
                "        return aire\n" +
                "    end aireRectangle ;\n" +
                "\n" +
                "\n" +
                "    function perimetreRectangle(larg : integer; long : integer) return integer is\n" +
                "    p : integer\n" +
                "    begin\n" +
                "        p := larg2 + long2 ;\n" +
                "        return p\n" +
                "    end perimetreRectangle;\n" +
                "\n" +
                "        -- VARIABLES\n" +
                "choix : integer ;\n" +
                "        -- PROCEDURE PRINCIPALE\n" +
                "    begin\n" +
                "        choix := 2.1;\n" +
                "        if choix = 1\n" +
                "            then valeur := perimetreRectangle(2, 3) ;\n" +
                "            else valeur := aireRectangale(2, 3) ;\n" +
                "        end if;\n" +
                "    end unDebut ; ");
    }
}