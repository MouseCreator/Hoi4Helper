package mouse.hoi.parser.property;

import mouse.hoi.parser.property.input.BlockProperty;
import mouse.hoi.parser.property.input.FieldValueProperty;
import mouse.hoi.parser.property.input.Property;
import mouse.hoi.parser.property.input.SimpleProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesTest {

    @Test
    void TestSimple() {
        Property simpleProperty = new SimpleProperty("Value");
        assertEquals("", simpleProperty.getKey());
        assertThrows(UnsupportedOperationException.class, simpleProperty::getChildren);
        assertFalse(simpleProperty.isBlock());
        assertEquals("Value", simpleProperty.getValue());
    }

    @Test
    void TestFieldValue() {
        Property fvProperty = new FieldValueProperty("Key", "Value");
        assertThrows(UnsupportedOperationException.class, fvProperty::getChildren);
        assertFalse(fvProperty.isBlock());
        assertEquals("Value", fvProperty.getValue());
        assertEquals("Key", fvProperty.getKey());
    }

    @Test
    void TestBlock() {
        Property block = new BlockProperty("Key");
        block.getChildren().add(new SimpleProperty("Value"));
        block.getChildren().add(new SimpleProperty("Value2"));
        assertTrue(block.isBlock());
        assertEquals("", block.getValue());
        assertEquals(2, block.getChildren().size());
    }
}