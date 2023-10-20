import java.util.ArrayList;
import java.util.Arrays;

public class Operators {
    public ArrayList<String> operators = new ArrayList<>(Arrays.asList(
            "+", "-", "*", "/", "**", "=", "<", ">", "<=", ">=", "/=", ":=", "&", "and", "or", "not"
    ));
    public String name;

    public Operators(String name) {
        this.name = name;
    }
}
