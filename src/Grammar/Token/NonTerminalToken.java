package Grammar.Token;

import Grammar.Action;

import java.util.ArrayList;

public class NonTerminalToken extends GrammarToken {

    private static final ArrayList<NonTerminalToken> nonTerminalValues = new ArrayList<>();

    private Action action;

    public NonTerminalToken(String value) {
        super(value);
        nonTerminalValues.add(this);
    }

    public NonTerminalToken setAction(Action action) {
        this.action = action;
        return this;
    }

    public Action getAction() {
        return this.action;
    }

    public void execute() {
        if (this.action == null) {
            return;
        }
        this.action.execute();
    }

    public ArrayList<NonTerminalToken> getNonTerminalValues() {
        return nonTerminalValues;
    }

}
