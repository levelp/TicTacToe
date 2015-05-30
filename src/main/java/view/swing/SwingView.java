package view.swing;

import model.Game;
import model.Move;
import model.UserException;
import view.GameView;

import javax.swing.*;

/**
 * Отображение игры в Swing интерфейсе
 */
public class SwingView implements GameView {

    public SwingView(Game game) {
        JFrame frame = new JFrame("Игра Крестики-нолики");
        MainForm mainForm = new MainForm(game);
        frame.setContentPane(mainForm.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Move inputMove() {
        return null;
    }

    public void reportError(UserException e) {
    }
}
