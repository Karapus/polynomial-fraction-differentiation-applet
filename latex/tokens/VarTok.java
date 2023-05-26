package latex.tokens;

import java.util.Optional;
import java.util.regex.Pattern;

public class VarTok extends Token {
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
