package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for Cell enum
 */
public class CellTest {

    @Test
    public void testCellEnum() {
        assertEquals("X", Cell.X.toString());
        assertEquals("O", Cell.O.toString());
        assertEquals("_", Cell.EMPTY.toString());
    }

    @Test
    public void testCellValues() {
        Cell[] cells = Cell.values();
        assertEquals(3, cells.length);
        assertEquals(Cell.X, cells[0]);
        assertEquals(Cell.O, cells[1]);
        assertEquals(Cell.EMPTY, cells[2]);
    }

    @Test
    public void testCellValueOf() {
        assertEquals(Cell.X, Cell.valueOf("X"));
        assertEquals(Cell.O, Cell.valueOf("O"));
        assertEquals(Cell.EMPTY, Cell.valueOf("EMPTY"));
    }
}
