package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class AddTok extends Token {
  AddTok(String s, int p) {
    super(s, p);
  }
  private static Pattern pattern = Pattern.compile("^\\+");
  public static Optional<Token> match(String str, int pos) {
    return Token.match(AddTok.class, pattern, str, pos);
  }
}
