package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for CellState class
 */
public class CellStateTest {

    @Test
    public void testCellStateConstructor() {
        CellState cs = new CellState(Cell.X);
        assertEquals(Cell.X, cs.getCell());
    }

    @Test
    public void testSetCell() {
        CellState cs = new CellState(Cell.EMPTY);
        assertEquals(Cell.EMPTY, cs.getCell());
        cs.setCell(Cell.X);
        assertEquals(Cell.X, cs.getCell());
    }

    @Test
    public void testSetCellNoChange() {
        CellState cs = new CellState(Cell.X);
        cs.setCell(Cell.X); // Setting same value
        assertEquals(Cell.X, cs.getCell());
    }

    @Test
    public void testCellChangedListener() {
        CellState cs = new CellState(Cell.EMPTY);
        final Cell[] lastCell = {null};

        CellChangedListener listener = new CellChangedListener() {
            @Override
            public void update(Cell newState) {
                lastCell[0] = newState;
            }
        };

        cs.addListener(listener);
        cs.setCell(Cell.X);
        assertEquals(Cell.X, lastCell[0]);
    }

    @Test
    public void testCellChangedListenerNotCalledOnSameValue() {
        CellState cs = new CellState(Cell.X);
        final int[] callCount = {0};

        CellChangedListener listener = new CellChangedListener() {
            @Override
            public void update(Cell newState) {
                callCount[0]++;
            }
        };

        cs.addListener(listener);
        cs.setCell(Cell.X); // Same value, listener should not be called
        assertEquals(0, callCount[0]);
    }

    @Test
    public void testMultipleListeners() {
        CellState cs = new CellState(Cell.EMPTY);
        final Cell[] lastCell1 = {null};
        final Cell[] lastCell2 = {null};

        CellChangedListener listener1 = new CellChangedListener() {
            @Override
            public void update(Cell newState) {
                lastCell1[0] = newState;
            }
        };

        CellChangedListener listener2 = new CellChangedListener() {
            @Override
            public void update(Cell newState) {
                lastCell2[0] = newState;
            }
        };

        cs.addListener(listener1);
        cs.addListener(listener2);
        cs.setCell(Cell.O);

        assertEquals(Cell.O, lastCell1[0]);
        assertEquals(Cell.O, lastCell2[0]);
    }

    @Test
    public void testToString() {
        CellState cs = new CellState(Cell.X);
        assertEquals("X", cs.toString());

        cs.setCell(Cell.O);
        assertEquals("O", cs.toString());

        cs.setCell(Cell.EMPTY);
        assertEquals("_", cs.toString());
    }
}
