package Grammar;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

public class Keyword extends TerminalToken {

    private static final ArrayList<String> keywords = new ArrayList<>(Arrays.asList(
            "access", "and", "begin", "else", "elsif", "end", "false", "for", "function", "if", "in", "is", "loop", "new", "not", "null", "or", "out", "procedure", "record", "rem", "return", "reverse", "then", "true", "type", "use", "while", "with"
    ));

    public Keyword(String value) {
        super(value);
        // Check if the value is a keyword
        if (!keywords.contains(value)) {
            throw new IllegalArgumentException("Value is not a keyword");
        }
    }

    public static boolean isKeyword(String value) {
        return keywords.contains(value);
    }

    public static AbstractList<String> getKeywords() {
        return keywords;
    }
}