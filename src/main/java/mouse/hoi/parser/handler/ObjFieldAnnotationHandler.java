package mouse.hoi.parser.handler;

import mouse.hoi.parser.FieldHelper;
import mouse.hoi.parser.annotation.ObjField;
import mouse.hoi.parser.property.Property;
import mouse.hoi.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class ObjFieldAnnotationHandler implements AnnotationHandler{

    private final FieldHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;
    @Autowired
    public ObjFieldAnnotationHandler(FieldHelper fieldHelper, AnnotationHandlerHelper annotationHandlerHelper) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
    }

    @Override
    public void handle(Object model, List<Property> propertyList) {
        List<Field> objFields = fieldHelper.getFieldsWithAnnotation(model, ObjField.class);
        HashMap<String, List<Property>> propertyHashMap = splitByKeys(propertyList);
        for (String key : propertyHashMap.keySet()) {
            List<Field> fields = getFieldsByKey(objFields, key);
            List<Property> properties = propertyHashMap.get(key);
            annotationHandlerHelper.initialize(model, fields, properties);
        }
    }

    private List<Field> getFieldsByKey(List<Field> fields, String key) {
        return fields.stream().filter(field -> key.equals(field.getAnnotation(ObjField.class).text())).toList();
    }

    private HashMap<String, List<Property>> splitByKeys(List<Property> propertyList) {
        HashMap<String, List<Property>> map = new HashMap<>();
        for (Property property : propertyList) {
            if (property.type()== PropertyType.SIMPLE)
                continue;
            String key = property.getKey();
            List<Property> properties = map.computeIfAbsent(key, j -> new ArrayList<>());
            properties.add(property);
        }
        return map;
    }
}
