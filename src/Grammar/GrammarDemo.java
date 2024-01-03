package Grammar;

import Grammar.Token.NonTerminalToken;
import Grammar.Token.TerminalToken;
import Lexer.Lexer;

import java.util.Objects;

public class GrammarDemo {

    private void print(String s) {
        System.out.println(s);
    }


    public void demo1(String entry) {
        Grammar grammar = new Grammar();
        Lexer lexer = new Lexer(entry);

        try {
            lexer.tokenize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Test pour la grammaire : a^(n)b^(n)");

        NonTerminalToken S = new NonTerminalToken("S");
        TerminalToken a = new TerminalToken("a");
        TerminalToken b = new TerminalToken("b");

        grammar.addNonTerminal(S.setAction(() -> {
            if (Objects.equals(lexer.getCurrentToken().getValue(), "a")) {
                System.out.println("S -> aSb");
                lexer.nextToken();
                S.execute();
                if (Objects.equals(lexer.getCurrentToken().getValue(), "b")) {
                    lexer.nextToken();
                } else {
                    System.out.println("Erreur");
                }
            } else {
                System.out.println("S -> epsilon");
            }

        }));

        S.execute();
        System.out.println("Test passé avec succès");
    }


    public void grammar1(String entry) {
        Lexer lexer = new Lexer(entry);
        try {
            lexer.tokenize();
        } catch (Exception e) {
            e.printStackTrace();
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
        NonTerminalToken In2 = new NonTerminalToken("In2"); // JAMAIS APPELE A REVOIR
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


        axiom.setAction(() -> {
            if (lexer.getCurrentToken().getValue().equals("with") && lexer.getCurrentToken().getType().getName().equals("KEYWORD")) {
                lexer.nextToken();
                if (lexer.getCurrentToken().getValue().equals("Ada")) {
                    lexer.nextToken();
                    if (lexer.getCurrentToken().getType().getName().equals("DOT")) {
                        lexer.nextToken();
                        if (lexer.getCurrentToken().getValue().equals("Text_IO")) {
                            lexer.nextToken();
                            if (lexer.getCurrentToken().getType().getName().equals("SEMICOLON")) {
                                lexer.nextToken();
                                if (lexer.getCurrentToken().getValue().equals("use")) {
                                    lexer.nextToken();
                                    if (lexer.getCurrentToken().getValue().equals("Ada")) {
                                        lexer.nextToken();
                                        if (lexer.getCurrentToken().getType().getName().equals("DOT")) {
                                            lexer.nextToken();
                                            if (lexer.getCurrentToken().getValue().equals("Text_IO")) {
                                                lexer.nextToken();
                                                if (lexer.getCurrentToken().getType().getName().equals("SEMICOLON")) {
                                                    lexer.nextToken();
                                                    if (lexer.getCurrentToken().getType().getName().equals("KEYWORD") && lexer.getCurrentToken().getValue().equals("procedure")) {
                                                        lexer.nextToken();
                                                        if (lexer.getCurrentToken().getType().getName().equals("IDENTIFIER")) {
                                                            lexer.nextToken();
                                                            if (lexer.getCurrentToken().getType().getName().equals("KEYWORD") && lexer.getCurrentToken().getValue().equals("is")) {
                                                                lexer.nextToken();
                                                                declarationStar.execute();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        declarationStar.setAction(() -> {
            // Try to do rules D and F1 else do nothing
            try {
                declaration.execute();
                declarationStar.execute();
                return;
            } catch (Exception e) {
                System.out.println("catch F1");
                return;
            }

        });

        declaration.setAction(() -> {
            if (lexer.currentTokenType().equals("KEYWORD") && lexer.getCurrentToken().getValue().equals("type")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    declarationFact1.execute();
                }
            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                identificatorCommaPlus.execute();
                if (lexer.currentTokenType().equals("COLON")) {
                    lexer.nextToken();
                    type.execute();
                    expressionAssignment.execute();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            } else if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("procedure")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    parameters0or1.execute();
                    if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("is")) {
                        lexer.nextToken();
                        declarationStar.execute();
                        if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("begin")) {
                            lexer.nextToken();
                            instruction.execute();
                            instructionPlus.execute();
                            if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("end")) {
                                lexer.nextToken();
                                identificator0or1.execute();
                                if (lexer.currentTokenType().equals("SEMICOLON")) {
                                    lexer.nextToken();
                                }
                            }
                        }
                    }
                }
            } else if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("function")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    parameters0or1.execute();
                    if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("return")) {
                        lexer.nextToken();
                        type.execute();
                        if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("is")) {
                            lexer.nextToken();
                            declarationStar.execute();
                            if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("begin")) {
                                lexer.nextToken();
                                instruction.execute();
                                instructionPlus.execute();
                                if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("end")) {
                                    lexer.nextToken();
                                    identificator0or1.execute();
                                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                                        lexer.nextToken();
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                throw new RuntimeException("D");
            }
        });

        declarationFact1.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                lexer.nextToken();
            } else if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("is")) {
                lexer.nextToken();
                declarationFact2.execute();
            } else {
                throw new RuntimeException("D1");
            }
        });

        declarationFact2.setAction(() -> {
            if (lexer.currentTokenValue().equals("access")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                        print("Leo");
                    }
                }
            } else if (lexer.currentTokenValue().equals("record")) {
                field.execute();
                fieldPlus.execute();
                if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("end")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            }
        });

        field.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                identificatorCommaPlus.execute();
                if (lexer.currentTokenType().equals("COLON")) {
                    lexer.nextToken();
                    type.execute();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            } else {
                throw new RuntimeException("C");
            }
        });

        identificatorCommaPlus.setAction(() -> {
            if (lexer.currentTokenType().equals("COMMA")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    identificatorCommaPlus.execute();
                }
            }
        });

        fieldPlus.setAction(() -> {
            try {
                field.execute();
                fieldPlus.execute();
            } catch (Exception e) {
                System.out.println("catch C2");
            }
        });

        method.setAction(() -> {
            if (lexer.currentTokenValue().equals("in")) {
                lexer.nextToken();
                methodFact.execute();
            }
        });

        methodFact.setAction(() -> {
            if (lexer.currentTokenValue().equals("out")) {
                lexer.nextToken();
            }
        });

        method0or1.setAction(() -> {
            try {
                method.execute();
            } catch (Exception e) {
                System.out.println("catch M2");
            }
        });

        // OPERATOR

        parameters.setAction(() -> {
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                lexer.nextToken();
                parameter.execute();
                parametersSemicolonPlus.execute();
                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    lexer.nextToken();
                }
            }
        });

        parametersSemicolonPlus.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                lexer.nextToken();
                parameter.execute();
                parametersSemicolonPlus.execute();
            }
        });

        parameters0or1.setAction(() -> {
            try {
                parameters.execute();
            } catch (Exception e) {
                System.out.println("catch PS2");
            }
        });

        parameter.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                identificatorCommaPlus.execute();
                if (lexer.currentTokenType().equals("COLON")) {
                    lexer.nextToken();
                    method0or1.execute();
                    type.execute();
                }
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
                lexer.nextToken();
                if (goodToken2) {
                    expression.execute();
                }
                expressionFollow.execute();
                if (lexer.currentTokenType().equals("DOT")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("IDENTIFIER")) {
                        lexer.nextToken();
                        if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                            lexer.nextToken();
                            expression.execute();
                            if (lexer.currentTokenType().equals("SEMICOLON")) {
                                lexer.nextToken();
                            }
                        }
                    }
                }
            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                lexer.nextToken();
                expression.execute();
                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    lexer.nextToken();
                    expressionFollow.execute();
                    if (lexer.currentTokenType().equals("DOT")) {
                        lexer.nextToken();
                        if (lexer.currentTokenType().equals("IDENTIFIER")) {
                            lexer.nextToken();
                            if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                                lexer.nextToken();
                                expression.execute();
                                if (lexer.currentTokenType().equals("SEMICOLON")) {
                                    lexer.nextToken();
                                }
                            }
                        }
                    }
                }
            } else if (lexer.currentTokenValue().equals("character")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("APOSTROPHE")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                        lexer.nextToken();
                        expression.execute();
                        if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                            lexer.nextToken();
                            expressionFollow.execute();
                            if (lexer.currentTokenType().equals("DOT")) {
                                lexer.nextToken();
                                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                                    lexer.nextToken();
                                    if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                                        lexer.nextToken();
                                        expression.execute();
                                        if (lexer.currentTokenType().equals("SEMICOLON")) {
                                            lexer.nextToken();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                instructionExpressionAssignment.execute();
            } else if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("return")) {
                expression0or1.execute();
                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    lexer.nextToken();
                }
            } else if (lexer.currentTokenValue().equals("begin")) {
                lexer.nextToken();
                instructionPlus.execute();
                if (lexer.currentTokenValue().equals("end")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            } else if (lexer.currentTokenValue().equals("if")) {
                lexer.nextToken();
                expression.execute();
                if (lexer.currentTokenValue().equals("then")) {
                    lexer.nextToken();
                    instruction.execute();
                    instructionPlus.execute();
                    elseIf.execute();
                    else1.execute();

                    if (lexer.currentTokenValue().equals("end")) {
                        lexer.nextToken();
                        if (lexer.currentTokenValue().equals("if")) {
                            lexer.nextToken();
                            if (lexer.currentTokenType().equals("SEMICOLON")) {
                                lexer.nextToken();
                            }
                        }
                    }
                }
            } else if (lexer.currentTokenValue().equals("for")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    if (lexer.currentTokenValue().equals("in")) {
                        lexer.nextToken();
                        reverse.execute();
                        expression.execute();
                        if (lexer.currentTokenType().equals("DOUBLE_DOT")) {
                            lexer.nextToken();
                            expression.execute();
                            if (lexer.currentTokenValue().equals("loop")) {
                                lexer.nextToken();
                                instruction.execute();
                                instructionPlus.execute();
                                if (lexer.currentTokenValue().equals("end")) {
                                    lexer.nextToken();
                                    if (lexer.currentTokenValue().equals("loop")) {
                                        lexer.nextToken();
                                        if (lexer.currentTokenType().equals("SEMICOLON")) {
                                            lexer.nextToken();
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } else if (lexer.currentTokenValue().equals("while")) {
                lexer.nextToken();
                expression.execute();
                if (lexer.currentTokenValue().equals("loop")) {
                    lexer.nextToken();
                    instruction.execute();
                    instructionPlus.execute();
                    if (lexer.currentTokenValue().equals("end")) {
                        lexer.nextToken();
                        if (lexer.currentTokenValue().equals("loop")) {
                            lexer.nextToken();
                            if (lexer.currentTokenType().equals("SEMICOLON")) {
                                lexer.nextToken();
                            }
                        }
                    }
                }
            } else {
                throw new RuntimeException("In");
            }
        });

        instructionExpressionAssignment.setAction(() -> {
            if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                lexer.nextToken();
                expression.execute();
                expressionCommaPlus.execute();
                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    lexer.nextToken();
                    instructionAssignment.execute();
                }
            } else if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                lexer.nextToken();
                expression.execute();
                if (lexer.currentTokenType().equals("SEMICOLON")) {
                    lexer.nextToken();
                }
            } else {
                expressionFollow.execute();
                if (lexer.currentTokenType().equals("DOT")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("IDENTIFIER")) {
                        lexer.nextToken();
                        if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                            lexer.nextToken();
                            expression.execute();
                            if (lexer.currentTokenType().equals("SEMICOLON")) {
                                lexer.nextToken();
                            }
                        }
                    }
                }
            }
        });

        instructionAssignment.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                lexer.nextToken();
            } else {
                expressionFollow.execute();
                if (lexer.currentTokenType().equals("DOT")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("IDENTIFIER")) {
                        lexer.nextToken();
                        if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                            lexer.nextToken();
                            expression.execute();
                            if (lexer.currentTokenType().equals("SEMICOLON")) {
                                lexer.nextToken();
                            }
                        }
                    }
                }
            }
        });

        instructionPlus.setAction(() -> {
            try {
                instruction.execute();
                instructionPlus.execute();
            } catch (Exception e) {
                System.out.println("catch In3");
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
                lexer.nextToken();
                if (goodToken2) {
                    expression.execute();
                }
                expressionFollow.execute();
            } else if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                lexer.nextToken();
                expression.execute();
                if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                    lexer.nextToken();
                    expressionFollow.execute();
                }
            } else if (lexer.currentTokenValue().equals("character")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("APOSTROPHE")) {
                    lexer.nextToken();
                    if (lexer.currentTokenValue().equals("val")) {
                        lexer.nextToken();
                        if (lexer.currentTokenType().equals("L_PARENTHESIS")) {
                            lexer.nextToken();
                            expression.execute();
                            if (lexer.currentTokenType().equals("R_PARENTHESIS")) {
                                lexer.nextToken();
                                expressionFollow.execute();
                            }
                        }
                    }
                }
            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                expressionFact.execute();
            } else {
                throw new RuntimeException("E");
            }
        });

        // expressionFollow

        expressionCommaPlus.setAction(() -> {
            if (lexer.currentTokenType().equals("COMMAT")) {
                lexer.nextToken();
                expression.execute();
                expressionCommaPlus.execute();
            }
        });

        expressionAssignment.setAction(() -> {
            if (lexer.currentTokenType().equals("ASSIGNMENT")) {
                lexer.nextToken();
                expression.execute();
            }
        });

        // expressionFact

        expression0or1.setAction(() -> {
            try {
                expression.execute();
            } catch (Exception e) {
                System.out.println("catch E5");
            }
        });

        elseIf.setAction(() -> {
            if (lexer.currentTokenValue().equals("elsif")) {
                lexer.nextToken();
                expression.execute();
                if (lexer.currentTokenValue().equals("then")) {
                    lexer.nextToken();
                    instruction.execute();
                    instructionPlus.execute();
                    elseIf.execute();
                }
            }
        });

        else1.setAction(() -> {
            if (lexer.currentTokenValue().equals("else")) {
                lexer.nextToken();
                instruction.execute();
                instructionPlus.execute();
            }
        });

        reverse.setAction(() -> {
            if (lexer.currentTokenValue().equals("reverse")) {
                lexer.nextToken();
            }
        });

        type.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
            } else if (lexer.currentTokenValue().equals("access")) {
                lexer.nextToken();
            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
            } else {
                throw new RuntimeException("T");
            }
        });

        identificator0or1.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
            }
        });


        axiom.execute();
        System.out.println("Test passé avec succès");


    }


    public void demo2(String entry) {
        Grammar grammar = new Grammar();
        Lexer lexer = new Lexer(entry);

        try {
            lexer.tokenize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(lexer.getTokens());
        System.out.println("Test pour la grammaire : a^(n)b^(n)");

        NonTerminalToken E0 = new NonTerminalToken("E0");
        NonTerminalToken E1 = new NonTerminalToken("E1");
        NonTerminalToken T0 = new NonTerminalToken("T0");
        NonTerminalToken T1 = new NonTerminalToken("T1");
        NonTerminalToken F0 = new NonTerminalToken("F0");

        TerminalToken plus = new TerminalToken("+");
        TerminalToken minus = new TerminalToken("-");
        TerminalToken mult = new TerminalToken("*");
        TerminalToken div = new TerminalToken("/");
        TerminalToken openP = new TerminalToken("(");
        TerminalToken closeP = new TerminalToken(")");
        TerminalToken nb = new TerminalToken("nb");
        TerminalToken empty = new TerminalToken("empty");

        E0.setAction(() -> {
            System.out.println("E0 -> T0 E1");
            T0.execute();
            E1.execute();
        });

        E1.setAction(() -> {
            if (!lexer.stillAvailableToken()) {
                System.out.println("E1 -> epsilon");
                return;
            }
            if (lexer.getCurrentToken().getValue().equals(plus.getValue())) {
                System.out.println("E1 -> + T0 E1");
                lexer.nextToken();
                T0.execute();
                E1.execute();
            } else {
                System.out.println("E1 -> epsilon");
            }
        });

        T0.setAction(() -> {
            System.out.println("T0 -> F0 T1");
            F0.execute();
            T1.execute();
        });

        T1.setAction(() -> {
            if (!lexer.stillAvailableToken()) {
                System.out.println("T1 -> epsilon");
                return;
            }
            if (lexer.getCurrentToken().getValue().equals(mult.getValue())) {
                System.out.println("T1 -> * F0 T1");
                lexer.nextToken();
                F0.execute();
                T1.execute();
            } else {
                System.out.println("T1 -> epsilon");
            }
        });

        F0.setAction(() -> {
            if (lexer.getCurrentToken().getType().getName().equals("INTEGER")) {
                System.out.println("F0 -> nb");
                lexer.nextToken();
            } else if (lexer.getCurrentToken().getValue().equals(openP.getValue())) {
                System.out.println("F0 -> ( E0 )");
                lexer.nextToken();
                E0.execute();
                if (lexer.getCurrentToken().getValue().equals(closeP.getValue())) {
                    lexer.nextToken();
                } else {
                    System.out.println("Erreur");
                }
            } else {
                System.out.println("Erreur");
            }

        });

        E0.execute();
        System.out.println("Test passé avec succès");
    }

}
