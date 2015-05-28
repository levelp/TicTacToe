package view;

import model.Move;
import model.UserException;

/**
 * Интерфейс игры
 */
public interface GameView {
    /**
     * Ввод хода
     *
     * @return Ход игрока
     */
    Move inputMove();

    void reportError(UserException e);
}
