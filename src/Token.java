
public class Token {
    public String type;
    public String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return String.format("Token(%s, %s)", type, value);
    }
}