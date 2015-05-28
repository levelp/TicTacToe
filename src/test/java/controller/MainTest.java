package controller;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Tests for Main controller class
 * Note: Full integration testing of main() would require mocking System.in/out
 * and handling the game loop, which is better suited for manual/integration tests
 */
public class MainTest {

    @Test
    public void testMainClassExists() {
        assertNotNull("Main class should exist", Main.class);
    }

    @Test
    public void testMainMethodExists() throws NoSuchMethodException {
        Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
        assertNotNull("main() method should exist", mainMethod);
    }

    @Test
    public void testMainMethodIsPublic() throws NoSuchMethodException {
        Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
        assertTrue("main() should be public",
                   Modifier.isPublic(mainMethod.getModifiers()));
    }

    @Test
    public void testMainMethodIsStatic() throws NoSuchMethodException {
        Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
        assertTrue("main() should be static",
                   Modifier.isStatic(mainMethod.getModifiers()));
    }

    @Test
    public void testMainMethodReturnsVoid() throws NoSuchMethodException {
        Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
        assertEquals("main() should return void",
                     void.class, mainMethod.getReturnType());
    }

    @Test
    public void testMainMethodAcceptsStringArray() throws NoSuchMethodException {
        Method mainMethod = Main.class.getDeclaredMethod("main", String[].class);
        Class<?>[] paramTypes = mainMethod.getParameterTypes();

        assertEquals("main() should accept one parameter", 1, paramTypes.length);
        assertEquals("main() parameter should be String[]",
                     String[].class, paramTypes[0]);
    }

    @Test
    public void testMainClassIsInControllerPackage() {
        String packageName = Main.class.getPackage().getName();
        assertEquals("Main should be in controller package",
                     "controller", packageName);
    }
}
