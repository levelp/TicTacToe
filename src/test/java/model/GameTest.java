package model;

import org.junit.Test;

import static model.Cell.O;
import static model.Cell.X;
import static model.Game.State.*;
import static org.junit.Assert.*;

/**
 * Процесс игры
 */
public class GameTest {

    @Test
    public void testSimple() throws UserException {
        Game game = new Game();
        assertEquals("По-умолчанию игра 3x3", 3, game.size);
        assertEquals(X_MOVE, game.state);
        assertEquals(3, game.getSize());

        // Ходим в верхний левый угол
        game.move(0, 0);
        assertEquals(X, game.field[0][0].getCell());
        assertEquals("Теперь ход ноликов", O_MOVE, game.state);
        assertEquals(O_MOVE, game.getState());
        assertFalse(game.isOver());
    }

    @Test
    public void testCustomSize() {
        Game game = new Game(5);
        assertEquals(5, game.size);
        assertEquals(5, game.getSize());
    }

    @Test
    public void testWinX() throws UserException {
        Game game = new Game();
        assertEquals(X_MOVE, game.state);
        game.field[0] = new CellState[]{new CellState(X), new CellState(X), new CellState(X)};
        assertEquals(X_MOVE, game.state);
        game.updateGameState(X);
        assertEquals(X_WINS, game.state);
        assertTrue(game.isOver());
    }

    @Test
    public void testWinO() throws UserException {
        Game game = new Game();
        assertEquals(X_MOVE, game.state);
        game.move(1, 1);
        assertEquals(O_MOVE, game.state);
        game.field[0] = new CellState[]{new CellState(O), new CellState(O), new CellState(O)};
        assertEquals(O_MOVE, game.state);
        game.updateGameState(O);
        assertEquals(O_WINS, game.state);
        assertTrue(game.isOver());
    }

    @Test
    public void testWinMainDiagonal() throws UserException {
        Game g = new Game();
        assertEquals(X_MOVE, g.state);
        g.move(0, 0);
        assertEquals(O_MOVE, g.state);
        g.move(1, 0);
        assertEquals(X_MOVE, g.state);
        g.move(1, 1);
        assertEquals(O_MOVE, g.state);
        g.move(0, 1);
        assertEquals(X_MOVE, g.state);
        g.move(2, 2);
        assertEquals(X_WINS, g.state);
        assertTrue(g.isOver());
        System.out.println(g.toString());
    }

    @Test
    public void testWinMainDiagonal2() throws UserException {
        Game g = new Game();
        assertEquals(X_MOVE, g.state);
        g.move(0, 0);
        assertEquals(O_MOVE, g.state);
        g.move(2, 0);
        assertEquals(X_MOVE, g.state);
        g.move(1, 0);
        assertEquals(O_MOVE, g.state);
        g.move(1, 1);
        assertEquals(X_MOVE, g.state);
        g.move(2, 1);
        assertEquals(O_MOVE, g.state);
        g.move(0, 2);
        assertEquals(O_WINS, g.state);
        assertTrue(g.isOver());
        System.out.println(g.toString());
    }

    @Test
    public void testDraw() throws UserException {
        Game g = new Game();
        assertEquals(X_MOVE, g.state);
        g.move(1, 1);
        assertEquals(O_MOVE, g.state);
        g.move(0, 1);
        assertEquals(X_MOVE, g.state);
        g.move(0, 0);
        assertEquals(O_MOVE, g.state);
        g.move(1, 0);
        assertEquals(X_MOVE, g.state);
        g.move(2, 1);
        assertEquals(O_MOVE, g.state);
        g.move(0, 2);
        assertEquals(X_MOVE, g.state);
        g.move(1, 2);
        assertEquals(O_MOVE, g.state);
        g.move(2, 2);
        assertEquals(X_MOVE, g.state);
        g.move(2, 0);
        assertEquals(DRAW, g.state);
        assertTrue(g.isOver());
        System.out.println(g.toString());
    }

    @Test
    public void testWinVerticalLine() throws UserException {
        Game g = new Game();
        // X wins with vertical line (column 0)
        g.move(0, 0); // X
        g.move(1, 0); // O
        g.move(0, 1); // X
        g.move(1, 1); // O
        g.move(0, 2); // X wins
        assertEquals(X_WINS, g.state);
        assertTrue(g.isOver());
    }

    @Test
    public void testWinHorizontalLine() throws UserException {
        Game g = new Game();
        // X wins with horizontal line (row 0)
        g.move(0, 0); // X
        g.move(0, 1); // O
        g.move(1, 0); // X
        g.move(1, 1); // O
        g.move(2, 0); // X wins
        assertEquals(X_WINS, g.state);
        assertTrue(g.isOver());
    }

    @Test(expected = UserException.class)
    public void testMoveOutOfBoundsX() throws UserException {
        Game g = new Game();
        g.move(3, 0); // Out of bounds
    }

    @Test(expected = UserException.class)
    public void testMoveOutOfBoundsY() throws UserException {
        Game g = new Game();
        g.move(0, 3); // Out of bounds
    }

    @Test(expected = UserException.class)
    public void testMoveNegativeX() throws UserException {
        Game g = new Game();
        g.move(-1, 0); // Negative x
    }

    @Test(expected = UserException.class)
    public void testMoveNegativeY() throws UserException {
        Game g = new Game();
        g.move(0, -1); // Negative y
    }

    @Test(expected = UserException.class)
    public void testMoveOccupiedCell() throws UserException {
        Game g = new Game();
        g.move(0, 0); // X
        g.move(0, 0); // O tries same cell - should throw
    }

    @Test(expected = UserException.class)
    public void testMoveAfterGameOver() throws UserException {
        Game g = new Game();
        // Create winning condition
        g.move(0, 0); // X
        g.move(1, 0); // O
        g.move(0, 1); // X
        g.move(1, 1); // O
        g.move(0, 2); // X wins
        // Try to move after game is over
        g.move(2, 2);
    }

    @Test
    public void testMoveWithMoveObject() throws UserException {
        Game g = new Game();
        Move m = new Move(0, 0);
        g.move(m);
        assertEquals(X, g.field[0][0].getCell());
    }

    @Test
    public void testGameUpdateListener() throws UserException {
        Game g = new Game();
        final Game.State[] lastState = {null};
        GameUpdateListener listener = new GameUpdateListener() {
            @Override
            public void update(Game.State state) {
                lastState[0] = state;
            }
        };
        g.listeners.add(listener);
        g.move(0, 0);
        assertEquals(O_MOVE, lastState[0]);
    }

    @Test
    public void testStateEnum() {
        assertEquals("Ход крестиков", X_MOVE.toString());
        assertEquals("Ход ноликов", O_MOVE.toString());
        assertEquals("Крестики выиграли", X_WINS.toString());
        assertEquals("Нолики выиграли", O_WINS.toString());
        assertEquals("Ничья", DRAW.toString());
    }

    @Test
    public void testToString() throws UserException {
        Game g = new Game();
        String str = g.toString();
        assertTrue(str.contains("_"));
        g.move(0, 0);
        str = g.toString();
        assertTrue(str.contains("X"));
    }

    @Test(expected = RuntimeException.class)
    public void testWinWithEmptyCell() {
        Game g = new Game();
        // Manually call updateGameState with EMPTY which should never happen
        g.updateGameState(Cell.EMPTY);
    }
}
