package AST;
import Grammar.Type.Type;

import java.util.List;
import java.util.ArrayList;

public class Node {
    private final String value;
    private final Type type;
    private final List<Node> children;

    public Node(String value, Type type, List<Node> children) {
        this.value = value;
        this.type = type;
        this.children = children != null ? children : new ArrayList<>();
    }

    public void addChildren(Node child) {
        this.children.add(child);
    }

    //getters
    public String getValue() {
        return this.value;
    }

    public String getType() {
        return this.type.getName();
    }

    public List<Node> getChildren() {
        return this.children;
    }

    //Affiche l'arbre avec une visualisation dans la console
    public void printTree(int depth) {
        System.out.println(" ".repeat(depth) + this.value);
        for (Node child : this.children) {
            child.printTree(depth + 1);
        }
    }
}