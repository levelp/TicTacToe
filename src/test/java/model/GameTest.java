package model;

import org.junit.Test;

import static model.Cell.O;
import static model.Cell.X;
import static model.Game.State.*;
import static org.junit.Assert.assertEquals;

/**
 * Процесс игры
 */
public class GameTest {

    @Test
    public void testSimple() throws UserException {
        Game game = new Game();
        assertEquals("По-умолчанию игра 3x3", 3, game.size);
        assertEquals(X_MOVE, game.state);

        // Ходим в верхний левый угол
        game.move(0, 0);
        assertEquals(X, game.field[0][0].getCell());
        assertEquals("Теперь ход ноликов", O_MOVE, game.state);
    }

    @Test
    public void testWinX() throws UserException {
        Game game = new Game();
        assertEquals(X_MOVE, game.state);
        game.field[0] = new CellState[]{new CellState(X), new CellState(X), new CellState(X)};
        assertEquals(X_MOVE, game.state);
        game.updateGameState(X);
        assertEquals(X_WINS, game.state);
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
        System.out.println(g.toString());
    }
}
