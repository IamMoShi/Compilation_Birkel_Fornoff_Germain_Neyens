package Grammar;

import Grammar.Rule.Expression;
import Grammar.Rule.Rule;
import Grammar.Token.GrammarToken;
import Grammar.Token.TerminalToken;
import Grammar.Token.NonTerminalToken;

import java.util.ArrayList;

public class GrammarTest {
    private Grammar grammar;

    private ArrayList<TerminalToken> terminalTokens;

    private int position;

    private TerminalToken currentToken;

    public GrammarTest() {
        grammar = new Grammar();
    }

    public boolean isCurrentToken(TerminalToken token) {
        return currentToken.getType().equals(token.getType());
    }

    public void nextToken() {
        position++;
        currentToken = terminalTokens.get(position);
    }

    public void test() {
        grammar = new Grammar();
        System.out.println("Test de la grammaire:");

        NonTerminalToken E0 = new NonTerminalToken("E0");
        grammar.addNonTerminal(E0);
        NonTerminalToken E1 = new NonTerminalToken("E1");
        grammar.addNonTerminal(E1);
        NonTerminalToken T0 = new NonTerminalToken("T0");
        grammar.addNonTerminal(T0);
        NonTerminalToken T1 = new NonTerminalToken("T1");
        grammar.addNonTerminal(T1);
        NonTerminalToken F0 = new NonTerminalToken("F0");
        grammar.addNonTerminal(F0);

        TerminalToken plus = new TerminalToken("+");
        grammar.addTerminal(plus);
        TerminalToken minus = new TerminalToken("-");
        grammar.addTerminal(minus);
        TerminalToken mult = new TerminalToken("*");
        grammar.addTerminal(mult);
        TerminalToken div = new TerminalToken("/");
        grammar.addTerminal(div);
        TerminalToken openP = new TerminalToken("(");
        grammar.addTerminal(openP);
        TerminalToken closeP = new TerminalToken(")");
        grammar.addTerminal(closeP);
        TerminalToken nb = new TerminalToken("nb");
        grammar.addTerminal(nb);
        TerminalToken empty = new TerminalToken("empty");
        grammar.addTerminal(empty);

        grammar.addRule((new Rule(E0, new Expression(new GrammarToken[]{T0, E1}))));

        Expression expE2_1 = new Expression();
        expE2_1.addToken(plus);
        expE2_1.addToken(T0);
        expE2_1.addToken(E1);
        Rule ruleE2_1 = new Rule(E1, expE2_1);
        grammar.addRule(ruleE2_1);

        Expression expE2_2 = new Expression();
        expE2_2.addToken(minus);
        expE2_2.addToken(T0);
        expE2_2.addToken(E1);
        Rule ruleE2_2 = new Rule(E1, expE2_2);
        grammar.addRule(ruleE2_2);

        Expression expE2_3 = new Expression();
        expE2_3.addToken(empty);
        Rule ruleE2_3 = new Rule(E1, expE2_3);
        grammar.addRule(ruleE2_3);

        Expression expT0_1 = new Expression();
        expT0_1.addToken(F0);
        expT0_1.addToken(T1);
        Rule ruleT0_1 = new Rule(T0, expT0_1);
        grammar.addRule(ruleT0_1);

        Expression expT1_1 = new Expression();
        expT1_1.addToken(mult);
        expT1_1.addToken(F0);
        expT1_1.addToken(T1);
        Rule ruleT2_1 = new Rule(T1, expT1_1);
        grammar.addRule(ruleT2_1);

        Expression expT1_2 = new Expression();
        expT1_2.addToken(div);
        expT1_2.addToken(F0);
        expT1_2.addToken(T1);
        Rule ruleT2_2 = new Rule(T1, expT1_2);
        grammar.addRule(ruleT2_2);

        Expression expT1_3 = new Expression();
        expT1_3.addToken(empty);
        Rule ruleT2_3 = new Rule(T1, expT1_3);
        grammar.addRule(ruleT2_3);

        Expression expF0_1 = new Expression();
        expF0_1.addToken(openP);
        expF0_1.addToken(E0);
        expF0_1.addToken(closeP);
        Rule ruleF0_1 = new Rule(F0, expF0_1);
        grammar.addRule(ruleF0_1);

        Expression expF0_2 = new Expression();
        expF0_2.addToken(nb);
        Rule ruleF0_2 = new Rule(F0, expF0_2);
        grammar.addRule(ruleF0_2);

        System.out.println(grammar.toString());

    }


    public void test2() {
        grammar = new Grammar();

        System.out.println("Test de la grammaire:");
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

        grammar.addNonTerminal(E0.setAction(() -> {
            T0.execute();
            E1.execute();
        }));

        grammar.addNonTerminal(E1.setAction(() -> {
            if (isCurrentToken(plus)) {
                nextToken();
                T0.execute();
                E1.execute();
            } else if (isCurrentToken(minus)) {
                nextToken();
                T0.execute();
                E1.execute();
            } else if (isCurrentToken(empty)) {
                System.out.println("Epsilon");
            }
        }));

        grammar.addNonTerminal(T0.setAction(() -> {
            F0.execute();
            T1.execute();
        }));

        grammar.addNonTerminal(T1.setAction(() -> {
            if (isCurrentToken(mult)) {
                nextToken();
                F0.execute();
                T1.execute();
            } else if (isCurrentToken(div)) {
                nextToken();
                F0.execute();
                T1.execute();
            } else if (isCurrentToken(empty)) {
                System.out.println("Epsilon");
            }
        }));

        grammar.addNonTerminal(F0.setAction(() -> {
            if (isCurrentToken(openP)) {
                nextToken();
                E0.execute();
                if (isCurrentToken(closeP)) {
                    nextToken();
                } else {
                    System.out.println("Erreur: ) attendu");
                }
            } else if (isCurrentToken(nb)) {
                nextToken();
            } else {
                System.out.println("Erreur: ( ou nb attendu");
            }
        }));
    }

}
