package Grammar;

import java.util.ArrayList;

public class Variable {
    private final ArrayList<Variable> variables = new ArrayList<>();

    private String name;
    private Type type;
    private String value;

    public Variable(String name, Type type) {
        this.name = name;
        this.type = type;
        this.value = null;
        variables.add(this);
    }

    public Variable(Type type, String value) {
        this.name = null;
        this.type = type;
        this.value = value;
        variables.add(this);
    }

    public Variable(String name, Type type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
        variables.add(this);
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
