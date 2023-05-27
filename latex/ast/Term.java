package latex.ast;

import latex.tokens.*;

import java.util.ArrayList;

public abstract class Term implements Node {
  double coeficient;
  Term(double c) {
    coeficient = c;
  }
  static public Term parse(ArrayList<Token> tokens) throws ParserError {
    double coeficient;
    if (tokens.get(0) instanceof NumTok) {
      coeficient = tokens.remove(0).getNum();
      if (!(tokens.get(0) instanceof VarTok))
        return new SimpleTerm(coeficient);
    } else if (tokens.get(0) instanceof VarTok)
      coeficient = 1.0;
    else
      throw new ParserError(tokens.remove(0));

    String varName = tokens.remove(0).getVar();
    if (!(tokens.get(0) instanceof PowTok))
      return new FullTerm(coeficient, varName);
    tokens.remove(0);

    double power  = Node.popExpectedToken(NumTok.class, tokens).getNum();
    return new FullTerm(coeficient, varName, power);
  }
  static public Term getDefault() {
    return new SimpleTerm(1.0);
  }
}

class FullTerm extends Term {
  double power;
  String varName;
  FullTerm(double c, String n, double p) {
    super(c);
    power = p;
    varName = n;
  }
  FullTerm(double c, String n) {
    super(c);
    power = 1.0;
    varName = n;
  }
  public String toString() {
    return "Term (" + String.valueOf(coeficient) + ", "
      + varName + ", "
      + String.valueOf(power) + ")";
  }
}

class SimpleTerm extends Term {
  SimpleTerm(double c) {
    super(c);
  }
  public String toString() {
    return "Term (" + String.valueOf(coeficient) + ", <none>, zero)";
  }
}
