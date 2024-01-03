package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.parser.property.Property;

import java.util.List;

public interface ParsingAnnotationHandler {
    void handle(Object model, List<Property> allProperties, List<Property> unusedProperties);
    void setNext(ParsingAnnotationHandler parsingAnnotationHandler);
}
