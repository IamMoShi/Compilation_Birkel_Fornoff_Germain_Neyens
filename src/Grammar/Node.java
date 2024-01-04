package Grammar;

import java.util.ArrayList;
import java.util.Iterator;


public class Node {
    private String rule;
    private boolean failed;
    private StringBuilder failedExplanation;
    private ArrayList<Node> children;

    public Node(String rule) {
        this.rule = rule;
        this.failed = false;
        this.failedExplanation = new StringBuilder();
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String getRule() {
        return rule;
    }

    public boolean isFailed() {
        return failed;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }


    public String getFailedExplanation() {
        return failedExplanation.toString();
    }

    public void addFailedExplanation(String failedExplanation) {
        this.failedExplanation.append(failedExplanation);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        // Utilisation de séquences d'échappement ANSI pour la coloration du texte
        String color = failed ? "\u001B[31m" : "\u001B[0m"; // Rouge si échec, sinon par défaut

        buffer.append(prefix);
        buffer.append(color);
        buffer.append(failed ? failedExplanation.toString() : rule);
        buffer.append("\u001B[0m"); // Réinitialiser la couleur après le texte
        buffer.append('\n');

        if (children != null) {
            for (Iterator<Node> it = children.iterator(); it.hasNext();) {
                Node next = it.next();
                if (next != null) {
                    if (it.hasNext()) {
                        next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
                    } else {
                        next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
                    }
                }
            }
        }
    }


}
