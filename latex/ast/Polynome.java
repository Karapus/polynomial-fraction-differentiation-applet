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
    Term term = Term.parse(tokens);

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
  abstract public Polynome derivative();
  abstract public Polynome clone();
}

enum Op {
  ADD,
  SUB
}

class Terms extends Polynome {
  private Op op;
  private Polynome tail;
  Terms(Term h, Op o, Polynome t) {
    super(h);
    op = o;
    tail = t;
  }
  public String toString() {
    String opStr;
    if (op == Op.ADD)
        opStr = "+";
    else
        opStr = "-";
    return head.toString() +  opStr + tail.toString();
  }
  public Terms derivative() {
    return new Terms(head.derivative(), op, tail.derivative());
  }
  public Terms clone() {
    return new Terms(head.clone(), op, tail.clone());
  }
}

class LastTerm extends Polynome {
  LastTerm(Term h) {
    super(h);
  }
  public String toString() {
    return head.toString();
  }
  public LastTerm derivative() {
    return new LastTerm(head.derivative());
  }
  public LastTerm clone() {
    return new LastTerm(head.clone());
  }
}
