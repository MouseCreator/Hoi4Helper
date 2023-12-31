package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.gamefiles.parser.ParseHelper;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.parser.PropertyToModelParser;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.common.annotation.Ordered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AnnotationHandlerHelper {
    private final PrimitivesParser primitivesParser;
    private final ParseHelper parseHelper;
    private PropertyToModelParser propertyToModelParser;
    @Autowired
    public AnnotationHandlerHelper(
                                   PrimitivesParser primitivesParser, ParseHelper parseHelper) {
        this.primitivesParser = primitivesParser;
        this.parseHelper = parseHelper;
    }

    public void setPropertyToModelParser(PropertyToModelParser propertyToModelParser) {
        this.propertyToModelParser = propertyToModelParser;
    }

    public void initialize(Object model, List<Field> fieldList, List<Property> propertyList) {
        if (fieldList.isEmpty() && !propertyList.isEmpty()) {
            throw new PropertyParseException("Unexpected tokens while parsing " + model.getClass().getSimpleName() + ": " + propertyList);
        }
        if (fieldList.isEmpty()) {
            return;
        }
        handleOrderedFields(model, fieldList, propertyList);
        handleNotOrderedFields(model, fieldList, propertyList);
    }

    private void handleNotOrderedFields(Object model, List<Field> fieldList, List<Property> propertyList) {
        List<Field> notOrderedFields = fieldList.stream()
                .filter(f -> !f.isAnnotationPresent(Ordered.class)).toList();
        for (Field field : notOrderedFields) {
            for (Property property : propertyList) {
                initializeFieldWithProperty(model, field, property);
            }
        }
    }

    private void handleOrderedFields(Object model, List<Field> fieldList, List<Property> propertyList) {
        HashMap<Integer, List<Field>> ordered = getWithOrderAnnotation(fieldList);
        for (int i = 0; i < propertyList.size(); i++) {
            Property property = propertyList.get(i);
            List<Field> fieldOrdered = ordered.get(i);
            if (fieldOrdered == null) {
                continue;
            }
            for (Field orderedField : fieldOrdered) {
                initializeFieldWithProperty(model, orderedField, property);
            }
        }
    }

    public void initializeFieldWithProperty(Object model, Field field, Property property) {
        List<Annotation> annotations = parseHelper.getAnnotations(field);
        if (parseHelper.isCollectionField(field)) {
            Class<?> generic = parseHelper.getGeneric(field);
            Object value = getPropertyValue(generic, annotations, property);
            parseHelper.push(model, field, value);
        } else {
            Object value = getPropertyValue(field.getType(), annotations, property);
            parseHelper.setField(model, field, value);
        }
    }

    private Object getPropertyValue(Class<?> clazz, List<Annotation> annotations, Property property) {
        if (primitivesParser.isPrimitiveClass(clazz)) {
            return primitivesParser.parsePrimitiveType(clazz, annotations, property);
        }
        return propertyToModelParser.getModel(clazz, property);
    }



    private HashMap<Integer, List<Field>> getWithOrderAnnotation(List<Field> fieldList) {
        HashMap<Integer, List<Field>> fieldMap = new HashMap<>();

        for (Field field : fieldList) {
            Ordered orderedAnnotation = field.getAnnotation(Ordered.class);
            if (orderedAnnotation != null) {
                int order = orderedAnnotation.num();
                List<Field> fields = fieldMap.computeIfAbsent(order, k -> new ArrayList<>());
                fields.add(field);
            }
        }

        return fieldMap;
    }
}
