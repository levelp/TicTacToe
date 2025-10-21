package bdd.steps;

import io.cucumber.java.ru.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Step definitions для BDD тестов игры
 */
public class GameSteps {
    private Game game;
    private UserException lastException;
    private String lastStringResult;
    private CellState cellState;
    private List<Boolean> listenersNotified;
    private Move move;
    private UserException userException;
    private Game.State lastGameState;
    private int notificationCount;

    // ============================================================
    // GIVEN steps (Допустим/Дано)
    // ============================================================

    @Допустим("у меня пустое поле {int}x{int}")
    public void emptyBoard(int size, int size2) {
        game = new Game(size);
        lastException = null;
    }

    @Допустим("у меня есть CellState со значением {}")
    public void cellStateWithValue(String cellValue) {
        Cell cell = Cell.valueOf(cellValue);
        cellState = new CellState(cell);
        listenersNotified = new ArrayList<>();
    }

    @Допустим("у CellState добавлен слушатель изменений")
    public void addCellListener() {
        cellState.addListener(newState -> {
            listenersNotified.add(true);
        });
    }

    @Допустим("у CellState добавлено {int} слушателя")
    public void addMultipleCellListeners(int count) {
        listenersNotified = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cellState.addListener(newState -> {
                listenersNotified.add(true);
            });
        }
    }

    @Допустим("X уже сходил в позицию {int}, {int}")
    public void xAlreadyMoved(int x, int y) throws UserException {
        if (game == null) {
            game = new Game();
        }
        game.move(x, y);
    }

    @Допустим("игра завершена победой X")
    public void gameFinishedWithXWin() throws UserException {
        game = new Game();
        // Создаем ситуацию, где X выигрывает
        game.move(0, 0); // X
        game.move(1, 0); // O
        game.move(0, 1); // X
        game.move(1, 1); // O
        game.move(0, 2); // X wins
    }

    @Допустим("у игры добавлен слушатель обновлений")
    public void addGameUpdateListener() {
        notificationCount = 0;
        game.listeners.add(state -> {
            lastGameState = state;
            notificationCount++;
        });
    }

    // ============================================================
    // WHEN steps (Когда)
    // ============================================================

    @Когда("я создаю новую игру")
    public void createNewGame() {
        game = new Game();
    }

    @Когда("я создаю новую игру с размером поля {int}")
    public void createGameWithSize(int size) {
        game = new Game(size);
    }

    @Когда("X ходит в позицию {int}, {int}")
    public void xMoves(int x, int y) throws UserException {
        game.move(x, y);
    }

    @Когда("O ходит в позицию {int}, {int}")
    public void oMoves(int x, int y) throws UserException {
        game.move(x, y);
    }

    @Когда("я пытаюсь сделать ход в позицию {int}, {int}")
    public void tryToMove(int x, int y) {
        try {
            game.move(x, y);
            lastException = null;
        } catch (UserException e) {
            lastException = e;
        }
    }

    @Когда("я делаю ход используя объект Move с координатами {int}, {int}")
    public void moveWithMoveObject(int x, int y) throws UserException {
        Move move = new Move(x, y);
        game.move(move);
    }

    @Когда("создается горизонтальная линия {} в строке {int}")
    public void createHorizontalLine(String symbol, int row) throws UserException {
        Cell cell = Cell.valueOf(symbol);
        Cell opponent = (cell == Cell.X) ? Cell.O : Cell.X;

        // Заполняем горизонтальную линию
        for (int x = 0; x < 3; x++) {
            game.field[x][row] = new CellState(cell);
        }

        // Заполняем одну клетку противника, чтобы был правильный ход
        if (cell == Cell.X) {
            game.field[0][(row + 1) % 3] = new CellState(opponent);
            game.state = Game.State.X_MOVE;
        } else {
            game.field[0][(row + 1) % 3] = new CellState(Cell.X);
            game.state = Game.State.O_MOVE;
        }

        game.updateGameState(cell);
    }

    @Когда("создается вертикальная линия {} в столбце {int}")
    public void createVerticalLine(String symbol, int col) throws UserException {
        Cell cell = Cell.valueOf(symbol);
        Cell opponent = (cell == Cell.X) ? Cell.O : Cell.X;

        // Заполняем вертикальную линию
        for (int y = 0; y < 3; y++) {
            game.field[col][y] = new CellState(cell);
        }

        // Заполняем одну клетку противника
        if (cell == Cell.O) {
            game.field[(col + 1) % 3][0] = new CellState(Cell.X);
            game.state = Game.State.O_MOVE;
        }

        game.updateGameState(cell);
    }

    @Когда("я устанавливаю значение CellState в {}")
    public void setCellStateValue(String cellValue) {
        Cell cell = Cell.valueOf(cellValue);
        cellState.setCell(cell);
    }

    @Когда("я проверяю значения enum Cell")
    public void checkCellValues() {
        // Просто триггер для проверки в Then
    }

    @Когда("я получаю строковое представление Cell.{}")
    public void getCellToString(String cellName) {
        Cell cell = Cell.valueOf(cellName);
        lastStringResult = cell.toString();
    }

    @Когда("я получаю строковое представление CellState")
    public void getCellStateToString() {
        lastStringResult = cellState.toString();
    }

    @Когда("я создаю Move с координатами {int}, {int}")
    public void createMove(int x, int y) {
        move = new Move(x, y);
    }

    @Когда("я получаю строковое представление Move")
    public void getMoveToString() {
        lastStringResult = move.toString();
    }

    @Когда("я создаю UserException с сообщением {string}")
    public void createUserException(String message) {
        userException = new UserException(message);
    }

    // ============================================================
    // THEN steps (Тогда)
    // ============================================================

    @Тогда("размер поля равен {int}")
    public void checkBoardSize(int expectedSize) {
        assertEquals(expectedSize, game.getSize());
    }

    @Тогда("ход крестиков")
    public void checkXMove() {
        assertEquals(Game.State.X_MOVE, game.getState());
    }

    @Тогда("ход ноликов")
    public void checkOMove() {
        assertEquals(Game.State.O_MOVE, game.getState());
    }

    @Тогда("клетка {int}, {int} содержит {}")
    public void checkCell(int x, int y, String cellValue) {
        Cell expected = Cell.valueOf(cellValue);
        assertEquals(expected, game.field[x][y].getCell());
    }

    @Тогда("игра не завершена")
    public void gameNotOver() {
        assertFalse(game.isOver());
    }

    @Тогда("игра завершена")
    public void gameOver() {
        assertTrue("Game should be over", game.isOver());
    }

    @Тогда("победитель {}")
    public void checkWinner(String symbol) {
        if (symbol.equals("X")) {
            assertEquals(Game.State.X_WINS, game.getState());
        } else if (symbol.equals("O")) {
            assertEquals(Game.State.O_WINS, game.getState());
        }
    }

    @Тогда("результат игры ничья")
    public void checkDraw() {
        assertEquals(Game.State.DRAW, game.getState());
    }

    @Тогда("строковое представление игры содержит {string}")
    public void checkGameStringContains(String substring) {
        String gameString = game.toString();
        assertTrue("Game string should contain " + substring,
                   gameString.contains(substring));
    }

    @Тогда("выбрасывается исключение UserException")
    public void checkUserExceptionThrown() {
        assertNotNull("UserException should be thrown", lastException);
        assertTrue("Exception should be UserException",
                   lastException instanceof UserException);
    }

    @Тогда("сообщение об ошибке содержит {string}")
    public void checkErrorMessage(String expectedMessage) {
        assertNotNull("Exception should be thrown", lastException);
        assertTrue("Error message should contain: " + expectedMessage,
                   lastException.getMessage().contains(expectedMessage));
    }

    @Тогда("значение CellState равно {}")
    public void checkCellStateValue(String cellValue) {
        Cell expected = Cell.valueOf(cellValue);
        assertEquals(expected, cellState.getCell());
    }

    @Тогда("слушатель был уведомлен о изменении на {}")
    public void checkListenerNotified(String cellValue) {
        assertFalse("Listener should be notified", listenersNotified.isEmpty());
    }

    @Тогда("слушатель не был уведомлен")
    public void checkListenerNotNotified() {
        assertTrue("Listener should not be notified",
                   listenersNotified == null || listenersNotified.isEmpty());
    }

    @Тогда("все {int} слушателя были уведомлены")
    public void checkAllListenersNotified(int count) {
        assertEquals("All listeners should be notified",
                     count, listenersNotified.size());
    }

    @Тогда("Cell содержит значение {}")
    public void checkCellContainsValue(String cellName) {
        Cell cell = Cell.valueOf(cellName);
        assertNotNull(cell);
    }

    @Тогда("результат равен {string}")
    public void checkStringResult(String expected) {
        assertEquals(expected, lastStringResult);
    }

    @Тогда("координата x равна {int}")
    public void checkMoveX(int expected) {
        assertEquals(expected, move.x);
    }

    @Тогда("координата y равна {int}")
    public void checkMoveY(int expected) {
        assertEquals(expected, move.y);
    }

    @Тогда("строковое представление Move равно {string}")
    public void checkMoveString(String expected) {
        assertEquals(expected, move.toString());
    }

    @Тогда("сообщение исключения равно {string}")
    public void checkExceptionMessage(String expected) {
        assertEquals(expected, userException.getMessage());
    }

    @Тогда("исключение является экземпляром Exception")
    public void checkExceptionInstance() {
        assertTrue("Should be instance of Exception",
                   userException instanceof Exception);
    }

    @Тогда("слушатель обновлений был уведомлен о состоянии {}")
    public void checkGameStateNotification(String stateName) {
        assertTrue("Listener should be notified", notificationCount > 0);
        assertEquals(Game.State.valueOf(stateName), lastGameState);
    }
}
