package controller;

import model.Game;
import model.Move;
import model.UserException;
import view.GameView;
import view.console.ConsoleView;
import view.swing.SwingView;

/**
 * Контроллер
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        // Если указан ключ "gui" => графический интерфейс
        boolean gui = false;
        for (String s : args)
            if (s.equals("gui")) {
                gui = true;
            }
        GameView gameView = new ConsoleView(game);
        GameView gameView2 = new SwingView(game);
        while (!game.isOver()) {
            try {
                Move move = gameView.inputMove();
                game.move(move);
            } catch (UserException e) {
                gameView.reportError(e);
            }
        }
    }
}
