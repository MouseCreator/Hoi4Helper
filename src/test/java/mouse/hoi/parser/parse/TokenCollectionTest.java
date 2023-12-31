package mouse.hoi.parser.parse;

import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenCollectionTest {
    private TokenCollection collection;

    @BeforeEach
    void setUp() {
        collection = new TokenCollection();
    }
    @Test
    void isEmpty() {
        assertTrue(collection.isEmpty());
        collection.addAll(List.of("A","=","B"));
        assertFalse(collection.isEmpty());
        collection.skip(3);
        assertTrue(collection.isEmpty());
    }

    @Test
    void hasNext() {
        assertFalse(collection.hasNext());
        collection.addAll(List.of("A","=","B"));
        assertTrue(collection.hasNext());
        collection.skip(3);
        assertFalse(collection.hasNext());
    }

    @Test
    void skip() {
        collection.addAll(List.of("A","=","B"));
        collection.skip();
        assertTrue(collection.hasNext());
        collection.skip(2);
        assertTrue(collection.isEmpty());
    }

    @Test
    void nextToken() {
        collection.addAll(List.of("A","=","B"));
        assertEquals("A",collection.nextToken());
        assertEquals("=",collection.nextToken());
        assertEquals("B",collection.nextToken());
    }
}