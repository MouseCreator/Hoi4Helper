package mouse.hoi.parser.handler;

import mouse.hoi.parser.FieldHelper;
import mouse.hoi.parser.annotation.CollectionKey;
import mouse.hoi.parser.collectiontype.CollectionType;
import mouse.hoi.parser.collectiontype.CollectionTypeManager;
import mouse.hoi.parser.property.Property;
import mouse.hoi.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class CollectionKeyAnnotationHandler implements AnnotationHandler{

    private final FieldHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;
    private final CollectionTypeManager collectionTypeManager;
    @Autowired
    public CollectionKeyAnnotationHandler(FieldHelper fieldHelper,
                                          AnnotationHandlerHelper annotationHandlerHelper,
                                          CollectionTypeManager collectionTypeManager) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
        this.collectionTypeManager = collectionTypeManager;
    }
    @Override
    public void handle(Object model, List<Property> propertyList) {
        List<Field> collectionKeyFields = fieldHelper.getFieldsWithAnnotation(model, CollectionKey.class);
        HashMap<CollectionType, List<Property>> collectionTypeMap = splitByCollectionTypes(propertyList);

        for (CollectionType type : collectionTypeMap.keySet()) {
            List<Field> fields = fieldsOfType(collectionKeyFields, type);
            List<Property> properties = collectionTypeMap.get(type);
            annotationHandlerHelper.initialize(model, fields, properties);
        }
    }

    private List<Field> fieldsOfType(List<Field> originFields, CollectionType type) {
        return originFields.stream()
                .filter(f -> type.equals(f.getAnnotation(CollectionKey.class).type()))
                .toList();
    }

    private HashMap<CollectionType, List<Property>> splitByCollectionTypes(List<Property> propertyList) {
        HashMap<CollectionType, List<Property>> map = new HashMap<>();
        for (Property property : propertyList) {
            if (property.type()== PropertyType.SIMPLE)
                continue;
            List<CollectionType> typesForKey = collectionTypeManager.findTypeForToken(property.getKey());
            for (CollectionType type : typesForKey) {
                List<Property> propertiesOfType = map.computeIfAbsent(type, t -> new ArrayList<>());
                propertiesOfType.add(property);
            }
        }
        return map;
    }
}
