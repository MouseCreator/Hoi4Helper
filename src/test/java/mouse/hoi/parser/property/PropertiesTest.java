package mouse.hoi.parser.property;

import mouse.hoi.gamefiles.parser.property.BlockProperty;
import mouse.hoi.gamefiles.parser.property.FieldValueProperty;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.parser.property.SimpleProperty;
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
        Property block = BlockProperty.withKey("key");
        block.getChildren().add(new SimpleProperty("Value"));
        block.getChildren().add(new SimpleProperty("Value2"));
        assertTrue(block.isBlock());
        assertEquals("", block.getValue());
        assertEquals(2, block.getChildren().size());
    }
}