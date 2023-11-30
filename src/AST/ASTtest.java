package AST;

import Grammar.Type.Type;

public class ASTtest {
    public static void main(String[] args) {
        // (a + b) * c
        Node root = new Node("*", new Type("int"), null);
        Node child1 = new Node("+", new Type("int"), null);
        Node child2 = new Node("a", new Type("int"), null);
        Node child3 = new Node("b", new Type("int"), null);
        Node child4 = new Node("c", new Type("int"), null);
        root.addChildren(child1);
        root.addChildren(child4);
        child1.addChildren(child2);
        child1.addChildren(child3);

        root.printTree(0);
    }
}
