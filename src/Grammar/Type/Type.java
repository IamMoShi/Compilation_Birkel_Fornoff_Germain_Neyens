package Grammar.Type;

import java.util.ArrayList;

public class Type {
    private static final ArrayList<Type> types = new ArrayList<>();

    private String name;

    public Type(String name) {
        this.name = name;
        types.add(this);
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    // Getters

    public String getName() {
        return name;
    }

    public static ArrayList<Type> getTypes() {
        return types;
    }

    public String toString() {
        return this.name;
    }

}
