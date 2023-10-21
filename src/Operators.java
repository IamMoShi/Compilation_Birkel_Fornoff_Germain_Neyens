import java.util.ArrayList;
import java.util.Arrays;

public class Operators extends Token{
    public ArrayList<String> operators = new ArrayList<>(Arrays.asList(
            "+", "-", "*", "/",  "=", "<", ">", "<=", ">=", "/=", ":="
    ));
    public String name;

    public Operators(String name){
        super("Operator", name);
        this.name = name;
    }

}
