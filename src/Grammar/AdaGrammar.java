package Grammar;

import Grammar.Token.NonTerminalToken;
import Grammar.Token.TerminalToken;
import Lexer.Lexer;

public class AdaGrammar {

    private void print(String s) {
        System.out.println("---" + s);
    }

    private void valueTest(Node node, Lexer lexer, String value) {
        if (!(lexer.getCurrentToken().getValue().equals(value))) {
            node.setFailed(true);
            node.addFailedExplanation("Expected '" + value + "' ; line : " + lexer.getCurrentToken().getLine());
        } else {
            node.addChild(new Node(lexer.getCurrentToken().getValue(), lexer.getCurrentToken()));
            lexer.nextToken();
        }
        print(value);
    }

    private void typeTest(Node node, Lexer lexer, String type) {
        if (!(lexer.getCurrentToken().getType().getName().equals(type))) {
            node.setFailed(true);
            node.addFailedExplanation("Expected '" + type + "' ; line : " + lexer.getCurrentToken().getLine());
        } else {
            node.addChild(new Node(lexer.getCurrentToken().getValue(), lexer.getCurrentToken()));
            lexer.nextToken();
        }
        print(type);
    }

    private String formatString(String input, int targetLength) {
        if (input.length() == targetLength) {
            // La longueur du mot est déjà égale à la longueur cible, aucune modification nécessaire
            return input;
        } else if (input.length() < targetLength) {
            // Ajouter des espaces à droite pour atteindre la longueur cible
            return String.format("%-" + targetLength + "s", input);
        } else {
            // Couper le mot et le remplacer par "..." pour atteindre la longueur cible
            return input.substring(0, targetLength - 3) + "...";
        }
    }

