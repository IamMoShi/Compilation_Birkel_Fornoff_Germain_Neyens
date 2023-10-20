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


}
