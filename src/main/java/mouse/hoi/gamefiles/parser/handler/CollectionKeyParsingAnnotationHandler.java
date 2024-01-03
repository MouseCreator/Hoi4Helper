package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.common.collectiontype.CollectionType;
import mouse.hoi.gamefiles.common.collectiontype.CollectionTypeManager;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.CollectionKey;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class CollectionKeyParsingAnnotationHandler implements ParsingAnnotationHandler {

    private final ParseHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;
    private final CollectionTypeManager collectionTypeManager;
    private ParsingAnnotationHandler next;
    @Autowired
    public CollectionKeyParsingAnnotationHandler(ParseHelper fieldHelper,
                                                 AnnotationHandlerHelper annotationHandlerHelper,
                                                 CollectionTypeManager collectionTypeManager) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
        this.collectionTypeManager = collectionTypeManager;
    }
    @Override
    public void handle(Object model, List<Property> propertyList, List<Property> unusedProperties) {
        List<Field> collectionKeyFields = fieldHelper.getFieldsWithAnnotation(model, CollectionKey.class);
        HashMap<CollectionType, List<Property>> collectionTypeMap = splitByCollectionTypes(propertyList, collectionKeyFields);

        for (CollectionType type : collectionTypeMap.keySet()) {
            List<Field> fields = fieldsOfType(collectionKeyFields, type);
            List<Property> properties = collectionTypeMap.get(type);
            unusedProperties.removeAll(properties);
            annotationHandlerHelper.initialize(model, fields, properties);
        }
        next.handle(model, propertyList, unusedProperties);
    }

    @Override
    public void setNext(ParsingAnnotationHandler parsingAnnotationHandler) {
        next = parsingAnnotationHandler;
    }

    private List<Field> fieldsOfType(List<Field> originFields, CollectionType type) {
        return originFields.stream()
                .filter(f -> type.equals(f.getAnnotation(CollectionKey.class).type()))
                .toList();
    }

    private HashMap<CollectionType, List<Property>> splitByCollectionTypes(List<Property> propertyList,
                                                                           List<Field> fields) {
        HashMap<CollectionType, List<Property>> map = new HashMap<>();
        Set<CollectionType> types = new HashSet<>();
        for (Field field : fields) {
            CollectionType type = field.getAnnotation(CollectionKey.class).type();
            types.add(type);
        }
        for (Property property : propertyList) {
            if (property.type()== PropertyType.SIMPLE)
                continue;
            List<CollectionType> typesForKey = collectionTypeManager.findTypeForToken(property.getKey());
            types.retainAll(typesForKey);
            for (CollectionType type : types) {
                List<Property> propertiesOfType = map.computeIfAbsent(type, t -> new ArrayList<>());
                propertiesOfType.add(property);
            }
        }
        return map;
    }
}
