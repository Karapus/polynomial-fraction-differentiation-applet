package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class RParTok extends Token {
  RParTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\)");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(RParTok.class, pattern, str, pos);
  }
}
