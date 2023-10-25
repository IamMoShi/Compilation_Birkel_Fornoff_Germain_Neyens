package Error;

public class LexerError extends Error {

    private String message;

    public LexerError(int line, int column, String message) {
        super(line, column);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return message + "\n" + "Lexer error at line " + this.getLine() + ", column " + this.getColumn() + ": " + this.getMessage();
    }
}
