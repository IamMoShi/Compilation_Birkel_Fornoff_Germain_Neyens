package Grammar;

import Grammar.Token.GrammarToken;
import Grammar.Token.NonTerminalToken;
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

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // ARBRE ABSTRAIT
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private boolean treeContainsErrors(Node node) {
        if (node == null) {
            return false;
        }
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

    // -----------------------------------------------------------------------------------------------------------------


    public void buildAbstractTree() {
        System.out.println("-".repeat(100));
        System.out.println("Construction de l'arbre abstrait");
        System.out.println("-".repeat(100));
        try {
            if (!this.getToken().getValue().equals("axiom"))
                throw new Exception("L'axiome doit être la racine de l'arbre");
            if (this.getChildren().size() != 20) throw new Exception("L'axiome doit avoir 20 enfants");
            if (this.treeContainsErrors(this)) throw new Exception("L'arbre contient des erreurs");

            for (int i = 0; i < 11; i++) {
                children.removeFirst();
            }

            ArrayList<Node> children = new ArrayList<Node>();
            children.add(this.getChildren().removeFirst()); // Récupération de l'identifiant du programme
            this.getChildren().removeFirst(); // Suppression de is

            // Gestion des déclarations de variables globales

            if (this.getChildren().getFirst().getRule().equals("declarationStar -> declaration declarationStar")) {
                ArrayList<Node> declarations = declarations(this.getChildren().removeFirst());
                if (!declarations.isEmpty()) {
                    children.addAll(declarations);
                }
            } else if (this.getChildren().getFirst().getRule().equals("declarationStar -> Epsilon")) {
                this.getChildren().removeFirst();
            }

            // Suppression du begin
            this.getChildren().removeFirst();

            // Gestion des instructions
            Node instructionsNode = instructions(this.getChildren().removeFirst());
            if (instructionsNode != null) children.add(instructionsNode);

            this.setChildren(children);


            // TODO

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


    // -----------------------------------------------------------------------------------------------------------------


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
                    return declarationProcedure(declaration);
                } else if (terminalToken.getValue().equals("function")) {
                    return declarationFunction(declaration);
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

        if (!identificatorCommaPlusNode.getChildren().isEmpty()) {

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

    private Node declarationProcedure(Node declaration) {
        if (declaration == null) {
            return null;
        }

        if (!declaration.getToken().getValue().equals("declaration") || declaration.getChildren().size() != 11) {
            return declaration;
        }

        // Construction de la nouvelle déclaration ---------------------------------------------
        Node newDeclaration = new Node("declarationProcedure", new NonTerminalToken("declarationProcedure"));

        // 1 supprimer le mot clé procedure
        declaration.getChildren().removeFirst();
        // 2 récupérer l'identificateur
        Node identifierNode = declaration.getChildren().removeFirst();
        // 3 récupérer parameters0or1
        Node parameters0or1Node = declaration.getChildren().removeFirst();
        // 4 supprimer le is
        declaration.getChildren().removeFirst();
        // 5 récupérer declarationStar
        Node declarationStarNode = declaration.getChildren().removeFirst();
        // 6 supprimer le begin
        declaration.getChildren().removeFirst();
        // 7 récupérer instruction
        Node instructionNode = declaration.getChildren().removeFirst();
        // 8 récupérer instructionPlus
        Node instructionPlusNode = declaration.getChildren().removeFirst();
        // 9 supprimer le end
        declaration.getChildren().removeFirst();
        // 10 récupérer identificator0or1
        Node identificator0or1Node = declaration.getChildren().removeFirst();
        // 11 supprimer le ;
        declaration.getChildren().removeFirst();

        newDeclaration.addChild(identifierNode);

        if (!parameters0or1Node.getChildren().isEmpty()) {
            Node parametersNode = parameters0or1(parameters0or1Node);
            if (parametersNode != null) {
                newDeclaration.addChild(parametersNode);
            }
        }

        ArrayList<Node> declarations = declarations(declarationStarNode);

        if (!declarations.isEmpty()) {
            // Ajout des déclarations potentielles
            newDeclaration.getChildren().addAll(declarations);
        }

        Node instructionsNode = instructions(instructionNode);

        if (instructionsNode == null) {
            return declaration;
        }
        instructionsNode.getChildren().addAll(instructionsPlus(instructionPlusNode));

        newDeclaration.addChild(instructionsNode);


        if (!identificator0or1Node.getChildren().isEmpty()) {
            newDeclaration.getChildren().add(identificator0or1Node.getChildren().removeFirst());
        }

        return newDeclaration;
    }

    private Node declarationFunction(Node declaration) {
        System.out.println("declarationFunction");
        if (declaration == null) {
            return null;
        }

        if (!declaration.getToken().getValue().equals("declaration") || declaration.getChildren().size() != 13) {
            return declaration;
        }

        // Construction de la nouvelle déclaration ---------------------------------------------
        Node newDeclaration = new Node("function", new NonTerminalToken("function"));

        // 1 supprimer le mot clé function
        declaration.getChildren().removeFirst();
        // 2 récupérer l'identificateur
        Node identifierNode = declaration.getChildren().removeFirst();
        // 3 récupérer parameters0or1
        Node parameters0or1Node = declaration.getChildren().removeFirst();
        // 4 supprimer le return
        declaration.getChildren().removeFirst();
        // 5 récupérer type
        Node typeNode = declaration.getChildren().removeFirst();
        // 6 supprimer le is
        declaration.getChildren().removeFirst();
        // 7 récupérer declarationStar
        Node declarationStarNode = declaration.getChildren().removeFirst();
        // 8 supprimer le begin
        declaration.getChildren().removeFirst();
        // 9 récupérer instruction
        Node instructionNode = declaration.getChildren().removeFirst();
        // 10 récupérer instructionPlus
        Node instructionPlusNode = declaration.getChildren().removeFirst();
        // 11 supprimer le end
        declaration.getChildren().removeFirst();
        // 12 récupérer identificator0or1
        Node identificator0or1Node = declaration.getChildren().removeFirst();
        // 13 supprimer le ;
        declaration.getChildren().removeFirst();

        newDeclaration.addChild(identifierNode);

        if (!parameters0or1Node.getChildren().isEmpty()) {
            Node parametersNode = parameters0or1(parameters0or1Node);
            if (parametersNode != null) {
                newDeclaration.addChild(parametersNode);
            }
        }

        if (typeNode.getChildren().size() == 1) {
            newDeclaration.addChild(typeNode.getChildren().removeFirst());
        } else {
            newDeclaration.addChild(typeNode);
        }

        ArrayList<Node> declarations = declarations(declarationStarNode);

        if (!declarations.isEmpty()) {
            // Ajout des déclarations potentielles
            newDeclaration.getChildren().addAll(declarations);
        }

        Node instructionsNode = instructions(instructionNode);

        if (instructionsNode == null) {
            return declaration;
        }

        instructionsNode.getChildren().addAll(instructionsPlus(instructionPlusNode));

        newDeclaration.addChild(instructionsNode);

        if (!identificator0or1Node.getChildren().isEmpty()) {
            newDeclaration.getChildren().add(identificator0or1Node.getChildren().removeFirst());
        }


        return newDeclaration;
    }


    // -----------------------------------------------------------------------------------------------------------------

    private Node parameters0or1(Node parameters0or1) {
        if (parameters0or1 == null || parameters0or1.getChildren().isEmpty()) {
            return null;
        }
        return parameters(parameters0or1.getChildren().removeFirst());
    }

    private ArrayList<Node> parametersSemicolonPlus(Node parametersSemicolonPlusNode) {
        if (parametersSemicolonPlusNode == null || parametersSemicolonPlusNode.getChildren().isEmpty()) {
            return new ArrayList<>();
        }

        if (parametersSemicolonPlusNode.getChildren().size() != 3) return new ArrayList<>();

        // 1 supprimer le ;
        parametersSemicolonPlusNode.getChildren().removeFirst();
        // 2 récupérer parameter
        Node parameterNode = parametersSemicolonPlusNode.getChildren().removeFirst();
        // 3 récupérer parameterSemicolonPlus
        Node parameterSemicolonPlusNode = parametersSemicolonPlusNode.getChildren().removeFirst();

        ArrayList<Node> parameters = new ArrayList<>();
        parameters.add(parameter(parameterNode));

        if (parameterSemicolonPlusNode != null && !parameterSemicolonPlusNode.getChildren().isEmpty()) {
            parameters.addAll(parametersSemicolonPlus(parameterSemicolonPlusNode));
        }

        return parameters;
    }

    private Node parameters(Node parametersNode) {
        if (parametersNode == null || parametersNode.getChildren().isEmpty()) {
            return null;
        }

        if (parametersNode.getChildren().size() != 4) return parametersNode;

        Node newParameters = new Node("parameters", new NonTerminalToken("parameters"));

        // 1 supprimer le (
        parametersNode.getChildren().removeFirst();
        // 2 récupérer parameter
        Node parameterNode = parametersNode.getChildren().removeFirst();
        // 3 récupérer parameterSemicolonPlus
        Node parameterSemicolonPlusNode = parametersNode.getChildren().removeFirst();
        // 4 supprimer le )
        parametersNode.getChildren().removeFirst();

        Node parameter = parameter(parameterNode);
        newParameters.addChild(parameter);

        if (parameterSemicolonPlusNode != null && !parameterSemicolonPlusNode.getChildren().isEmpty()) {
            newParameters.getChildren().addAll(parametersSemicolonPlus(parameterSemicolonPlusNode));
        }

        return newParameters;
    }

    private Node parameter(Node parameterNode) {
        if (parameterNode == null || parameterNode.getChildren().isEmpty()) {
            return null;
        }

        if (parameterNode.getChildren().size() != 5) return parameterNode;

        // 1 récupérer l'identificateur
        Node identifierNode = parameterNode.getChildren().removeFirst();
        // 2 récupérer indentificatorCommaPlus
        Node identificatorCommaPlusNode = parameterNode.getChildren().removeFirst();
        // 3 supprimer le :
        parameterNode.getChildren().removeFirst();
        // 4 récupérer la méthode
        Node methodNode = parameterNode.getChildren().removeFirst();
        // 5 récupérer le type
        Node typeNode = parameterNode.getChildren().removeFirst();

        Node newParameter = new Node("parameter", new NonTerminalToken("parameter"));

        ArrayList<Node> identificatorCommaPlus = identificatorCommaPlus(identificatorCommaPlusNode);
        identificatorCommaPlus.add(identifierNode);

        if (!identificatorCommaPlusNode.getChildren().isEmpty()) {
            identificatorCommaPlus.addAll(identificatorCommaPlus(identificatorCommaPlusNode));
        }

        newParameter.setChildren(identificatorCommaPlus);

        if (methodNode != null && !methodNode.getChildren().isEmpty()) {
            newParameter.addChild(methodNode.getChildren().removeFirst()); // TODO
        }

        if (typeNode != null && typeNode.getChildren().size() == 1) {
            newParameter.addChild(typeNode.getChildren().removeFirst());
        } else {
            newParameter.addChild(typeNode);
        }

        return newParameter;

    }

    private ArrayList<Node> identificatorCommaPlus(Node indentificatorCommaPlusNode) {
        if (indentificatorCommaPlusNode == null || indentificatorCommaPlusNode.getChildren().isEmpty()) {
            return new ArrayList<>();
        }

        // 1 supprimer la virgule
        indentificatorCommaPlusNode.getChildren().removeFirst();
        // 2 récupérer l'identificateur
        Node identifierNode = indentificatorCommaPlusNode.getChildren().removeFirst();
        // 3 récupérer indentificatorCommaPlus
        Node identificatorCommaPlusNode2 = indentificatorCommaPlusNode.getChildren().removeFirst();

        ArrayList<Node> identificators = new ArrayList<>();
        identificators.add(identifierNode);
        identificators.addAll(identificatorCommaPlus(identificatorCommaPlusNode2));

        return identificators;
    }

    // -----------------------------------------------------------------------------------------------------------------


    private Node instructions(Node instructionNode) {
        if (instructionNode == null || instructionNode.getChildren().isEmpty()) {
            return null;
        }
        Node newInstructionNode = new Node("instructions", new NonTerminalToken("instructions"));

        if (instructionNode.getChildren().size() == 2) {
            Node leftPart = instructionNode.getChildren().removeFirst(); // TODO
            Node assignment = instructionExpressionAssignment(instructionNode.getChildren().removeFirst());
            assignment.addChild(leftPart);
            newInstructionNode.addChild(assignment);
            return newInstructionNode;
        }

        if (instructionNode.getChildren().size() == 3) {
            // Suppression de return
            instructionNode.getChildren().removeFirst();
            // Expression0or1
            Node expression0or1Node = expression0or1(instructionNode.getChildren().removeFirst());
            if (expression0or1Node != null) {
                newInstructionNode.addChild(expression0or1Node);
            }
            // Suppression du ;
            instructionNode.getChildren().removeFirst();
            return newInstructionNode;
        }

        if (instructionNode.getChildren().size() == 4) {
            // Suppression de begin
            instructionNode.getChildren().removeFirst();
            // InstructionPlus
            newInstructionNode.getChildren().addAll(instructionsPlus(instructionNode.getChildren().removeFirst()));
            // Suppression du end
            instructionNode.getChildren().removeFirst();
            // Suppression du ;
            instructionNode.getChildren().removeFirst();
            return newInstructionNode;
        }

        if (instructionNode.getChildren().size() == 13) {
            return instructionFor(instructionNode);
        }

        if (instructionNode.getChildren().size() == 10) {
            return instructionIf(instructionNode);
        }

        if (instructionNode.getChildren().size() == 8) {
            return instructionWhile(instructionNode);
        }


        return newInstructionNode;
    }

    private Node instructionFor(Node instructionForNode) {
        if (instructionForNode == null || instructionForNode.getChildren().size() != 13) {
            return instructionForNode;
        }

        // 1 Suppression de for
        instructionForNode.getChildren().removeFirst();
        // 2 Récupération de l'identificateur
        Node identifierNode = instructionForNode.getChildren().removeFirst();
        // 3 Suppression de in
        instructionForNode.getChildren().removeFirst();
        // 4 Récupération de reverse0or1
        Node reverse0or1Node = instructionForNode.getChildren().removeFirst();
        // 5 Récupération de expression
        Node expressionNode = instructionForNode.getChildren().removeFirst();
        // 6 Suppression de doubleDot
        instructionForNode.getChildren().removeFirst();
        // 7 Récupération de expression
        Node expressionNode2 = instructionForNode.getChildren().removeFirst();
        // 8 Suppression de loop
        instructionForNode.getChildren().removeFirst();
        // 9 Récupération de instruction
        Node instructionNode = instructionForNode.getChildren().removeFirst();
        // 10 Récupération de instructionPlus
        Node instructionPlusNode = instructionForNode.getChildren().removeFirst();
        // 11 Suppression de end
        instructionForNode.getChildren().removeFirst();
        // 12 Suppression de loop
        instructionForNode.getChildren().removeFirst();
        // 13 Suppression de ;
        instructionForNode.getChildren().removeFirst();

        Node newInstructionForNode = new Node("instructionFor", new NonTerminalToken("instructionFor"));

        newInstructionForNode.addChild(identifierNode);


        if (!reverse0or1Node.getChildren().isEmpty()) {
            newInstructionForNode.addChild(reverse0or1Node.getChildren().getFirst());
        }

        Node expressionsNode = expression(expressionNode);

        if (expressionsNode != null) {
            newInstructionForNode.addChild(expressionsNode);
        }

        newInstructionForNode.addChild(expressionNode2);

        Node expressionsNode2 = expression(expressionNode2);
        if (expressionsNode2 != null) {
            newInstructionForNode.addChild(expressionsNode2);
        }

        Node instructionsNode = instructions(instructionNode);

        if (instructionsNode != null) {
            newInstructionForNode.addChild(instructionsNode);
        }

        if (!instructionPlusNode.getChildren().isEmpty()) {
            newInstructionForNode.getChildren().addAll(instructionsPlus(instructionPlusNode));
        }

        return newInstructionForNode;

    }

    private Node instructionWhile(Node instructionWhileNode) {
        if (instructionWhileNode == null || instructionWhileNode.getChildren().size() != 8) {
            return instructionWhileNode;
        }

        // 1 Suppression de while
        instructionWhileNode.getChildren().removeFirst();
        // 2 Récupération de expression
        Node expressionNode = instructionWhileNode.getChildren().removeFirst();
        // 3 Suppression de loop
        instructionWhileNode.getChildren().removeFirst();
        // 4 Récupération de instruction
        Node instructionNode = instructionWhileNode.getChildren().removeFirst();
        // 5 Récupération de instructionPlus
        Node instructionPlusNode = instructionWhileNode.getChildren().removeFirst();
        // 6 Suppression de end
        instructionWhileNode.getChildren().removeFirst();
        // 7 Suppression de loop
        instructionWhileNode.getChildren().removeFirst();
        // 8 Suppression de ;
        instructionWhileNode.getChildren().removeFirst();

        Node newInstructionWhileNode = new Node("instructionWhile", new NonTerminalToken("instructionWhile"));

        Node expression = expression(expressionNode);
        if (expression != null) {
            newInstructionWhileNode.addChild(expression);
        }

        Node instructionsNode = instructions(instructionNode);
        if (instructionsNode != null) {
            newInstructionWhileNode.addChild(instructionsNode);
        }

        if (!instructionPlusNode.getChildren().isEmpty()) {
            newInstructionWhileNode.getChildren().addAll(instructionsPlus(instructionPlusNode));
        }

        return newInstructionWhileNode;
    }

    private Node instructionIf(Node instructionIfNode) {
        if (instructionIfNode == null || instructionIfNode.getChildren().size() != 10) {
            return instructionIfNode;
        }

        // 1 Suppression de if
        instructionIfNode.getChildren().removeFirst();
        // 2 Récupération de expression
        Node expressionNode = instructionIfNode.getChildren().removeFirst();
        // 3 Suppression de then
        instructionIfNode.getChildren().removeFirst();
        // 4 Récupération de instruction
        Node instructionNode = instructionIfNode.getChildren().removeFirst();
        // 5 Récupération de instructionPlus
        Node instructionPlusNode = instructionIfNode.getChildren().removeFirst();
        // 6 Récupération de elseIf0or1
        Node elseIf0or1Node = instructionIfNode.getChildren().removeFirst();
        // 7 Récupération de else0or1
        Node else0or1Node = instructionIfNode.getChildren().removeFirst();
        // 8 Suppression de end
        instructionIfNode.getChildren().removeFirst();
        // 9 Suppression de if
        instructionIfNode.getChildren().removeFirst();
        // 10 Suppression de ;
        instructionIfNode.getChildren().removeFirst();

        Node newInstructionIfNode = new Node("instructionIf", new NonTerminalToken("instructionIf"));

        Node expression = expression(expressionNode);
        if (expression != null) {
            newInstructionIfNode.addChild(expression);
        }

        Node instructionsNode = instructions(instructionNode);
        if (instructionsNode != null) {
            newInstructionIfNode.addChild(instructionsNode);
        }

        if (!instructionPlusNode.getChildren().isEmpty()) {
            newInstructionIfNode.getChildren().addAll(instructionsPlus(instructionPlusNode));
        }

        Node elseIfNode = elseIf0or1(elseIf0or1Node);
        if (elseIfNode != null) {
            newInstructionIfNode.addChild(elseIfNode);
        }

        Node elseNode = else0or1(else0or1Node);
        if (elseNode != null) {
            newInstructionIfNode.addChild(elseNode);
        }

        return newInstructionIfNode;
    }

    private Node elseIf0or1(Node elseIf0or1Node) {
        if (elseIf0or1Node == null || elseIf0or1Node.getChildren().isEmpty()) {
            return null;
        }

        if (elseIf0or1Node.getChildren().size() != 6) return null;

        // 1 Suppression de elseif
        elseIf0or1Node.getChildren().removeFirst();
        // 2 Récupération de expression
        Node expressionNode = elseIf0or1Node.getChildren().removeFirst();
        // 3 Suppression de then
        elseIf0or1Node.getChildren().removeFirst();
        // 4 Récupération de instruction
        Node instructionNode = elseIf0or1Node.getChildren().removeFirst();
        // 5 Récupération de instructionPlus
        Node instructionPlusNode = elseIf0or1Node.getChildren().removeFirst();
        // 6 Récupération de elseIf0or1
        Node elseIf0or1Node2 = elseIf0or1Node.getChildren().removeFirst();

        Node newElseIfNode = new Node("elseIf", new NonTerminalToken("elseIf"));

        Node expression = expression(expressionNode);
        if (expression != null) {
            newElseIfNode.addChild(expression);
        }

        Node instructionsNode = instructions(instructionNode);
        if (instructionsNode != null) {
            newElseIfNode.addChild(instructionsNode);
        }

        if (!instructionPlusNode.getChildren().isEmpty()) {
            newElseIfNode.getChildren().addAll(instructionsPlus(instructionPlusNode));
        }

        Node elseIfNode = elseIf0or1(elseIf0or1Node2);
        if (elseIfNode != null) {
            newElseIfNode.addChild(elseIf0or1(elseIfNode));
        }

        return newElseIfNode;
    }

    private Node else0or1(Node else0or1Node) {

        if (else0or1Node == null || else0or1Node.getChildren().isEmpty()) {
            return null;
        }

        if (else0or1Node.getChildren().size() != 3) return null;

        // 1 Suppression de else
        else0or1Node.getChildren().removeFirst();
        // 2 Récupération de instruction
        Node instructionNode = else0or1Node.getChildren().removeFirst();
        // 3 Récupération de instructionPlus
        Node instructionPlusNode = else0or1Node.getChildren().removeFirst();

        Node newElseNode = new Node("else", new NonTerminalToken("else"));

        Node instructionsNode = instructions(instructionNode);
        if (instructionsNode != null) {
            newElseNode.addChild(instructionsNode);
        }

        if (!instructionPlusNode.getChildren().isEmpty()) {
            newElseNode.getChildren().addAll(instructionsPlus(instructionPlusNode));
        }

        return newElseNode;
    }

    private ArrayList<Node> instructionsPlus(Node instructionPlusNode) {
        // TODO
        return new ArrayList<>();
    }

    private Node instructionExpressionAssignment(Node instructionExpressionAssignmentNode) {

        if (instructionExpressionAssignmentNode == null || instructionExpressionAssignmentNode.getChildren().isEmpty()) {
            return null;
        }

        if (instructionExpressionAssignmentNode.getChildren().size() == 5)
            return callFunction(instructionExpressionAssignmentNode);

        return null;
    }

    private Node callFunction(Node callFunctionNode) {
        if (callFunctionNode == null || callFunctionNode.getChildren().isEmpty()) {
            return null;
        }

        if (callFunctionNode.getChildren().size() != 5) return callFunctionNode;

        // Suppression de la parenthèse ouvrante
        callFunctionNode.getChildren().removeFirst();
        // Récupération de l'expression
        Node expressionNode = callFunctionNode.getChildren().removeFirst();
        // Récupération de l'expressionCommaPlus
        Node expressionCommaPlusNode = callFunctionNode.getChildren().removeFirst();
        // Suppression de la parenthèse fermante
        callFunctionNode.getChildren().removeFirst();
        // Récupération d'instructionAssignment
        Node instructionAssignmentNode = callFunctionNode.getChildren().removeFirst();

        Node newCallFunctionNode = new Node("callFunction", new NonTerminalToken("callFunction"));

        Node expression = expression(expressionNode);
        if (expression != null) {
            newCallFunctionNode.addChild(expression);
        }

        ArrayList<Node> expressions = expressionCommaPlus(expressionCommaPlusNode);
        if (!expressions.isEmpty()) {
            newCallFunctionNode.getChildren().addAll(expressions);
        }

        Node instructionAssignment = instructionExpressionAssignment(instructionAssignmentNode);
        if (instructionAssignment != null) {
            newCallFunctionNode.addChild(instructionAssignment);
        }

        return newCallFunctionNode;
    }


    // -----------------------------------------------------------------------------------------------------------------


    private ArrayList<Node> expressionCommaPlus(Node expressionCommaPlusNode) {

        if (expressionCommaPlusNode == null || expressionCommaPlusNode.getChildren().isEmpty()) {
            return new ArrayList<>();
        }

        if (expressionCommaPlusNode.getChildren().size() != 3) return new ArrayList<>();

        // 1 Suppression de la virgule
        expressionCommaPlusNode.getChildren().removeFirst();
        // 2 Récupération de l'expression
        Node expressionNode = expressionCommaPlusNode.getChildren().removeFirst();
        // 3 Récupération de l'expressionCommaPlus
        Node expressionCommaPlusNode2 = expressionCommaPlusNode.getChildren().removeFirst();

        ArrayList<Node> expressions = new ArrayList<>();
        expressions.add(expression(expressionNode));
        expressions.addAll(expressionCommaPlus(expressionCommaPlusNode2));

        return expressions;
    }

    private Node expression0or1(Node expression0or1Node) {
        // TODO
        return null;
    }

    private Node expression(Node expressionsNode) {

        if (expressionsNode == null || expressionsNode.getChildren().isEmpty()) {
            return null;
        }

        // TODO

        return null;
    }







    // -----------------------------------------------------------------------------------------------------------------


}


