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
        GameView gameView = new ConsoleView();
        GameView gui = new SwingView();
        while (!game.isOver()) {
            gameView.render(game);
            try {
                Move move = gameView.inputMove();
                game.move(move);
            } catch (UserException e) {
                gameView.reportError(e);
            }
        }
    }
}
