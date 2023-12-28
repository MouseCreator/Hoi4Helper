package mouse.hoi.parser.handler;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.FieldHelper;
import mouse.hoi.parser.StringValueProcessor;
import mouse.hoi.parser.annotation.Ordered;
import mouse.hoi.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AnnotationHandlerHelper {
    private final FieldHelper fieldHelper;
    private final StringValueProcessor stringValueProcessor;
    @Autowired
    public AnnotationHandlerHelper(FieldHelper fieldHelper, StringValueProcessor stringValueProcessor) {
        this.fieldHelper = fieldHelper;
        this.stringValueProcessor = stringValueProcessor;
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

    private void initializeFieldWithProperty(Object model, Field field, Property property) {
        //If collection : push
        //Else set value to field
        //Format the value with @Accuracy and @UseQuotes annotations
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
