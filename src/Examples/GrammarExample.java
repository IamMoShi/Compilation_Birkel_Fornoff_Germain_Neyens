package Examples;

import Grammar.*;
import Grammar.Rule.*;
import Grammar.Token.*;

public class GrammarExample {

    public static void main(String[] args) {
        // Instanciation de grammar
        Grammar grammar = new Grammar();

        // Définition des tokens non terminaux
        NonTerminalToken F0 = new NonTerminalToken("F0"); //fichier
        NonTerminalToken F1 = new NonTerminalToken("F1");
        NonTerminalToken C0 = new NonTerminalToken("C0"); //champs
        NonTerminalToken C1 = new NonTerminalToken("C1");
        NonTerminalToken C2 = new NonTerminalToken("C2");
        NonTerminalToken Ent0 = new NonTerminalToken("Ent0"); //entier
        NonTerminalToken Ent1 = new NonTerminalToken("Ent1");
        NonTerminalToken M0 = new NonTerminalToken("M0"); //mode
        NonTerminalToken M1 = new NonTerminalToken("M1");
        NonTerminalToken M2 = new NonTerminalToken("M2");
        NonTerminalToken Op0 = new NonTerminalToken("Op0"); //opérateur
        NonTerminalToken Ps0 = new NonTerminalToken("Ps0"); //params
        NonTerminalToken Ps1 = new NonTerminalToken("Ps1");
        NonTerminalToken Ps2 = new NonTerminalToken("Ps2");
        NonTerminalToken P0 = new NonTerminalToken("P0"); //param
        NonTerminalToken P1 = new NonTerminalToken("P1");
        NonTerminalToken Id0 = new NonTerminalToken("Id0"); //ident
        NonTerminalToken Id1 = new NonTerminalToken("Id1");
        NonTerminalToken Id2 = new NonTerminalToken("Id2");
        NonTerminalToken alpha = new NonTerminalToken("alpha"); //alpha
        NonTerminalToken ch = new NonTerminalToken("ch"); //chiffre*
        NonTerminalToken Char = new NonTerminalToken("Char0"); //caractère , un des 95 ASCII imprimeables
        NonTerminalToken In0 = new NonTerminalToken("In0"); //instr
        NonTerminalToken In1 = new NonTerminalToken("In1");
        NonTerminalToken In2 = new NonTerminalToken("In2");
        NonTerminalToken In3 = new NonTerminalToken("In3");
        NonTerminalToken El0 = new NonTerminalToken("El0");
        NonTerminalToken R0 = new NonTerminalToken("R1");
        NonTerminalToken E0 = new NonTerminalToken("E0"); //expr
        NonTerminalToken E1 = new NonTerminalToken("E1");
        NonTerminalToken E2 = new NonTerminalToken("E2");
        NonTerminalToken E3 = new NonTerminalToken("E3");
        NonTerminalToken E4 = new NonTerminalToken("E4");
        NonTerminalToken A0 = new NonTerminalToken("A0"); //accès
        NonTerminalToken A1 = new NonTerminalToken("A1");
        NonTerminalToken A2 = new NonTerminalToken("A2");
        NonTerminalToken D0 = new NonTerminalToken("D0"); //decl
        NonTerminalToken D1 = new NonTerminalToken("D1");
        NonTerminalToken D2 = new NonTerminalToken("D2");
        NonTerminalToken T0 = new NonTerminalToken("T0"); //type
        // PREDICATES:
        NonTerminalToken Pred0 = new NonTerminalToken("Pred0");


        //Définition des tokens terminaux
        // voici les tokens terminaux: // zone d'incertitude : "and" ou "and then" et token du type, ". .", comment écrire le backslash

        TerminalToken semicolon = new TerminalToken(";");
        TerminalToken comma = new TerminalToken(",");
        TerminalToken colon = new TerminalToken(":");
        TerminalToken point = new TerminalToken(".");
        TerminalToken assign = new TerminalToken(":=");
        TerminalToken reverse = new TerminalToken("reverse");
        TerminalToken is = new TerminalToken("is");
        TerminalToken begin = new TerminalToken("begin");
        TerminalToken end = new TerminalToken("end");
        TerminalToken procedure = new TerminalToken("procedure");
        TerminalToken function = new TerminalToken("function");
        TerminalToken use = new TerminalToken("use");
        TerminalToken with = new TerminalToken("with");
        TerminalToken nullToken = new TerminalToken("null");
        TerminalToken trueToken = new TerminalToken("true");
        TerminalToken falseToken = new TerminalToken("false");
        TerminalToken character = new TerminalToken("character");
        TerminalToken newToken = new TerminalToken("new");
        TerminalToken returnToken = new TerminalToken("return");
        TerminalToken ifToken = new TerminalToken("if");
        TerminalToken then = new TerminalToken("then");
        TerminalToken elseToken = new TerminalToken("else");
        TerminalToken elseifToken = new TerminalToken("elseif");
        TerminalToken endIfToken = new TerminalToken("end if");
        TerminalToken forToken = new TerminalToken("for");
        TerminalToken in = new TerminalToken("in");
        TerminalToken loop = new TerminalToken("loop");
        TerminalToken endLoop = new TerminalToken("end loop");
        TerminalToken whileToken = new TerminalToken("while");
        TerminalToken end_of_file = new TerminalToken("EOF");
        TerminalToken aToken = new TerminalToken("a");
        TerminalToken bToken = new TerminalToken("b");
        TerminalToken cToken = new TerminalToken("c");
        TerminalToken dToken = new TerminalToken("d");
        TerminalToken eToken = new TerminalToken("e");
        TerminalToken fToken = new TerminalToken("f");
        TerminalToken gToken = new TerminalToken("g");
        TerminalToken hToken = new TerminalToken("h");
        TerminalToken iToken = new TerminalToken("i");
        TerminalToken jToken = new TerminalToken("j");
        TerminalToken kToken = new TerminalToken("k");
        TerminalToken lToken = new TerminalToken("l");
        TerminalToken mToken = new TerminalToken("m");
        TerminalToken nToken = new TerminalToken("n");
        TerminalToken oToken = new TerminalToken("o");
        TerminalToken pToken = new TerminalToken("p");
        TerminalToken qToken = new TerminalToken("q");
        TerminalToken rToken = new TerminalToken("r");
        TerminalToken sToken = new TerminalToken("s");
        TerminalToken tToken = new TerminalToken("t");
        TerminalToken uToken = new TerminalToken("u");
        TerminalToken vToken = new TerminalToken("v");
        TerminalToken wToken = new TerminalToken("w");
        TerminalToken xToken = new TerminalToken("x");
        TerminalToken yToken = new TerminalToken("y");
        TerminalToken zToken = new TerminalToken("z");
        TerminalToken AToken = new TerminalToken("A");
        TerminalToken BToken = new TerminalToken("B");
        TerminalToken CToken = new TerminalToken("C");
        TerminalToken DToken = new TerminalToken("D");
        TerminalToken EToken = new TerminalToken("E");
        TerminalToken FToken = new TerminalToken("F");
        TerminalToken GToken = new TerminalToken("G");
        TerminalToken HToken = new TerminalToken("H");
        TerminalToken IToken = new TerminalToken("I");
        TerminalToken JToken = new TerminalToken("J");
        TerminalToken KToken = new TerminalToken("K");
        TerminalToken LToken = new TerminalToken("L");
        TerminalToken MToken = new TerminalToken("M");
        TerminalToken NToken = new TerminalToken("N");
        TerminalToken OToken = new TerminalToken("O");
        TerminalToken PToken = new TerminalToken("P");
        TerminalToken QToken = new TerminalToken("Q");
        TerminalToken RToken = new TerminalToken("R");
        TerminalToken SToken = new TerminalToken("S");
        TerminalToken TToken = new TerminalToken("T");
        TerminalToken UToken = new TerminalToken("U");
        TerminalToken VToken = new TerminalToken("V");
        TerminalToken WToken = new TerminalToken("W");
        TerminalToken XToken = new TerminalToken("X");
        TerminalToken YToken = new TerminalToken("Y");
        TerminalToken ZToken = new TerminalToken("Z");
        TerminalToken zero = new TerminalToken("0");
        TerminalToken one = new TerminalToken("1");
        TerminalToken two = new TerminalToken("2");
        TerminalToken three = new TerminalToken("3");
        TerminalToken four = new TerminalToken("4");
        TerminalToken five = new TerminalToken("5");
        TerminalToken six = new TerminalToken("6");
        TerminalToken seven = new TerminalToken("7");
        TerminalToken eight = new TerminalToken("8");
        TerminalToken nine = new TerminalToken("9");
        TerminalToken exclamation = new TerminalToken("!");
        TerminalToken quote = new TerminalToken("\"");
        TerminalToken hashtag = new TerminalToken("#");
        TerminalToken dollar = new TerminalToken("$");
        TerminalToken percent = new TerminalToken("%");
        TerminalToken esperluet = new TerminalToken("&");
        TerminalToken simpleQuote = new TerminalToken("'");
        TerminalToken openParenthesis = new TerminalToken("(");
        TerminalToken closeParenthesis = new TerminalToken(")");
        TerminalToken star = new TerminalToken("*");
        TerminalToken plus = new TerminalToken("+");
        TerminalToken minus = new TerminalToken("-");
        TerminalToken slash = new TerminalToken("/");
        TerminalToken underscore = new TerminalToken("_");
        TerminalToken arobase = new TerminalToken("@");
        TerminalToken openBracket = new TerminalToken("[");
        TerminalToken backslash = new TerminalToken("\\");
        TerminalToken closeBracket = new TerminalToken("]");
        TerminalToken circumflex = new TerminalToken("^");
        TerminalToken openBrace = new TerminalToken("{");
        TerminalToken pipe = new TerminalToken("|");
        TerminalToken closeBrace = new TerminalToken("}");
        TerminalToken tilde = new TerminalToken("~");
        TerminalToken lessThan = new TerminalToken("<");
        TerminalToken greaterThan = new TerminalToken(">");
        TerminalToken equal = new TerminalToken("=");
        TerminalToken space = new TerminalToken(" ");       // SPACE TOKEN HERE
        TerminalToken out = new TerminalToken("out");
        TerminalToken notEqual = new TerminalToken("/=");
        TerminalToken lessOrEqual = new TerminalToken("<=");
        TerminalToken greaterOrEqual = new TerminalToken(">=");
        TerminalToken andThen = new TerminalToken("and then");   // PAS SUR
        TerminalToken orElse = new TerminalToken("or else");     // PAS SUR
        TerminalToken andToken = new TerminalToken("and");
        TerminalToken orToken = new TerminalToken("or");
        TerminalToken AdaIOToken = new TerminalToken("Ada.Text_IO");
        TerminalToken not = new TerminalToken("not");
        TerminalToken rem = new TerminalToken("rem");
        TerminalToken characterToken = new TerminalToken("character");
        TerminalToken val = new TerminalToken("val");
        TerminalToken access = new TerminalToken("access");
        TerminalToken record = new TerminalToken("record");
        TerminalToken accent= new TerminalToken("`");
        TerminalToken pointPoint = new TerminalToken("..");                                          // JSP
        TerminalToken question = new TerminalToken("?");
        TerminalToken type = new TerminalToken("type");


        // Ajout des tokens à la grammaire
        // les non-terminaux:
        grammar.addNonTerminal(F0);
        grammar.addNonTerminal(F1);
        grammar.addNonTerminal(C0);
        grammar.addNonTerminal(C1);
        grammar.addNonTerminal(C2);
        grammar.addNonTerminal(Ent0);
        grammar.addNonTerminal(Ent1);
        grammar.addNonTerminal(M0);
        grammar.addNonTerminal(M1);
        grammar.addNonTerminal(M2);
        grammar.addNonTerminal(Op0);
        grammar.addNonTerminal(Ps0);
        grammar.addNonTerminal(Ps1);
        grammar.addNonTerminal(Ps2);
        grammar.addNonTerminal(P0);
        grammar.addNonTerminal(P1);
        grammar.addNonTerminal(Id0);
        grammar.addNonTerminal(Id1);
        grammar.addNonTerminal(Id2);
        grammar.addNonTerminal(alpha);
        grammar.addNonTerminal(ch);
        grammar.addNonTerminal(Char);
        grammar.addNonTerminal(In0);
        grammar.addNonTerminal(In1);
        grammar.addNonTerminal(In2);
        grammar.addNonTerminal(In3);
        grammar.addNonTerminal(El0);
        grammar.addNonTerminal(R0);
        grammar.addNonTerminal(E0);
        grammar.addNonTerminal(E1);
        grammar.addNonTerminal(E2);
        grammar.addNonTerminal(E3);
        grammar.addNonTerminal(E4);
        grammar.addNonTerminal(A0);
        grammar.addNonTerminal(A1);
        grammar.addNonTerminal(A2);
        grammar.addNonTerminal(D0);
        grammar.addNonTerminal(D1);
        grammar.addNonTerminal(D2);
        grammar.addNonTerminal(T0);

        // les terminaux:
        grammar.addTerminal(semicolon);
        grammar.addTerminal(comma);
        grammar.addTerminal(colon);
        grammar.addTerminal(point);
        grammar.addTerminal(assign);
        grammar.addTerminal(reverse);
        grammar.addTerminal(is);
        grammar.addTerminal(begin);
        grammar.addTerminal(end);
        grammar.addTerminal(procedure);
        grammar.addTerminal(function);
        grammar.addTerminal(use);
        grammar.addTerminal(with);
        grammar.addTerminal(nullToken);
        grammar.addTerminal(trueToken);
        grammar.addTerminal(falseToken);
        grammar.addTerminal(character);
        grammar.addTerminal(newToken);
        grammar.addTerminal(returnToken);
        grammar.addTerminal(ifToken);
        grammar.addTerminal(then);
        grammar.addTerminal(elseToken);
        grammar.addTerminal(elseifToken);
        grammar.addTerminal(endIfToken);
        grammar.addTerminal(forToken);
        grammar.addTerminal(in);
        grammar.addTerminal(loop);
        grammar.addTerminal(endLoop);
        grammar.addTerminal(whileToken);
        grammar.addTerminal(aToken);
        grammar.addTerminal(bToken);
        grammar.addTerminal(cToken);
        grammar.addTerminal(dToken);
        grammar.addTerminal(eToken);
        grammar.addTerminal(fToken);
        grammar.addTerminal(gToken);
        grammar.addTerminal(hToken);
        grammar.addTerminal(iToken);
        grammar.addTerminal(jToken);
        grammar.addTerminal(kToken);
        grammar.addTerminal(lToken);
        grammar.addTerminal(mToken);
        grammar.addTerminal(nToken);
        grammar.addTerminal(oToken);
        grammar.addTerminal(pToken);
        grammar.addTerminal(qToken);
        grammar.addTerminal(rToken);
        grammar.addTerminal(sToken);
        grammar.addTerminal(tToken);
        grammar.addTerminal(uToken);
        grammar.addTerminal(vToken);
        grammar.addTerminal(wToken);
        grammar.addTerminal(xToken);
        grammar.addTerminal(yToken);
        grammar.addTerminal(zToken);
        grammar.addTerminal(AToken);
        grammar.addTerminal(BToken);
        grammar.addTerminal(CToken);
        grammar.addTerminal(DToken);
        grammar.addTerminal(EToken);
        grammar.addTerminal(FToken);
        grammar.addTerminal(GToken);
        grammar.addTerminal(HToken);
        grammar.addTerminal(IToken);
        grammar.addTerminal(JToken);
        grammar.addTerminal(KToken);
        grammar.addTerminal(LToken);
        grammar.addTerminal(MToken);
        grammar.addTerminal(NToken);
        grammar.addTerminal(OToken);
        grammar.addTerminal(PToken);
        grammar.addTerminal(QToken);
        grammar.addTerminal(RToken);
        grammar.addTerminal(SToken);
        grammar.addTerminal(TToken);
        grammar.addTerminal(UToken);
        grammar.addTerminal(VToken);
        grammar.addTerminal(WToken);
        grammar.addTerminal(XToken);
        grammar.addTerminal(YToken);
        grammar.addTerminal(ZToken);
        grammar.addTerminal(zero);
        grammar.addTerminal(one);
        grammar.addTerminal(two);
        grammar.addTerminal(three);
        grammar.addTerminal(four);
        grammar.addTerminal(five);
        grammar.addTerminal(six);
        grammar.addTerminal(seven);
        grammar.addTerminal(eight);
        grammar.addTerminal(nine);
        grammar.addTerminal(exclamation);
        grammar.addTerminal(quote);
        grammar.addTerminal(hashtag);
        grammar.addTerminal(dollar);
        grammar.addTerminal(percent);
        grammar.addTerminal(andToken);
        grammar.addTerminal(simpleQuote);
        grammar.addTerminal(openParenthesis);
        grammar.addTerminal(closeParenthesis);
        grammar.addTerminal(star);
        grammar.addTerminal(plus);
        grammar.addTerminal(minus);
        grammar.addTerminal(slash);
        grammar.addTerminal(underscore);
        grammar.addTerminal(arobase);
        grammar.addTerminal(openBracket);
        grammar.addTerminal(backslash);
        grammar.addTerminal(closeBracket);
        grammar.addTerminal(circumflex);
        grammar.addTerminal(openBrace);
        grammar.addTerminal(pipe);
        grammar.addTerminal(closeBrace);
        grammar.addTerminal(tilde);
        grammar.addTerminal(lessThan);
        grammar.addTerminal(greaterThan);
        grammar.addTerminal(equal);
        grammar.addTerminal(space);
        grammar.addTerminal(out);
        grammar.addTerminal(notEqual);
        grammar.addTerminal(lessOrEqual);
        grammar.addTerminal(greaterOrEqual);
        grammar.addTerminal(andThen);
        grammar.addTerminal(orElse);
        grammar.addTerminal(andToken);
        grammar.addTerminal(orToken);
        grammar.addTerminal(AdaIOToken);
        grammar.addTerminal(end_of_file);
        grammar.addTerminal(not);
        grammar.addTerminal(rem);
        grammar.addTerminal(characterToken);
        grammar.addTerminal(val);
        grammar.addTerminal(access);
        grammar.addTerminal(record);
        grammar.addTerminal(accent);
        grammar.addTerminal(pointPoint); // JSP
        grammar.addTerminal(question);
        grammar.addTerminal(type);


        // ajout prédicats
        // Predicate alphaPredicate = new AlphaPredicate();
        AlphaPredicate alphaPredicate = new AlphaPredicate();

        // Définition des règles
        grammar.addRule(new Rule(F0, new Expression(new GrammarToken[]{with, AdaIOToken, semicolon, use, AdaIOToken, semicolon, procedure, Id0, is, F1, begin, In0, In3, end, Id2, semicolon, end_of_file})));
        grammar.addRule(new Rule(F1, new Expression(new GrammarToken[]{D0, F1})));
        grammar.addRule(new Rule(F1, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(C0, new Expression(new GrammarToken[]{Id0, C1, colon, T0, semicolon})));
        grammar.addRule(new Rule(C1, new Expression(new GrammarToken[]{comma, Id0, C1})));
        grammar.addRule(new Rule(C1, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(C2, new Expression(new GrammarToken[]{C0, C2})));
        grammar.addRule(new Rule(C2, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(Ent0, new Expression(new GrammarToken[]{ch, Ent1})));
        grammar.addRule(new Rule(Ent1, new Expression(new GrammarToken[]{ch, Ent1})));
        grammar.addRule(new Rule(Ent1, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(M0, new Expression(new GrammarToken[]{in, M1})));
        grammar.addRule(new Rule(M1, new Expression(new GrammarToken[]{out})));
        grammar.addRule(new Rule(M1, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(M2, new Expression(new GrammarToken[]{M0})));
        grammar.addRule(new Rule(M2, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{equal})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{notEqual})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{lessThan})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{lessOrEqual})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{greaterThan})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{greaterOrEqual})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{plus})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{minus})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{star})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{slash})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{rem})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{and})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{andThen})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{orToken})));
        grammar.addRule(new Rule(Op0, new Expression(new GrammarToken[]{orElse})));
        grammar.addRule(new Rule(Ps0, new Expression(new GrammarToken[]{openParenthesis, P0, Ps1, closeParenthesis})));
        grammar.addRule(new Rule(Ps1, new Expression(new GrammarToken[]{comma, P0, Ps1})));
        grammar.addRule(new Rule(Ps1, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(Ps2, new Expression(new GrammarToken[]{Ps0})));
        grammar.addRule(new Rule(Ps2, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(P0, new Expression(new GrammarToken[]{Id0, P1, colon, M2, T0})));
        grammar.addRule(new Rule(P1, new Expression(new GrammarToken[]{comma, Id0, P1})));
        grammar.addRule(new Rule(P1, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(Id0, new Expression(new GrammarToken[]{alpha, Id1})));
        // --- modification à faire avec un prédicats ---

        // Id→Pred(α)Id1
        // Pred(X)​→rules_alpha(X)∣rules_ch()∣rules_underscore()
        // Id1→αId1∣chId1∣_Id1∣ε​

        // rules_alpha(X): Prendre alpha.
        // rules_ch(): Les règles correspondant à l'option ch.
        // rules_underscore() : // _.


        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{alpha, Id1})));
        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{ch, Id1})));
        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{underscore, Id1})));
        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{})));

        // // Ajout règle du Predicate 
        // grammar.addRule(new Rule(Pred0, new Expression(new GrammarToken[]{Id0, alphaPredicate})));
        // grammar.addRule(new Rule(Pred0, new Expression(new GrammarToken[]{alpha, alphaPredicate})));

        // // Ajout des règles à la grammaire
        // grammaire.addRule(new Rule(Pred, new Expression(new GrammarToken[]{Id, alphaPredicate})));
        // grammaire.addRule(new Rule(Pred, new Expression(new GrammarToken[]{alpha, alphaPredicate})));
        // // ... autres règles avec Pred


        // grammar.addRule(new Rule(alphaPredicate, new Expression(new GrammarToken[]{Id0, new Predicate()})));
        // grammar.addRule(new Rule(Pred, new Expression(new GrammarToken[]{alpha})));
        // grammar.addRule(new Rule(Pred, new Expression(new GrammarToken[]{ch})));
        // grammar.addRule(new Rule(Pred, new Expression(new GrammarToken[]{underscore})));

        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{alpha, Id1})));
        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{ch, Id1})));
        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{underscore, Id1})));
        // grammar.addRule(new Rule(Id1, new Expression(new GrammarToken[]{})));

        // --- fin de la modification ---

        grammar.addRule(new Rule(Id2, new Expression(new GrammarToken[]{Id0})));
        grammar.addRule(new Rule(Id2, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{aToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{bToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{cToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{dToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{eToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{fToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{gToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{hToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{iToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{jToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{kToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{lToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{mToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{nToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{oToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{pToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{qToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{rToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{sToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{tToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{uToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{vToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{wToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{xToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{yToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{zToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{AToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{BToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{CToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{DToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{EToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{FToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{GToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{HToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{IToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{JToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{KToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{LToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{MToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{NToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{OToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{PToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{QToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{RToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{SToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{TToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{UToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{VToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{WToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{XToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{YToken})));
        grammar.addRule(new Rule(alpha, new Expression(new GrammarToken[]{ZToken})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{zero})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{one})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{two})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{three})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{four})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{five})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{six})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{seven})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{eight})));
        grammar.addRule(new Rule(ch, new Expression(new GrammarToken[]{nine})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{exclamation})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{quote})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{hashtag})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{dollar})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{percent})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{esperluet})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{simpleQuote})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{openParenthesis})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{closeParenthesis})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{star})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{plus})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{comma})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{minus})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{point})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{slash})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{zero})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{one})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{two})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{three})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{four})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{five})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{six})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{seven})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{eight})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{nine})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{space}))); // ajout de l'espace
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{colon})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{semicolon})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{lessThan})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{equal})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{greaterThan})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{question})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{arobase})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{AToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{BToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{CToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{DToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{EToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{FToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{GToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{HToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{IToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{JToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{KToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{LToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{MToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{NToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{OToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{PToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{QToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{RToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{SToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{TToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{UToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{VToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{WToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{XToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{YToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{ZToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{openBracket})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{backslash})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{closeBracket})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{circumflex})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{underscore})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{accent})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{aToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{bToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{cToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{dToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{eToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{fToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{gToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{hToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{iToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{jToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{kToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{lToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{mToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{nToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{oToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{pToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{qToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{rToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{sToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{tToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{uToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{vToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{wToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{xToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{yToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{zToken})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{openBrace})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{pipe})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{closeBrace})));
        grammar.addRule(new Rule(Char, new Expression(new GrammarToken[]{tilde})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{Id0, In1})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{Ent0, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{Char, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{trueToken, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{falseToken, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{nullToken, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{openParenthesis, E0, closeParenthesis, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{not, E0, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{minus, E0, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{newToken, Id0, E1, point, Id0, A1, assign, E0, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{characterToken, accent, val, openParenthesis, E0, closeParenthesis, E1, point, Id0, A1, assign, E0, semicolon}))); // faute dans le excel normalement ligne 213
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{returnToken, E4, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{begin, In0, In3, end})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{ifToken, E0, then, In0, In3, elseifToken, E0, then, In0, In3, El0, end, ifToken, semicolon})));  // les parenthèses ne sont pas des non terminaux
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{forToken, Id0, in, R0, E0, pointPoint, E0, loop, In0, In3, end, loop, semicolon})));
        grammar.addRule(new Rule(In0, new Expression(new GrammarToken[]{whileToken, E0, loop, In0, In3, end, loop, semicolon})));
        grammar.addRule(new Rule(El0, new Expression(new GrammarToken[]{elseToken, In0, In3})));
        grammar.addRule(new Rule(El0, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(R0, new Expression(new GrammarToken[]{reverse})));
        grammar.addRule(new Rule(R0, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(In1, new Expression(new GrammarToken[]{semicolon})));
        grammar.addRule(new Rule(In1, new Expression(new GrammarToken[]{point, Id0, A0, assign, E0, semicolon})));
        grammar.addRule(new Rule(In1, new Expression(new GrammarToken[]{openParenthesis, E0, E2, closeParenthesis, In2})));
        grammar.addRule(new Rule(In2, new Expression(new GrammarToken[]{E1, point, Id0, A0, assign, E0, semicolon})));
        grammar.addRule(new Rule(In2, new Expression(new GrammarToken[]{semicolon})));

        // --- modification pour second prédicats ---
        //              --- optionnel ---
        grammar.addRule(new Rule(In3, new Expression(new GrammarToken[]{In0, In3})));
        grammar.addRule(new Rule(In3, new Expression(new GrammarToken[]{})));
        // fin modif optionnel

        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{Ent0, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{Char, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{trueToken, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{falseToken, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{nullToken, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{openParenthesis, E0, closeParenthesis, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{Id0, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{not, E0, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{minus, E0, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{newToken, Id0, E1})));
        grammar.addRule(new Rule(E0, new Expression(new GrammarToken[]{characterToken, accent, val, openParenthesis, E0, closeParenthesis, E1}))); // je crois que j'ai fait une faute dans le excel ici je vérifierai

        // --- prédicat 2 --- ligne 241 - 242 du excel
        grammar.addRule(new Rule(E1,new Expression(new GrammarToken[]{Op0, E0, E1})));
        grammar.addRule(new Rule(E1, new Expression(new GrammarToken[]{})));
        // fin prédicat 2

        grammar.addRule(new Rule(E2, new Expression(new GrammarToken[]{comma, E0, E2})));
        grammar.addRule(new Rule(E2, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(E3, new Expression(new GrammarToken[]{assign, E0})));
        grammar.addRule(new Rule(E3, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(E4, new Expression(new GrammarToken[]{E0})));
        grammar.addRule(new Rule(E4, new Expression(new GrammarToken[]{})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{Id0, A2})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{Ent0, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{Char, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{trueToken, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{falseToken, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{nullToken, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{openParenthesis, E0, closeParenthesis, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{not, E0, E1, point, Id0, A1}))); // à vérifier je crois qu'il y a encore une faute ici dans le excel
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{minus, E0, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{newToken, Id0, E1, point, Id0, A1})));
        grammar.addRule(new Rule(A0, new Expression(new GrammarToken[]{characterToken, accent, val, openParenthesis, E0, closeParenthesis, E1, point, Id0, A1})));

        // prédicat 3 ligne 259-260 du excel
        grammar.addRule(new Rule(A1, new Expression(new GrammarToken[]{E1, point, Id0, A1})));
        grammar.addRule(new Rule(A1, new Expression(new GrammarToken[]{})));
        // fin prédicat 3

        grammar.addRule(new Rule(A2, new Expression(new GrammarToken[]{point, Id0, A1})));
        grammar.addRule(new Rule(A2, new Expression(new GrammarToken[]{openParenthesis, E0, E2, closeParenthesis,E1, point, Id0, A1})));
        grammar.addRule(new Rule(D0, new Expression(new GrammarToken[]{type, Id0, D1})));
        grammar.addRule(new Rule(D0, new Expression(new GrammarToken[]{Id0, C1, colon, T0, E3, semicolon})));
        grammar.addRule(new Rule(D0, new Expression(new GrammarToken[]{procedure, Id0, Ps2, semicolon, is, F0, begin, In0, In3, end, Id2, semicolon})));
        grammar.addRule(new Rule(D0, new Expression(new GrammarToken[]{function, Id0, Ps2, returnToken, T0, is, F1, begin, In0, In3, end, Id2, semicolon})));  // je vais vérifier
        grammar.addRule(new Rule(D1, new Expression(new GrammarToken[]{semicolon})));
        grammar.addRule(new Rule(D1, new Expression(new GrammarToken[]{is, D2})));
        grammar.addRule(new Rule(D2, new Expression(new GrammarToken[]{access, Id0, semicolon})));
        grammar.addRule(new Rule(D2, new Expression(new GrammarToken[]{record, C0,C2, end, record, semicolon})));


        System.out.println(grammar.toString());

    }
}
