package latex.ast;

import latex.tokens.*;

public class ParserError extends Exception {
  private String str;
  <T extends Token> ParserError(Class<T> cls, Token tok) {
    str = "Unexpected token " + tok.getClass().getSimpleName()
      + " expected " + cls.getSimpleName();
  }
  ParserError(Token tok) {
    str = "Unexpected token " + tok.getClass().getSimpleName();
  }
  public String toString() {
    return str;
  }
}
