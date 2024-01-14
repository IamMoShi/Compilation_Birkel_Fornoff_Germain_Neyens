package Grammar;

import Grammar.Token.GrammarToken;
import Grammar.Token.NonTerminalToken;
import Grammar.Token.TerminalToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


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

    public Node correctAssignment(Node currentNode) {
        if (currentNode == null) {
            return null;
        }

        assert currentNode.getChildren().size() >= 2;

        Node gauche = currentNode.getChildren().getFirst();
        Node droite = currentNode.getChildren().get(1);

        return null;
    }


    public Node abstractTreeOne() {
        return abstractTreeOne(this);
    }

    private Node abstractTreeOne(Node currentNode) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.getToken().getValue().equals("instruction")) {
            return abstractTreeInstruction(currentNode);
        }

        ArrayList<Node> children = new ArrayList<>();
        for (Node child : currentNode.getChildren()) {
            children.add(abstractTreeOne(child));
        }
        currentNode.setChildren(children);
        return currentNode;
    }

    private ArrayList<Node> copyArrayList(ArrayList<Node> arrayList) {
        return new ArrayList<>(arrayList);
    }

    private Node abstractTreeInstruction(Node currentNode) {
        try {
            assert currentNode.getToken().getValue().equals("instruction");
            if (currentNode.getRule().equals("instruction -> ( expression ) expressionFollow . IDENTIFIER := expression ;")) {
                return abstractInstruction1(currentNode);
            }
            if (currentNode.getRule().equals("instruction -> IDENTIFIER instructionExpressionAssignment")) {
                return abstractInstruction2(currentNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private Node abstractInstruction1(Node currentNode) {
        Node newCurrentNode = null;

        try {
            assert currentNode.getToken().getValue().equals("instruction");
            assert currentNode.getRule().equals("instruction -> ( expression ) expressionFollow . IDENTIFIER := expression ;");

            ArrayList<Node> leftPart = new ArrayList<>();
            ArrayList<Node> rightPart = new ArrayList<>();

            ArrayList<Node> pile = new ArrayList<>();

            for (Node child : currentNode.getChildren()) {
                if (child.getToken().getValue().equals(":=")) {
                    leftPart = copyArrayList(pile);
                    pile = new ArrayList<>();
                    newCurrentNode = child;
                } else {
                    pile.add(child);
                }
            }
            rightPart = pile;

            Node newLeftPart = new Node("leftPart", new NonTerminalToken("leftPart"));
            Node newRightPart = new Node("rightPart", new NonTerminalToken("rightPart"));

            newLeftPart.setChildren(leftPart);
            newLeftPart.setChildren(assignmentExpressions(newLeftPart));

            newRightPart.setChildren(rightPart);
            newRightPart.setChildren(assignmentExpressions(newRightPart));
            newRightPart.setChildren(buildParenthesisTree(newRightPart.getChildren()));

            assert newCurrentNode != null;

            newCurrentNode.addChild(newLeftPart);
            newCurrentNode.addChild(newRightPart);

            return newCurrentNode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Node abstractInstruction2(Node currentNode) {
        Node newCurrentNode = null;
        try {
            assert currentNode.getToken().getValue().equals("instruction");
            assert currentNode.getRule().equals("instruction -> IDENTIFIER instructionExpressionAssignment");

            ArrayList<Node> leftPart = new ArrayList<>();
            ArrayList<Node> rightPart = new ArrayList<>();
            ArrayList<Node> pile = new ArrayList<>();

            Node firstLeft = currentNode.getChildren().getFirst();


            assert currentNode.getChildren().size() >= 2;

            currentNode = currentNode.getChildren().get(1);

            for (Node child : currentNode.getChildren()) {
                if (child.getToken().getValue().equals(":=")) {
                    leftPart = copyArrayList(pile);
                    leftPart.addFirst(firstLeft);
                    pile = new ArrayList<>();
                } else {
                    pile.add(child);
                }
            }

            rightPart = pile;

            Node newLeftPart = new Node("leftPart", new NonTerminalToken("leftPart"));
            Node newRightPart = new Node("rightPart", new NonTerminalToken("rightPart"));

            newLeftPart.setChildren(leftPart);
            newLeftPart.setChildren(assignmentExpressions(newLeftPart));

            newRightPart.setChildren(rightPart);
            newRightPart.setChildren(assignmentExpressions(newRightPart));
            newRightPart.setChildren(buildParenthesisTree(newRightPart.getChildren()));

            newCurrentNode = new Node(":=", new TerminalToken(":="));

            newCurrentNode.addChild(newLeftPart);
            newCurrentNode.addChild(newRightPart);

            return newCurrentNode;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private ArrayList<Node> assignmentExpressions(Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.getRule().equals("leftPart")) {
            return assignmentExpressionsLeftPart(currentNode);
        }
        if (currentNode.getRule().equals("rightPart")) {
            return assignmentExpressionsRightPart(currentNode);
        }
        return null;
    }

    private ArrayList<Node> assignmentExpressionsLeftPart(Node currentNode) {
        // Ajout en profondeur des noeuds qui sont des tokens terminaux
        if (currentNode == null) {
            return null;
        }

        if (currentNode.getToken() instanceof TerminalToken) {
            ArrayList<Node> children = new ArrayList<>();
            children.add(currentNode);
            return children;

        }
        ArrayList<Node> children = new ArrayList<>();
        for (Node child : currentNode.getChildren()) {
            children.addAll(assignmentExpressionsLeftPart(child));
        }
        return children;
    }


    private ArrayList<Node> assignmentExpressionsRightPart(Node currentNode) {
        // Ajout en profondeur des noeuds qui sont des tokens terminaux
        if (currentNode == null) {
            return null;
        }

        if (currentNode.getToken() instanceof TerminalToken) {
            ArrayList<Node> children = new ArrayList<>();
            children.add(currentNode);
            return children;

        }
        ArrayList<Node> children = new ArrayList<>();
        for (Node child : currentNode.getChildren()) {
            children.addAll(assignmentExpressionsRightPart(child));
        }
        return children;
    }


    private ArrayList<Node> buildParenthesisTree(ArrayList<Node> nodes) {
        System.out.println("buildParenthesisTree");

        ArrayList<Node> newNodes = new ArrayList<>();
        nodes = moinsUnaire(nodes);

        while (!nodes.isEmpty()) {
            Node currentNode = nodes.removeFirst();

            if (currentNode.getToken().getValue().equals("(")) {
                Node parenthesisNode = new Node("parenthesis", new NonTerminalToken("parenthesis"));
                parenthesisNode.setChildren(buildParenthesisTree(nodes));
                newNodes.add(parenthesisNode);
            } else if (currentNode.getToken().getValue().equals(")")) {
                return dotGesture(newNodes);
            } else {
                newNodes.add(currentNode);
            }
        }

        return dotGesture(newNodes);
    }


    private ArrayList<Node> dotGesture(ArrayList<Node> nodes) {
        System.out.println("dotGesture");
        // Le point est un opérateur binaire
        ArrayList<Node> newNodes = new ArrayList<> ();

        // On parcourt la liste de droite à gauche

        while ((nodes.size() > 1)) {
            Node currentNode = nodes.removeFirst();

            if (nodes.getFirst().getToken().getValue().equals(".")) {
                Node dotNode = new Node("dot Node", new NonTerminalToken("dot Node"));
                dotNode.addChild(currentNode); // On ajoute l'élément avant le point
                dotNode.addChild(dotRecursive(nodes)); // On regarde ce qu'il y a après le point
                newNodes.add(dotNode); // On ajoute le noeud dotNode
            } else {
                newNodes.add(currentNode); // On ajoute le noeud courant à la liste car il n'est pas concerné par le point
            }
        }

        if (nodes.size() == 1) {
            newNodes.add(nodes.removeFirst());
        }

        return notGesture(newNodes);

    }

    private Node dotRecursive(ArrayList<Node> nodes) {
        System.out.println("dotRecursive");
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        Node firstNode = nodes.getFirst(); // Dot or what is just after
        if (firstNode.getToken().getValue().equals(".")) {
            Node dotNode = new Node("dot Node", new NonTerminalToken("dot Node"));
            nodes.removeFirst(); // On enlève le point
            Node valueNode = nodes.removeFirst();
            dotNode.addChild(valueNode); // On ajoute l'élément après le point

            Node node = dotRecursive(nodes); // On regarde s'il y a un autre point
            if (node != null) { // Si oui, on l'ajoute sinon on ne fait rien
                dotNode.addChild(node);
                return dotNode;
            } else {
                return valueNode;
            }

        } else {
            return null;
        }
    }

    private ArrayList<Node> moinsUnaire(ArrayList<Node> nodes) {
        System.out.println("moinsUnaire");
        if (nodes.isEmpty()) {
            return nodes;
        }

        if (nodes.getFirst().getToken().getValue().equals("-")) {

            Node moinsUnaireNode = new Node("Moins unaire", new NonTerminalToken("Moins unaire"));
            Node moins = nodes.removeFirst();
            moinsUnaireNode.setChildren(buildParenthesisTree(nodes));
            moinsUnaireNode.getChildren().addFirst(moins);

            ArrayList<Node> newNodes = new ArrayList<> ();
            newNodes.add(moinsUnaireNode);

            return newNodes;
        }
        return nodes;

    }


    private ArrayList<Node> notGesture(ArrayList<Node> nodes) {
        System.out.println("notGesture");
        ArrayList<Node> newNodes = new ArrayList<> ();
        try {
            while (!nodes.isEmpty()) {
                Node currentNode = nodes.removeFirst();
                if (currentNode.getToken().getValue().equals("not")) {
                    Node notNode = new Node("not Node", new NonTerminalToken("not Node"));
                    notNode.addChild(currentNode);
                    notNode.addChild(notRecursive(nodes));
                    newNodes.add(notNode);

                } else {
                    newNodes.add(currentNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newNodes;
    }


    private Node notRecursive(ArrayList<Node> nodes) {
        System.out.println("notRecursive");
        if (nodes == null) {
            return null;
        }
        Node firstNode = nodes.removeFirst(); // Not or what is just after
        if (firstNode.getToken().getValue().equals("not")) {
            Node notNode = new Node("not Node", new NonTerminalToken("not Node"));
            notNode.addChild(firstNode);
            notNode.addChild(notRecursive(nodes));
            return notNode;
        } else {
            return firstNode;
        }
    }

}


