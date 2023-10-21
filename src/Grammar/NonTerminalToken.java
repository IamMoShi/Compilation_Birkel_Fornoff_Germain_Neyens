package Grammar;

import java.util.ArrayList;

public class NonTerminalToken extends GrammarToken {

    private final ArrayList<NonTerminalToken> nonTerminalValues = new ArrayList<>();

    public NonTerminalToken(String value) {
        super(value);
        nonTerminalValues.add(this);
    }

    public ArrayList<NonTerminalToken> getNonTerminalValues() {
        return nonTerminalValues;
    }

}
