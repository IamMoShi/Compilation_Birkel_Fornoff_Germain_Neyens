package Grammar;

import java.util.ArrayList;

public class Structure extends Type {
    private final ArrayList<Structure> structures = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();

    public Structure(String name) {
        super(name);
        structures.add(this);
    }

    // Setters

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    public void removeVariable(Variable variable) {
        this.variables.remove(variable);
    }

    // Getters

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }
}
