package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.parser.property.Property;

import java.util.List;

public interface ParsingAnnotationManager {
    void parseWithAnnotations(Object model, List<Property> propertyList);
}
