package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.*;
import mouse.hoi.parser.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.List;


@Service
public class GameFileParser {
    private final ParsedModelCreator parsedModelCreator;
    private final StringValueProcessor stringValueProcessor;
    private final ParseHelper helper;
    private final FieldHelper fieldHelper;

    @Autowired
    public GameFileParser(ParsedModelCreator parsedModelCreator, ParseHelper helper, StringValueProcessor processor, FieldHelper fieldHelper) {
        this.parsedModelCreator = parsedModelCreator;
        this.helper = helper;
        this.stringValueProcessor = processor;
        this.fieldHelper = fieldHelper;
    }


    public <T> T parseFrom(Class<T> tClass, List<Property> properties) {
        if (properties.isEmpty()) {
            throw new PropertyParseException("No properties provided to initialize " + tClass.getName());
        }
        return tClass.cast(parseToObject(tClass, properties));
    }
    private Object parseToObject(Class<?> tClass, List<Property> properties) {
        if (skipDeclaration(tClass)) {
            BlockProperty blockProperty = new BlockProperty("", "", properties);
            properties = List.of(blockProperty);
        } else {
            validateBlockName(properties.get(0), tClass);
        }
        Object model = parsedModelCreator.lookup(tClass);
        for (Property property : properties) {
            parseProperty(property, model);
        }
        validateModel(model);
        return model;
    }

    private void parseProperty(Property property, Object model) {
    }

    private void validateModel(Object model) {
        List<Field> requiredFields = fieldHelper.getFieldsWithAnnotation(model, RequireField.class);
        for (Field field : requiredFields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(model);
            } catch (IllegalAccessException e) {
                throw new PropertyParseException("Unable to get field value " + field.getName(), e);
            }
            if (value == null) {
                throw new PropertyParseException("Required field" + field.getName() + " is not initialized for " + fieldHelper.toClass(model));
            }
        }
    }


    private boolean skipDeclaration(Class<?> tClass) {
        return tClass.isAnnotationPresent(SkipDeclaration.class);
    }

    private void validateBlockName(Property property, Class<?> modelClass) {
        Block blockAnnotation = modelClass.getAnnotation(Block.class);
        if(blockAnnotation == null) {
            throw new PropertyParseException("Provided class "
                    + modelClass.getSimpleName()
                    + " does not have " + Block.class.getSimpleName() + " annotation present");
        }
        String blockName = blockAnnotation.name();
        if (!property.getKey().equals(blockName)) {
            String msg = String.format("Property key and block name does not match for class %s: %s, %s", modelClass.getSimpleName(),
                    property.getKey(), blockName);
            throw new PropertyParseException(msg);
        }
    }

    private BlockProperty toBlock(Property property) {
        if (property.isBlock()) {
            return (BlockProperty) property;
        }
        throw new IllegalArgumentException("Trying to convert to block not a block property " + property.print());
    }

    private void setValueToFields(List<Field> simpleFields, String propertyValue) {

    }


    private void initializeFromBlockValue(BlockProperty blockProperty, Object model) {
        if (!blockProperty.getValue().isEmpty()) {
            fieldHelper.initializeFieldsWithAnnotation(model, FromBlockValue.class, blockProperty.getValue());
        }
    }




}
