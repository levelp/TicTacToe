package view.console;

import model.Game;
import model.Move;
import model.UserException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Tests for ConsoleView class
 */
public class ConsoleViewTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private Game game;

    @Before
    public void setUp() {
        // Redirect System.out to capture console output
        System.setOut(new PrintStream(outContent));
        game = new Game();
    }

    @After
    public void tearDown() {
        // Restore original System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testConsoleViewCreation() {
        ConsoleView view = new ConsoleView(game);
        assertNotNull("ConsoleView should be created", view);

        String output = outContent.toString();
        assertTrue("Should display game title",
                   output.contains(Game.GAME_TITLE));
        assertTrue("Should display X_MOVE state",
                   output.contains("X_MOVE"));
    }

    @Test
    public void testRenderAfterMove() throws UserException {
        ConsoleView view = new ConsoleView(game);
        outContent.reset(); // Clear initial output

        // Make a move
        game.move(0, 0);

        String output = outContent.toString();
        assertTrue("Should display X mark after move",
                   output.contains("X"));
        assertTrue("Should display O_MOVE state after X's move",
                   output.contains("O_MOVE"));
    }

    @Test
    public void testInputMoveWithValidInput() {
        String input = "1\n2\n"; // столбец=1, строка=2
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleView view = new ConsoleView(game);
        outContent.reset();

        Move move = view.inputMove();

        assertNotNull("Move should not be null", move);
        assertEquals("X coordinate should be 1", 1, move.getX());
        assertEquals("Y coordinate should be 2", 2, move.getY());
    }

    @Test
    public void testInputMoveWithInvalidThenValidInput() {
        // First input: invalid (letter), then valid (1, 2)
        String input = "abc\n1\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleView view = new ConsoleView(game);
        outContent.reset();

        Move move = view.inputMove();

        assertNotNull("Move should not be null", move);
        assertEquals("X coordinate should be 1", 1, move.getX());
        assertEquals("Y coordinate should be 2", 2, move.getY());
    }

    @Test
    public void testReportError() {
        ConsoleView view = new ConsoleView(game);
        outContent.reset();

        UserException exception = new UserException("Test error message");
        view.reportError(exception);

        String output = outContent.toString();
        assertTrue("Should display error message",
                   output.contains("Test error message"));
        assertTrue("Should prompt to repeat move",
                   output.contains("Повторите ход"));
    }

    @Test
    public void testRenderDisplaysField() throws UserException {
        ConsoleView view = new ConsoleView(game);
        outContent.reset();

        // Make some moves
        game.move(0, 0); // X
        game.move(1, 0); // O
        game.move(0, 1); // X

        String output = outContent.toString();
        assertTrue("Should contain X marks", output.contains("X"));
        assertTrue("Should contain O marks", output.contains("O"));
        assertTrue("Should contain empty cells", output.contains("_"));
    }

    @Test
    public void testGameTitleConstant() {
        ConsoleView view = new ConsoleView(game);
        String output = outContent.toString();

        assertTrue("Should use Game.GAME_TITLE constant",
                   output.contains("Игра Крестики-нолики"));
    }
}
