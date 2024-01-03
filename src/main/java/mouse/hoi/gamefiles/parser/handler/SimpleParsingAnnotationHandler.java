package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Simple;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
@Service
public class SimpleParsingAnnotationHandler implements ParsingAnnotationHandler {
    private final ParseHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;

    private ParsingAnnotationHandler next = null;
    @Autowired
    public SimpleParsingAnnotationHandler(ParseHelper fieldHelper, AnnotationHandlerHelper annotationHandlerHelper) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
    }

    @Override
    public void handle(Object model, List<Property> propertyList, List<Property> unusedProperties) {
        List<Field> simpleFields = fieldHelper.getFieldsWithAnnotation(model, Simple.class);
        List<Property> simpleProperties = getSimpleAnnotations(propertyList);
        unusedProperties.removeAll(simpleProperties);
        annotationHandlerHelper.initialize(model, simpleFields, simpleProperties);
        next.handle(model, propertyList, unusedProperties);
    }

    @Override
    public void setNext(ParsingAnnotationHandler parsingAnnotationHandler) {
        next = parsingAnnotationHandler;
    }

    private List<Property> getSimpleAnnotations(List<Property> propertyList) {
        return propertyList.stream()
                .filter(p -> p.type()==PropertyType.SIMPLE)
                .toList();
    }
}
