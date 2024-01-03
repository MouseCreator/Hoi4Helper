package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class ObjFieldParsingAnnotationHandler implements ParsingAnnotationHandler {

    private final ParseHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;

    private ParsingAnnotationHandler next = null;
    @Autowired
    public ObjFieldParsingAnnotationHandler(ParseHelper fieldHelper, AnnotationHandlerHelper annotationHandlerHelper) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
    }

    @Override
    public void handle(Object model, List<Property> propertyList, List<Property> unusedProperties) {
        List<Field> objFields = fieldHelper.getFieldsWithAnnotation(model, ObjField.class);
        HashMap<String, List<Property>> propertyHashMap = splitByKeys(propertyList, objFields);
        for (String key : propertyHashMap.keySet()) {
            List<Field> fields = getFieldsByKey(objFields, key);
            List<Property> properties = propertyHashMap.get(key);
            unusedProperties.removeAll(properties);
            annotationHandlerHelper.initialize(model, fields, properties);
        }
        next.handle(model, propertyList, unusedProperties);
    }

    @Override
    public void setNext(ParsingAnnotationHandler parsingAnnotationHandler) {
        next = parsingAnnotationHandler;
    }

    private List<Field> getFieldsByKey(List<Field> fields, String key) {
        return fields.stream().filter(field -> key.equals(field.getAnnotation(ObjField.class).text())).toList();
    }

    private HashMap<String, List<Property>> splitByKeys(List<Property> propertyList, List<Field> objFields) {
        HashMap<String, List<Property>> map = new HashMap<>();
        List<String> keys = new ArrayList<>();
        for (Field field : objFields) {
            String text = field.getAnnotation(ObjField.class).text();
            keys.add(text);
        }
        for (String key : keys) {
            List<Property> propertiesByKey = propertyList.stream().filter(property -> {
                if (property.type() == PropertyType.SIMPLE)
                    return false;
                return property.getKey().equals(key);
            }).toList();
            map.put(key, propertiesByKey);
        }
        return map;
    }
}
