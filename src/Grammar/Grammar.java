package Grammar;

import Grammar.Rule.Rule;
import Grammar.Token.TerminalToken;
import Grammar.Token.NonTerminalToken;

import java.util.ArrayList;

public class Grammar {
    private ArrayList<TerminalToken> terminalesValues;
    private ArrayList<NonTerminalToken> nonTerminalValues;
    private ArrayList<Rule> rules;

    public Grammar() {
        terminalesValues = new ArrayList<>();
        nonTerminalValues = new ArrayList<>();
        rules = new ArrayList<>();
    }

    public void addTerminal(TerminalToken terminal) {
        terminalesValues.add(terminal);
    }

    public void addNonTerminal(NonTerminalToken nonTerminal) {
        nonTerminalValues.add(nonTerminal);
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public String showTerminals() {
        StringBuilder str = new StringBuilder();
        str.append("Terminaux: \n");
        for (TerminalToken terminal : terminalesValues) {
            str.append(terminal.toString()).append("\n");
        }
        return str.toString();
    }

    public String showNonTerminals() {
        StringBuilder str = new StringBuilder();
        str.append("Non terminaux: \n");
        for (NonTerminalToken nonTerminal : nonTerminalValues) {
            str.append(nonTerminal.toString()).append("\n");
        }
        return str.toString();
    }

    public String showRules() {
        StringBuilder str = new StringBuilder();
        str.append("Règles: \n");
        for (Rule rule : rules) {
            str.append(rule.toString()).append("\n");
        }
        return str.toString();
    }

    public String toString() {
        return showTerminals() +
                showNonTerminals() +
                showRules();
    }
}
