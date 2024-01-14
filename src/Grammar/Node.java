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
            Node node = initializeBuildParenthesisTree(newRightPart.getChildren());
            newRightPart.setChildren(new ArrayList<>());
            newRightPart.addChild(node);


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
            Node node = initializeBuildParenthesisTree(newRightPart.getChildren());
            newRightPart.setChildren(new ArrayList<>());
            newRightPart.addChild(node);

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

    private Node initializeBuildParenthesisTree(ArrayList<Node> nodes) {
        return notGesture(nodes); // A chaque fois que l'on rentre dans une parenthèse, on regarde s'il y a un moins unaire
    }


    private Node buildParenthesisTree(ArrayList<Node> nodes) {
        System.out.println("buildParenthesisTree");

        ArrayList<Node> newNodes = new ArrayList<>();

        while (!nodes.isEmpty()) {
            Node currentNode = nodes.getFirst();

            if (currentNode.getToken().getValue().equals("(")) {

                nodes.removeFirst();
                Node parenthesisNode = new Node("parenthesis", new NonTerminalToken("parenthesis"));
                Node node = moinsUnaire(nodes);
                System.out.println("parenthesisTree, node : " + node);
                parenthesisNode.addChild(node); // On construit l'arbre de la parenthèse
                System.out.println("parenthesisTree : " + parenthesisNode);
                return (parenthesisNode);

            } else if (currentNode.getToken().getValue().equals(")")) {

                nodes.removeFirst(); // On enlève la parenthèse fermante
                System.out.println("parenthesisTree : " + newNodes);
                return notGesture(newNodes);


            } else if (currentNode.getToken().getValue().equals(";")) {
                return notGesture(newNodes);

            } else {
                newNodes.add(nodes.removeFirst());
            }
        }

        return notGesture(newNodes);
    }

    private Node moinsUnaire(ArrayList<Node> nodes) {
        System.out.println("moinsUnaire");
        if (nodes.isEmpty()) {
            System.out.println("moinsUnaire, nodes is empty");
            return null;
        }

        if (nodes.getFirst().getToken().getValue().equals("-")) {

            Node moinsUnaireNode = new Node("Moins unaire", new NonTerminalToken("Moins unaire"));
            Node moins = nodes.removeFirst();
            moinsUnaireNode.addChild(buildParenthesisTree(nodes));
            moinsUnaireNode.getChildren().addFirst(moins);
            System.out.println("moinsUnaire, moinsUnaireNode : " + moinsUnaireNode);
            return moinsUnaireNode;
        }
        return buildParenthesisTree(nodes);

    }



    private Node notGesture(ArrayList<Node> nodes) {
        if (nodes == null || nodes.size() < 2) {
            return null;
        }

        if (nodes.getFirst().getToken().getValue().equals("-")) {
            return moinsUnaire(nodes);
        }

        System.out.println("notGesture, first node : " + nodes.getFirst().getToken().getValue());
        Node newNode = null;
        try {
            if (nodes.size() > 1) {
                Node currentNode = nodes.getFirst();
                if (currentNode.getToken().getValue().equals("not")) {
                    Node notNode = new Node("not Node", new NonTerminalToken("not Node"));
                    notNode.addChild(currentNode);
                    nodes.removeFirst(); // On enlève le not
                    notNode.addChild(notRecursive(nodes)); // On regarde si il y a un autre not
                    newNode = notNode;

                } else {
                    newNode = ajouterSoustraire(nodes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("notGesture : " + newNode);
        return newNode;
    }


    private Node notRecursive(ArrayList<Node> nodes) {

        if (nodes == null || nodes.size() < 2) {
            return null;
        }

        if (nodes.getFirst().getToken().getValue().equals("-")) {
            return moinsUnaire(nodes);
        }

        System.out.println("notRecursive, first node : " + nodes.getFirst().getToken().getValue());
        Node firstNode = nodes.getFirst(); // Not or what is just after
        if (firstNode.getToken().getValue().equals("not")) {
            nodes.removeFirst(); // On enlève le not
            Node notNode = new Node("not Node", new NonTerminalToken("not Node"));
            notNode.addChild(firstNode);
            notNode.addChild(notRecursive(nodes));
            return notNode;
        } else {
            return ajouterSoustraire(nodes);
        }
    }


    private Node ajouterSoustraire(ArrayList<Node> nodes) {

        ArrayList<Node> newNodes = new ArrayList<>();

        if (nodes == null || nodes.isEmpty()) {
            System.out.println("ajouterSoustraire, nodes is empty");
            // Pour éviter de planter
            return null;
        }


        // Récupérer l'expression à lire
        Node node1 = dotGesture(nodes);

        if (nodes.size() <= 1) {
            System.out.println("plus rien à faire");
            // Il n'y a plus d'opération à faire
            return node1;
        }


        Node node2 = nodes.getFirst();

        if (node2.getToken().getValue().equals("+")) {

            nodes.removeFirst();
            Node plusNode = new Node("plus Node", new NonTerminalToken("plus Node"));
            plusNode.addChild(node1);
            System.out.println(plusNode);
            plusNode.addChild(ajouterSoustraire(nodes));

            System.out.println("ajouterSoustraire, plusNode : " + plusNode);
            return plusNode;

        } else if (node2.getToken().getValue().equals("-")) {

            nodes.removeFirst();
            Node plusNode = new Node("moins Node", new NonTerminalToken("moins Node"));
            plusNode.addChild(node1);
            plusNode.addChild(ajouterSoustraire(nodes));

            System.out.println("ajouterSoustraire, moinsNode : " + plusNode);
            return plusNode;

        } else {
            // remettre le premier node
            nodes.addFirst(node1);
            return multiplierDiviserRem(nodes);
        }

    }


    private Node multiplierDiviserRem(ArrayList<Node> nodes) {

        ArrayList<Node> newNodes = new ArrayList<>();

        if (nodes == null || nodes.isEmpty()) {
            return null;
        }


        Node node1 = dotGesture(nodes);
        System.out.println("multiplierDiviserRem, dotGesture result : " + node1);

        if (nodes.size() <= 1) {
            return node1;
        }

        System.out.println("multiplierDiviserRem, first node : " + nodes.getFirst().getToken().getValue());
        Node node2 = nodes.getFirst();

        if (node2.getToken().getValue().equals("*")) {
            nodes.removeFirst();

            Node plusNode = new Node("multiplier Node", new NonTerminalToken("multiplier Node"));
            plusNode.addChild(node1);
            plusNode.addChild(multiplierDiviserRem(nodes));
            return plusNode;
        } else if (node2.getToken().getValue().equals("/")) {
            nodes.removeFirst();

            Node plusNode = new Node("diviser Node", new NonTerminalToken("diviser Node"));
            plusNode.addChild(node1);
            plusNode.addChild(multiplierDiviserRem(nodes));
            return plusNode;
        } else if (node2.getToken().getValue().equals("rem")) {
            nodes.removeFirst();

            Node plusNode = new Node("rem Node", new NonTerminalToken("rem Node"));
            plusNode.addChild(node1);
            plusNode.addChild(multiplierDiviserRem(nodes));
            return plusNode;
        } else {
            return dotGesture(nodes);
        }
    }

    private Node dotGesture(ArrayList<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        if (nodes.size() == 1) {
            System.out.println("dotGesture first : " + nodes.getFirst());
            return nodes.removeFirst();
        }

        System.out.println("dotGesture , first node : " + nodes.getFirst().getToken().getValue());
        // Le point est un opérateur binaire
        Node newNode = nodes.removeFirst();
        Node currentNode = nodes.getFirst();

        if (currentNode.getToken().getValue().equals(".")) { // Si le prochain token est un point sinon on retourne juste le premier token
            Node dotNode = new Node("dot Node", new NonTerminalToken("dot Node"));
            dotNode.addChild(newNode);
            nodes.removeFirst(); // On enlève le point
            dotNode.addChild(dotGesture(nodes));
            newNode = dotNode;
        }

        return newNode;
    }



}


