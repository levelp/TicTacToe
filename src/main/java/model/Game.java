package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Состояние игры
 */
public class Game {
    /**
     * Поле игры
     * Координаты отсчитываем от
     * верхнего левого угла
     */
    public final CellState[][] field;

    /**
     * Наблюдатели за состоянием игры
     */
    public final List<GameUpdateListener> listeners =
            new ArrayList<>();

    /**
     * Размер поля
     */
    final int size;
    /**
     * Состояние игры
     */
    State state = State.X_MOVE;

    public Game(int size) {
        this.size = size;
        field = new CellState[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                field[x][y] = new CellState(Cell.EMPTY);
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
        if (field[x][y].getCell() != Cell.EMPTY)
            throw new UserException("Ячейка занята x = " + x + " y = " + y);

        switch (state) {
            case X_MOVE:
                field[x][y].setCell(Cell.X);
                state = State.O_MOVE;
                updateGameState(Cell.X);
                notifyListeners();
                break;
            case O_MOVE:
                field[x][y].setCell(Cell.O);
                state = State.X_MOVE;
                updateGameState(Cell.O);
                notifyListeners();
                break;
            default:
                throw new UserException("Ход невозможен!");
        }
    }

    private void notifyListeners() {
        for (GameUpdateListener listener : listeners)
            listener.update(state);
    }

    /**
     * Проверка на окончание игры
     *
     * @param lastMove чей был последний ход?
     */
    void updateGameState(Cell lastMove) {
        // Проверяем на выйгрыш
        // Горизонтальные строки
        for (int y = 0; y < size; y++) {
            boolean line = true;
            for (int x = 0; x < size; x++)
                if (field[x][y].getCell() != lastMove) {
                    line = false;
                    break;
                }
            if (line) {
                win(lastMove);
                return;
            }
        }
        // Вертикальные строки
        for (int x = 0; x < size; x++) {
            boolean line = true;
            for (int y = 0; y < size; y++)
                if (field[x][y].getCell() != lastMove) {
                    line = false;
                    break;
                }
            if (line) {
                win(lastMove);
                return;
            }
        }
        // Прямая диагональ
        boolean line = true;
        for (int i = 0; i < size; i++) {
            if (field[i][i].getCell() != lastMove) {
                line = false;
                break;
            }
        }
        if (line) {
            win(lastMove);
            return;
        }
        // Обратная диагональ
        line = true;
        for (int i = 0; i < size; i++) {
            if (field[i][size - i - 1].getCell() != lastMove) {
                line = false;
                break;
            }
        }
        if (line) {
            win(lastMove);
            return;
        }
        // Проверка на ничью
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (field[x][y].getCell() == Cell.EMPTY)
                    return; // Нашли пустую ячейку
        // Нет пустых ячеек
        state = State.DRAW; // Ничья
    }

    /**
     * Кто-то выйграл
     *
     * @param lastMove чей последний ход?
     */
    private void win(Cell lastMove) {
        switch (lastMove) {
            case X:
                state = State.X_WINS;
                break;
            case O:
                state = State.O_WINS;
                break;
            case EMPTY:
                throw new RuntimeException("Ошибка в логике игры!" +
                        "Метод не должен был быть вызван!");
        }
    }

    public void move(Move move) throws UserException {
        move(move.x, move.y);
    }

    public boolean isOver() {
        return state == State.X_WINS ||
                state == State.O_WINS ||
                state == State.DRAW;
    }

    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                sb.append(field[x][y]);
            }
            sb.append("\n");
        }
        return sb.toString();
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

        @Override
        public String toString() {
            return name;
        }
    }
}
