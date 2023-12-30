package mouse.hoi.parser.propertyfactory;

import mouse.hoi.parser.property.input.BlockProperty;
import mouse.hoi.parser.property.input.FieldValueProperty;
import mouse.hoi.parser.property.input.Property;
import mouse.hoi.parser.property.input.SimpleProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyFactory {

    public BlockPropertyCreator block() {
        return new BlockPropertyCreator();
    }

    public SimplePropertyCreator simple() {
        return new SimplePropertyCreator();
    }
    public FieldValuePropertyCreator fieldValue() {
        return new FieldValuePropertyCreator();
    }
    public static class BlockPropertyCreator {
        private final BlockProperty blockProperty = new BlockProperty();
        public BlockProperty get() {
            return blockProperty;
        }
        public BlockProperty get(String key) {
            blockProperty.setKey(key);
            return blockProperty;
        }
        public BlockProperty get(String key, String value) {
            blockProperty.setKey(key);
            blockProperty.setValue(value);
            return blockProperty;
        }
        public BlockPropertyCreator withKey(String key) {
            blockProperty.setKey(key);
            return this;
        }
        public BlockPropertyCreator withValue(String value) {
            blockProperty.setValue(value);
            return this;
        }

        public BlockPropertyCreator with(String key, String value) {
            blockProperty.setKey(key);
            blockProperty.setValue(value);
            return this;
        }

        public BlockPropertyCreator with(List<Property> children) {
            for (Property ch : children) {
                blockProperty.addChild(ch);
            }
            return this;
        }
    }
    public static class SimplePropertyCreator {
        private final SimpleProperty simpleProperty = new SimpleProperty();
        public SimpleProperty get(String value) {
            simpleProperty.setValue(value);
            return simpleProperty;
        }
    }
    public static class FieldValuePropertyCreator {
        private final FieldValueProperty fieldValueProperty = new FieldValueProperty();
        public FieldValueProperty get() {
            return fieldValueProperty;
        }
        public FieldValueProperty get(String key) {
            fieldValueProperty.setKey(key);
            return fieldValueProperty;
        }
        public FieldValueProperty get(String key, String value) {
            fieldValueProperty.setKey(key);
            fieldValueProperty.setValue(value);
            return fieldValueProperty;
        }
        public FieldValuePropertyCreator withKey(String key) {
            fieldValueProperty.setKey(key);
            return this;
        }
        public FieldValuePropertyCreator withValue(String value) {
            fieldValueProperty.setValue(value);
            return this;
        }
    }
}
