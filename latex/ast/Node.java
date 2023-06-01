package latex.ast;

import latex.tokens.*;

import java.util.ArrayList;

public interface Node {
  static <T extends Token> Token
    popExpectedToken(Class<T> cls, ArrayList<Token> tokens)
      throws ParserError {
    if (cls.isInstance(tokens.get(0)))
      return tokens.remove(0);
    throw new ParserError(cls, tokens.get(0));
  }
  Node derivative();
  Node clone();
}
