package Lexer;

import Grammar.TerminalToken;
import Grammar.Keyword;
import Grammar.VariableToken;

import java.util.ArrayList;

public class Lexer {

    /* PRIVATE VARIABLES -------------------------------------------------------------------------------------- */

    private String sourceCode;
    private int positionID;
    private char currentChar;
    private ArrayList<TerminalToken> tokens;

    /* CONSTRUCTOR -------------------------------------------------------------------------------------------- */

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.positionID = -1;
        this.currentChar = ' ';
        this.tokens = new ArrayList<TerminalToken>();
    }

    /* GETTERS AND SETTERS ------------------------------------------------------------------------------------ */

    public void getNextChar() {
        this.positionID += 1;
        if (this.positionID > this.sourceCode.length() - 1) {
            this.currentChar = ' ';
        } else {
            this.currentChar = this.sourceCode.charAt(this.positionID);
        }
    }

    public ArrayList<TerminalToken> getTokens() {
        return this.tokens;
    }

    public void newTerminalToken(String name) {
        this.tokens.add(new TerminalToken(name));
    }

    public void newVariableToken(String name) {
        this.tokens.add(new VariableToken(name));
    }

    /* ANALYZER METHODS --------------------------------------------------------------------------------------- */

    // SPACES AND COMMENTS Ada *********************************************************************************
    private void skipWhitespace() {
        while (this.currentChar != ' ' && Character.isWhitespace(this.currentChar)) {
            this.getNextChar();
        }
    }

    private void skipOneLineComment() {
        while (positionID < this.sourceCode.length() - 1 && this.currentChar != '\n') {
            this.getNextChar();
        }
    }

    // PONCTUATION Ada **************************************************************************

    private void isPunctuation(){
        switch (this.currentChar) {
            case '(': this.newTerminalToken("("); this.getNextChar(); break;
            case ')': this.newTerminalToken(")"); this.getNextChar(); break;
            case ';': this.newTerminalToken(";"); this.getNextChar(); break;
            case '\n': this.getNextChar(); break;
        }
    }

    // OPERATORS Ada *******************************************************************************************
    private void isOperator() {
        switch (this.currentChar) {
            case ':': equals(); break;
            case '+': this.newTerminalToken("+"); this.getNextChar(); break;
            case '-': this.newTerminalToken("-"); this.getNextChar(); break;
            case '*': this.newTerminalToken("*"); this.getNextChar(); break;
            case '/': this.newTerminalToken("/"); this.getNextChar(); break;
            case '=': this.newTerminalToken("="); this.getNextChar(); break;

            case '<': inferior(); break;
            case '>': superior(); break;
        }
    }


    private void inferior() {
        if (this.sourceCode.charAt(this.positionID + 1) == '=') {
            this.newTerminalToken("<=");
            this.getNextChar();
        } else {
            this.newTerminalToken("<");
        }
    }

    private void superior() {
        if (this.sourceCode.charAt(this.positionID + 1) == '=') {
            this.newTerminalToken(">=");
            this.getNextChar();
        } else {
            this.newTerminalToken(">");
        }
    }

    private void equals() {
        if (this.sourceCode.charAt(this.positionID + 1) == '=') {
            this.newTerminalToken(":=");
            this.getNextChar();
        } else {
            // Error Ada
        }
    }

    // KEYWORDS Ada *****************************************************************************

    private String currentWord() {
        // Check if the current char is a letter or a number
        StringBuilder word = new StringBuilder();
        while (Character.isLetter(this.currentChar) || Character.isDigit(this.currentChar)) {
            word.append(this.currentChar);
            this.getNextChar();
        }
        return word.toString();
    }

    private void isKeyword() {
        // Catch the current word and check if it is a keyword (enum Keywords)
        String word = this.currentWord();
        if (Keyword.isKeyword(word)) {
            this.newTerminalToken(word);
        } else {
            this.newVariableToken(word);
        }
    }


    /* TOKENIZER ---------------------------------------------------------------------------------------------- */

    public ArrayList<TerminalToken> tokenize() {
        while (this.positionID < this.sourceCode.length() - 1) {
            this.getNextChar();

            // ---------------------------------------------------------------------------------------------
            // Space Ada
            if (Character.isWhitespace(this.currentChar)) {
                this.skipWhitespace();
            }
            // Comment Ada
            if (this.currentChar == '-') {
                this.getNextChar();
                if (this.currentChar == '-') {
                    this.skipOneLineComment();
                } else {
                    // Error Ada
                }
            }
            // ---------------------------------------------------------------------------------------------

            // Punctuation Ada ****************************************************************************
            this.isPunctuation();

            // Operator Ada *******************************************************************************
            this.isOperator();

            // KEYWORDS Ada *****************************************************************************
            this.isKeyword();

        }
        return this.tokens;
    }
}
