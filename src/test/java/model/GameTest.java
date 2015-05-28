package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Процесс игры
 */
public class GameTest {

    @Test
    public void testSimple() throws UserException {
        Game game = new Game();
        assertEquals("По-умолчанию игра 3x3", 3, game.size);
        assertEquals(Game.State.X_MOVE, game.state);

        // Ходим в верхний левый угол
        game.move(0, 0);
        assertEquals(Cell.X, game.field[0][0]);
    }
}
