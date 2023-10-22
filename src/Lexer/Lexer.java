package Lexer;

import Grammar.TerminalToken;
import Grammar.Keyword;
import Grammar.VariableToken;

import java.util.ArrayList;
import java.util.List;

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

    public void printTokensTypes() {
        // Create a list to store the formatted token strings
        List<String> formattedTokens = new ArrayList<>();

        for (TerminalToken token : this.tokens) {
            // Create a formatted string for each token
            String formattedToken;
            if (token.getValue().equals(":") || token.getValue().equals(";") || token.getValue().equals(",") )  {
                formattedToken = "'" + token.getValue() + "'" + " : " + token.getType().getName();
            }else {
                formattedToken = token.getValue() + " : " + token.getType().getName();
            }
            formattedTokens.add(formattedToken);


        }

        // Use String.join to concatenate the formatted tokens with ", " as the separator
        String result = String.join(", ", formattedTokens);

        // Print the result
        System.out.println(result);
    }


    public void newTerminalToken(String name, String type) {
        TerminalToken token = new TerminalToken(name);
        this.newTypeToken(token, type);
        this.tokens.add(token);
    }

    public void newTypeToken(TerminalToken token, String name) {
        token.setType(name);
    }

    public void newVariableToken(String name) {
        this.tokens.add(new VariableToken(name));
    }

    /* ANALYZER METHODS --------------------------------------------------------------------------------------- */

    // SPACES AND COMMENTS Ada *********************************************************************************
    private void skipWhitespace() {
        while (Character.isWhitespace(this.currentChar) && positionID < this.sourceCode.length() - 1) {
            this.getNextChar();
        }
    }

    private void skipOneLineComment() {
        while (positionID < this.sourceCode.length() - 1 && this.currentChar != '\n') {
            this.getNextChar();
        }
    }

    // PONCTUATION Ada **************************************************************************

    private void isPunctuation() {
        switch (this.currentChar) {
            case '(':
                this.newTerminalToken("(", "L_PARENTHESIS");
                this.getNextChar();
                break;
            case ')':
                this.newTerminalToken(")", "R_PARENTHESIS");
                this.getNextChar();
                break;
            case ';':
                this.newTerminalToken(";", "SEMICOLON");
                this.getNextChar();
                break;
            case ',':
                this.newTerminalToken(",", "COMMA");
                this.getNextChar();
                break;
            case ':':
                this.newTerminalToken(":", "COLON");
            case '\n':
                this.getNextChar();
                break;
        }
    }

    // OPERATORS Ada *******************************************************************************************
    private void isOperator() {
        switch (this.currentChar) {
            case ':':
                assignment();
                break;
            case '+':
                this.newTerminalToken("+", "PLUS");
                this.getNextChar();
                break;
            case '-':
                this.newTerminalToken("-", "MINUS");
                this.getNextChar();
                break;
            case '*':
                this.newTerminalToken("*", "MULTIPLY");
                this.getNextChar();
                break;
            case '/':
                this.newTerminalToken("/", "DIVIDE");
                this.getNextChar();
                break;
            case '=':
                this.newTerminalToken("=", "EQUALS");
                this.getNextChar();
                break;

            case '<':
                inferior();
                break;
            case '>':
                superior();
                break;
        }
    }


    private void inferior() {
        if (this.sourceCode.charAt(this.positionID + 1) == '=') {
            this.newTerminalToken("<=", "INFERIOR_EQUALS");
            this.getNextChar();
        } else {
            this.newTerminalToken("<", "INFERIOR");

        }
    }

    private void superior() {
        if (this.sourceCode.charAt(this.positionID + 1) == '=') {
            this.newTerminalToken(">=", "SUPERIOR_EQUALS");
            this.getNextChar();
        } else {
            this.newTerminalToken(">", "SUPERIOR");
        }
    }

    private void assignment() {
        if (this.sourceCode.charAt(this.positionID + 1) == '=') {
            this.newTerminalToken(":=", "ASSIGNMENT");

            this.getNextChar();
        } else {
            // Error Ada
        }
        this.getNextChar();
    }

    // STRING Ada *******************************************************************************************

    private void isString() {
        if (this.currentChar == '"') {
            this.getNextChar();
            StringBuilder word = new StringBuilder();
            while (this.currentChar != '"') {
                word.append(this.currentChar);
                this.getNextChar();
            }
            this.newTerminalToken(word.toString(), "STRING");
            System.out.println(word);
            this.getNextChar();
        }
    }

    // KEYWORDS Ada *****************************************************************************

    private String currentIdentifier() {
        // Check if the current char is a letter or a number or an underscore
        StringBuilder word = new StringBuilder();
        while (Character.isLetter(this.currentChar) || Character.isDigit(this.currentChar) || this.currentChar == '_' || this.currentChar == '.') {
            word.append(this.currentChar);
            this.getNextChar();
        }

        return word.toString();
    }

    private String currentInteger() {
        // Check if the current char is a letter or a number or an underscore
        StringBuilder word = new StringBuilder();
        while (Character.isDigit(this.currentChar)) {
            word.append(this.currentChar);
            this.getNextChar();
        }

        return word.toString();
    }

    private String currentFloat() {
        // Check if the current char is a letter or a number or an underscore
        StringBuilder word = new StringBuilder();
        while (Character.isDigit(this.currentChar) || this.currentChar == '.') {
            word.append(this.currentChar);
            this.getNextChar();
        }

        return word.toString();
    }

    private void isInteger() {
        // Catch the current word and check if it is a keyword (enum Keywords)
        String word = this.currentInteger();
        if (!word.isEmpty()) {
            this.newTerminalToken(word, "INTEGER");
        }
    }

    private void isFloat() {
        // Catch the current word and check if it is a keyword (enum Keywords)
        String word = this.currentFloat();
        if (!word.isEmpty()) {
            this.newTerminalToken(word, "FLOAT");
        }
    }

    private void isKeyword() {
        // Catch the current word and check if it is a keyword (enum Keywords)
        String word = this.currentIdentifier();
        if (Keyword.isKeyword(word)) {
            this.newTerminalToken(word, "KEYWORD");
        } else if (word.equals("Ada.Text_IO")){
            this.newTerminalToken(word, "LIBRARY");
        }
        else {
            if (!word.isEmpty()) {
                this.newTerminalToken(word, "IDENTIFIER");
            }
        }
    }


    /* TOKENIZER ---------------------------------------------------------------------------------------------- */

    public ArrayList<TerminalToken> tokenize() {
        while (this.positionID < this.sourceCode.length() - 1) {
            // ---------------------------------------------------------------------------------------------
            // Space Ada
            this.skipWhitespace();

            // Comment Ada
            if (this.currentChar == '-') {
                this.getNextChar();
                if (this.currentChar == '-') {
                    this.skipOneLineComment();
                    this.getNextChar();
                } else {
                    // Error Ada
                }
            }
            // ---------------------------------------------------------------------------------------------

            // Punctuation Ada ****************************************************************************
            this.isPunctuation();

            // Operator Ada *******************************************************************************
            this.isOperator();

            // String Ada ********************************************************************************
            this.isString();

            // Integer Ada *******************************************************************************
            this.isInteger();

            // Float Ada *******************************************************************************
            this.isFloat();


            // KEYWORDS Ada *****************************************************************************
            this.isKeyword();
            System.out.println("Current char" + this.currentChar);
        }
        return this.tokens;
    }
}
