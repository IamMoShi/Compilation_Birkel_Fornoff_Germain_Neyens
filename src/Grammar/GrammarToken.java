package Grammar;

import java.util.ArrayList;

public abstract class GrammarToken {
    private final ArrayList<GrammarToken> values = new ArrayList<>();
    private String value;

    private void newToken() {
        // Check if the value is in the list of tokens values
        if (!values.contains(this.value)) {
            // Add the value to the list of tokens values
            values.add(this);
        }
    }

    public GrammarToken(String value) {
        this.value = value;
        newToken();
    }

    public String getValue() {
        return value;
    }

    public ArrayList<GrammarToken> getValues() {
        return values;
    }
}
