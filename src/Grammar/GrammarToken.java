package Grammar;

import java.util.ArrayList;

public abstract class GrammarToken {
    private static final ArrayList<GrammarToken> values = new ArrayList<>();
    private String value;

    public GrammarToken(String value) {
        this.value = value;
        values.add(this);
    }

    public String getValue() {
        return value;
    }

    public ArrayList<GrammarToken> getValues() {
        return values;
    }

    public String toString() {
        return this.value;
    }
}
