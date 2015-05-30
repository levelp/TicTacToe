package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Состояние ячейки
 */
public class CellState {
    private Cell cell = Cell.EMPTY;
    private List<CellChangedListener> listeners =
            new ArrayList<>();

    public CellState(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newState) {
        if (this.cell != newState) {
            for (CellChangedListener listener : listeners)
                listener.update(newState);
        }
        this.cell = newState;
    }

    public void addListener(CellChangedListener listener) {
        listeners.add(listener);
    }

    @Override
    public String toString() {
        return cell.toString();
    }
}
