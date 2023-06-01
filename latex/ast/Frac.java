package latex.ast;

import latex.tokens.*;

import java.util.ArrayList;

public class Frac implements Node {
  protected Node lhs;
  protected Node rhs;
  Frac(Node n, Node d) {
    lhs = n;
    rhs = d;
  }
  public String toString() {
    return "\\frac{" + lhs.toString() + "}{" + rhs.toString() + "}";
  }
  static public Frac parse(ArrayList<Token> tokens) throws ParserError {
    if (tokens.get(0) instanceof  FracTok) {
      tokens.remove(0);

      Node.popExpectedToken(LBrTok.class, tokens);
      Polynome numer = Polynome.parse(tokens);
      Node.popExpectedToken(RBrTok.class, tokens);

      Node.popExpectedToken(LBrTok.class, tokens);
      Polynome denom = Polynome.parse(tokens);
      Node.popExpectedToken(RBrTok.class, tokens);

      return new Frac(numer, denom);
    }
    return new Frac(Polynome.parse(tokens), Polynome.getDefault());
  }
  public Node derivative() {
    return new Frac(new Sub(
          new Mul(lhs.derivative(), rhs.clone()),
          new Mul(lhs.clone(), rhs.derivative())),
        new Mul(rhs.clone(), rhs.clone()));
  }
  public Frac clone() {
    return new Frac(lhs.clone(), rhs.clone());
  }
}

class Mul implements Node {
  protected Node lhs;
  protected Node rhs;
  Mul(Node l, Node r) {
    lhs = l;
    rhs = r;
  }
  public Mul clone() {
    return new Mul(lhs.clone(), rhs.clone());
  }
  public Add derivative() {
    return new Add(
        new Mul(lhs.derivative(), rhs.clone()),
        new Mul(lhs.clone(), rhs.derivative()));
  }
  public String toString() {
    return " ( " + lhs.toString() + " )( " + rhs.toString() + " ) ";
  }
}

class Sub implements Node {
  protected Node lhs;
  protected Node rhs;
  Sub(Node l, Node r) {
    lhs = l;
    rhs = r;
  }
  public Sub clone() {
    return new Sub(lhs.clone(), rhs.clone());
  }
  public Sub derivative() {
    return new Sub(lhs.derivative(), rhs.derivative());
  }
  public String toString() {
    return " ( " + lhs.toString() + " ) - ( " + rhs.toString() + " ) ";
  }
}

class Add implements Node {
  protected Node lhs;
  protected Node rhs;
  Add(Node l, Node r) {
    lhs = l;
    rhs = r;
  }
  public Add clone() {
    return new Add(lhs.clone(), rhs.clone());
  }
  public Add derivative() {
    return new Add(lhs.derivative(), rhs.derivative());
  }
  public String toString() {
    return " ( " + lhs.toString() + " ) + ( " + rhs.toString() + " ) ";
  }
}
