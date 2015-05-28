package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for UserException class
 */
public class UserExceptionTest {

    @Test
    public void testUserExceptionMessage() {
        UserException ex = new UserException("Test message");
        assertEquals("Test message", ex.getMessage());
    }

    @Test
    public void testUserExceptionThrow() {
        try {
            throw new UserException("Error occurred");
        } catch (UserException e) {
            assertEquals("Error occurred", e.getMessage());
        }
    }

    @Test(expected = UserException.class)
    public void testUserExceptionIsThrowable() throws UserException {
        throw new UserException("Should be throwable");
    }

    @Test
    public void testUserExceptionIsException() {
        UserException ex = new UserException("Test");
        assertTrue(ex instanceof Exception);
    }
}
