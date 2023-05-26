package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class FracTok extends Token {
  FracTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\\\frac");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(FracTok.class, pattern, str, pos);
  }
}
