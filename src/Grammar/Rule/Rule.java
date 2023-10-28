package Grammar.Rule;

import Grammar.Token.NonTerminalToken;

public class Rule {

    private NonTerminalToken leftSide;
    private Expression rightSide;

    public Rule(NonTerminalToken leftSide, Expression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public NonTerminalToken getLeftSide() {
        return this.leftSide;
    }

    public Expression getRightSide() {
        return this.rightSide;
    }

    public String toString() {
        return this.leftSide.toString() + " -> " + this.rightSide.toString();
    }
}
