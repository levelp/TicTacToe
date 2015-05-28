package model;

/**
 * Координаты x, y
 */
public class Move {
    public final int x, y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d; %d)", x, y);
    }
}
