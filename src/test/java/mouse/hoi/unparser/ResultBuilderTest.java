package mouse.hoi.unparser;

import mouse.hoi.gamefiles.unparser.StringResultBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultBuilderTest {
    @Test
    void testBlocks() {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        String result = resultBuilder.append("A").openBlock().newline().append("b").newline().closeBlock().get();
        assertEquals("A = {\n\tb\n}\n", result);
    }

    @Test
    void testSpaces() {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        String result = resultBuilder.append("A").space().append("B").space().append("C").get();
        assertEquals("A B C", result);
    }

    @Test
    void testNewlines() {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        String result = resultBuilder.append("A").newline().append("B").space().append("C").get();
        assertEquals("A\nB C", result);
    }

    @Test
    void testEquality() {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        String result = resultBuilder.append("A").equality().append("B").
                space().append("C").equality().append("D").get();
        assertEquals("A = B C = D", result);
    }
}