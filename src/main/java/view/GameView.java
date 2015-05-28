package view;

import model.Game;
import model.Move;
import model.UserException;

/**
 * Интерфейс игры
 */
public interface GameView {
    void render(Game game);

    /**
     * Ввод хода
     *
     * @return Ход игрока
     */
    Move inputMove();

    void reportError(UserException e);
}
