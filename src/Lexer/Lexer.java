package Lexer;

import Error.LexerError;
import Grammar.Token.TerminalToken;
import Grammar.Token.Keyword;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    /* PRIVATE VARIABLES -------------------------------------------------------------------------------------- */

    private final String sourceCode;
    private int positionID;
    private int lineCounter;
    private int columnCounter;
    private char currentChar;

    private final ArrayList<TerminalToken> tokens;

    private int tokenPosition;



    /* CONSTRUCTOR -------------------------------------------------------------------------------------------- */

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.positionID = -1;
        this.currentChar = ' ';
        this.lineCounter = 1;
        this.tokens = new ArrayList<>();
        this.tokenPosition = -1;
    }

    /* GETTERS AND SETTERS ------------------------------------------------------------------------------------ */

    public void getNextChar() {
        this.positionID += 1;
        this.columnCounter += 1;
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
            if (token.getValue().equals(":") || token.getValue().equals(";") || token.getValue().equals(",")) {
                formattedToken = "'" + token.getValue() + "'" + " : " + token.getType().getName();
            } else {
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

    public TerminalToken getCurrentToken() {
        if (this.tokenPosition >= this.tokens.size()) {
            throw new IndexOutOfBoundsException("No more tokens");
        }
        return this.tokens.get(this.tokenPosition);
    }

    public TerminalToken getNextToken() {
        if (this.tokenPosition + 1 >= this.tokens.size()) {
            throw new IndexOutOfBoundsException("No more tokens");
        }
        return this.tokens.get(this.tokenPosition + 1);
    }

    public void nextToken() {

        this.tokenPosition += 1;

    }

    public List<TerminalToken> getTokens() {
        return this.tokens;
    }

    public TerminalToken getPreviousToken() {
        if (this.tokenPosition - 1 < 0) {
            throw new IndexOutOfBoundsException("No more tokens");
        }
        return this.tokens.get(this.tokenPosition - 1);
    }

    public int getTokenPosition() {
        return this.tokenPosition;
    }

    public boolean stillAvailableToken() {
        return this.tokenPosition <= this.tokens.size() - 1;
    }

    /* ANALYZER METHODS --------------------------------------------------------------------------------------- */

    // SPACES AND COMMENTS Ada *********************************************************************************
    private void skipWhitespace() {
        while (Character.isWhitespace(this.currentChar) && positionID < this.sourceCode.length() - 1) {
            if (this.currentChar == '\n') {
                this.lineCounter += 1;
                this.columnCounter = 0;
            }
            this.getNextChar();
        }
    }

    private void skipOneLineComment() {
        while (positionID < this.sourceCode.length() - 1 && this.currentChar != '\n') {
            this.getNextChar();
        }
    }

    // PONCTUATION Ada **************************************************************************

    private void dotAndDoubleDot() {
        if (this.sourceCode.charAt(this.positionID + 1) == '.') {
            this.newTerminalToken("..", "DOUBLE_DOT");
            this.getNextChar();
        } else {
            this.newTerminalToken(".", "DOT");
            this.getNextChar();
        }
    }

    private void isPunctuation() {
        switch (this.currentChar) {
            case '.':
                this.dotAndDoubleDot();
                break;
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
                this.assignment();
        }
    }

    // OPERATORS Ada *******************************************************************************************
    private void isOperator() throws LexerError {
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
            case '\'':
                isCharacter();
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
            newTerminalToken(":", "COLON");
        }
        this.getNextChar();
    }

    private void isCharacter() throws LexerError {
        /* Appends after ' and check if it is :
            - T'val(e) : T is a type, val is a value and e is an expression
            - 'a' : a is a character
            - '''' : ' is a character, but we need to escape it
         */
        if (this.currentChar == '\'') {
            if (sourceCode.charAt(positionID + 1) == '\'') {
                this.getNextChar();
                if (sourceCode.charAt(positionID + 1) == '\'' && sourceCode.charAt(positionID + 2) == '\'') {
                    // case : ''''
                    this.newTerminalToken("'", "CHARACTER");
                    this.getNextChar();
                    this.getNextChar();
                } else {
                    // Case : '' or '''
                    throw new LexerError(this.lineCounter, this.columnCounter, "Invalid character '" + this.currentChar + "'" + "Expected '''");
                }
            } else if (sourceCode.charAt(positionID + 2) == '\'') {
                // Case : 'a'
                this.getNextChar(); // 'a' : the char
                this.newTerminalToken(String.valueOf(this.currentChar), "CHARACTER");
                this.getNextChar(); // '\'' : the closing quote
            } else {
                this.newTerminalToken("'", "APOSTROPHE"); // Reference for types
            }
        }
        this.getNextChar();
    }

    // STRING Ada *******************************************************************************************

    private void isString() throws LexerError {
        if (this.currentChar == '"') {
            this.getNextChar();
            StringBuilder word = new StringBuilder();
            while (this.currentChar != '"') {
                if (this.positionID > this.sourceCode.length() - 1) {
                    throw new LexerError(this.lineCounter, this.columnCounter, "Missing closing quote");
                }
                if (this.currentChar == '\n') {
                    throw new LexerError(this.lineCounter, this.columnCounter, "Missing closing quote");
                }
                word.append(this.currentChar);
                this.getNextChar();
            }
            this.newTerminalToken(word.toString(), "STRING");
            this.getNextChar();
        }
    }

    // KEYWORDS Ada *****************************************************************************

    private String currentIdentifier() {
        // Check if the current char is a letter or a number or an underscore
        StringBuilder word = new StringBuilder();

        while (Character.isLetter(this.currentChar) || Character.isDigit(this.currentChar) || this.currentChar == '_') {
            word.append(this.currentChar);
            this.getNextChar();
        }

        return word.toString();
    }


    public String currentTokenType() {
        return this.getCurrentToken().getType().getName();
    }


    public String currentTokenValue() {
        return this.getCurrentToken().getValue();
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
        // Analyse the next char to determine if it is a float
        // A float is composed like this ([0-9]+.[0-9]*)
        // If the word is not a float, return an empty string and reset the position
        // Must know how many char to go back
        StringBuilder word = new StringBuilder();
        int position = this.positionID;
        int numbers = 0;
        while (Character.isDigit(this.currentChar)) {
            word.append(this.currentChar);
            numbers += 1;
            this.getNextChar();
        }
        if (this.currentChar == '.' && numbers > 0) {
            word.append(this.currentChar);
            this.getNextChar();
            while (Character.isDigit(this.currentChar)) {
                word.append(this.currentChar);
                this.getNextChar();
            }
            if (word.length() > 1) {
                return word.toString();
            }
        }
        this.positionID = position;
        return "";
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
        } else if (word.equals("Ada.Text_IO")) {
            this.newTerminalToken(word, "LIBRARY");
        } else {
            if (!word.isEmpty() && Character.isLetter(word.charAt(0)) ) {
                this.newTerminalToken(word, "IDENTIFIER");
            }
        }
    }


    /* TOKENIZER ---------------------------------------------------------------------------------------------- */

    public ArrayList<TerminalToken> tokenize() throws LexerError {
        while (this.positionID < this.sourceCode.length() - 1) {
            int currentPosition = this.positionID;
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
                    throw new LexerError(this.lineCounter, this.columnCounter, "Invalid character '" + this.currentChar + "'" + "Expected '-'");
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

            if (currentPosition == this.positionID) {
                throw new LexerError(this.lineCounter, this.columnCounter, "Invalid character '" + this.currentChar + "'");
            }
        }

        this.tokenPosition = 0;
        return this.tokens;
    }
}
