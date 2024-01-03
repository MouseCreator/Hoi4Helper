package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.parser.PropertyToModelParser;
import mouse.hoi.gamefiles.parser.collection.CollectionsParser;
import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
@Service
public class InsertionHandler {
    private final ParseHelper parseHelper;
    private final PrimitivesParser primitivesParser;
    private final CollectionsParser collectionsParser;
    private final List<InsertionCaller> insertionCallers;
    private PropertyToModelParser propertyToModelParser;
    @Autowired
    public InsertionHandler(ParseHelper parseHelper,
                            PrimitivesParser primitivesParser,
                            CollectionsParser collectionsParser,
                            List<InsertionCaller> insertionCallers) {
        this.parseHelper = parseHelper;
        this.primitivesParser = primitivesParser;
        this.collectionsParser = collectionsParser;
        this.insertionCallers = insertionCallers;
    }

    @PostConstruct
    void init() {
        for (InsertionCaller insertionCaller : insertionCallers) {
            insertionCaller.setInsertionHandler(this);
        }
    }
    public void initializeFieldWithProperty(Object model, Field field, Property property) {

        if (collectionsParser.isCollectionField(field)) {
            collectionsParser.pushToField(model, field, property);
        } else {
            Object value = getPropertyValue(field.getType(), field, property);
            parseHelper.setField(model, field, value);
        }
    }

    public Object getPropertyValue(Class<?> clazz, Field field, Property property) {
        List<Annotation> annotations = parseHelper.getAnnotations(field);
        if (primitivesParser.isPrimitiveClass(clazz)) {
            return primitivesParser.parsePrimitiveType(clazz, annotations, property);
        }
        return propertyToModelParser.getModel(clazz, property);
    }

    public void setPropertyToModelParser(PropertyToModelParser parser) {
        this.propertyToModelParser = parser;
    }
}
