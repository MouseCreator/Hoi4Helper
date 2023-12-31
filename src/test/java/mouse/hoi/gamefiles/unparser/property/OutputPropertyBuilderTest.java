package mouse.hoi.gamefiles.unparser.property;

import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.common.style.PrintStyle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutputPropertyBuilderTest {

    private OutputPropertyBuilder defaultInit() {
        return new OutputPropertyBuilder()
                .withKey("Key")
        .withValue("Value")
        .withChildren(new OutputPropertyImpl())
        .withChildren(List.of(new OutputPropertyImpl(), new OutputPropertyImpl()));
    }


    @Test
    void defaultTest() {
        OutputPropertyBuilder builder = defaultInit();
        OutputProperty block = builder.block();
        assertEquals(PrintStyle.COMPLEX, block.getStyle());
        assertEquals("Key", block.getKey());
        assertEquals("Value", block.getValue());
        assertEquals(3, block.getChildren().size());
    }
    @Test
    void simple() {
        OutputProperty simple = defaultInit().simple();
        assertEquals(PropertyType.SIMPLE, simple.getType());
    }

    @Test
    void fieldValue() {
        OutputProperty fv = defaultInit().fieldValue();
        assertEquals(PropertyType.FIELD_VALUE, fv.getType());
    }

    @Test
    void block() {
        OutputProperty block = defaultInit().block();
        assertEquals(PropertyType.BLOCK, block.getType());
    }

    @Test
    void style() {
        OutputPropertyBuilder builder = defaultInit();
        OutputProperty block = builder.withStyle(PrintStyle.SIMPLE).block();
        assertEquals(PrintStyle.SIMPLE, block.getStyle());
    }
}