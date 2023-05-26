import latex.Lexer;
import latex.tokens.Token;

import java.util.ArrayList;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

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
