package Grammar;

import Grammar.*;
import Grammar.Rule.*;
import Grammar.Token.*;

import java.util.ArrayList;

public class GrammarExample {

    private Grammar grammar;
    
    private ArrayList<TerminalToken> terminalTokens;
    
    private int position;
    
    private TerminalToken currentToken;
    
    public GrammarTest() {
        grammar = new Grammar();
    }
    
    public boolean isCurrentToken(TerminalToken token) {
        return currentToken.getType().equals(token.getType());
    }
    
    public void nextToken() {
        position++;
        currentToken = terminalTokens.get(position);
    }

    public static void main(String[] args) {
        // Instanciation de grammar
        Grammar grammar = new Grammar();

/*
Voici les rèles de la grammaire :

F0 -> with Ada.Text_IO ; use Ada.Text_IO ; procedure Ident is F1 begin In In3 end Ident2 ; EOF
Ident2 -> Ident
Ident2 -> ''
F1 -> D F1
F1 -> ''
C0 -> Ident C1 : T ;
C1 -> , Ident C1
C1 -> ''
C2 -> C C2
C2 -> ''
M0 -> in M1
M1 -> out
M1 -> ''
M2 -> M
M2 -> ''
Op0 -> OPERATOR
Ps0 -> ( P Ps1 )
Ps1 -> ; P Ps1
Ps1 -> ''
Ps2 -> Ps
Ps2 -> ''
P0 -> Ident P1 : M2 T
P1 -> , Ident P1
P1 -> ''
ch -> CHIFFRE (plus util)
Char -> ASCII
In0 -> Ident In1
In0 -> Ent E1 . Ident A1 := E ;
In0 -> char E1 . Ident A1 := E ;
In0 -> true E1 . Ident A1 := E ;
In0 -> float E1 . Ident A1 := E ;
In0 -> false E1 . Ident A1 := E ;
In0 -> null E1 . Ident A1 := E ;
In0 -> ( E ) E1 . Ident A1 := E ;
In0 -> not E E1 . Ident A1 :=E ;
In0 -> - E E1 . Ident A1 := E ;
In0 -> new Ident E1 . Ident A1 := E ;
In0 -> character`val ( E ) E1 . Ident A1 := E ;
In0 -> return E4 ;
In0 -> begin In In3 end
In0 -> if E then In In3 Eif El end if ;
Eif -> ''
Eif -> elseif E then In In3 Eif
In0 -> for Ident in R1 E .. E loop In In3 end loop ;
In0 -> while E loop In In3 end loop ;
El -> else In In3
El -> ''
R1 -> reverse
R1 -> ''
In1 -> ;
In1 -> A1 := E ;
In1 -> ( E E2 ) In2
In2 -> E1 . Ident A1 := E ;
In2 -> ;
In3 -> In In3
In3 -> ''
E1 -> Op E E1
E1-> ''
E2 -> , E E2
E2 -> ''
E3 -> : = E
E3 -> ''
E4 -> E
E4 -> ''
E-> Ident A2 E1
E-> Ent E1 E5
E-> Char E1 E5
E5 -> . Ident A1 E1
E5 -> “
E0-> True E1 E5
E0-> False E1 E5
E0-> float E1 E5
E0-> null E1 E5
E0-> ( E ) E1 E5
E0-> not E1 E5
E0-> -E E1 E5
E0-> new Ident E1 E5
E0->  character`val E1 E5
E0 -> Ident ( E E2 ) E1
A1 -> E1 . Ident A1
A1 -> ''
A2 -> A1
A2 -> ( E E2 ) E1 . Ident A1
D0 -> Type Ident D1
D0 -> Ident C1 : T E3 ;
D0 -> procedure Ident Ps2 is F1 begin In In3 end Ident2 ;
D0 -> function Ident Ps2 return T is F1 begin In In3 end Ident2 ;
D1 -> ;
D1 -> is D2
D2 -> access Ident ;
D2 -> record C C2 end record;
T0 -> Ident
T0 -> access Ident

*/

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
        NonTerminalToken E5 = new NonTerminalToken("E5");
        NonTerminalToken A1 = new NonTerminalToken("A1");
        NonTerminalToken A2 = new NonTerminalToken("A2");
        NonTerminalToken D0 = new NonTerminalToken("D0"); //decl
        NonTerminalToken D1 = new NonTerminalToken("D1");
        NonTerminalToken D2 = new NonTerminalToken("D2");
        NonTerminalToken T0 = new NonTerminalToken("T0"); //type
        // PREDICATES:
        // NonTerminalToken Pred0 = new NonTerminalToken("Pred0");



        //Définition des tokens terminaux
        // voici les tokens terminaux: //comment écrire le backslash

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
        TerminalToken withToken = new TerminalToken("with");
        TerminalToken nullToken = new TerminalToken("null");
        TerminalToken trueToken = new TerminalToken("true");
        TerminalToken falseToken = new TerminalToken("false");
        TerminalToken character = new TerminalToken("character");
        TerminalToken newToken = new TerminalToken("new");
        TerminalToken returnToken = new TerminalToken("return");
        TerminalToken ifToken = new TerminalToken("if");
        TerminalToken thenToken = new TerminalToken("then");
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
        TerminalToken space = new TerminalToken(" ");       // space token
        TerminalToken out = new TerminalToken("out");
        TerminalToken notEqual = new TerminalToken("/=");
        TerminalToken lessOrEqual = new TerminalToken("<=");
        TerminalToken greaterOrEqual = new TerminalToken(">=");
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
        TerminalToken pointPoint = new TerminalToken("..");
        TerminalToken question = new TerminalToken("?");
        TerminalToken type = new TerminalToken("type");
        TerminalToken accessToken = new TerminalToken("access");


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
        grammar.addNonTerminal(E5);
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
        grammar.addTerminal(thenToken);
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
        grammar.addTerminal(pointPoint);
        grammar.addTerminal(question);
        grammar.addTerminal(type);
        grammar.addTerminal(accessToken);



        /*
        // Définition des règles
        grammar.addRule(new Rule(F0, new Expression(new GrammarToken[]{with, AdaIOToken, semicolon, use, AdaIOToken, semicolon, procedure, Id0, is, F1, begin, In0, In3, end, Id2, semicolon, end_of_file})));
        grammar.addRule(new Rule(F1, new Expression(new GrammarToken[]{D0, F1})));
        
        */
        
        //on utilise les règles pour définir :
        grammar.addNonTerminal(F0.setAction(() -> {
            if (isCurrentToken(withToken)){

            }
        }));

    }
}
