package mouse.hoi.parser.handler;

import mouse.hoi.parser.property.input.Property;

import java.util.List;

public interface AnnotationHandler {
    void handle(Object model, List<Property> propertyList);
}
