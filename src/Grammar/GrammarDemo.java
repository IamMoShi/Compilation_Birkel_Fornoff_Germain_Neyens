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

        NonTerminalToken E = new NonTerminalToken("E");
        NonTerminalToken E1 = new NonTerminalToken("E1");
        NonTerminalToken E2 = new NonTerminalToken("E2");
        NonTerminalToken E3 = new NonTerminalToken("E3");
        NonTerminalToken D = new NonTerminalToken("D");
        NonTerminalToken D1 = new NonTerminalToken("D1");
        NonTerminalToken D2 = new NonTerminalToken("D2");
        NonTerminalToken F = new NonTerminalToken("F");
        NonTerminalToken F1 = new NonTerminalToken("F1");
        NonTerminalToken C = new NonTerminalToken("C");
        NonTerminalToken C1 = new NonTerminalToken("C1");
        NonTerminalToken C2 = new NonTerminalToken("C2");
        NonTerminalToken In = new NonTerminalToken("In");
        NonTerminalToken In1 = new NonTerminalToken("In1");
        NonTerminalToken In2 = new NonTerminalToken("In2");
        NonTerminalToken In3 = new NonTerminalToken("In3");
        NonTerminalToken In4 = new NonTerminalToken("In4");
        NonTerminalToken In5 = new NonTerminalToken("In5");
        NonTerminalToken Eif = new NonTerminalToken("Eif");
        NonTerminalToken Ei = new NonTerminalToken("Ei");
        NonTerminalToken r1 = new NonTerminalToken("r1");
        NonTerminalToken M = new NonTerminalToken("M");
        NonTerminalToken M1 = new NonTerminalToken("M1");
        NonTerminalToken M2 = new NonTerminalToken("M2");
        NonTerminalToken PS = new NonTerminalToken("PS");
        NonTerminalToken PS1 = new NonTerminalToken("PS1");
        NonTerminalToken PS2 = new NonTerminalToken("PS2");
        NonTerminalToken P = new NonTerminalToken("P");
        NonTerminalToken P1 = new NonTerminalToken("P1");
        NonTerminalToken Ident2 = new NonTerminalToken("Ident2");
        NonTerminalToken T = new NonTerminalToken("T");


        F.setAction(() -> {
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
                                                                F1.execute();
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

        F1.setAction(() -> {
            // Try to do rules D and F1 else do nothing
            try {
                D.execute();
                F1.execute();
                return;
            } catch (Exception e) {
                System.out.println("catch F1");
                return;
            }

        });

        D.setAction(() -> {
            if (lexer.currentTokenType().equals("KEYWORD") && lexer.getCurrentToken().getValue().equals("type")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    D1.execute();
                }
            } else if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                C1.execute();
                if (lexer.currentTokenType().equals("COLON")) {
                    lexer.nextToken();
                    T.execute();
                    E3.execute();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            } else if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("procedure")) {
                lexer.nextToken();
                if (lexer.currentTokenType().equals("IDENTIFIER")) {
                    lexer.nextToken();
                    PS2.execute();
                    if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("is")) {
                        lexer.nextToken();
                        F1.execute();
                        if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("begin")) {
                            lexer.nextToken();
                            In.execute();
                            In3.execute();
                            if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("end")) {
                                lexer.nextToken();
                                Ident2.execute();
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
                    PS2.execute();
                    if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("return")) {
                        lexer.nextToken();
                        T.execute();
                        if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("is")) {
                            lexer.nextToken();
                            F1.execute();
                            if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("begin")) {
                                lexer.nextToken();
                                In.execute();
                                In3.execute();
                                if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("end")) {
                                    lexer.nextToken();
                                    Ident2.execute();
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

        D1.setAction(() -> {
            if (lexer.currentTokenType().equals("SEMICOLON")) {
                lexer.nextToken();
            } else if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("is")) {
                lexer.nextToken();
                D2.execute();
            } else {
                throw new RuntimeException("D1");
            }
        });

        D2.setAction(() -> {
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
                C.execute();
                C2.execute();
                if (lexer.currentTokenType().equals("KEYWORD") && lexer.currentTokenValue().equals("end")) {
                    lexer.nextToken();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            }
        });


        C.setAction(() -> {
            if (lexer.currentTokenType().equals("IDENTIFIER")) {
                lexer.nextToken();
                C1.execute();
                if (lexer.currentTokenType().equals("COLON")) {
                    lexer.nextToken();
                    T.execute();
                    if (lexer.currentTokenType().equals("SEMICOLON")) {
                        lexer.nextToken();
                    }
                }
            } else {
                throw new RuntimeException("C");
            }
        });

        C1.setAction(() -> {

        });
        C2.setAction(() -> {
        });
        T.setAction(() -> {
        });
        E3.setAction(() -> {
        });
        PS2.setAction(() -> {
        });
        In.setAction(() -> {
        });
        In3.setAction(() -> {
        });
        Ident2.setAction(() -> {
        });


        F.execute();
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
