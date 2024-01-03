package mouse.hoi.gamefiles.parser.collection;

import mouse.hoi.gamefiles.parser.property.Property;

import java.lang.reflect.Field;

public interface ParsingCollectionHandler {
    boolean canHandle(Field field);
    void push(Object model, Field field, Property property);
}
