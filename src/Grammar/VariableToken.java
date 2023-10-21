package Grammar;

import java.util.ArrayList;

public class VariableToken extends TerminalToken{
    private static ArrayList<VariableToken> VARIABLE_TOKENS = new ArrayList<>();

    private String name;
    private Type type;
    private String value;


    public VariableToken(String name) {
        super(name);
        this.name = name;
        this.type = null;
        this.value = null;
        VARIABLE_TOKENS.add(this);
    }

    public VariableToken(String name, Type type) {
        super(name);
        this.name = name;
        this.type = type;
        this.value = null;
        VARIABLE_TOKENS.add(this);
    }

    public VariableToken(String name, Type type, String value) {
        super(name);
        this.name = name;
        this.type = type;
        this.value = value;
        VARIABLE_TOKENS.add(this);
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // Getters

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
