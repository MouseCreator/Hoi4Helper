package mouse.hoi.gamefiles.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.gamefiles.parser.handler.ParsingAnnotationHandler;
import mouse.hoi.gamefiles.parser.property.BlockProperty;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.common.annotation.DefaultField;
import mouse.hoi.gamefiles.common.annotation.FromBlockValue;
import mouse.hoi.gamefiles.common.annotation.FromKeyValue;
import mouse.hoi.gamefiles.common.annotation.RequireField;
import mouse.hoi.gamefiles.parser.handler.AnnotationHandlerHelper;
import mouse.hoi.gamefiles.parser.property.SimpleProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;
@Service
public class PropertyToModelParserImpl implements PropertyToModelParser {
    private final ParseHelper parseHelper;
    private final ModelCreator modelCreator;
    private final List<ParsingAnnotationHandler> parsingAnnotationHandlers;
    private final AnnotationHandlerHelper annotationHandlerHelper;

    @Autowired
    public PropertyToModelParserImpl(ParseHelper parseHelper,
                                 ModelCreator modelCreator,
                                 List<ParsingAnnotationHandler> parsingAnnotationHandlers,
                                 AnnotationHandlerHelper annotationHandlerHelper) {
        this.parseHelper = parseHelper;
        this.modelCreator = modelCreator;
        this.parsingAnnotationHandlers = parsingAnnotationHandlers;
        this.annotationHandlerHelper = annotationHandlerHelper;
    }

    @PostConstruct
    void init() {
        annotationHandlerHelper.setPropertyToModelParser(this);
    }

    public Object getModel(Class<?> tClass, Property property) {
        Object model = modelCreator.lookup(tClass);
        parseProperty(property, model);
        validateModel(model);
        return model;
    }

    private void parseProperty(Property property, Object model) {
        if (property.isBlock()) {
            parseBlockProperty((BlockProperty) property, model);
        } else {
            parseRegularProperty(property, model);
        }
    }

    private void parseBlockProperty(BlockProperty blockProperty, Object model) {
        List<Property> children = blockProperty.getChildren();
        initializeBlockValue(model, blockProperty);
        initializeKeyValue(model, blockProperty);
        for (ParsingAnnotationHandler handler : parsingAnnotationHandlers) {
            handler.handle(model, children);
        }
    }

    private void initializeBlockValue(Object model, Property property) {
        String blockValue = property.getValue();
        if (blockValue.isEmpty()) {
            return;
        }
        List<Field> fields = parseHelper.getFieldsWithAnnotation(model, FromBlockValue.class);
        if (fields.isEmpty()) {
            throw new PropertyParseException("Cannot assign block value "
                    + blockValue + " to any field in "
                    + model.getClass().getSimpleName());
        }
        SimpleProperty simpleProperty = new SimpleProperty(blockValue);
        for (Field field : fields) {
            annotationHandlerHelper.initializeFieldWithProperty(model, field, simpleProperty);
        }
    }

    private void initializeKeyValue(Object model, Property property) {
        List<Field> keyFields = parseHelper.getFieldsWithAnnotation(model, FromKeyValue.class);
        if (keyFields.isEmpty()) {
            return;
        }
        SimpleProperty simpleProperty = new SimpleProperty(property.getKey());
        for (Field field : keyFields) {
            annotationHandlerHelper.initializeFieldWithProperty(model, field, simpleProperty);
        }
    }

    private void parseRegularProperty(Property property, Object model) {
        initializeKeyValue(model, property);
        List<Field> fields = parseHelper.getFieldsWithAnnotation(model, DefaultField.class);
        if (fields.isEmpty()) {
            throw new PropertyParseException("Non-Block Property " + property.print() + " is used to defined a model "
                    + model.getClass().getSimpleName() + ", but no default field is found");
        }
        annotationHandlerHelper.initialize(model, fields, List.of(property));
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
                throw new PropertyParseException("Required field"
                        + field.getName()
                        + " is not initialized for "
                        + parseHelper.toClass(model));
            }
        }
    }
}
