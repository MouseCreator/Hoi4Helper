package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.RequireField;
import mouse.hoi.parser.handler.AnnotationHandler;
import mouse.hoi.parser.property.Property;

import java.lang.reflect.Field;
import java.util.List;

public class PropertyToModelParser {

    private ParseHelper parseHelper;
    private ParsedModelCreator parsedModelCreator;
    private List<AnnotationHandler> annotationHandlers;
    public Object getModel(Class<?> tClass, Property property) {
        Object model = parsedModelCreator.lookup(tClass);
        parseProperty(property, model);
        validateModel(model);
        return model;
    }

    private void parseProperty(Property property, Object model) {

    }

    private void validateModel(Object model) {
        List<Field> requiredFields = parseHelper.getFieldsWithAnnotation(model, RequireField.class);
        for (Field field : requiredFields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(model);
            } catch (IllegalAccessException e) {
                throw new PropertyParseException("Unable to get field value " + field.getName(), e);
            }
            if (value == null) {
                throw new PropertyParseException("Required field" + field.getName() + " is not initialized for " + parseHelper.toClass(model));
            }
        }
    }
}
