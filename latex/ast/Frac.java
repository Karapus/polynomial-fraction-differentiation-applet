package latex.ast;

import latex.tokens.*;

import java.util.ArrayList;

public class Frac implements Node {
  private Polynome numer;
  private Polynome denom;
  Frac(Polynome n, Polynome d) {
    numer = n;
    denom = d;
  }
  public String toString() {
    return "Frac {" + numer.toString() + "} {" + denom.toString() + "}";
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
}
