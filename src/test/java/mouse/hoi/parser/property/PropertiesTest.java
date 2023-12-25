package mouse.hoi.parser.property;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesTest {

    @Test
    void TestSimple() {
        Property simpleProperty = new SimpleProperty("Value");
        assertThrows(UnsupportedOperationException.class, simpleProperty::getKey);
        assertThrows(UnsupportedOperationException.class, simpleProperty::getChildren);
        assertFalse(simpleProperty.isBlock());
        assertEquals("Value", simpleProperty.getValue());
    }

    @Test
    void TestFieldValue() {
        Property simpleProperty = new FieldValueProperty("Key", "Value");
        assertThrows(UnsupportedOperationException.class, simpleProperty::getChildren);
        assertFalse(simpleProperty.isBlock());
        assertEquals("Value", simpleProperty.getValue());
        assertEquals("Key", simpleProperty.getKey());
    }

    @Test
    void TestBlock() {
        Property simpleProperty = BlockProperty.withKey("Key");
        simpleProperty.getChildren().add(new SimpleProperty("Value"));
        simpleProperty.getChildren().add(new SimpleProperty("Value2"));
        assertTrue(simpleProperty.isBlock());
        assertEquals("", simpleProperty.getValue());
        assertEquals(2, simpleProperty.getChildren().size());
    }
}