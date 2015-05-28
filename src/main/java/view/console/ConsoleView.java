package view.console;

import model.Game;
import model.Move;
import model.UserException;
import view.GameView;

import java.util.Scanner;

/**
 * Запуск консольного варианта игры
 */
public class ConsoleView implements GameView {
    public ConsoleView() {
        System.out.println("Игра Крестики-нолики");
        System.out.println("====================");
    }

    public void render(Game game) {
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
        System.out.print("Введите ход (x,y): ");
        Scanner in = new Scanner(System.in);
        return new Move(in.nextInt(),
                in.nextInt());
    }

    public void reportError(UserException e) {
        System.out.println(e.getMessage());
        System.out.println("Повторите ход");
    }
}
