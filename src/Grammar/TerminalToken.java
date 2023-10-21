package Grammar;

import java.util.ArrayList;

public class TerminalToken extends GrammarToken {
    private static ArrayList<TerminalToken> terminalesValues = new ArrayList<>();

    public TerminalToken(String value) {
        super(value);
        terminalesValues.add(this);
    }

    public ArrayList<TerminalToken> geTerminalesValues() {
        return terminalesValues;
    }

}
