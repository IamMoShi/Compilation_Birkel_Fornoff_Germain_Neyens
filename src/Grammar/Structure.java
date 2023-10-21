package Grammar;

import java.util.ArrayList;

public class Structure extends Type {
    private static ArrayList<Structure> structures = new ArrayList<>();
    private ArrayList<VariableToken> variableTokens = new ArrayList<>();

    public Structure(String name) {
        super(name);
        structures.add(this);
    }

    // Setters

    public void setVariables(ArrayList<VariableToken> variableTokens) {
        this.variableTokens = variableTokens;
    }

    public void addVariable(VariableToken variableToken) {
        this.variableTokens.add(variableToken);
    }

    public void removeVariable(VariableToken variableToken) {
        this.variableTokens.remove(variableToken);
    }

    // Getters

    public ArrayList<VariableToken> getVariables() {
        return variableTokens;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }
}
