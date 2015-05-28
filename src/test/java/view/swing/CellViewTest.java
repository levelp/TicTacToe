package view.swing;

import model.Cell;
import model.Game;
import model.UserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for CellView class
 */
public class CellViewTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testCellViewCreation() {
        CellView cellView = new CellView(0, 0, game);

        assertNotNull("CellView should be created", cellView);
        assertEquals("X coordinate should be 0", 0, cellView.x);
        assertEquals("Y coordinate should be 0", 0, cellView.y);
        assertEquals("Initial text should be EMPTY",
                     Cell.EMPTY.toString(), cellView.getText());
        assertTrue("Button should be enabled initially", cellView.isEnabled());
    }

    @Test
    public void testCellViewCoordinates() {
        CellView cellView = new CellView(2, 1, game);

        assertEquals("X coordinate should be 2", 2, cellView.x);
        assertEquals("Y coordinate should be 1", 1, cellView.y);
    }

    @Test
    public void testCellViewUpdatesOnStateChange() {
        CellView cellView = new CellView(0, 0, game);

        // Simulate state change via listener
        game.field[0][0].setCell(Cell.X);

        assertEquals("Text should update to X",
                     Cell.X.toString(), cellView.getText());
        assertFalse("Button should be disabled after cell is set",
                    cellView.isEnabled());
    }

    @Test
    public void testCellViewDisablesOnGameOver() throws UserException {
        CellView cellView = new CellView(2, 2, game);
        assertTrue("Button should be enabled initially", cellView.isEnabled());

        // Create a win scenario: X wins horizontally in row 0
        game.move(0, 0); // X
        game.move(1, 0); // O
        game.move(0, 1); // X
        game.move(1, 1); // O
        game.move(0, 2); // X wins

        assertFalse("Button should be disabled when game is over",
                    cellView.isEnabled());
    }

    @Test
    public void testMultipleCellViewsInGame() {
        CellView cell00 = new CellView(0, 0, game);
        CellView cell01 = new CellView(0, 1, game);
        CellView cell10 = new CellView(1, 0, game);

        assertNotNull("Cell (0,0) should be created", cell00);
        assertNotNull("Cell (0,1) should be created", cell01);
        assertNotNull("Cell (1,0) should be created", cell10);

        assertEquals(0, cell00.x);
        assertEquals(0, cell00.y);
        assertEquals(0, cell01.x);
        assertEquals(1, cell01.y);
        assertEquals(1, cell10.x);
        assertEquals(0, cell10.y);
    }

    @Test
    public void testCellViewListenerRegistration() {
        int initialListenerCount = game.listeners.size();
        CellView cellView = new CellView(0, 0, game);

        // CellView should register a game listener
        assertTrue("Game listener should be added",
                   game.listeners.size() > initialListenerCount);
    }

    @Test
    public void testCellViewTextSyncWithModel() {
        CellView cellView = new CellView(1, 1, game);

        // Initially should match model
        assertEquals("Text should match model state",
                     game.field[1][1].toString(), cellView.getText());

        // Change model state
        game.field[1][1].setCell(Cell.O);

        // Text should update
        assertEquals("Text should update when model changes",
                     Cell.O.toString(), cellView.getText());
    }

    @Test
    public void testCellViewInCustomSizeGame() {
        Game largeGame = new Game(5);
        CellView cellView = new CellView(4, 4, largeGame);

        assertNotNull("CellView should work with custom board size", cellView);
        assertEquals(4, cellView.x);
        assertEquals(4, cellView.y);
        assertEquals(Cell.EMPTY.toString(), cellView.getText());
    }
}