    public void grammar1(String entry) throws Exception {
        Lexer lexer = new Lexer(entry);
        try {
            lexer.tokenize();
        } catch (Exception e) {
            System.out.println("Error while tokenizing");
            System.out.println(e.toString());
            return;
        }

        // Parcours les tokens terminaux du lexer affiche sous la forme d'une liste les tokens terminaux avec leur type (valeur, type)
        for (TerminalToken terminalToken : lexer.getTokens()) {
            // Formatage de la valeur avec 25 caractères et du type avec 30 caractères
            String formattedValue = formatString(terminalToken.getValue(), 15);
            String formattedType = formatString(terminalToken.getType().getName(), 15);

            // Affichage sous la forme d'une liste
            System.out.println(formattedValue + "── " + formattedType);
        }
        System.out.println("Test pour la grammaire : grammar1");

        NonTerminalToken expression = new NonTerminalToken("expression");
        NonTerminalToken expressionFollow = new NonTerminalToken("expressionFollow"); // E1
        NonTerminalToken expressionCommaPlus = new NonTerminalToken("expressionCommaPlus");
        NonTerminalToken expressionAssignment = new NonTerminalToken("expressionAssignment");
        NonTerminalToken expressionFact = new NonTerminalToken("expressionFact"); // E4
        NonTerminalToken expression0or1 = new NonTerminalToken("expression0or1"); // E5
        NonTerminalToken declaration = new NonTerminalToken("declaration");
        NonTerminalToken declarationFact1 = new NonTerminalToken("declarationFact1 ");
        NonTerminalToken declarationFact2 = new NonTerminalToken("declarationFact2");
        NonTerminalToken axiom = new NonTerminalToken("axiom");
        NonTerminalToken declarationStar = new NonTerminalToken("declarationStar");
        NonTerminalToken field = new NonTerminalToken("field");
        NonTerminalToken identificatorCommaPlus = new NonTerminalToken("identificatorCommaPlus");
        NonTerminalToken fieldPlus = new NonTerminalToken("fieldPlus");
        NonTerminalToken instruction = new NonTerminalToken("instruction");
        NonTerminalToken instructionPlus = new NonTerminalToken("instructionPlus");
        NonTerminalToken instructionExpressionAssignment = new NonTerminalToken("instructionExpressionAssignment"); // In4
        NonTerminalToken instructionAssignment = new NonTerminalToken("instructionAssignment");
        NonTerminalToken elseIf = new NonTerminalToken("elseIf"); // Eif
        NonTerminalToken else1 = new NonTerminalToken("else1"); // Ei
        NonTerminalToken reverse = new NonTerminalToken("reverse"); // R1
        NonTerminalToken method = new NonTerminalToken("method");
        NonTerminalToken methodFact = new NonTerminalToken("methodFact");
        NonTerminalToken method0or1 = new NonTerminalToken("method0or1");
        NonTerminalToken parameters = new NonTerminalToken("parameters");
        NonTerminalToken parametersSemicolonPlus = new NonTerminalToken("parametersSemicolonPlus");
        NonTerminalToken parameters0or1 = new NonTerminalToken("parameters0or1");
        NonTerminalToken parameter = new NonTerminalToken("parameter");
        NonTerminalToken identificator0or1 = new NonTerminalToken("identificator0or1");
        NonTerminalToken type = new NonTerminalToken("type");
        NonTerminalToken operator = new NonTerminalToken("operator");


        axiom.setAction(() -> {
            System.out.println("axiom");
            Node node = new Node("axiom -> with Ada.Text_IO; use Ada.Text_IO; ...", axiom);

            valueTest(node, lexer, "with");
            valueTest(node, lexer, "Ada");
            typeTest(node, lexer, "DOT");
            valueTest(node, lexer, "Text_IO");
            typeTest(node, lexer, "SEMICOLON");
            valueTest(node, lexer, "use");
            valueTest(node, lexer, "Ada");
            typeTest(node, lexer, "DOT");
            valueTest(node, lexer, "Text_IO");
            typeTest(node, lexer, "SEMICOLON");
            valueTest(node, lexer, "procedure");
            typeTest(node, lexer, "IDENTIFIER");
            valueTest(node, lexer, "is");
            //
            node.addChild(declarationStar.execute());
            //
            valueTest(node, lexer, "begin");
            //

            try {
                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
            } catch (Exception e) {

            }
            //
            valueTest(node, lexer, "end");
            //
            node.addChild(identificator0or1.execute());
            //
            typeTest(node, lexer, "SEMICOLON");

            return node;
        });

        declarationStar.setAction(() -> {
            System.out.println("declarationStar");
            // Try to do rules D and F1 else do nothing
            try {
                Node node = new Node("declarationStar -> declaration declarationStar", declarationStar);
                node.setStatus(2);
                node.addChild(declaration.execute());
                node.addChild(declarationStar.execute());
                return node;
            } catch (Exception e) {
                System.out.println("catch F1");
                return null;
            }
        });

        declaration.setAction(() -> {
            System.out.println("declaration");

            if (lexer.getCurrentToken().getValue().equals("type")) { // ------------------------------------------------
                print("type");

                Node node = new Node("declaration -> type IDENTIFIER declarationFact1", declaration);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) { // -------------------------------
                    print("IDENTIFIER");
                    node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                    lexer.nextToken();
                    node.addChild(declarationFact1.execute());
                    return node;

                } else { // ---------------------------------------------------------------------------
                    print("Error IDENTIFIER");
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier ; line : " + lexer.getCurrentToken().getLine());
                }

                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) { // ---------------------------------------------
                print("IDENTIFIER");
                Node node = new Node("declaration -> IDENTIFIER indentificatorCommaPlus : type expressionAssignment;", declaration);
                node.setStatus(2);
                node.addChild(new Node(lexer.getCurrentToken().getValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                node.addChild(identificatorCommaPlus.execute());

                if (lexer.currentTokenType().equals("COLON")) { // ------------------------------------
                    print(":");
                    node.addChild(new Node(lexer.getCurrentToken().getValue(), lexer.getCurrentToken()));
                    lexer.nextToken();

                    node.addChild(type.execute());
                    node.addChild(expressionAssignment.execute());

                    if (lexer.currentTokenType().equals("SEMICOLON")) { // ------------------
                        print(";");
                        node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                        lexer.nextToken();

                    } else { // -------------------------------------------------------------
                        print("Error ;");
                        node.setFailed(true);
                        node.addFailedExplanation("Expected 'SEMICOLON' ; line : " + lexer.getCurrentToken().getLine());
                    }

                } else { // ----------------------------------------------------------------------------
                    print("Error :");
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'COLON' ; line : " + lexer.getCurrentToken().getLine());
                }

                return node;

            } else if (lexer.currentTokenValue().equals("procedure")) { // ---------------------------------------------
                print("procedure");

                Node node = new Node("declaration -> procedure IDENTIFIER [...]", declaration);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                typeTest(node, lexer, "IDENTIFIER");
                node.addChild(parameters0or1.execute());
                valueTest(node, lexer, "is");
                node.addChild(declarationStar.execute());
                valueTest(node, lexer, "begin");

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception e) {

                }

                valueTest(node, lexer, "end");
                node.addChild(identificator0or1.execute());
                typeTest(node, lexer, "SEMICOLON");

                return node;

            } else if (lexer.currentTokenValue().equals("function")) {
                print("function");
                Node node = new Node("declaration -> function IDENTIFIER [...]", declaration);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                node.addChild(parameters0or1.execute());
                valueTest(node, lexer, "return");
                node.addChild(type.execute());
                valueTest(node, lexer, "is");
                node.addChild(declarationStar.execute());
                valueTest(node, lexer, "begin");

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception ignored) {

                }

                valueTest(node, lexer, "end");
                node.addChild(identificator0or1.execute());
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else {
                print("Error declaration");
                throw new Exception("Expected 'type', 'procedure' or 'function' keyword ");
            }
        });

        declarationFact1.setAction(() -> {
            System.out.println("declarationFact1");
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                print(";");
                Node node = new Node(";", declarationFact1);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else if (lexer.currentTokenValue().equals("is")) {
                print("is");
                Node node = new Node("is", lexer.getCurrentToken());
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(declarationFact2.execute());
                return node;
            } else {
                print("Error");
                Node node = new Node("declarationFact1", declarationFact1);
                node.setFailed(true);
                node.addFailedExplanation("Expected 'SEMICOLON' or 'is' keyword ; line : " + lexer.getCurrentToken().getLine());
                return node;
            }
        });

        declarationFact2.setAction(() -> {
            System.out.println("declarationFact2");
            if (lexer.currentTokenValue().equals("access")) {
                print("access");
                Node node = new Node("declarationFact2 -> access type", declarationFact2);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("record")) {
                print("record");
                Node node = new Node("declarationFact2 -> record field fieldPlus end record;", declarationFact2);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(field.execute());
                node.addChild(fieldPlus.execute());

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "record");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else {
                print("Error");
                Node node = new Node("declarationFact2", declarationFact2);
                node.setFailed(true);
                node.addFailedExplanation("Expected 'access' or 'record' keywords ; line : " + lexer.getCurrentToken().getLine());
                return node;
            }
        });

        field.setAction(() -> {
            System.out.println("field");
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("IDENTIFIER");
                Node node = new Node("field -> IDENTIFIER identificatorCommaPlus : type ;", field);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                typeTest(node, lexer, "COLON");

                node.addChild(type.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else {
                print("Error");
                throw new Exception("Expected an identifier");
            }
        });

        identificatorCommaPlus.setAction(() -> {
            System.out.println("identificatorCommaPlus");
            if (lexer.currentTokenType().equals("COMMA")) {
                print(",");
                Node node = new Node("identificatorCommaPlus -> , IDENTIFIER identificatorCommaPlus", identificatorCommaPlus);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                return node;

            } else {
                print("identificatorCommaPlus -> Epsilon");
                Node node = new Node("identificatorCommaPlus -> Epsilon", identificatorCommaPlus);
                node.setStatus(3);
                return node;

            }
        });

        fieldPlus.setAction(() -> {
            System.out.println("fieldPlus");
            try {
                print("fieldPlus -> field fieldPlus");
                Node node = new Node("fieldPlus -> field fieldPlus", fieldPlus);
                node.setStatus(2);
                node.addChild(field.execute());
                node.addChild(fieldPlus.execute());
                return node;
            } catch (Exception e) {
                print("fieldPlus -> Epsilon");
                Node node = new Node("fieldPlus -> Epsilon", fieldPlus);
                node.setStatus(3);
                return node;

            }
        });

        method.setAction(() -> {
            System.out.println("method");
            if (lexer.currentTokenValue().equals("in")) {
                print("in");
                Node node = new Node("method -> in methodFact", method);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(methodFact.execute());
                return node;
            } else {
                print("Error in");
                throw new Exception("Expected 'in' keyword");
            }
        });

        methodFact.setAction(() -> {
            System.out.println("methodFact");
            if (lexer.currentTokenValue().equals("out")) {
                print("out");
                Node node = new Node("methodFact -> out", methodFact);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else {
                print("Error out");
                Node node = new Node("methodFact -> Epsilon", methodFact);
                node.setStatus(3);
                return node;
            }
        });

        method0or1.setAction(() -> {
            System.out.println("method0or1");
            try {
                print("method0or1 -> method");
                Node node = new Node("method0or1 -> method", method0or1);
                node.setStatus(2);
                node.addChild(method.execute());
                return node;
            } catch (Exception e) {
                Node node = new Node("method0or1 -> Epsilon", method0or1);
                node.setStatus(3);
                return node;
            }
        });

        operator.setAction(() -> {
            System.out.println("operator");
            boolean isOperator;
            isOperator = lexer.currentTokenType().equals("EQUALS");
            isOperator = isOperator || lexer.currentTokenType().equals("PLUS");
            isOperator = isOperator || lexer.currentTokenType().equals("MINUS");
            isOperator = isOperator || lexer.currentTokenType().equals("MULTIPLY");
            isOperator = isOperator || lexer.currentTokenType().equals("DIVIDE");
            isOperator = isOperator || lexer.currentTokenType().equals("INFERIOR_EQUAL");
            isOperator = isOperator || lexer.currentTokenType().equals("INFERIOR");
            isOperator = isOperator || lexer.currentTokenType().equals("SUPERIOR_EQUAL");
            isOperator = isOperator || lexer.currentTokenType().equals("SUPERIOR");
            isOperator = isOperator || lexer.currentTokenValue().equals("rem");
            isOperator = isOperator || lexer.currentTokenValue().equals("and");
            isOperator = isOperator || lexer.currentTokenValue().equals("or");

            if (lexer.currentTokenValue().equals("and") && lexer.getNextToken().getValue().equals("then")) {
                print("and then");
                Node node = new Node("operator -> and then", operator);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else if (lexer.currentTokenValue().equals("or") && lexer.getNextToken().getValue().equals("else")) {
                print("or else");
                Node node = new Node("operator -> or else", operator);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else if (isOperator) {
                print("operator -> " + lexer.currentTokenValue());
                Node node = new Node("operator -> " + lexer.currentTokenValue(), operator);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else {
                print("Error");
                throw new Exception("Expected an operator");
            }
        });

        parameters.setAction(() -> {
            System.out.println("parameters");
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("parameters -> ( parameter parametersSemicolonPlus )", parameters);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(parameter.execute());
                node.addChild(parametersSemicolonPlus.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                return node;
            } else {
                print("Error (");
                throw new Exception("Expected 'L_PARENTHESIS' ; line : " + lexer.getCurrentToken().getLine());
            }
        });

        parametersSemicolonPlus.setAction(() -> {
            System.out.println("parametersSemicolonPlus");
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                print(";");
                Node node = new Node("parametersSemicolonPlus -> ; parameter parametersSemicolonPlus", parametersSemicolonPlus);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(parameter.execute());
                node.addChild(parametersSemicolonPlus.execute());

                return node;
            } else {
                print("parametersSemicolonPlus -> Epsilon");
                Node node = new Node("parametersSemicolonPlus -> Epsilon", parametersSemicolonPlus);
                node.setStatus(3);
                return node;
            }
        });

        parameters0or1.setAction(() -> {
            System.out.println("parameters0or1");
            try {
                print("parameters0or1 -> parameters");
                Node node = new Node("parameters0or1 -> parameters", parameters0or1);
                node.setStatus(2);
                node.addChild(parameters.execute());
                return node;
            } catch (Exception e) {
                print("parameters0or1 -> Epsilon");
                Node node = new Node("parameters0or1 -> Epsilon", parameters0or1);
                node.setStatus(3);
                return node;
            }
        });

        parameter.setAction(() -> {
            System.out.println("parameter");
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("IDENTIFIER");
                Node node = new Node("parameter -> IDENTIFIER identificatorCommaPlus : type method0or1", parameter);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                typeTest(node, lexer, "COLON");

                node.addChild(method0or1.execute());
                node.addChild(type.execute());

                return node;
            } else {
                print("Error IDENTIFIER");
                Node node = new Node("parameter", parameter);
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier line : " + lexer.getCurrentToken().getLine());
                return node;
            }
        });

        instruction.setAction(() -> {
            System.out.println("instruction");
            boolean goodToken;
            boolean goodToken2;
            goodToken = lexer.currentTokenType().equals("CHARACTER");
            goodToken = goodToken || lexer.currentTokenType().equals("INTEGER");
            goodToken = goodToken || lexer.currentTokenValue().equals("True");
            goodToken = goodToken || lexer.currentTokenValue().equals("False");
            goodToken = goodToken || lexer.currentTokenValue().equals("null");
            goodToken = goodToken || lexer.currentTokenValue().equals("float");
            goodToken2 = lexer.currentTokenValue().equals("not");
            goodToken2 = goodToken2 || lexer.currentTokenValue().equals("new");
            goodToken2 = goodToken2 || lexer.currentTokenType().equals("MINUS");
            print("current Token : " + lexer.currentTokenValue());

            if (goodToken || goodToken2) {
                Node node = new Node("instruction -> " + lexer.currentTokenValue() + " [...]", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                print("instruction 2 -> " + lexer.currentTokenValue() + " [...]");
                lexer.nextToken();

                if (goodToken2) {
                    print("instruction -> " + lexer.currentTokenValue() + " [...]");
                    node.addChild(expression.execute());
                }

                node.addChild(expressionFollow.execute());

                typeTest(node, lexer, "DOT");
                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "ASSIGNMENT");

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("instruction -> ( expression ) expressionFollow . IDENTIFIER := expression ;", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());

                typeTest(node, lexer, "DOT");
                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "ASSIGNMENT");

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");

                return node;

            } else if (lexer.currentTokenValue().equals("character")) {
                print("character");
                Node node = new Node("instruction -> character'val [...]", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "APOSTROPHE");
                valueTest(node, lexer, "val");
                typeTest(node, lexer, "L_PARENTHESIS");

                node.addChild(expression.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());

                typeTest(node, lexer, "DOT");
                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "ASSIGNMENT");

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("IDENTIFIER");
                Node node = new Node("instruction -> IDENTIFIER instructionExpressionAssignment", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(instructionExpressionAssignment.execute());
                return node;

            } else if (lexer.currentTokenValue().equals("return")) {
                print("return");
                Node node = new Node("instruction -> return expression ;", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("begin")) {
                print("begin");
                Node node = new Node("instruction -> begin instruction instructionPlus end IDENTIFIER ;", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(instructionPlus.execute());

                valueTest(node, lexer, "end");
                typeTest(node, lexer, "SEMICOLON");
                return node;


            } else if (lexer.currentTokenValue().equals("if")) {
                print("if");
                Node node = new Node("instruction -> if expression then instruction instructionPlus elseIf else1 end if ;", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());

                valueTest(node, lexer, "then");

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception ignored) {

                }
                node.addChild(elseIf.execute());
                node.addChild(else1.execute());

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "if");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("for")) {
                print("for");
                Node node = new Node("instruction -> for IDENTIFIER in reverse expression .. expression loop instruction instructionPlus end loop ;", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                valueTest(node, lexer, "in");

                node.addChild(reverse.execute());

                node.addChild(expression.execute());

                typeTest(node, lexer, "DOUBLE_DOT");

                node.addChild(expression.execute());

                valueTest(node, lexer, "loop");

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception ignored) {

                }

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "loop");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("while")) {
                print("while");
                Node node = new Node("instruction -> while expression loop instruction instructionPlus end loop ;", instruction);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());

                valueTest(node, lexer, "loop");

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception ignored) {
                }

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "loop");
                typeTest(node, lexer, "SEMICOLON");

                return node;

            } else {
                print("current Token : " + lexer.currentTokenValue());
                throw new Exception("Expected an instruction ; line : " + lexer.getCurrentToken().getLine());
            }
        });

        instructionExpressionAssignment.setAction(() -> {
            System.out.println("instructionExpressionAssignment");
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("instructionExpressionAssignment -> ( expression ) expressionFollow . IDENTIFIER := expression ;", instructionExpressionAssignment);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(instructionAssignment.execute());

                return node;

            } else if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                print(":=");
                Node node = new Node("instructionExpressionAssignment -> := expression ;", instructionExpressionAssignment);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else {
                Node node = new Node("instructionExpressionAssignment -> expressionFollow [...] ", instructionExpressionAssignment);
                node.setStatus(2);
                node.addChild(expressionFollow.execute());

                typeTest(node, lexer, "DOT");
                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "ASSIGNMENT");

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;
            }
        });

        instructionAssignment.setAction(() -> {
            System.out.println("instructionAssignment");
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                print(";");
                Node node = new Node("instructionAssignment -> ;", instructionAssignment);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else {
                print("Error ;");
                Node node = new Node("instructionAssignment -> expression ;", instructionAssignment);
                node.setStatus(2);
                node.addChild(expressionFollow.execute());

                typeTest(node, lexer, "DOT");
                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "ASSIGNMENT");

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;
            }
        });

        instructionPlus.setAction(() -> {
            System.out.println("instructionPlus");
            try {
                print("Try");
                Node node = new Node("instructionPlus -> instruction instructionPlus", instructionPlus);
                node.setStatus(2);

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception e) {
                }

                return node;
            } catch (Exception e) {
                print("Catch");
                Node node = new Node("instructionPlus -> Epsilon", instructionPlus);
                node.setStatus(3);
                return node;

            }
        });

        expression.setAction(() -> {
            System.out.println("expression");
            boolean goodToken;
            boolean goodToken2;

            goodToken = lexer.currentTokenType().equals("CHARACTER");
            goodToken = goodToken || lexer.currentTokenType().equals("INTEGER");
            goodToken = goodToken || lexer.currentTokenValue().equals("True");
            goodToken = goodToken || lexer.currentTokenValue().equals("False");
            goodToken = goodToken || lexer.currentTokenValue().equals("null");
            goodToken = goodToken || lexer.currentTokenType().equals("FLOAT");

            goodToken2 = lexer.currentTokenValue().equals("not");
            goodToken2 = goodToken2 || lexer.currentTokenValue().equals("new");
            goodToken2 = goodToken2 || lexer.currentTokenType().equals("MINUS");

            if (goodToken || goodToken2) {
                print("expression -> " + lexer.currentTokenValue() + " expressionFollow");
                Node node = new Node("expression -> " + lexer.currentTokenValue() + " expressionFollow", expression);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                if (goodToken2) {
                    print("expression -> " + lexer.currentTokenValue() + " expressionFollow");
                    node.addChild(expression.execute());
                }

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("expression -> ( expression ) expressionFollow", expression);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenValue().equals("character")) {
                print("character");
                Node node = new Node("expression -> character'val ( expression ) expressionFollow", expression);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "APOSTROPHE");
                valueTest(node, lexer, "val");
                typeTest(node, lexer, "L_PARENTHESIS");

                node.addChild(expression.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("IDENTIFIER");
                Node node = new Node("expression -> IDENTIFIER expressionFact", expression);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expressionFact.execute());
                return node;
            } else {
                print("Error expression :" + lexer.currentTokenValue());
                Node node = new Node("expression", expression);
                node.setFailed(true);
                node.addFailedExplanation("Expected an expression ; line : " + lexer.getCurrentToken().getLine());
                return node;
            }
        });

        expressionFollow.setAction(() -> {
            System.out.println("expressionFollow");
            if (lexer.currentTokenType().equals("DOT")) {
                print(".");
                Node node = new Node("expressionFollow -> . IDENTIFIER expressionAssignment", expressionFollow);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                node.addChild(expressionFollow.execute());
                return node;
            }

            // Try operator and check if the error raised is Op else do nothing
            try {
                print("Try");
                Node node = new Node("expressionFollow -> operator expression expressionFollow", expressionFollow);
                node.setStatus(2);
                node.addChild(operator.execute());
                node.addChild(expression.execute());
                node.addChild(expressionFollow.execute());
                return node;
            } catch (Exception e) {
                print("Catch");
                Node node = new Node("expressionFollow -> Epsilon", expressionFollow);
                node.setStatus(3);
                return node;
            }
        });

        expressionCommaPlus.setAction(() -> {
            System.out.println("expressionCommaPlus");
            if (lexer.currentTokenType().equals("COMMA")) {
                print(",");
                Node node = new Node("expressionCommaPlus -> , expression expressionCommaPlus", expressionCommaPlus);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());
                return node;
            } else {
                print("expressionCommaPlus -> Epsilon");
                Node node = new Node("expressionCommaPlus -> Epsilon", expressionCommaPlus);
                node.setStatus(3);
                return node;
            }
        });

        expressionAssignment.setAction(() -> {
            System.out.println("expressionAssignment");
            if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                print(":=");
                Node node = new Node("expressionAssignment -> := expression", expressionAssignment);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());
                return node;
            } else {
                print("expressionAssignment -> Epsilon");
                Node node = new Node("expressionAssignment -> Epsilon", expressionAssignment);
                node.setStatus(3);
                return node;
            }
        });

        expressionFact.setAction(() -> {
            System.out.println("expressionFact");
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("expressionFact -> ( expressionCommaPlus ) expressionFollow", expressionFact);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());
                return node;

            } else {
                print("expressionFact -> expressionFollow");
                Node node = new Node("expressionFact -> expressionFollow", expressionFact);
                node.setStatus(2);
                node.addChild(expressionFollow.execute());
                return node;
            }
        });

        expression0or1.setAction(() -> {
            System.out.println("expression0or1");
            try {
                print("Try");
                Node node = new Node("expression0or1 -> expression", expression0or1);
                node.setStatus(2);
                node.addChild(expression.execute());
                return node;
            } catch (Exception e) {
                print("Catch");
                Node node = new Node("expression0or1 -> Epsilon", expression0or1);
                node.setStatus(3);
                return node;
            }
        });

        elseIf.setAction(() -> {
            System.out.println("elseIf");
            if (lexer.currentTokenValue().equals("elsif")) {
                print("elsif");
                Node node = new Node("elseIf -> elsif expression then instruction instructionPlus elseIf", elseIf);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                node.addChild(expression.execute());
                valueTest(node, lexer, "then");

                try {
                    node.addChild(instruction.execute());
                    node.addChild(instructionPlus.execute());
                } catch (Exception e) {

                }

                node.addChild(elseIf.execute());
                return node;
            } else {
                print("elseIf -> Epsilon");
                Node node = new Node("elseIf -> Epsilon", elseIf);
                node.setStatus(3);
                return node;
            }
        });

        else1.setAction(() -> {
            System.out.println("else1");
            if (lexer.currentTokenValue().equals("else")) {
                print("else");
                Node node = new Node("else1 -> else instruction instructionPlus", else1);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                return node;
            } else {
                print("else1 -> Epsilon");
                Node node = new Node("else1 -> Epsilon", else1);
                node.setStatus(3);
                return node;
            }
        });

        reverse.setAction(() -> {
            System.out.println("reverse");
            if (lexer.currentTokenValue().equals("reverse")) {
                print("reverse");
                Node node = new Node("reverse -> reverse", reverse);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else {
                print("reverse -> Epsilon");
                Node node = new Node("reverse -> Epsilon", reverse);
                node.setStatus(3);
                return node;
            }
        });

        type.setAction(() -> {
            System.out.println("type");

            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("type -> IDENTIFIER");
                Node node = new Node("type -> IDENTIFIER", type);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;

            } else if (lexer.currentTokenValue().equals("access")) {
                print("type -> access type");
                Node node = new Node("type -> access type", type);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                return node;

            } else {
                print("Error");
                Node node = new Node("type", type);
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier or 'access' keyword; line " + lexer.getCurrentToken().getLine());
                return node;
            }
        });

        identificator0or1.setAction(() -> {
            System.out.println("identificator0or1");
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("identificator0or1 -> IDENTIFIER");
                Node node = new Node("identificator0or1 -> IDENTIFIER", identificator0or1);
                node.setStatus(2);
                node.addChild(new Node(lexer.currentTokenValue(), lexer.getCurrentToken()));
                lexer.nextToken();
                return node;
            } else {
                print("identificator0or1 -> Epsilon");
                Node node = new Node("identificator0or1 -> Epsilon", identificator0or1);
                node.setStatus(3);
                return node;
            }
        });

        Node node = axiom.execute();
        System.out.println(node);
        Node epsilon = node.removeEpsilon();
        System.out.println(epsilon);
        Node removed = epsilon.removeOneChildNodeTree();
        System.out.println(removed);
        System.out.println("Test passé");

    }
}
