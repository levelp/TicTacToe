package model;

/**
 * Изменение состояния игры
 */
public interface GameUpdateListener {
    void update(Game.State state);
}
