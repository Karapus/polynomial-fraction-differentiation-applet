package latex.ast;

import latex.tokens.*;

import java.util.ArrayList;
import java.util.Optional;

public abstract class Polynome implements Node {
  protected Term head;
  Polynome(Term h) {
    head = h;
  }
  static Polynome parse(ArrayList<Token> tokens) throws ParserError {
      System.out.println(tokens.toString());
    Term term = Term.parse(tokens);
      System.out.println(tokens.toString());

    Op op;
    if (tokens.get(0) instanceof  AddTok)
      op = Op.ADD;
    else if (tokens.get(0) instanceof  SubTok)
      op = Op.SUB;
    else
      return new LastTerm(term);
    tokens.remove(0);

    Polynome tail = Polynome.parse(tokens);

    return new Terms(term, op, tail);
  }
  static public Polynome getDefault() {
    return new LastTerm(Term.getDefault());
  }
}

enum Op {
  ADD,
  SUB
}

class Terms extends Polynome {
  private Op op;
  private Polynome tail;
  Terms(Term h, Op op, Polynome t) {
    super(h);
    op = op;
    tail = t;
  }
  public String toString() {
    String opStr;
    if (op == Op.ADD)
        opStr = "+";
    else
        opStr = "-";
    return "Polynome [" + head.toString() + opStr + tail.toString() + "]";
  }
}

class LastTerm extends Polynome {
  LastTerm(Term h) {
    super(h);
  }
  public String toString() {
    return "Polynome [" + head.toString() + "]";
  }
}
