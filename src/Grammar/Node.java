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

    private boolean treeContainsErrors(Node node) {
        if (node.status == 1) {
            return true;
        }
        for (Node child : node.getChildren()) {
            if (treeContainsErrors(child)) {
                return true;
            }
        }
        return false;
    }

    public void buildAbstractTree() {
        System.out.println("-".repeat(100));
        System.out.println("Construction de l'arbre abstrait");
        System.out.println("-".repeat(100));
        try {
            if (!this.getToken().getValue().equals("axiom")) throw new Exception("L'axiome doit être la racine de l'arbre");
            if (this.getChildren().size() != 20) throw new Exception("L'axiome doit avoir 20 enfants");
            if (this.treeContainsErrors(this)) throw new Exception("L'arbre contient des erreurs");

            for (int i = 0; i < 11; i++) {
                children.removeFirst();
            }

            ArrayList<Node>children = new ArrayList<Node>();
            children.add(this.getChildren().removeFirst()); // Récupération de l'identifiant du programme
            this.getChildren().removeFirst(); // Suppression de is

            // Gestion des déclarations de variables globales

            if (this.getChildren().getFirst().getRule().equals("declarationStar -> declaration declarationStar")) {
                ArrayList<Node> declarations = declarations(this.getChildren().removeFirst());
                if (!declarations.isEmpty()) {
                    children.addAll(declarations);
                }
            }

            this.setChildren(children);

            // ---------------------------------------------------------------------------------------------
            System.out.println("-".repeat(50));
            System.out.println("Arbre construit avec succès");
            System.out.println("-".repeat(50));

            System.out.println(this);
        } catch (Exception e) {

            // ---------------------------------------------------------------------------------------------
            System.out.println("-".repeat(50));
            System.out.println("Erreur lors de la création de l'arbre abstrait");
            System.out.println("-".repeat(50));
            e.printStackTrace();
        }
    }

    private ArrayList<Node> declarations(Node declarationStar) {
        ArrayList<Node> declarations = new ArrayList<Node>();

        if (declarationStar == null || declarationStar.getChildren().isEmpty()) {
            return declarations;
        }

        // Ajout de la déclaration
        declarations.add(declaration(declarationStar.getChildren().removeFirst()));

        // Est-ce qu'il y a d'autres déclarations ?
        if (!declarationStar.getChildren().isEmpty()) {
            declarations.addAll(declarations(declarationStar.getChildren().removeFirst()));
        }

        return declarations;
    }

    private Node declaration(Node declaration) {
        if (declaration == null) {
            return null;
        }

        if (!declaration.getToken().getValue().equals("declaration")) {
            return declaration;
        }

        Node newDeclaration = new Node("declaration", new NonTerminalToken("declaration"));

        try {
            Node firstNode = declaration.getChildren().getFirst();
            GrammarToken token = firstNode.getToken();

            if (token instanceof TerminalToken terminalToken) {
                if (terminalToken.getType().getName().equals("IDENTIFIER")) {
                    return declarationIdentifier(declaration);
                } else if (terminalToken.getValue().equals("procedure")) {
                    // TODO
                } else if (terminalToken.getValue().equals("function")) {
                    // TODO
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDeclaration;
    }

    private Node declarationIdentifier(Node declaration) {
        if (declaration == null) {
            return null;
        }

        if (!declaration.getToken().getValue().equals("declaration") || declaration.getChildren().size() != 6) {
            return declaration;
        }


        // Construction de la nouvelle déclaration ---------------------------------------------


        Node newDeclaration = new Node("declarationIdentifier", new NonTerminalToken("declarationIdentifier"));

        // 1 Récupération de l'identifier
        Node identifierNode = declaration.getChildren().removeFirst();
        assert identifierNode.getToken().getValue().equals("IDENTIFIER");

        // 2 Récupération de l'indentificatorCommaPlus
        Node identificatorCommaPlusNode = declaration.getChildren().removeFirst();
        assert identificatorCommaPlusNode.getToken().getValue().equals("identificatorCommaPlus");

        // 3 Suppression du :

        declaration.getChildren().removeFirst();

        // 4 Récupération du type:
        Node typeNode = declaration.getChildren().removeFirst();
        assert typeNode.getToken().getValue().equals("type");
        typeNode = typeNode.getChildren().removeFirst();

        // 6 Suppression du ;
        declaration.getChildren().removeLast();

        // 5 Récupération de l'expressionAssignment
        Node expressionAssignementNode = declaration.getChildren().removeLast();
        assert expressionAssignementNode.getToken().getValue().equals("expressionAssignment");


        // Création des nouveaux enfants -------------------------------------------------------

        if (!identificatorCommaPlusNode.getChildren().isEmpty()){

            newDeclaration = new Node("multipleDeclaration", new NonTerminalToken("multipleDeclaration"));
            newDeclaration.addChild(identifierNode);

            while (!identificatorCommaPlusNode.getChildren().isEmpty()) {
                if (identificatorCommaPlusNode.getChildren().size() == 3) {
                    // On enlève la virgule
                    identificatorCommaPlusNode.getChildren().removeFirst();
                    // On récupère l'identificateur
                    Node identifier = identificatorCommaPlusNode.getChildren().removeFirst();
                    // On ajoute l'identificateur à la liste
                    newDeclaration.addChild(identifier);
                    // On continue
                    identificatorCommaPlusNode = identificatorCommaPlusNode.getChildren().removeFirst();
                }
            }
        } else {
            newDeclaration.addChild(identifierNode);
        }

        newDeclaration.addChild(typeNode);

        if (!expressionAssignementNode.getChildren().isEmpty()) {

            assert expressionAssignementNode.getChildren().size() == 2;

            Node assignementNode = new Node("ASSIGNMENT", new NonTerminalToken(":="));
            // Ajoute la partie de déclaration à l'assignement
            assignementNode.addChild(newDeclaration);
            // Ajoute l'expression à assigner
            // TODO

            return assignementNode;
        }

        return newDeclaration;
    }



}


