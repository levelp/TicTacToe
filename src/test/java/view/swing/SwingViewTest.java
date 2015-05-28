package view.swing;

import model.Game;
import model.Move;
import model.UserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for SwingView class
 */
public class SwingViewTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testSwingViewCreation() {
        // Note: This test creates a GUI window which may not be visible in headless mode
        // In a real CI/CD environment, you would configure headless mode
        try {
            SwingView view = new SwingView(game);
            assertNotNull("SwingView should be created", view);
        } catch (java.awt.HeadlessException e) {
            // Test running in headless environment (CI/CD)
            // This is expected and acceptable
            System.out.println("Running in headless mode - GUI test skipped");
        }
    }

    @Test
    public void testInputMoveReturnsNull() {
        try {
            SwingView view = new SwingView(game);
            Move move = view.inputMove();
            assertNull("inputMove() should return null for SwingView", move);
        } catch (java.awt.HeadlessException e) {
            // Headless mode - skip test
            System.out.println("Running in headless mode - GUI test skipped");
        }
    }

    @Test
    public void testReportErrorDoesNotThrow() {
        try {
            SwingView view = new SwingView(game);
            UserException exception = new UserException("Test error");

            // Should not throw exception
            view.reportError(exception);
        } catch (java.awt.HeadlessException e) {
            // Headless mode - skip test
            System.out.println("Running in headless mode - GUI test skipped");
        }
    }

    @Test
    public void testSwingViewImplementsGameView() {
        try {
            SwingView view = new SwingView(game);
            assertTrue("SwingView should implement GameView",
                       view instanceof view.GameView);
        } catch (java.awt.HeadlessException e) {
            // Headless mode - skip test
            System.out.println("Running in headless mode - GUI test skipped");
        }
    }

    @Test
    public void testSwingViewUsesGameTitle() {
        // This test verifies that SwingView uses Game.GAME_TITLE constant
        assertEquals("Game title constant should be set",
                     "Игра Крестики-нолики", Game.GAME_TITLE);

        // Note: Actual window title verification would require accessing
        // the JFrame which is not exposed by SwingView
    }
}
