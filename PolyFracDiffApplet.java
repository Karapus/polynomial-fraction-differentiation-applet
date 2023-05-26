import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.regex.*;
import java.util.function.BiFunction;

import java.lang.reflect.*;

abstract class Token {
  private int pos;
  private int length;
  Token(String s, int p) {
    pos = p;
    length = s.length();
  }
  public float getNum() {
    throw new AssertionError();
  }
  public String getVar() {
    throw new AssertionError();
  }
  public int getPos() {
    return pos;
  }
  public int length() {
    return length;
  }
  protected static <T extends Token> Optional<Token> match(Class<T> cls, Pattern pattern, String str, int pos) {
    Matcher matcher = pattern.matcher(str.substring(pos));
    if (matcher.find(0))
      try {
        String s = matcher.group();
        return Optional.of(cls.getDeclaredConstructor(String.class, int.class).newInstance(s, pos));
      } catch (Exception e) {
        System.out.println("Exception " + e + " occured while creating " + cls.getSimpleName() + " from " + matcher.group());
      }
    return Optional.empty();
  }
}

class FracTok extends Token {
  FracTok(String s, int p) {
    super(s, p);
  }
  public String toStr() { return "\\frac"; }
  private static Pattern pattern = Pattern.compile("^\\\\frac");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(FracTok.class, pattern, str, pos);
  }
}

class PowTok extends Token {
  PowTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\^");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(PowTok.class, pattern, str, pos);
  }
}

class AddTok extends Token {
  AddTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\+");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(AddTok.class, pattern, str, pos);
  }
}

class SubTok extends Token {
  SubTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^-");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(SubTok.class, pattern, str, pos);
  }
}

class LBrTok extends Token {
  LBrTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\{");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(LBrTok.class, pattern, str, pos);
  }
}

class RBrTok extends Token {
  RBrTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\}");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(RBrTok.class, pattern, str, pos);
  }
}

class LParTok extends Token {
  LParTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\(");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(LParTok.class, pattern, str, pos);
  }
}

class RParTok extends Token {
  RParTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\)");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(RParTok.class, pattern, str, pos);
  }
}

class NumTok extends Token {
  private float num;
  NumTok(String s, int p) {
    super(s, p);
    num = Float.parseFloat(s);
  }
  public float getNum() {
    return num;
  }
  private static Pattern pattern = Pattern.compile("(([1-9][0-9]+)|[0-9])((\\.[0-9]+)?)");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(NumTok.class, pattern, str, pos);
  }
}

class VarTok extends Token {
  private String name;
  VarTok(String s, int p) {
    super(s, p);
    name = s;
  }
  public String getVar() {
    return name;
  }
  private static Pattern pattern = Pattern.compile("[a-z]");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(VarTok.class, pattern, str, pos);
  }
}

class ErrTok extends Token {
  ErrTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile(".+");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(ErrTok.class, pattern, str, pos);
  }
}

class Lexer {
  private ArrayList<BiFunction<String, Integer, Optional<Token>>> matchers;
  Lexer() {
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

public class PolyFracDiffApplet extends Applet implements ActionListener {
  Button button;
  TextArea input;
  Lexer lexer = new Lexer();
  ArrayList<Token> tokens = null;

  public void init() {
    input = new TextArea();
    add(input);

    button = new Button("Show derivative");
    add(button);
    button.addActionListener(this);
  }

  public void paint(Graphics g) {
    if (tokens == null)
      return;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button) {
      tokens = lexer.split(input.getText());
    }
  }
}
