package model;

/**
 * Состояние клетки поля
 */
public enum Cell {
    X("X"), O("O"), EMPTY("_");

    final String text;

    Cell(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
