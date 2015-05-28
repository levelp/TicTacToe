package view.console;

import model.Game;
import view.GameView;

/**
 * Запуск консольного варианта игры
 */
public class ConsoleView implements GameView {
    public ConsoleView() {
        System.out.println("Игра Крестики-нолики");
        System.out.println("====================");
    }

    public void render(Game game) {
        // TODO: Реализовать
    }
}
