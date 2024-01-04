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
            node.addFailedExplanation("Expected '" + value);
        } else {
            node.addChild(new Node(lexer.getCurrentToken().getValue()));
            lexer.nextToken();
        }
        print(value);
    }

    private void typeTest(Node node, Lexer lexer, String type) {
        if (!(lexer.getCurrentToken().getType().getName().equals(type))) {
            node.setFailed(true);
            node.addFailedExplanation("Expected '" + type);
        } else {
            node.addChild(new Node(lexer.getCurrentToken().getValue()));
            lexer.nextToken();
        }
        print(type);
    }

    public void grammar1(String entry) throws Exception {
        Lexer lexer = new Lexer(entry);
        try {
            lexer.tokenize();
        } catch (Exception e) {
            System.out.println("Error while tokenizing");
            System.out.println(e.getMessage());
        }

        // Parcours les tokens terminaux du lexer affiche sous la forme d'une liste les tokens terminaux avec leur type (valeur, type)
        for (TerminalToken terminalToken : lexer.getTokens()) {
            System.out.println(terminalToken.getValue() + " " + terminalToken.getType().getName());
        }

        System.out.println("Test pour la grammaire : grammar1");

        NonTerminalToken expression = new NonTerminalToken("E");
        NonTerminalToken expressionFollow = new NonTerminalToken("E1"); // E1
        NonTerminalToken expressionCommaPlus = new NonTerminalToken("E2");
        NonTerminalToken expressionAssignment = new NonTerminalToken("E3");
        NonTerminalToken expressionFact = new NonTerminalToken("E4"); // E4
        NonTerminalToken expression0or1 = new NonTerminalToken("E5"); // E5
        NonTerminalToken declaration = new NonTerminalToken("D");
        NonTerminalToken declarationFact1 = new NonTerminalToken("D1");
        NonTerminalToken declarationFact2 = new NonTerminalToken("D2");
        NonTerminalToken axiom = new NonTerminalToken("F");
        NonTerminalToken declarationStar = new NonTerminalToken("F1");
        NonTerminalToken field = new NonTerminalToken("C");
        NonTerminalToken identificatorCommaPlus = new NonTerminalToken("C1");
        NonTerminalToken fieldPlus = new NonTerminalToken("C2");
        NonTerminalToken instruction = new NonTerminalToken("In");
        // NonTerminalToken In2 = new NonTerminalToken("In2"); // JAMAIS APPELE A REVOIR
        NonTerminalToken instructionPlus = new NonTerminalToken("In3");
        NonTerminalToken instructionExpressionAssignment = new NonTerminalToken("In4"); // In4
        NonTerminalToken instructionAssignment = new NonTerminalToken("In5");
        NonTerminalToken elseIf = new NonTerminalToken("Eif"); // Eif
        NonTerminalToken else1 = new NonTerminalToken("Ei"); // Ei
        NonTerminalToken reverse = new NonTerminalToken("R1"); // R1
        NonTerminalToken method = new NonTerminalToken("M");
        NonTerminalToken methodFact = new NonTerminalToken("M1");
        NonTerminalToken method0or1 = new NonTerminalToken("M2");
        NonTerminalToken parameters = new NonTerminalToken("PS");
        NonTerminalToken parametersSemicolonPlus = new NonTerminalToken("PS1");
        NonTerminalToken parameters0or1 = new NonTerminalToken("PS2");
        NonTerminalToken parameter = new NonTerminalToken("P");
        NonTerminalToken identificator0or1 = new NonTerminalToken("Ident2");
        NonTerminalToken type = new NonTerminalToken("T");
        NonTerminalToken operator = new NonTerminalToken("Op");




        axiom.setAction(() -> {
            System.out.println("axiom");
            Node node = new Node("axiom -> with Ada.Text_IO; use Ada.Text_IO; ...");

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
            node.addChild(instruction.execute());
            node.addChild(instructionPlus.execute());
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
                Node node = new Node("declarationStar -> declaration declarationStar");
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

                Node node = new Node("declaration -> type IDENTIFIER declarationFact1");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) { // -------------------------------
                    print("IDENTIFIER");

                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();

                    node.addChild(declarationFact1.execute());
                    return node;

                } else { // ---------------------------------------------------------------------------
                    print("Error IDENTIFIER");

                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) { // ---------------------------------------------
                print("IDENTIFIER");

                Node node = new Node("declaration -> IDENTIFIER indentificatorCommaPlus : type expressionAssignment;");
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                if (lexer.currentTokenType().equals("COLON")) { // ------------------------------------
                    print(":");
                    node.addChild(new Node(lexer.getCurrentToken().getValue()));
                    lexer.nextToken();

                    node.addChild(type.execute());
                    node.addChild(expressionAssignment.execute());

                    if (lexer.currentTokenType().equals("SEMICOLON")) { // ------------------
                        print(";");

                        node.addChild(new Node(lexer.currentTokenValue()));
                        lexer.nextToken();

                    } else { // -------------------------------------------------------------
                        print("Error ;");
                        node.setFailed(true);
                        node.addFailedExplanation("Expected ';'");
                    }

                } else { // ----------------------------------------------------------------------------
                    print("Error :");
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':'");
                }

                return node;

            } else if (lexer.currentTokenValue().equals("procedure")) { // ---------------------------------------------
                print("procedure");

                Node node = new Node("declaration -> procedure IDENTIFIER [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");

                node.addChild(parameters0or1.execute());

                valueTest(node, lexer, "is");

                node.addChild(declarationStar.execute());

                valueTest(node, lexer, "begin");

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                valueTest(node, lexer, "end");

                node.addChild(identificator0or1.execute());

                typeTest(node, lexer, "SEMICOLON");

                return node;

            } else if (lexer.currentTokenValue().equals("function")) {
                print("function");
                Node node = new Node("declaration -> function IDENTIFIER [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");

                node.addChild(parameters0or1.execute());

                valueTest(node, lexer, "return");

                node.addChild(type.execute());

                valueTest(node, lexer, "is");

                node.addChild(declarationStar.execute());

                valueTest(node, lexer, "begin");

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                valueTest(node, lexer, "end");

                node.addChild(identificator0or1.execute());

                typeTest(node, lexer, "SEMICOLON");

                return node;

            } else {
                print("Error declaration");
                throw new Exception("Expected 'type', 'procedure' or 'function' keyword");
            }
        });

        declarationFact1.setAction(() -> {
            System.out.println("declarationFact1");
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                print(";");
                Node node = new Node("declarationFact1 -> ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else if (lexer.currentTokenValue().equals("is")) {
                print("is");
                Node node = new Node("declarationFact1 -> is");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(declarationFact2.execute());
                return node;
            } else {
                print("Error");
                Node node = new Node("declarationFact1");
                node.setFailed(true);
                node.addFailedExplanation("Expected ';' or 'is' keyword");
                return node;
            }
        });

        declarationFact2.setAction(() -> {
            System.out.println("declarationFact2");
            if (lexer.currentTokenValue().equals("access")) {
                print("access");
                Node node = new Node("declarationFact2 -> access type");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("record")) {
                print("record");
                Node node = new Node("declarationFact2 -> record field fieldPlus end record;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(field.execute());
                node.addChild(fieldPlus.execute());

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "record");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else {
                print("Error");
                Node node = new Node("declarationFact2");
                node.setFailed(true);
                node.addFailedExplanation("Expected 'access' or 'record' keyword");
                return node;
            }
        });

        field.setAction(() -> {
            System.out.println("field");
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("IDENTIFIER");
                Node node = new Node("field -> IDENTIFIER identificatorCommaPlus : type ;");
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("identificatorCommaPlus -> , IDENTIFIER identificatorCommaPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                return node;

            } else {
                print("identificatorCommaPlus -> Epsilon");
                return new Node("identificatorCommaPlus -> Epsilon");

            }
        });

        fieldPlus.setAction(() -> {
            System.out.println("fieldPlus");
            try {
                print("fieldPlus -> field fieldPlus");
                Node node = new Node("fieldPlus -> field fieldPlus");
                node.addChild(field.execute());
                node.addChild(fieldPlus.execute());
                return node;
            } catch (Exception e) {
                print("fieldPlus -> Epsilon");
                return new Node("fieldPlus -> Epsilon");
            }
        });

        method.setAction(() -> {
            System.out.println("method");
            if (lexer.currentTokenValue().equals("in")) {
                print("in");
                Node node = new Node("method -> in methodFact");
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("methodFact -> out");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                print("Error out");
                return new Node("methodFact -> Epsilon");
            }
        });

        method0or1.setAction(() -> {
            System.out.println("method0or1");
            try {
                print("method0or1 -> method");
                Node node = new Node("method0or1 -> method");
                node.addChild(method.execute());
                return node;
            } catch (Exception e) {
                return new Node("method0or1 -> Epsilon");
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
                Node node = new Node("operator -> and then");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else if (lexer.currentTokenValue().equals("or") && lexer.getNextToken().getValue().equals("else")) {
                print("or else");
                Node node = new Node("operator -> or else");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else if (isOperator) {
                print("operator -> " + lexer.currentTokenValue());
                Node node = new Node("operator -> " + lexer.currentTokenValue());
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("parameters -> ( parameter parametersSemicolonPlus )");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(parameter.execute());
                node.addChild(parametersSemicolonPlus.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                return node;
            } else {
                print("Error (");
                throw new Exception("Expected '('");
            }
        });

        parametersSemicolonPlus.setAction(() -> {
            System.out.println("parametersSemicolonPlus");
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                print(";");
                Node node = new Node("parametersSemicolonPlus -> ; parameter parametersSemicolonPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(parameter.execute());
                node.addChild(parametersSemicolonPlus.execute());

                return node;
            } else {
                print("parametersSemicolonPlus -> Epsilon");
                return new Node("parametersSemicolonPlus -> Epsilon");
            }
        });

        parameters0or1.setAction(() -> {
            System.out.println("parameters0or1");
            try {
                print("parameters0or1 -> parameters");
                Node node = new Node("parameters0or1 -> parameters");
                node.addChild(parameters.execute());
                return node;
            } catch (Exception e) {
                print("parameters0or1 -> Epsilon");
                return new Node("parameters0or1 -> Epsilon");
            }
        });

        parameter.setAction(() -> {
            System.out.println("parameter");
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("IDENTIFIER");
                Node node = new Node("parameter -> IDENTIFIER identificatorCommaPlus : type method0or1");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                typeTest(node, lexer, "COLON");

                node.addChild(method0or1.execute());
                node.addChild(type.execute());

                return node;
            } else {
                print("Error IDENTIFIER");
                Node node = new Node("parameter");
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier");
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
                Node node = new Node("instruction -> " + lexer.currentTokenValue() + " [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("instruction -> ( expression ) expressionFollow . IDENTIFIER := expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("instruction -> character'val [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("instruction -> IDENTIFIER instructionExpressionAssignment");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(instructionExpressionAssignment.execute());
                return node;

            } else if (lexer.currentTokenValue().equals("return")) {
                print("return");
                Node node = new Node("instruction -> return expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("begin")) {
                print("begin");
                Node node = new Node("instruction -> begin instruction instructionPlus end IDENTIFIER ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(instructionPlus.execute());

                valueTest(node, lexer, "end");
                typeTest(node, lexer, "SEMICOLON");
                return node;


            } else if (lexer.currentTokenValue().equals("if")) {
                print("if");
                Node node = new Node("instruction -> if expression then instruction instructionPlus elseIf else1 end if ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                valueTest(node, lexer, "then");

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                node.addChild(elseIf.execute());
                node.addChild(else1.execute());

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "if");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("for")) {
                print("for");
                Node node = new Node("instruction -> for IDENTIFIER in reverse expression .. expression loop instruction instructionPlus end loop ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                valueTest(node, lexer, "in");

                node.addChild(reverse.execute());

                node.addChild(expression.execute());

                typeTest(node, lexer, "DOUBLE_DOT");

                node.addChild(expression.execute());

                valueTest(node, lexer, "loop");

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "loop");
                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else if (lexer.currentTokenValue().equals("while")) {
                print("while");
                Node node = new Node("instruction -> while expression loop instruction instructionPlus end loop ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                valueTest(node, lexer, "loop");

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                valueTest(node, lexer, "end");
                valueTest(node, lexer, "loop");
                typeTest(node, lexer, "SEMICOLON");

                return node;

            } else {
                print("current Token : " + lexer.currentTokenValue());
                throw new Exception("Expected an instruction");
            }
        });

        instructionExpressionAssignment.setAction(() -> {
            System.out.println("instructionExpressionAssignment");
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("instructionExpressionAssignment -> ( expression ) expressionFollow . IDENTIFIER := expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(instructionAssignment.execute());

                return node;

            } else if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                print(":=");
                Node node = new Node("instructionExpressionAssignment -> := expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "SEMICOLON");
                return node;

            } else {
                Node node = new Node("instructionExpressionAssignment -> expressionFollow [...] ");
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
                Node node = new Node("instructionAssignment -> ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                print("Error ;");
                Node node = new Node("instructionAssignment -> expression ;");
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
                Node node = new Node("instructionPlus -> instruction instructionPlus");
                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                return node;
            } catch (Exception e) {
                print("Catch");
                return new Node("instructionPlus -> Epsilon");
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
                Node node = new Node("expression -> " + lexer.currentTokenValue() + " expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (goodToken2) {
                    print("expression -> " + lexer.currentTokenValue() + " expressionFollow");
                    node.addChild(expression.execute());
                }

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("expression -> ( expression ) expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenValue().equals("character")) {
                print("character");
                Node node = new Node("expression -> character'val ( expression ) expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
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
                Node node = new Node("expression -> IDENTIFIER expressionFact");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expressionFact.execute());
                return node;
            } else {
                print("Error expression :" + lexer.currentTokenValue());
                Node node = new Node("expression");
                node.setFailed(true);
                node.addFailedExplanation("Expected an expression");
                return node;
            }
        });

        expressionFollow.setAction(() -> {
            System.out.println("expressionFollow");
            if (lexer.currentTokenType().equals("DOT")) {
                print(".");
                Node node = new Node("expressionFollow -> . IDENTIFIER expressionAssignment");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                node.addChild(expressionFollow.execute());
                return node;
            }

            // Try operator and check if the error raised is Op else do nothing
            try {
                print("Try");
                Node node = new Node("expressionFollow -> operator expression expressionFollow");
                node.addChild(operator.execute());
                node.addChild(expression.execute());
                node.addChild(expressionFollow.execute());
                return node;
            } catch (Exception e) {
                print("Catch");
                return new Node("expressionFollow -> Epsilon");
            }
        });

        expressionCommaPlus.setAction(() -> {
            System.out.println("expressionCommaPlus");
            if (lexer.currentTokenType().equals("COMMA")) {
                print(",");
                Node node = new Node("expressionCommaPlus -> , expression expressionCommaPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());
                return node;
            } else {
                print("expressionCommaPlus -> Epsilon");
                return new Node("expressionCommaPlus -> Epsilon");
            }
        });

        expressionAssignment.setAction(() -> {
            System.out.println("expressionAssignment");
            if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                print(":=");
                Node node = new Node("expressionAssignment -> := expression");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                return node;
            } else {
                print("expressionAssignment -> Epsilon");
                return new Node("expressionAssignment -> Epsilon");
            }
        });

        expressionFact.setAction(() -> {
            System.out.println("expressionFact");
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                print("(");
                Node node = new Node("expressionFact -> ( expressionCommaPlus ) expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());

                typeTest(node, lexer, "R_PARENTHESIS");

                node.addChild(expressionFollow.execute());
                return node;

            } else {
                print("expressionFact -> Epsilon");
                Node node = new Node("expressionFact -> expressionFollow");
                node.addChild(expressionFollow.execute());
                return node;
            }
        });

        expression0or1.setAction(() -> {
            System.out.println("expression0or1");
            try {
                print("Try");
                Node node = new Node("expression0or1 -> expression");
                node.addChild(expression.execute());
                return node;
            } catch (Exception e) {
                print("Catch");
                return new Node("expression0or1 -> Epsilon");
            }
        });

        elseIf.setAction(() -> {
            System.out.println("elseIf");
            if (lexer.currentTokenValue().equals("elsif")) {
                print("elsif");
                Node node = new Node("elseIf -> elsif expression then instruction instructionPlus elseIf");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                valueTest(node, lexer, "then");

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                node.addChild(elseIf.execute());
                return node;
            } else {
                print("elseIf -> Epsilon");
                return new Node("elseIf -> Epsilon");
            }
        });

        else1.setAction(() -> {
            System.out.println("else1");
            if (lexer.currentTokenValue().equals("else")) {
                print("else");
                Node node = new Node("else1 -> else instruction instructionPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                return node;
            } else {
                print("else1 -> Epsilon");
                return new Node("else1 -> Epsilon");
            }
        });

        reverse.setAction(() -> {
            System.out.println("reverse");
            if (lexer.currentTokenValue().equals("reverse")) {
                print("reverse");
                Node node = new Node("reverse -> reverse");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                print("reverse -> Epsilon");
                return new Node("reverse -> Epsilon");
            }
        });

        type.setAction(() -> {
            System.out.println("type");

            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("type -> IDENTIFIER");
                Node node = new Node("type -> IDENTIFIER");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;

            } else if (lexer.currentTokenValue().equals("access")) {
                print("type -> access type");
                Node node = new Node("type -> access type");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                typeTest(node, lexer, "IDENTIFIER");
                return node;

            } else {
                print("Error");
                Node node = new Node("type");
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier or 'access' keyword");
                return node;
            }
        });

        identificator0or1.setAction(() -> {
            System.out.println("identificator0or1");
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                print("identificator0or1 -> IDENTIFIER");
                Node node = new Node("identificator0or1 -> IDENTIFIER");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                print("identificator0or1 -> Epsilon");
                return new Node("identificator0or1 -> Epsilon");
            }
        });


        Node node = axiom.execute();
        System.out.println(node);
        System.out.println("Test passé");


    }
}
