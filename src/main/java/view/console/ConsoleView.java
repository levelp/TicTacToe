package view.console;

import model.Game;
import model.Move;
import model.UserException;
import view.GameView;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Запуск консольного варианта игры
 */
public class ConsoleView implements GameView {
    private final Game game;

    public ConsoleView(Game game) {
        this.game = game;
        System.out.println("Игра Крестики-нолики");
        System.out.println("====================");
        game.listeners.add(state -> render());
        render();
    }

    private void render() {
        // Вывод поля
        for (int y = 0; y < game.getSize(); y++) {
            for (int x = 0; x < game.getSize(); x++) {
                System.out.printf("%s", game.field[x][y]);
            }
            System.out.println();
        }
        System.out.println(game.getState());
    }

    public Move inputMove() {
        System.out.print("Введите ход: ");
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(" столбец = ");
                int x = in.nextInt();
                System.out.print(" строка = ");
                int y = in.nextInt();
                return new Move(x, y);
            } catch (InputMismatchException |
                    NumberFormatException ex) {
                System.out.println(ex.getMessage());
                in.next();
            }
        }
    }

    public void reportError(UserException e) {
        System.out.println(e.getMessage());
        System.out.println("Повторите ход");
    }
}
