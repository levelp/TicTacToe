package model;

/**
 * Состояние игры
 */
public class Game {


    /**
     * Размер поля
     */
    final int size;
    /**
     * Поле игры
     * Координаты отсчитываем от
     * верхнего левого угла
     */
    final Cell[][] field;
    /**
     * Состояние игры
     */
    State state = State.X_MOVE;

    public Game(int size) {
        this.size = size;
        field = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                field[x][y] = Cell.EMPTY;
            }
        }
    }

    public Game() {
        this(3);
    }

    public int getSize() {
        return size;
    }

    /**
     * Ход
     *
     * @param x координата по горизонтали (столбец)
     * @param y координата по вертикали (строка)
     */
    public void move(int x, int y) throws UserException {
        if (x < 0 || x >= size)
            throw new UserException("x за пределами поля");
        if (y < 0 || y >= size)
            throw new UserException("y за пределами поля");
        if (field[x][y] != Cell.EMPTY)
            throw new UserException("Ячейка занята x = " + x + " y = " + y);

        switch (state) {
            case X_MOVE:
                field[x][y] = Cell.X;
                break;
            case O_MOVE:
                field[x][y] = Cell.O;
                break;
            default:
                throw new UserException("Ход невозможен!");
        }
    }

    public enum State {
        X_MOVE("Ход крестиков"),
        O_MOVE("Ход ноликов"),
        X_WINS("Крестики выиграли"),
        O_WINS("Нолики выиграли"),
        DRAW("Ничья");

        public final String name;

        State(String name) {
            this.name = name;
        }
    }
}
