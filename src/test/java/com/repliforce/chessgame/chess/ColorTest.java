package com.repliforce.chessgame.chess;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testEnumValues() {
        assertEquals(4, Color.values().length, "Enum Color should have 4 values");
        assertEquals(Color.BLUE, Color.valueOf("BLUE"));
        assertEquals(Color.RED, Color.valueOf("RED"));
        assertEquals(Color.WHITE, Color.valueOf("WHITE"));
        assertEquals(Color.BLACK, Color.valueOf("BLACK"));
    }

    @Test
    void testEnumOrder() {
        Color[] expectedOrder = {Color.BLUE, Color.RED, Color.WHITE, Color.BLACK};
        assertArrayEquals(expectedOrder, Color.values(), "Enum Color should have values in the correct order");
    }
}