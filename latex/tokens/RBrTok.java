package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class RBrTok extends Token {
  RBrTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\}");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(RBrTok.class, pattern, str, pos);
  }
}
