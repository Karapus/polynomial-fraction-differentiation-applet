package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class LBrTok extends Token {
  LBrTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\{");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(LBrTok.class, pattern, str, pos);
  }
}
