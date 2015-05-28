package view;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Tests for GameView interface
 */
public class GameViewTest {

    @Test
    public void testGameViewIsInterface() {
        assertTrue("GameView should be an interface",
                   GameView.class.isInterface());
    }

    @Test
    public void testGameViewIsPublic() {
        assertTrue("GameView should be public",
                   Modifier.isPublic(GameView.class.getModifiers()));
    }

    @Test
    public void testGameViewHasInputMoveMethod() throws NoSuchMethodException {
        Method inputMove = GameView.class.getDeclaredMethod("inputMove");
        assertNotNull("GameView should have inputMove() method", inputMove);
        assertEquals("inputMove() should return Move",
                     model.Move.class, inputMove.getReturnType());
    }

    @Test
    public void testGameViewHasReportErrorMethod() throws NoSuchMethodException {
        Method reportError = GameView.class.getDeclaredMethod("reportError",
                                                               model.UserException.class);
        assertNotNull("GameView should have reportError() method", reportError);
        assertEquals("reportError() should return void",
                     void.class, reportError.getReturnType());
    }

    @Test
    public void testGameViewHasTwoMethods() {
        Method[] methods = GameView.class.getDeclaredMethods();
        assertEquals("GameView should have exactly 2 methods", 2, methods.length);
    }

    @Test
    public void testGameViewIsInViewPackage() {
        String packageName = GameView.class.getPackage().getName();
        assertEquals("GameView should be in view package", "view", packageName);
    }

    @Test
    public void testGameViewMethodsAreAbstract() {
        Method[] methods = GameView.class.getDeclaredMethods();
        for (Method method : methods) {
            assertTrue("All interface methods should be abstract: " + method.getName(),
                       Modifier.isAbstract(method.getModifiers()) ||
                       method.isDefault());
        }
    }
}
