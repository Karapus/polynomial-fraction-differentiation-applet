package latex;
import latex.tokens.*;

import java.util.*;
import java.util.function.BiFunction;

import java.lang.reflect.*;

public class Lexer {
  private ArrayList<BiFunction<String, Integer, Optional<Token>>> matchers;
  public Lexer() {
    matchers = new ArrayList<BiFunction<String, Integer, Optional<Token>>>();
    matchers.add(FracTok::match);
    matchers.add(SubTok::match);
    matchers.add(PowTok::match);
    matchers.add(AddTok::match);
    matchers.add(LBrTok::match);
    matchers.add(RBrTok::match);
    matchers.add(LParTok::match);
    matchers.add(RParTok::match);
    matchers.add(NumTok::match);
    matchers.add(VarTok::match);
    matchers.add(ErrTok::match);
  }
  public ArrayList<Token> split(String text) {
    System.out.println("Splitting text '" + text + "'");
    ArrayList<Token> tokens = new ArrayList<Token>();
    int pos = 0;
    while (pos < text.length()) {
      if (Character.isWhitespace(text.charAt(pos)))
        ++pos;
      else {
        Optional<Token> optTok;
        for (BiFunction<String, Integer, Optional<Token>> matcher : matchers) {
          optTok = matcher.apply(text, pos);
          if (optTok.isPresent()) {
            Token tok = optTok.get();
            System.out.println("Matched token '" + tok.getClass().getSimpleName() + "' at position " + String.valueOf(tok.getPos()));
            tokens.add(tok);
            pos += tok.length();
            break;
          }
        }
      }
    }
    return tokens;
  }
}
