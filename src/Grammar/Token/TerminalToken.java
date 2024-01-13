package Grammar.Token;

import Grammar.Type.Type;

import java.util.ArrayList;

public class TerminalToken extends GrammarToken {
    private static final ArrayList<TerminalToken> terminalesValues = new ArrayList<>();
    private Type type;
    private int line;

    public TerminalToken(String value) {
        super(value);
        terminalesValues.add(this);
        line = 0;
    }

    public ArrayList<TerminalToken> getTerminalesValues() {
        return terminalesValues;
    }

    public Type getType() {
        return type;
    }
    public void setType(String name) {
        this.type = new Type(name);
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

}
