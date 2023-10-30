package Error;

public class Error extends Exception {
    private final int line;
    private final int column;

    public Error(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public String toString() {
        return "Error at line " + this.line + ", column " + this.column + ": " + this.getMessage();
    }
}
