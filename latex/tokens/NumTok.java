package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class NumTok extends Token {
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
