package controller;

import model.Game;
import view.GameView;
import view.console.ConsoleView;
import view.swing.SwingView;

/**
 * Контроллер
 */
public class Main {
    Game game = new Game();

    public static void main(String[] args) {
        GameView gameView = new ConsoleView();
        GameView gui = new SwingView();
    }
}
