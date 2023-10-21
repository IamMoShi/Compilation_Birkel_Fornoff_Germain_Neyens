import java.util.ArrayList;

public class Lexer {

    private String sourceCode;
    private int positionID;
    private char currentChar;
    private ArrayList<Token> tokens;

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.positionID = -1;
        this.currentChar = ' ';
        this.tokens = new ArrayList<Token>();
    }

    public void getNextChar() {
        this.positionID += 1;
        if (this.positionID > this.sourceCode.length() - 1) {
            this.currentChar = ' ';
        } else {
            this.currentChar = this.sourceCode.charAt(this.positionID);
        }
    }

    public ArrayList<Token> getTokens() {
        return this.tokens;
    }

    public void skipWhitespace() {
        while (this.currentChar != ' ' && Character.isWhitespace(this.currentChar)) {
            this.getNextChar();
        }
    }

    // Comment Ada

    public void skipOneLineComment() {
        while (positionID < this.sourceCode.length() - 1 && this.currentChar != '\n') {
            this.getNextChar();
        }
    }


    public ArrayList<Token> tokenize() {
        while (this.positionID < this.sourceCode.length() - 1) {
            this.getNextChar();

            // Space Ada
            if (Character.isWhitespace(this.currentChar)) {
                this.skipWhitespace();
            }
            // Comment Ada
            if (this.currentChar == '-') {
                this.getNextChar();
                if (this.currentChar == '-') {
                    this.skipOneLineComment();
                }
            }
            // Variable Ada
            switch (currentChar) {
                case '(':
                    this.tokens.add(new Token("LPAREN", "(" ));
                    break;
                case ')':
                    this.tokens.add(new Token("RPAREN", ")" ));
                    break;
                case ';':
                    this.tokens.add(new Token("SEMI", ";" ));
                    break;

                //Operators Ada
                case ':':
                    if (this.sourceCode.charAt(this.positionID + 1) == '=') {
                        this.tokens.add(new Operators(":=" ));
                        this.getNextChar();
                    }
                    break;
                case '+':
                    this.tokens.add(new Operators("+" ));
                    break;
                case '-':
                    this.tokens.add(new Operators("-" ));
                    break;
                case '*':
                    this.tokens.add(new Operators("*" ));
                    break;
                case '/':
                    this.tokens.add(new Operators("/" ));
                    break;
                case '=':
                    this.tokens.add(new Operators("=" ));
                    break;
                case '<':
                    if (this.sourceCode.charAt(this.positionID + 1) == '=') {
                        this.tokens.add(new Operators("<=" ));
                        this.getNextChar();
                    } else {
                        this.tokens.add(new Operators("<" ));
                    }
                    break;
                case '>':
                    if (this.sourceCode.charAt(this.positionID + 1) == '=') {
                        this.tokens.add(new Operators(">=" ));
                        this.getNextChar();
                    } else {
                        this.tokens.add(new Operators(">" ));
                    }
                    break;

                // keywords Ada
                case 'a':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'c' && this.sourceCode.charAt(this.positionID + 2) == 'c' && this.sourceCode.charAt(this.positionID + 3) == 'e' && this.sourceCode.charAt(this.positionID + 4) == 's' && this.sourceCode.charAt(this.positionID + 5) == 's' && this.sourceCode.charAt(this.positionID + 6) == '\n') {
                        this.tokens.add(new Keyword("access" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'n' && this.sourceCode.charAt(this.positionID + 2) == 'd') {
                        this.tokens.add(new Keyword("and" ));
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'b':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'e' && this.sourceCode.charAt(this.positionID + 2) == 'g' && this.sourceCode.charAt(this.positionID + 3) == 'i' && this.sourceCode.charAt(this.positionID + 4) == 'n') {
                        this.tokens.add(new Keyword("begin" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'e':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'l' && this.sourceCode.charAt(this.positionID + 2) == 's' && this.sourceCode.charAt(this.positionID + 3) == 'e') {
                        this.tokens.add(new Keyword("else" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'l' && this.sourceCode.charAt(this.positionID + 2) == 's' && this.sourceCode.charAt(this.positionID + 3) == 'i' && this.sourceCode.charAt(this.positionID + 4) == 'f') {
                        this.tokens.add(new Keyword("elsif" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'n' && this.sourceCode.charAt(this.positionID + 2) == 'd') {
                        this.tokens.add(new Keyword("end" ));
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'f':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'a' && this.sourceCode.charAt(this.positionID + 2) == 'l' && this.sourceCode.charAt(this.positionID + 3) == 's' && this.sourceCode.charAt(this.positionID + 4) == 'e') {
                        this.tokens.add(new Keyword("false" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'o' && this.sourceCode.charAt(this.positionID + 2) == 'r') {
                        this.tokens.add(new Keyword("for" ));
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'u' && this.sourceCode.charAt(this.positionID + 2) == 'n' && this.sourceCode.charAt(this.positionID + 3) == 'c' && this.sourceCode.charAt(this.positionID + 4) == 't' && this.sourceCode.charAt(this.positionID + 5) == 'i' && this.sourceCode.charAt(this.positionID + 6) == 'o' && this.sourceCode.charAt(this.positionID + 7) == 'n') {
                        this.tokens.add(new Keyword("function" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'i':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'f') {
                        this.tokens.add(new Keyword("if" ));
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'n') {
                        this.tokens.add(new Keyword("in" ));
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 's') {
                        this.tokens.add(new Keyword("is" ));
                        this.getNextChar();
                    }

                    break;
                case 'l':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'o' && this.sourceCode.charAt(this.positionID + 2) == 'o' && this.sourceCode.charAt(this.positionID + 3) == 'p') {
                        this.tokens.add(new Keyword("loop" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'n':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'e' && this.sourceCode.charAt(this.positionID + 2) == 'w') {
                        this.tokens.add(new Keyword("new" ));
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'o' && this.sourceCode.charAt(this.positionID + 2) == 't') {
                        this.tokens.add(new Keyword("not" ));
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'u' && this.sourceCode.charAt(this.positionID + 2) == 'l' && this.sourceCode.charAt(this.positionID + 3) == 'l') {
                        this.tokens.add(new Keyword("null" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'o':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'r') {
                        this.tokens.add(new Keyword("or" ));
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'u' && this.sourceCode.charAt(this.positionID + 2) == 't') {
                        this.tokens.add(new Keyword("out" ));
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'p':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'r' && this.sourceCode.charAt(this.positionID + 2) == 'o' && this.sourceCode.charAt(this.positionID + 3) == 'c' && this.sourceCode.charAt(this.positionID + 4) == 'e' && this.sourceCode.charAt(this.positionID + 5) == 'd' && this.sourceCode.charAt(this.positionID + 6) == 'u' && this.sourceCode.charAt(this.positionID + 7) == 'r' && this.sourceCode.charAt(this.positionID + 8) == 'e') {
                        this.tokens.add(new Keyword("procedure" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'r':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'e' && this.sourceCode.charAt(this.positionID + 2) == 'c' && this.sourceCode.charAt(this.positionID + 3) == 'o' && this.sourceCode.charAt(this.positionID + 4) == 'r' && this.sourceCode.charAt(this.positionID + 5) == 'd') {
                        this.tokens.add(new Keyword("record" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'e' && this.sourceCode.charAt(this.positionID + 2) == 'm') {
                        this.tokens.add(new Keyword("rem" ));
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'e' && this.sourceCode.charAt(this.positionID + 2) == 't' && this.sourceCode.charAt(this.positionID + 3) == 'u' && this.sourceCode.charAt(this.positionID + 4) == 'r' && this.sourceCode.charAt(this.positionID + 5) == 'n') {
                        this.tokens.add(new Keyword("return" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'e' && this.sourceCode.charAt(this.positionID + 2) == 'v' && this.sourceCode.charAt(this.positionID + 3) == 'e' && this.sourceCode.charAt(this.positionID + 4) == 'r' && this.sourceCode.charAt(this.positionID + 5) == 's' && this.sourceCode.charAt(this.positionID + 6) == 'e') {
                        this.tokens.add(new Keyword("reverse" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 't':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'h' && this.sourceCode.charAt(this.positionID + 2) == 'e' && this.sourceCode.charAt(this.positionID + 3) == 'n') {
                        this.tokens.add(new Keyword("then" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'r' && this.sourceCode.charAt(this.positionID + 2) == 'u' && this.sourceCode.charAt(this.positionID + 3) == 'e') {
                        this.tokens.add(new Keyword("true" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'y' && this.sourceCode.charAt(this.positionID + 2) == 'p' && this.sourceCode.charAt(this.positionID + 3) == 'e') {
                        this.tokens.add(new Keyword("type" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                    case 'u':
                    if (this.sourceCode.charAt(this.positionID + 1) == 's' && this.sourceCode.charAt(this.positionID + 2) == 'e') {
                        this.tokens.add(new Keyword("use" ));
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;
                case 'w':
                    if (this.sourceCode.charAt(this.positionID + 1) == 'h' && this.sourceCode.charAt(this.positionID + 2) == 'i' && this.sourceCode.charAt(this.positionID + 3) == 'l' && this.sourceCode.charAt(this.positionID + 4) == 'e') {
                        this.tokens.add(new Keyword("while" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    } else if (this.sourceCode.charAt(this.positionID + 1) == 'i' && this.sourceCode.charAt(this.positionID + 2) == 't' && this.sourceCode.charAt(this.positionID + 3) == 'h') {
                        this.tokens.add(new Keyword("with" ));
                        this.getNextChar();
                        this.getNextChar();
                        this.getNextChar();
                    }
                    break;


            }

        }
        return this.tokens;
    }
}
