package Grammar;

import Grammar.Token.NonTerminalToken;
import Grammar.Token.TerminalToken;
import Lexer.Lexer;

import java.util.Objects;

public class GrammarDemo {

    private void print(String s) {
        System.out.println(s);
    }

    /*
    public void demo1(String entry) {
        Grammar grammar = new Grammar();
        Lexer lexer = new Lexer(entry);

        try {
            lexer.tokenize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Test pour la grammaire : a^(n)b^(n)");

        NonTerminalToken S = new NonTerminalToken("S");

        grammar.addNonTerminal(S.setAction(() -> {
            if (Objects.equals(lexer.getCurrentToken().getValue(), "a")) {
                System.out.println("S -> aSb");
                lexer.nextToken();
                S.execute();
                if (Objects.equals(lexer.getCurrentToken().getValue(), "b")) {
                    lexer.nextToken();
                } else {
                    System.out.println("Erreur");
                }
            } else {
                System.out.println("S -> epsilon");
            }

        }));

        S.execute();
        System.out.println("Test passé avec succès");
    }


    public void demo2(String entry) {
        Grammar grammar = new Grammar();
        Lexer lexer = new Lexer(entry);

        try {
            lexer.tokenize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(lexer.getTokens());
        System.out.println("Test pour la grammaire : a^(n)b^(n)");

        NonTerminalToken E0 = new NonTerminalToken("E0");
        NonTerminalToken E1 = new NonTerminalToken("E1");
        NonTerminalToken T0 = new NonTerminalToken("T0");
        NonTerminalToken T1 = new NonTerminalToken("T1");
        NonTerminalToken F0 = new NonTerminalToken("F0");

        TerminalToken plus = new TerminalToken("+");
        TerminalToken minus = new TerminalToken("-");
        TerminalToken mult = new TerminalToken("*");
        TerminalToken div = new TerminalToken("/");
        TerminalToken openP = new TerminalToken("(");
        TerminalToken closeP = new TerminalToken(")");
        TerminalToken nb = new TerminalToken("nb");
        TerminalToken empty = new TerminalToken("empty");

        E0.setAction(() -> {
            System.out.println("E0 -> T0 E1");
            T0.execute();
            E1.execute();
        });

        E1.setAction(() -> {
            if (!lexer.stillAvailableToken()) {
                System.out.println("E1 -> epsilon");
                return;
            }
            if (lexer.getCurrentToken().getValue().equals(plus.getValue())) {
                System.out.println("E1 -> + T0 E1");
                lexer.nextToken();
                T0.execute();
                E1.execute();
            } else {
                System.out.println("E1 -> epsilon");
            }
        });

        T0.setAction(() -> {
            System.out.println("T0 -> F0 T1");
            F0.execute();
            T1.execute();
        });

        T1.setAction(() -> {
            if (!lexer.stillAvailableToken()) {
                System.out.println("T1 -> epsilon");
                return;
            }
            if (lexer.getCurrentToken().getValue().equals(mult.getValue())) {
                System.out.println("T1 -> * F0 T1");
                lexer.nextToken();
                F0.execute();
                T1.execute();
            } else {
                System.out.println("T1 -> epsilon");
            }
        });

        F0.setAction(() -> {
            if (lexer.getCurrentToken().getType().getName().equals("INTEGER")) {
                System.out.println("F0 -> nb");
                lexer.nextToken();
            } else if (lexer.getCurrentToken().getValue().equals(openP.getValue())) {
                System.out.println("F0 -> ( E0 )");
                lexer.nextToken();
                E0.execute();
                if (lexer.getCurrentToken().getValue().equals(closeP.getValue())) {
                    lexer.nextToken();
                } else {
                    System.out.println("Erreur");
                }
            } else {
                System.out.println("Erreur");
            }

        });

        E0.execute();
        System.out.println("Test passé avec succès");
    }
    */
}
