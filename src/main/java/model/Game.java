package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Состояние игры
 */
public class Game {
    /**
     * Название игры
     */
    public static final String GAME_TITLE = "Игра Крестики-нолики";

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
     * Проверка координаты на допустимость
     *
     * @param coord координата
     * @param name имя координаты для сообщения об ошибке
     * @throws UserException если координата за пределами поля
     */
    private void validateCoordinate(int coord, String name) throws UserException {
        if (coord < 0 || coord >= size) {
            throw new UserException(name + " за пределами поля");
        }
    }

    /**
     * Выполнение хода
     *
     * @param x координата по горизонтали
     * @param y координата по вертикали
     * @param player символ игрока
     * @param nextState следующее состояние игры
     * @throws UserException если ячейка занята
     */
    private void performMove(int x, int y, Cell player, State nextState) throws UserException {
        field[x][y].setCell(player);
        state = nextState;
        updateGameState(player);
        notifyListeners();
    }

    /**
     * Ход
     *
     * @param x координата по горизонтали (столбец)
     * @param y координата по вертикали (строка)
     */
    public void move(int x, int y) throws UserException {
        validateCoordinate(x, "x");
        validateCoordinate(y, "y");

        if (field[x][y].getCell() != Cell.EMPTY) {
            throw new UserException("Ячейка занята x = " + x + " y = " + y);
        }

        switch (state) {
            case X_MOVE:
                performMove(x, y, Cell.X, State.O_MOVE);
                break;
            case O_MOVE:
                performMove(x, y, Cell.O, State.X_MOVE);
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
     * Проверка линии на заполненность одним символом
     *
     * @param lastMove символ для проверки
     * @param coordinates массив координат для проверки
     * @return true если вся линия заполнена lastMove
     */
    private boolean checkLine(Cell lastMove, int[][] coordinates) {
        for (int[] coord : coordinates) {
            if (field[coord[0]][coord[1]].getCell() != lastMove) {
                return false;
            }
        }
        return true;
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
            int[][] horizontalLine = new int[size][2];
            for (int x = 0; x < size; x++) {
                horizontalLine[x] = new int[]{x, y};
            }
            if (checkLine(lastMove, horizontalLine)) {
                win(lastMove);
                return;
            }
        }

        // Вертикальные строки
        for (int x = 0; x < size; x++) {
            int[][] verticalLine = new int[size][2];
            for (int y = 0; y < size; y++) {
                verticalLine[y] = new int[]{x, y};
            }
            if (checkLine(lastMove, verticalLine)) {
                win(lastMove);
                return;
            }
        }

        // Прямая диагональ
        int[][] mainDiagonal = new int[size][2];
        for (int i = 0; i < size; i++) {
            mainDiagonal[i] = new int[]{i, i};
        }
        if (checkLine(lastMove, mainDiagonal)) {
            win(lastMove);
            return;
        }

        // Обратная диагональ
        int[][] antiDiagonal = new int[size][2];
        for (int i = 0; i < size; i++) {
            antiDiagonal[i] = new int[]{i, size - i - 1};
        }
        if (checkLine(lastMove, antiDiagonal)) {
            win(lastMove);
            return;
        }

        // Проверка на ничью
        if (isBoardFull()) {
            state = State.DRAW;
        }
    }

    /**
     * Проверка, заполнено ли поле полностью
     *
     * @return true если нет пустых ячеек
     */
    private boolean isBoardFull() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (field[x][y].getCell() == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
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
