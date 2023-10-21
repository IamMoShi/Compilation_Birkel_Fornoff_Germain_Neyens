import java.util.ArrayList;
import java.util.Arrays;

public class Keyword extends Token{
    public ArrayList<String> keywords = new ArrayList<>(Arrays.asList(
            "access", "and", "begin", "else", "elsif", "end", "false", "for", "function", "if", "in", "is", "loop", "new", "not", "null", "or", "out", "procedure", "record", "rem", "return", "reverse", "then", "true", "type", "use", "while", "with"
    ));
    public String name;

    public Keyword(String name){
        super("keyword", name);
        this.name = name;
    }
}


