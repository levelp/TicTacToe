package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for Move class
 */
public class MoveTest {

    @Test
    public void testMoveConstructor() {
        Move m = new Move(1, 2);
        assertEquals(1, m.x);
        assertEquals(2, m.y);
    }

    @Test
    public void testMoveToString() {
        Move m = new Move(3, 4);
        assertEquals("(3; 4)", m.toString());
    }

    @Test
    public void testMoveWithZeroCoordinates() {
        Move m = new Move(0, 0);
        assertEquals(0, m.x);
        assertEquals(0, m.y);
        assertEquals("(0; 0)", m.toString());
    }

    @Test
    public void testMoveWithNegativeCoordinates() {
        Move m = new Move(-1, -2);
        assertEquals(-1, m.x);
        assertEquals(-2, m.y);
        assertEquals("(-1; -2)", m.toString());
    }
}
