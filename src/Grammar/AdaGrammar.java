package Grammar;

import Grammar.Token.NonTerminalToken;
import Grammar.Token.TerminalToken;
import Lexer.Lexer;

public class AdaGrammar {

    public void grammar1(String entry) {
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
            Node node = new Node("F -> with Ada.Text_IO; use Ada.Text_IO; ...");

            if (!(lexer.getCurrentToken().getValue().equals("with") && lexer.getCurrentToken().getType().getName().equals("KEYWORD"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'with' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getValue().equals("Ada"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'Ada' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getType().getName().equals("DOT"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected '.'");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getValue().equals("Text_IO"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'Text_IO' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getType().getName().equals("SEMICOLON"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected ';'");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getValue().equals("use"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'use' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getValue().equals("Ada"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'Ada' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getType().getName().equals("DOT"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected '.'");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getValue().equals("Text_IO"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'Text_IO' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getType().getName().equals("SEMICOLON"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected ';'");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getValue().equals("procedure"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'procedure' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.getCurrentToken().getType().getName().equals("IDENTIFIER"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            if (!(lexer.currentTokenValue().equals("is"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'is' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            node.addChild(declarationStar.execute());

            if (!(lexer.currentTokenValue().equals("begin"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'begin' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            node.addChild(instruction.execute());

            node.addChild(instructionPlus.execute());

            if (!(lexer.currentTokenValue().equals("end"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected 'end' keyword");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            node.addChild(identificator0or1.execute());

            if (!(lexer.currentTokenType().equals("SEMICOLON"))) {
                node.setFailed(true);
                node.addFailedExplanation("Expected ';'");
            } else {
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();
            }

            return node;
        });

        declarationStar.setAction(() -> {
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

            if (lexer.getCurrentToken().getValue().equals("type")) { // ------------------------------------------------

                Node node = new Node("declaration -> type IDENTIFIER declarationFact1");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) { // -------------------------------

                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();

                    node.addChild(declarationFact1.execute());
                    return node;

                } else { // ---------------------------------------------------------------------------
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) { // ---------------------------------------------

                Node node = new Node("declaration -> IDENTIFIER indentificatorCommaPlus : type expressionAssignment;");
                node.addChild(new Node(lexer.getCurrentToken().getValue()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                if (lexer.currentTokenType().equals("COLON")) { // ------------------------------------
                    node.addChild(new Node(lexer.getCurrentToken().getValue()));
                    lexer.nextToken();

                    node.addChild(type.execute());
                    node.addChild(expressionAssignment.execute());

                    if (lexer.currentTokenType().equals("SEMICOLON")) { // ------------------

                        node.addChild(new Node(lexer.currentTokenValue()));
                        lexer.nextToken();

                    } else { // -------------------------------------------------------------
                        node.setFailed(true);
                        node.addFailedExplanation("Expected ';'");
                    }

                } else { // ----------------------------------------------------------------------------
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':'");
                }

                return node;

            } else if (lexer.currentTokenValue().equals("procedure")) { // ---------------------------------------------

                Node node = new Node("declaration -> procedure IDENTIFIER [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (!lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(parameters0or1.execute());

                if (!lexer.currentTokenValue().equals("is")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'is' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(declarationStar.execute());

                if (!lexer.currentTokenValue().equals("begin")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'begin' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                if (!lexer.currentTokenValue().equals("end")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(identificator0or1.execute());

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else if (lexer.currentTokenValue().equals("function")) {
                Node node = new Node("declaration -> function IDENTIFIER [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (!lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(parameters0or1.execute());

                if (!lexer.currentTokenValue().equals("return")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'return' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(type.execute());

                if (!lexer.currentTokenValue().equals("is")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'is' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(declarationStar.execute());

                if (!lexer.currentTokenValue().equals("begin")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'begin' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                if (!lexer.currentTokenValue().equals("end")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(identificator0or1.execute());

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else {
                Node node = new Node("declaration");
                node.setFailed(true);
                node.addFailedExplanation("Expected 'type', 'procedure' or 'function' keyword");
                return node;
            }
        });

        declarationFact1.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                Node node = new Node("declarationFact1 -> ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else if (lexer.currentTokenValue().equals("is")) {
                Node node = new Node("declarationFact1 -> is");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(declarationStar.execute());
                return node;
            } else {
                Node node = new Node("declarationFact1");
                node.setFailed(true);
                node.addFailedExplanation("Expected ';' or 'is' keyword");
                return node;
            }
        });

        declarationFact2.setAction(() -> {

            if (lexer.currentTokenValue().equals("access")) {
                Node node = new Node("declarationFact2 -> access type");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (!lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else if (lexer.currentTokenValue().equals("record")) {
                Node node = new Node("declarationFact2 -> record field fieldPlus end record;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(field.execute());
                node.addChild(fieldPlus.execute());

                if (!lexer.currentTokenValue().equals("end")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenValue().equals("record")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'record' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;


            } else {
                Node node = new Node("declarationFact2");
                node.setFailed(true);
                node.addFailedExplanation("Expected 'access' or 'record' keyword");
                return node;
            }
        });

        field.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                Node node = new Node("field -> IDENTIFIER identificatorCommaPlus : type ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                if (!lexer.currentTokenType().equals("COLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(type.execute());

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else {
                Node node = new Node("field");
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier");
                return node;
            }
        });

        identificatorCommaPlus.setAction(() -> {
            if (lexer.currentTokenType().equals("COMMA")) {
                Node node = new Node("identificatorCommaPlus -> , IDENTIFIER identificatorCommaPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();

                    node.addChild(identificatorCommaPlus.execute());
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                return node;

            } else {

                return new Node("identificatorCommaPlus -> Epsilon");

            }
        });

        fieldPlus.setAction(() -> {
            try {
                Node node = new Node("fieldPlus -> field fieldPlus");

                node.addChild(field.execute());
                node.addChild(fieldPlus.execute());
                return node;
            } catch (Exception e) {
                return new Node("fieldPlus -> Epsilon");
            }
        });

        method.setAction(() -> {
            if (lexer.currentTokenValue().equals("in")) {
                Node node = new Node("method -> in methodFact");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(methodFact.execute());
                return node;
            } else {
                Node node = new Node("method");
                node.setFailed(true);
                node.addFailedExplanation("Expected 'in' keyword");
                return node;
            }
        });

        methodFact.setAction(() -> {
            if (lexer.currentTokenValue().equals("out")) {
                Node node = new Node("methodFact -> out");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                return new Node("methodFact -> Epsilon");
            }
        });

        method0or1.setAction(() -> {
            try {
                Node node = new Node("method0or1 -> method");
                node.addChild(method.execute());
                return node;
            } catch (Exception e) {
                return new Node("method0or1 -> Epsilon");
            }
        });

        operator.setAction(() -> {
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
                Node node = new Node("operator -> and then");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else if (lexer.currentTokenValue().equals("or") && lexer.getNextToken().getValue().equals("else")) {
                Node node = new Node("operator -> or else");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else if (isOperator) {
                Node node = new Node("operator -> " + lexer.currentTokenValue());
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                Node node = new Node("operator");
                node.setFailed(true);
                node.addFailedExplanation("Expected an operator");
                return node;
            }
        });

        parameters.setAction(() -> {
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                Node node = new Node("parameters -> ( parameter parametersSemicolonPlus )");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(parameter.execute());
                node.addChild(parametersSemicolonPlus.execute());

                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                }

                return node;
            } else {
                Node node = new Node("parameters");
                node.setFailed(true);
                node.addFailedExplanation("Expected '('");
                return node;
            }
        });

        parametersSemicolonPlus.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                Node node = new Node("parametersSemicolonPlus -> ; parameter parametersSemicolonPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(parameter.execute());
                node.addChild(parametersSemicolonPlus.execute());

                return node;
            } else {
                return new Node("parametersSemicolonPlus -> Epsilon");
            }
        });

        parameters0or1.setAction(() -> {
            try {
                Node node = new Node("parameters0or1 -> parameters");
                node.addChild(parameters.execute());
                return node;
            } catch (Exception e) {
                return new Node("parameters0or1 -> Epsilon");
            }
        });

        parameter.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                Node node = new Node("parameter -> IDENTIFIER identificatorCommaPlus : type method0or1");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(identificatorCommaPlus.execute());

                if (lexer.currentTokenType().equals("COLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();

                    node.addChild(method0or1.execute());
                    node.addChild(type.execute());
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':'");
                }

                return node;
            } else {
                Node node = new Node("parameter");
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier");
                return node;
            }
        });

        instruction.setAction(() -> {
            boolean goodToken;
            boolean goodToken2;
            goodToken = lexer.currentTokenType().equals("CHARACTER");
            goodToken = goodToken || lexer.currentTokenType().equals("INTEGER");
            goodToken = goodToken || lexer.currentTokenType().equals("IDENTIFIER");
            goodToken = goodToken || lexer.currentTokenValue().equals("True");
            goodToken = goodToken || lexer.currentTokenValue().equals("False");
            goodToken = goodToken || lexer.currentTokenValue().equals("null");
            goodToken = goodToken || lexer.currentTokenValue().equals("float");
            goodToken2 = lexer.currentTokenValue().equals("not");
            goodToken2 = goodToken2 || lexer.currentTokenValue().equals("new");
            goodToken2 = goodToken2 || lexer.currentTokenType().equals("MINUS");

            if (goodToken || goodToken2) {
                Node node = new Node("instruction -> " + lexer.currentTokenValue() + " [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (goodToken2) {
                    node.addChild(expression.execute());
                }

                node.addChild(expressionFollow.execute());

                if (!lexer.currentTokenType().equals("DOT")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '.'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("ASSIGNMENT")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':='");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(expression.execute());

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                Node node = new Node("instruction -> ( expression ) expressionFollow . IDENTIFIER := expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (!lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(expressionFollow.execute());

                if (!lexer.currentTokenType().equals("DOT")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '.'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("ASSIGNMENT")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':='");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(expression.execute());

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else if (lexer.currentTokenValue().equals("character")) {
                Node node = new Node("instruction -> character'val [...]");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (!lexer.currentTokenType().equals("APOSTROPHE")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '''");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenValue().equals("val")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'val' keyword");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("L_PARENTHESIS")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '('");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(expression.execute());

                if (!lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(expressionFollow.execute());

                if (!lexer.currentTokenType().equals("DOT")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '.'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                if (!lexer.currentTokenType().equals("ASSIGNMENT")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':='");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                node.addChild(expression.execute());

                if (!lexer.currentTokenType().equals("SEMICOLON")) {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                } else {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                }

                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                Node node = new Node("instruction -> IDENTIFIER instructionExpressionAssignment");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(instructionExpressionAssignment.execute());
                return node;

            } else if (lexer.currentTokenValue().equals("return")) {
                Node node = new Node("instruction -> return expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;

            } else if (lexer.currentTokenValue().equals("begin")) {
                Node node = new Node("instruction -> begin instruction instructionPlus end IDENTIFIER ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(instructionPlus.execute());

                if (lexer.currentTokenValue().equals("end")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                }

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;


            } else if (lexer.currentTokenValue().equals("if")) {
                Node node = new Node("instruction -> if expression then instruction instructionPlus elseIf else1 end if ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (lexer.currentTokenValue().equals("then")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'then' keyword");
                }

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                node.addChild(elseIf.execute());
                node.addChild(else1.execute());

                if (lexer.currentTokenValue().equals("end")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                }

                if (lexer.currentTokenValue().equals("if")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'if' keyword");
                }

                return node;
            } else if (lexer.currentTokenValue().equals("for")) {
                Node node = new Node("instruction -> for IDENTIFIER in reverse expression .. expression loop instruction instructionPlus end loop ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                if (lexer.currentTokenValue().equals("in")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'in' keyword");
                }

                node.addChild(reverse.execute());

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("DOUBLE_DOT")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '..'");
                }

                node.addChild(expression.execute());

                if (lexer.currentTokenValue().equals("loop")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'loop' keyword");
                }

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());

                if (lexer.currentTokenValue().equals("end")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                }

                if (lexer.currentTokenValue().equals("loop")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'loop' keyword");
                }

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;

            } else if (lexer.currentTokenValue().equals("while")) {
                Node node = new Node("instruction -> while expression loop instruction instructionPlus end loop ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (lexer.currentTokenValue().equals("loop")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'loop' keyword");
                }

                node.addChild(instruction.execute());

                node.addChild(instructionPlus.execute());

                if (lexer.currentTokenValue().equals("end")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'end' keyword");
                }

                if (lexer.currentTokenValue().equals("loop")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'loop' keyword");
                }

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;

            } else {
                Node node = new Node("instruction");
                node.setFailed(true);
                node.addFailedExplanation("Expected an instruction");
                return node;
            }
        });

        instructionExpressionAssignment.setAction(() -> {
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                Node node = new Node("instructionExpressionAssignment -> ( expression ) expressionFollow . IDENTIFIER := expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());

                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                }

                node.addChild(instructionAssignment.execute());

                return node;

            } else if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                Node node = new Node("instructionExpressionAssignment -> := expression ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;

            } else {
                Node node = new Node("instructionExpressionAssignment -> expressionFollow [...] ");
                node.addChild(expressionFollow.execute());

                if (lexer.currentTokenType().equals("DOT")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '.'");
                }

                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':='");
                }

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;
            }
        });

        instructionAssignment.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                Node node = new Node("instructionAssignment -> ;");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                Node node = new Node("instructionAssignment -> expression ;");
                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("DOT")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '.'");
                }

                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ':='");
                }

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ';'");
                }

                return node;
            }
        });

        instructionPlus.setAction(() -> {
            try {
                Node node = new Node("instructionPlus -> instruction instructionPlus");
                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                return node;
            } catch (Exception e) {
                return new Node("instructionPlus -> Epsilon");
            }
        });

        expression.setAction(() -> {
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
                Node node = new Node("expression -> " + lexer.currentTokenValue() + " expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (goodToken2) {
                    node.addChild(expression.execute());
                }

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                Node node = new Node("expression -> ( expression ) expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                }

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenValue().equals("character")) {
                Node node = new Node("expression -> character'val ( expression ) expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();


                if (lexer.currentTokenType().equals("APOSTROPHE")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '''");
                }

                if (lexer.currentTokenValue().equals("val")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'val' keyword");
                }

                if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected '('");
                }

                node.addChild(expression.execute());

                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                }

                node.addChild(expressionFollow.execute());
                return node;

            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                Node node = new Node("expression -> IDENTIFIER expressionFact");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expressionFact.execute());
                return node;
            } else {
                Node node = new Node("expression");
                node.setFailed(true);
                node.addFailedExplanation("Expected an expression");
                return node;
            }
        });

        expressionFollow.setAction(() -> {
            if (lexer.currentTokenType().equals("DOT")) {
                Node node = new Node("expressionFollow -> . IDENTIFIER expressionAssignment");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                node.addChild(expressionFollow.execute());
            }

            // Try operator and check if the error raised is Op else do nothing
            try {
                Node node = new Node("expressionFollow -> operator expression expressionFollow");
                node.addChild(operator.execute());
                node.addChild(expression.execute());
                node.addChild(expressionFollow.execute());
                return node;
            } catch (Exception e) {
                return new Node("expressionFollow -> Epsilon");
            }
        });

        expressionCommaPlus.setAction(() -> {
            if (lexer.currentTokenType().equals("COMMA")) {
                Node node = new Node("expressionCommaPlus -> , expression expressionCommaPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());
                return node;
            } else {
                return new Node("expressionCommaPlus -> Epsilon");
            }
        });

        expressionAssignment.setAction(() -> {
            if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                Node node = new Node("expressionAssignment -> := expression");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                return node;
            } else {
                return new Node("expressionAssignment -> Epsilon");
            }
        });

        expressionFact.setAction(() -> {
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                Node node = new Node("expressionFact -> ( expressionCommaPlus ) expressionFollow");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());
                node.addChild(expressionCommaPlus.execute());

                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected ')'");
                }

                node.addChild(expressionFollow.execute());
                return node;

            } else {
                Node node = new Node("expressionFact -> expressionFollow");
                node.addChild(expressionFollow.execute());
                return node;
            }
        });

        expression0or1.setAction(() -> {
            try {
                Node node = new Node("expression0or1 -> expression");
                node.addChild(expression.execute());
                return node;
            } catch (Exception e) {
                return new Node("expression0or1 -> Epsilon");
            }
        });

        elseIf.setAction(() -> {
            if (lexer.currentTokenValue().equals("elsif")) {
                Node node = new Node("elseIf -> elsif expression then instruction instructionPlus elseIf");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(expression.execute());

                if (lexer.currentTokenValue().equals("then")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected 'then' keyword");
                }

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                node.addChild(elseIf.execute());
                return node;
            } else {
                return new Node("elseIf -> Epsilon");
            }
        });

        else1.setAction(() -> {
            if (lexer.currentTokenValue().equals("else")) {
                Node node = new Node("else1 -> else instruction instructionPlus");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                node.addChild(instruction.execute());
                node.addChild(instructionPlus.execute());
                return node;
            } else {
                return new Node("else1 -> Epsilon");
            }
        });

        reverse.setAction(() -> {
            if (lexer.currentTokenValue().equals("reverse")) {
                Node node = new Node("reverse -> reverse");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                return new Node("reverse -> Epsilon");
            }
        });

        type.setAction(() -> {

            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                Node node = new Node("type -> IDENTIFIER");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;

            } else if (lexer.currentTokenValue().equals("access")) {
                Node node = new Node("type -> access type");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();

                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    node.addChild(new Node(lexer.currentTokenValue()));
                    lexer.nextToken();
                } else {
                    node.setFailed(true);
                    node.addFailedExplanation("Expected an identifier");
                }

                return node;

            } else {
                Node node = new Node("type");
                node.setFailed(true);
                node.addFailedExplanation("Expected an identifier or 'access' keyword");
                return node;
            }
        });

        identificator0or1.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                Node node = new Node("identificator0or1 -> IDENTIFIER");
                node.addChild(new Node(lexer.currentTokenValue()));
                lexer.nextToken();
                return node;
            } else {
                return new Node("identificator0or1 -> Epsilon");
            }
        });


        axiom.execute();
        System.out.println("Test passé");


    }
}
