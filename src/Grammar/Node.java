package Grammar;

import Grammar.Token.GrammarToken;
import Grammar.Token.TerminalToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Node {
    private String rule;
    private boolean failed;
    private StringBuilder failedExplanation;
    private ArrayList<Node> children;
    private int status;
    private GrammarToken token;

    public Node(String rule, GrammarToken token) {
        this.rule = rule;
        this.failed = false;
        this.failedExplanation = new StringBuilder();
        this.children = new ArrayList<>();
        this.status = 0;
        this.token = token;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
        this.status = 1;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void addFailedExplanation(String failedExplanation) {
        this.failedExplanation.append(failedExplanation);
    }

    public GrammarToken getToken() {
        return token;
    }

    public void setChildren(List<Node> children) {
        this.children = new ArrayList<>(children);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        String color = switch (status) {
            case 0 -> // Status 0 (blanc)
                    "\u001B[0m"; // Blanc
            case 1 -> // Status 1 (erreur)
                    "\u001B[31m"; // Rouge
            case 2 -> // Status 2 (autre couleur)
                    "\u001B[32m"; // Vert
            case 3 -> // Status 3 (autre couleur)
                    "\u001B[94m"; // Bleu clair
            case 4 -> // Status 4 (autre couleur)
                    "\u001B[34m"; // Bleu
            default -> "\u001B[0m"; // Blanc par défaut
        };

        buffer.append(prefix);
        buffer.append(color);
        buffer.append(failed ? failedExplanation.toString() : rule);
        buffer.append("\u001B[0m"); // Réinitialiser la couleur après le texte
        buffer.append('\n');

        if (children != null) {
            for (Iterator<Node> it = children.iterator(); it.hasNext(); ) {
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

    public Node removeEpsilon() {
        return removeEpsilon(this);
    }

    private Node removeEpsilon(Node racine) {
        if (racine == null) {
            return null;
        }

        if (racine.getStatus() == 3) {
            return null;
        }

        List<Node> descendants = new ArrayList<>();
        for (Node descendant : racine.getChildren()) {
            Node newDescendant = removeEpsilon(descendant);
            if (newDescendant != null) {
                descendants.add(newDescendant);
            }
        }

        racine.setChildren(descendants);

        if (racine.status == 2 && descendants.isEmpty()) {
            return null;
        }

        return racine;
    }

    public Node removeTerminalNotUseful() {
        return removeTerminalNotUseful(this);
    }

    private Node removeTerminalNotUseful(Node racine) {
        if (racine == null) {
            return null;
        }

        Node newRacine = new Node(racine.getRule(), racine.getToken());
        newRacine.setStatus(racine.getStatus());

        if (racine.getChildren().isEmpty()) {
            // Si c'est un nœud terminal avec la valeur "IDENTIFIER", le conserver
            TerminalToken token = (TerminalToken) racine.getToken();

            if ("IDENTIFIER".equals(token.getType().getName()) ||
                    "INTEGER".equals(token.getType().getName()) ||
                    "FLOAT".equals(token.getType().getName()) ||
                    "STRING".equals(token.getType().getName()) ||
                    "CHARACTER".equals(token.getType().getName()) ||
                    "BOOLEAN".equals(token.getType().getName()) ||
                    "PLUS".equals(token.getType().getName()) ||
                    "MINUS".equals(token.getType().getName()) ||
                    "MULTIPLY".equals(token.getType().getName()) ||
                    "DIVIDE".equals(token.getType().getName()) ||
                    "INFERIOR_EQUALS".equals(token.getType().getName()) ||
                    "INFERIOR".equals(token.getType().getName()) ||
                    "SUPERIOR_EQUALS".equals(token.getType().getName()) ||
                    "SUPERIOR".equals(token.getType().getName()) ||
                    "rem".equals(token.getType().getName()) ||
                    "and".equals(token.getType().getName()) ||
                    "or".equals(token.getType().getName()) ||
                    "ASSIGNMENT".equals(token.getType().getName())
            ) {
                return newRacine;
            } else {
                // Sinon, le nœud terminal n'est pas utile, donc retourner null
                return null;
            }
        }

        ArrayList<Node> descendants = new ArrayList<>();
        for (Node descendant : racine.getChildren()) {
            Node newDescendant = removeTerminalNotUseful(descendant);
            if (newDescendant != null) {
                descendants.add(newDescendant);
            }
        }

        newRacine.setChildren(descendants);

        // Si tous les descendants ont été retirés, le nœud actuel n'est pas utile, retourner null
        if (newRacine.getChildren().isEmpty()) {
            return null;
        }

        return newRacine;
    }


    public Node removeOneChildNodeTree() {
        return removeOneChildNodeTree(this);
    }

    private Node removeOneChildNodeTree(Node racine) {
        if (racine.getChildren().size() == 1) {
            return removeOneChildNodeTree(racine.getChildren().getFirst());
        } else {
            Node newRacine = new Node(racine.getRule(), racine.getToken());
            newRacine.setStatus(racine.getStatus());
            for (Node child : racine.getChildren()) {
                if (child != null) {
                    newRacine.addChild(removeOneChildNodeTree(child));
                }

            }
            return newRacine;
        }
    }

}
