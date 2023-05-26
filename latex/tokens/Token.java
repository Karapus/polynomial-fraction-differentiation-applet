package latex.tokens;

import java.util.Optional;
import java.util.regex.*;

import java.lang.reflect.*;

public abstract class Token {
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
