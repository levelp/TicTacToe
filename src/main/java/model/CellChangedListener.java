package model;

/**
 * Изменение состояния ячейки
 */
public interface CellChangedListener {
    void update(Cell newState);
}
