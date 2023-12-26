package mouse.hoi.parser.propertyfactory;

import mouse.hoi.parser.property.BlockProperty;
import mouse.hoi.parser.property.FieldValueProperty;
import mouse.hoi.parser.property.SimpleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyFactoryTest {

    private PropertyFactory propertyFactory;

    @BeforeEach
    void setUp() {
        propertyFactory = new PropertyFactory();
    }

    @Test
    void block() {
        BlockProperty blockProperty = propertyFactory.block()
                .withKey("Hello")
                .withValue("World")
                .with(List.of(new SimpleProperty("D"))).get();
        assertEquals("Hello", blockProperty.getKey());
        assertEquals("World", blockProperty.getValue());
        assertEquals(1, blockProperty.getChildren().size());

        BlockProperty blockProperty2 = propertyFactory.block().get("F", "V");
        assertEquals("F", blockProperty2.getKey());
        assertEquals("V", blockProperty2.getValue());
        assertEquals(0,  blockProperty2.getChildren().size());


        BlockProperty blockProperty3 = propertyFactory.block().get("Field");
        assertEquals("Field", blockProperty3.getKey());
        assertEquals("", blockProperty3.getValue());
        assertEquals(0,  blockProperty3.getChildren().size());
    }

    @Test
    void simple() {
        SimpleProperty value = propertyFactory.simple().get("Value");
        assertEquals("Value", value.getValue());
    }

    @Test
    void fieldValue() {
        FieldValueProperty property = propertyFactory.fieldValue().withKey("Key").withValue("Value").get();
        assertEquals("Key", property.getKey());
        assertEquals("Value", property.getValue());

        FieldValueProperty property2 = propertyFactory.fieldValue().get("A", "B");
        assertEquals("A", property2.getKey());
        assertEquals("B", property2.getValue());
    }


}