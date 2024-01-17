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

    public void setRule(String rule) {
        this.rule = rule;
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


    private Node removeUselessChain(Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.getToken() instanceof NonTerminalToken && currentNode.getChildren().isEmpty()) {
            return null;
        }

        if (currentNode.getToken() instanceof NonTerminalToken && currentNode.getChildren().size() == 1) {
            return removeUselessChain(currentNode.getChildren().getFirst());
        }

        if (currentNode.getToken() instanceof TerminalToken) {
            return currentNode;
        }

        ArrayList<Node> children = new ArrayList<>();
        for (Node child : currentNode.getChildren()) {
            Node newChild = removeUselessChain(child);
            if (newChild != null) {
                children.add(newChild);
            }
        }
        if (children.isEmpty()) {
            return null;
        }
        currentNode.setChildren(children);
        return currentNode;
    }

    private Node removeNotUseful(Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.getChildren() == null || currentNode.getChildren().isEmpty()) {
            return currentNode;
        }

        ArrayList<Node> children = new ArrayList<>();

        for (Node child : currentNode.getChildren()) {
            if (child == null) {
                continue;
            } else if (child.getToken() instanceof TerminalToken terminalToken) {

                if (!terminalToken.getValue().equals("Ada") && !
                        terminalToken.getValue().equals("Text_IO")
                ) {
                    if (terminalToken.getType() != null && !terminalToken.getType().getName().equals("IDENTIFIER")) {
                        continue;
                    } else {
                        children.add(child);
                    }

                }
            } else {
                children.add(removeUselessChain(child));
            }
        }
        currentNode.setChildren(children);
        return currentNode;
    }

    private Node factorize(Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.getChildren().isEmpty() && currentNode.getToken() instanceof TerminalToken) {
            return currentNode;
        }

        if (currentNode.getChildren().size() == 1) {
            return factorize(currentNode.getChildren().getFirst());
        }

        ArrayList<Node> children = new ArrayList<>();
        for (Node child : currentNode.getChildren()) {
            children.add(factorize(child));
        }
        currentNode.setChildren(children);
        return currentNode;

    }

    public Node abstractTreeOne() {
        this.setRule("Fichier");
        abstractTreeOne(this);
        System.out.println("abstractTreeOne : this : " + this);
        return this;
    }

    private Node abstractTreeOne(Node currentNode) {
        if (currentNode == null) {
            return null;
        }

        ArrayList<Node> children = new ArrayList<>();
        for (Node child : currentNode.getChildren()) {
            if (child.getToken().getValue().equals("declarationStar")) {
                children.add(abstractTreeDeclarationStar(child));
            } else {
                children.add(abstractTreeOne(child));
            }

        }

        /*
        if (currentNode.getToken().getValue().equals("instruction")) {
            return abstractTreeInstruction(currentNode);
        }

        if (currentNode.getToken().getValue().equals("parameters")) {
            return abstractTreeParameters(currentNode);
        }
        */


        currentNode.setChildren(children);
        return currentNode;
    }

    // DECLARATIONS-----------------------------------------------------------------------------------------------------


    private Node abstractTreeDeclarationStar(Node currentNode) {

        // Cas où la règle donnerai epsilon
        if (currentNode == null || currentNode.getChildren().isEmpty()) {
            return null;
        }

        Node newNode;

        if (currentNode.getChildren().size()==1) {
            newNode = new Node("declaration", new NonTerminalToken("declaration"));
        } else {
            newNode = new Node("Declarations", new NonTerminalToken("Declarations"));
        }

        while (currentNode.getChildren().size() == 2) {
            newNode.addChild(abstractTreeDeclaration(currentNode.getChildren().removeFirst()));
            currentNode = currentNode.getChildren().removeLast();
        }

        newNode.addChild(abstractTreeDeclaration(currentNode.getChildren().removeFirst()));

        return newNode;
    }

    private Node abstractTreeDeclaration(Node declarationNode) {
        if (declarationNode == null || declarationNode.getChildren().isEmpty()) {
            return null;
        }

        assert declarationNode.getToken().getValue().equals("declaration");

        Node newNode = new Node("declaration", new NonTerminalToken("declaration"));

        try {
            Node node = declarationNode.getChildren().getFirst();
            GrammarToken token = node.getToken();

            if (token instanceof TerminalToken terminalToken) {

                if (terminalToken.getType().getName().equals("IDENTIFIER")) {
                    newNode = abstractTreeDeclarationIdentifier(declarationNode);

                } else if (terminalToken.getValue().equals("procedure")) {
                    // TODO
                } else if (terminalToken.getValue().equals("function")) {
                    // TODO
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newNode;

    }

    private Node abstractTreeDeclarationIdentifier(Node declarationNode) {
        if (declarationNode == null || declarationNode.getChildren().isEmpty()) {
            return null;
        }

        assert declarationNode.getToken().getValue().equals("IDENTIFIER");
        assert declarationNode.getChildren().size() == 6;

        // 1 Récupération de l'identifier
        Node identifierNode = declarationNode.getChildren().removeFirst();
        assert identifierNode.getToken().getValue().equals("IDENTIFIER");

        // 2 Récupération de l'indentificatorCommaPlus
        Node identificatorCommaPlusNode = declarationNode.getChildren().removeFirst();
        assert identificatorCommaPlusNode.getToken().getValue().equals("identificatorCommaPlus");

        // 3 Suppression du :

        declarationNode.getChildren().removeFirst();

        // 4 Récupération du type:
        Node typeNode = declarationNode.getChildren().removeFirst();
        assert typeNode.getToken().getValue().equals("type");
        typeNode = typeNode.getChildren().removeFirst();

        // 6 Suppression du ;
        declarationNode.getChildren().removeLast();

        // 5 Récupération de l'expressionAssignment
        Node expressionAssignementNode = declarationNode.getChildren().removeLast();
        assert expressionAssignementNode.getToken().getValue().equals("expressionAssignment");

        // Contient ce qui est à déclarer
        Node newNode;

        if (!identificatorCommaPlusNode.getChildren().isEmpty()){

            newNode = new Node("multipleDeclaration", new NonTerminalToken("multipleDeclaration"));
            newNode.addChild(identifierNode);

            while (!identificatorCommaPlusNode.getChildren().isEmpty()) {
                assert identificatorCommaPlusNode.getChildren().size() == 3;

                // On enlève la virgule
                identificatorCommaPlusNode.getChildren().removeFirst();

                // On récupère l'identificateur
                Node identifier = identificatorCommaPlusNode.getChildren().removeFirst();

                // On ajoute l'identificateur à la liste
                newNode.addChild(identifier);

                identificatorCommaPlusNode = identificatorCommaPlusNode.getChildren().removeFirst();
            }
        } else {
            newNode = new Node("declaration", new NonTerminalToken("declaration"));
            newNode.addChild(identifierNode);

        }

        newNode.addChild(typeNode);

        if (!expressionAssignementNode.getChildren().isEmpty()) {

            assert expressionAssignementNode.getChildren().size() == 2;

            Node assignementNode = new Node("ASSIGNMENT", new NonTerminalToken(":="));
            // Ajoute la partie de déclaration à l'assignement
            assignementNode.addChild(newNode);
            // Ajoute l'expression à assigner //TODO
            assignementNode.addChild(expressionAssignementNode.getChildren().removeLast());

            return assignementNode;
        }
        return newNode;
    }


    // PARAMETERS-------------------------------------------------------------------------------------------------------


    private Node abstractTreeParameters(Node currentNode) {
        currentNode = removeUselessChain(currentNode);
        currentNode = removeNotUseful(currentNode);
        currentNode.getToken().setValue("parametersList");
        System.out.println("currentNode : " + currentNode);
        try {
            ArrayList<Node> children = new ArrayList<>();
            children.add(parameter(currentNode.getChildren().removeFirst()));

            if (currentNode.getChildren().isEmpty()) {
                currentNode.setChildren(children);
                return currentNode;
            }

            Node nextParam = currentNode.getChildren().removeFirst(); // Récupération du premier paramètresSemicolonPlus

            while (true) {
                System.out.println("nextParam : " + nextParam);
                nextParam.getChildren().removeFirst();
                children.add(parameter(nextParam.getChildren().removeFirst()));
                if (nextParam.getChildren().isEmpty()) {
                    break;
                }
                nextParam = nextParam.getChildren().removeFirst();
            }

            currentNode.setChildren(children);
            System.out.println("currentNode result : " + currentNode);
            return currentNode;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private Node parameter(Node currentNode) {
        System.out.println("parameter : " + currentNode);
        try {
            Node newNode = new Node("param", new NonTerminalToken("parameter"));
            assert currentNode.getToken().getValue().equals("parameter");
            assert currentNode.getChildren().size() == 3;
            currentNode.getChildren().remove(1);
            newNode.addChild(currentNode.getChildren().removeFirst());
            newNode.addChild(currentNode.getChildren().removeFirst());
            return newNode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Node> copyArrayList(ArrayList<Node> arrayList) {
        return new ArrayList<>(arrayList);
    }


    // INSTRUCTIONS-----------------------------------------------------------------------------------------------------


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


    // -----------------------------------------------------------------------------------------------------------------


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


    // -----------------------------------------------------------------------------------------------------------------

    private Node initializeBuildParenthesisTree(ArrayList<Node> nodes) {
        System.out.println("initializeBuildParenthesisTree");
        return switch (nodes.getFirst().getToken().getValue()) {
            case "(" -> buildParenthesisTree(nodes);
            case "-" -> moinsUnaire(nodes);
            default -> orOrElse(nodes);
        };
    }

    // --------------------------------------------------------

    private Node buildParenthesisTree(ArrayList<Node> nodes) {
        System.out.println("buildParenthesisTree");

        ArrayList<Node> newNodes = new ArrayList<>();

        while (!nodes.isEmpty()) {
            Node currentNode = nodes.getFirst();
            System.out.println("parenthesis currentNode : " + currentNode);
            if (currentNode.getToken().getValue().equals("(")) {

                nodes.removeFirst();

                Node parenthesisNode = new Node("parenthesis", new NonTerminalToken("parenthesis"));
                Node node = moinsUnaire(nodes);
                System.out.println("parenthesisTree ( : " + node);
                parenthesisNode.addChild(node); // On construit l'arbre de la parenthèse
                System.out.println("parenthesisNode : " + parenthesisNode);
                System.out.println("Nodes : " + nodes);
                newNodes.add(parenthesisNode);
                System.out.println("newNodes : " + newNodes);

            } else if (currentNode.getToken().getValue().equals(")")) {

                nodes.removeFirst(); // On enlève la parenthèse fermante
                System.out.println("parenthesisTree : " + newNodes);
                Node node = initializeBuildParenthesisTree(newNodes);
                System.out.println("parenthesisTree ) : " + node);
                return node;

            } else if (currentNode.getToken().getValue().equals(";")) {
                nodes.removeFirst();
                return initializeBuildParenthesisTree(newNodes);

            } else {
                System.out.println("parenthesisTree else : " + nodes);
                newNodes.add(nodes.removeFirst());
            }
        }

        return initializeBuildParenthesisTree(newNodes);
    }

    // --------------------------------------------------------

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

    // --------------------------------------------------------

    private Node orOrElse(ArrayList<Node> nodes) {
        System.out.println("orOrElse");


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

        if (node2.getToken().getValue().equals("or")) {

            nodes.removeFirst();
            Node plusNode = new Node("or Node", new NonTerminalToken("or Node"));
            plusNode.addChild(node1);
            System.out.println(plusNode);
            plusNode.addChild(orOrElse(nodes));

            System.out.println("or, orNode : " + plusNode);
            return plusNode;

        } else {
            // remettre le premier node
            nodes.addFirst(node1);
            return ouOuSinon(andAndThen(nodes), nodes);
        }
    }

    // --------------------------------------------------------

    private Node andAndThen(ArrayList<Node> nodes) {
        System.out.println("andAndThen");


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

        if (node2.getToken().getValue().equals("and")) {

            nodes.removeFirst();
            Node plusNode = new Node("and", new NonTerminalToken("and Node"));
            plusNode.addChild(node1);
            System.out.println("ici" + plusNode);
            plusNode.addChild(andAndThen(nodes));

            System.out.println("and, andNode : " + plusNode);
            return plusNode;

        } else {
            // remettre le premier node
            nodes.addFirst(node1);
            return etEtEnsuite(notGesture(nodes), nodes);
        }
    }

    // --------------------------------------------------------

    private Node notGesture(ArrayList<Node> nodes) {
        if (nodes == null || nodes.size() < 2) {
            return null;
        }

        if (nodes.getFirst().getToken().getValue().equals("-")) {
            return initializeBuildParenthesisTree(nodes);
        }
        if (nodes.getFirst().getToken().getValue().equals("(")) {
            return initializeBuildParenthesisTree(nodes);
        }

        if (nodes.getFirst().getToken().getValue().equals("not")) {
            Node notNode = new Node("not Node", new NonTerminalToken("not Node"));
            notNode.addChild(nodes.removeFirst());
            notNode.addChild(notGesture(nodes));
            return notNode;
        }
        return equalsNotEquals(nodes);
    }

    // --------------------------------------------------------

    private Node equalsNotEquals(ArrayList<Node> nodes) {
        System.out.println("equalsNotEquals");

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

        if (node2.getToken().getValue().equals("=")) {

            nodes.removeFirst();
            Node plusNode = new Node("equals Node", new NonTerminalToken("equals Node"));
            plusNode.addChild(node1);
            System.out.println(plusNode);
            plusNode.addChild(equalsNotEquals(nodes));

            System.out.println("equals, equalsNode : " + plusNode);
            return plusNode;

        } else if (node2.getToken().getValue().equals("/=")) {

            nodes.removeFirst();
            Node plusNode = new Node("notEquals Node", new NonTerminalToken("notEquals Node"));
            plusNode.addChild(node1);
            plusNode.addChild(equalsNotEquals(nodes));

            System.out.println("notEquals, notEqualsNode : " + plusNode);
            return plusNode;

        } else {
            if (node1.getToken().getValue().equals("not")) {
                nodes.addFirst(node1);
                return notGesture(nodes);
            }
            // remettre le premier node
            nodes.addFirst(node1);
            return egalDifferent(inferiorSuperior(nodes), nodes);
        }
    }

    // --------------------------------------------------------

    private Node inferiorSuperior(ArrayList<Node> nodes) {
        System.out.println("inferiorSuperior");
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

        if (node2.getToken().getValue().equals(">")) {

            nodes.removeFirst();
            Node plusNode = new Node("superior Node", new NonTerminalToken("superior Node"));
            plusNode.addChild(node1);
            System.out.println(plusNode);
            plusNode.addChild(inferiorSuperior(nodes));

            System.out.println("superior, superiorNode : " + plusNode);
            return plusNode;

        } else if (node2.getToken().getValue().equals("<")) {

            nodes.removeFirst();
            Node plusNode = new Node("inferior Node", new NonTerminalToken("inferior Node"));
            plusNode.addChild(node1);
            plusNode.addChild(inferiorSuperior(nodes));

            System.out.println("inferior, inferiorNode : " + plusNode);
            return plusNode;

        } else if (node2.getToken().getValue().equals(">=")) {

            nodes.removeFirst();
            Node plusNode = new Node("superiorEquals Node", new NonTerminalToken("superiorEquals Node"));
            plusNode.addChild(node1);
            plusNode.addChild(inferiorSuperior(nodes));

            System.out.println("superiorEquals, superiorEqualsNode : " + plusNode);
            return plusNode;

        } else if (node2.getToken().getValue().equals("<=")) {

            nodes.removeFirst();
            Node plusNode = new Node("inferiorEquals Node", new NonTerminalToken("inferiorEquals Node"));
            plusNode.addChild(node1);
            plusNode.addChild(inferiorSuperior(nodes));

            System.out.println("inferiorEquals, inferiorEqualsNode : " + plusNode);
            return plusNode;

        } else {
            if (node1.getToken().getValue().equals("not")) {
                nodes.addFirst(node1);
                return notGesture(nodes);
            }
            // remettre le premier node
            nodes.addFirst(node1);
            return inferieurSuperieur(ajouterSoustraire(nodes), nodes);
        }
    }

    // --------------------------------------------------------

    private Node ajouterSoustraire(ArrayList<Node> nodes) {
        System.out.println("ajouterSoustraire");

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
            Node plusNode = new Node("plus", new NonTerminalToken("plus Node"));
            plusNode.addChild(node1);
            System.out.println(plusNode);
            plusNode.addChild(ajouterSoustraire(nodes));

            System.out.println("ajouterSoustraire, plusNode : " + plusNode);
            return plusNode;

        } else if (node2.getToken().getValue().equals("-")) {

            nodes.removeFirst();
            Node plusNode = new Node("moins", new NonTerminalToken("moins Node"));
            plusNode.addChild(node1);
            plusNode.addChild(ajouterSoustraire(nodes));

            System.out.println("ajouterSoustraire, moinsNode : " + plusNode);
            return plusNode;

        } else {
            if (node1.getToken().getValue().equals("not")) {
                nodes.addFirst(node1);
                return notGesture(nodes);
            }

            // remettre le premier node
            nodes.addFirst(node1);
            System.out.println("ajouterSoustraire Else, nodes : " + nodes);
            return additionSoustractionNode(multiplierDiviserRem(nodes), nodes);
        }

    }

    // --------------------------------------------------------

    private Node multiplierDiviserRem(ArrayList<Node> nodes) {
        System.out.println("multiplierDiviserRem, nodes : " + nodes);

        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        Node node1 = dotGesture(nodes);

        if (nodes.size() <= 1) {
            return node1;
        }

        System.out.println("multiplierDiviserRem, first node : " + nodes.getFirst().getToken().getValue());
        Node node2 = nodes.getFirst();

        if (node2.getToken().getValue().equals("*")) {
            nodes.removeFirst();

            Node plusNode = new Node("fois", new NonTerminalToken("multiplier Node"));
            plusNode.addChild(node1);
            System.out.println(plusNode);
            plusNode.addChild(multiplierDiviserRem(nodes));
            System.out.println("multiplierDiviserRem, multiplierNode : " + plusNode);
            System.out.println("nodes : " + nodes);
            return plusNode;
        } else if (node2.getToken().getValue().equals("/")) {
            nodes.removeFirst();

            Node plusNode = new Node("diviser", new NonTerminalToken("diviser Node"));
            plusNode.addChild(node1);
            plusNode.addChild(multiplierDiviserRem(nodes));
            return plusNode;
        } else if (node2.getToken().getValue().equals("rem")) {
            nodes.removeFirst();

            Node plusNode = new Node("rem", new NonTerminalToken("rem Node"));
            plusNode.addChild(node1);
            plusNode.addChild(multiplierDiviserRem(nodes));
            return plusNode;
        } else {
            if (node1.getToken().getValue().equals("not")) {
                nodes.addFirst(node1);
                return notGesture(nodes);
            }
            return node1;

        }
    }

    // --------------------------------------------------------

    private Node dotGesture(ArrayList<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        if (nodes.size() == 1) {
            System.out.println("dotGesture first : " + nodes.getFirst());
            return nodes.removeFirst();
        }

        System.out.println("dotGesture , first node : " + nodes.getFirst());
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

    // --------------------------------------------------------
    // AJOUT D'ARBRE A DROITE D'UN NOEUD
    // --------------------------------------------------------

    private Node additionSoustractionNode(Node filsGauche, ArrayList<Node> nodes) {
        System.out.println("additionSoustractionNode, nodes : " + nodes);
        if (nodes == null || nodes.isEmpty()) {
            return filsGauche;
        }
        if (nodes.getFirst().getToken().getValue().equals("+")) {
            nodes.removeFirst();
            Node plusNode = new Node("plus", new NonTerminalToken("plus Node"));
            plusNode.addChild(filsGauche);
            plusNode.addChild(ajouterSoustraire(nodes));
            return plusNode;
        } else if (nodes.getFirst().getToken().getValue().equals("-")) {
            nodes.removeFirst();
            Node moinsNode = new Node("moins Node", new NonTerminalToken("moins Node"));
            moinsNode.addChild(filsGauche);
            moinsNode.addChild(ajouterSoustraire(nodes));
            return moinsNode;
        } else {
            return filsGauche;
        }
    }

    // --------------------------------------------------------

    private Node inferieurSuperieur(Node filsGauche, ArrayList<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return filsGauche;
        }
        if (nodes.getFirst().getToken().getValue().equals(">")) {
            nodes.removeFirst();
            Node plusNode = new Node("superior Node", new NonTerminalToken("superior Node"));
            plusNode.addChild(filsGauche);
            plusNode.addChild(inferiorSuperior(nodes));
            return plusNode;
        } else if (nodes.getFirst().getToken().getValue().equals("<")) {
            nodes.removeFirst();
            Node moinsNode = new Node("inferior Node", new NonTerminalToken("inferior Node"));
            moinsNode.addChild(filsGauche);
            moinsNode.addChild(inferiorSuperior(nodes));
            return moinsNode;
        } else if (nodes.getFirst().getToken().getValue().equals(">=")) {
            nodes.removeFirst();
            Node moinsNode = new Node("superiorEquals Node", new NonTerminalToken("superiorEquals Node"));
            moinsNode.addChild(filsGauche);
            moinsNode.addChild(inferiorSuperior(nodes));
            return moinsNode;
        } else if (nodes.getFirst().getToken().getValue().equals("<=")) {
            nodes.removeFirst();
            Node moinsNode = new Node("inferiorEquals Node", new NonTerminalToken("inferiorEquals Node"));
            moinsNode.addChild(filsGauche);
            moinsNode.addChild(inferiorSuperior(nodes));
            return moinsNode;
        } else {
            return filsGauche;
        }
    }

    // --------------------------------------------------------

    private Node egalDifferent(Node filsGauche, ArrayList<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return filsGauche;
        }
        if (nodes.getFirst().getToken().getValue().equals("=")) {
            nodes.removeFirst();
            Node plusNode = new Node("equals Node", new NonTerminalToken("equals Node"));
            plusNode.addChild(filsGauche);
            plusNode.addChild(equalsNotEquals(nodes));
            return plusNode;
        } else if (nodes.getFirst().getToken().getValue().equals("/=")) {
            nodes.removeFirst();
            Node moinsNode = new Node("notEquals Node", new NonTerminalToken("notEquals Node"));
            moinsNode.addChild(filsGauche);
            moinsNode.addChild(equalsNotEquals(nodes));
            return moinsNode;
        } else {
            return filsGauche;
        }
    }

    // --------------------------------------------------------

    private Node etEtEnsuite(Node filsGauche, ArrayList<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return filsGauche;
        }
        if (nodes.getFirst().getToken().getValue().equals("and")) {
            nodes.removeFirst();
            Node plusNode = new Node("and", new NonTerminalToken("and Node"));
            plusNode.addChild(filsGauche);
            plusNode.addChild(andAndThen(nodes));
            return plusNode;
        } else {
            return filsGauche;
        }
    }

    // --------------------------------------------------------

    private Node ouOuSinon(Node filsGauche, ArrayList<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return filsGauche;
        }
        if (nodes.getFirst().getToken().getValue().equals("or")) {
            nodes.removeFirst();
            Node plusNode = new Node("or Node", new NonTerminalToken("or Node"));
            plusNode.addChild(filsGauche);
            plusNode.addChild(orOrElse(nodes));
            return plusNode;
        } else {
            return filsGauche;
        }
    }


}


