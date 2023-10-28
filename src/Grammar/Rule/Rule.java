package Grammar.Rule;

import Grammar.Token.NonTerminalToken;

public record Rule(NonTerminalToken leftSide, Expression rightSide) {
    public String toString() {
        return this.leftSide.toString() + " -> " + this.rightSide.toString();
    }
}
