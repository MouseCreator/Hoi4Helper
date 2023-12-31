package mouse.hoi.gamefiles.unparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultBuilderTest {
    @Test
    void testBlocks() {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        String result = resultBuilder.append("A").equality().openBlock().newline().append("b").newline().closeBlock().newline().get();
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

    @Test
    void testSafeSpace() {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        String result = resultBuilder.space().spaceSafe().space().get();
        assertEquals("  ", result);
    }
}