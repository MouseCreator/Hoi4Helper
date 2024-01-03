package mouse.hoi.gamefiles.parser.collection;

import mouse.hoi.gamefiles.parser.property.Property;

import java.lang.reflect.Field;

public interface CollectionsParser {
    boolean isCollectionField(Field field);
    void pushToField(Object model, Field field, Property property);
}
