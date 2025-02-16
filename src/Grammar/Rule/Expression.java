package Grammar.Rule;

import Grammar.Token.GrammarToken;

import java.util.ArrayList;
import java.util.Collections;

public class Expression {
    private final ArrayList<GrammarToken> tokens;

    public Expression() {
        this.tokens = new ArrayList<>();
    }

    public  Expression(GrammarToken[] tokens) {
        this.tokens = new ArrayList<>();
        Collections.addAll(this.tokens, tokens);
    }

    public void addToken(GrammarToken token) {
        this.tokens.add(token);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (GrammarToken token : this.tokens) {
            str.append(token.toString()).append(" ");
        }
        return str.toString();
    }
}
