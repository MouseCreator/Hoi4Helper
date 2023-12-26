package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.*;
import mouse.hoi.parser.property.BlockProperty;
import mouse.hoi.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GameFileParser {
    private final ParsedModelCreator parsedModelCreator;
    private final ParseHelper helper;
    private final StringFormatter formatter;
    @Autowired
    public GameFileParser(ParsedModelCreator parsedModelCreator, ParseHelper helper, StringFormatter formatter) {
        this.parsedModelCreator = parsedModelCreator;
        this.helper = helper;
        this.formatter = formatter;
    }

    private static class PropertyChildren {
        private final List<Property> propertyList;

        public PropertyChildren(BlockProperty blockProperty) {
            if (blockProperty == null) {
                propertyList = new ArrayList<>();
            } else {
                propertyList = new ArrayList<>(blockProperty.getChildren());
            }
        }
        public List<Property> getPropertiesByKey(String key) {
            return propertyList.stream().filter(p -> key.equals(p.getKey())).toList();
        }
    }

    public <T> T parseFrom(Class<T> tClass, Property property) {
        return tClass.cast(parseToObject(tClass, property));
    }
    private Object parseToObject(Class<?> tClass, Property property) {
        validateBlockName(property, tClass);
        if (!property.isBlock()) {
            throw new PropertyParseException("Main property is not a block " + property.print());
        }
        return parseInstanceOfClass(tClass, property);
    }

    private Object parseInstanceOfClass(Class<?> clazz, Property property) {
        Object model = parsedModelCreator.lookup(clazz);
        if (!property.isBlock()) {
            initializeWithDefaultField(clazz, property, model);
            return model;
        }
        initializeWithBlock(clazz, property, model);
        return model;
    }

    private void initializeWithBlock(Class<?> clazz, Property property, Object model) {
        BlockProperty mainProperty = toBlock(property);
        PropertyChildren children = new PropertyChildren(mainProperty);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            ObjField fieldAnnotation = field.getAnnotation(ObjField.class);
            if (fieldAnnotation == null)
                continue;
            String text = fieldAnnotation.text();
            List<Property> propertiesByKey = children.getPropertiesByKey(text);
            if (propertiesByKey.isEmpty()) {
                validateRequire(clazz, field);
                continue;
            }
            if (field.isAnnotationPresent(FromBlockValue.class)) {
                initializeField(model, field, mainProperty.getValue());
            }
            if (field.isAnnotationPresent(Ordered.class)) {
                int num = field.getAnnotation(Ordered.class).num();
                if (num > propertiesByKey.size()) {
                    validateRequire(clazz, field);
                    continue;
                }
                Property orderedProperty = propertiesByKey.get(num);
                propertiesByKey = List.of(orderedProperty);
            }
            if (helper.isCollectionField(field)) {
                pushToCollection(model, field, propertiesByKey);
            } else {
                initializeField(model, field, propertiesByKey.get(0));
            }
        }
    }

    private static void validateRequire(Class<?> clazz, Field field) {
        if (field.isAnnotationPresent(RequireField.class)) {
            throw new PropertyParseException("Field " + field.getName() +
                    " for class " + clazz.getSimpleName() + " is required, but not present");
        }
    }

    private void initializeWithDefaultField(Class<?> clazz, Property property, Object model) {
        String value = property.getValue();
        initializeWithDefaultField(clazz, model, value);
    }

    private void initializeWithDefaultField(Class<?> clazz, Object model, String value) {
        Optional<Field> defaultField = getDefaultField(clazz);
        if(defaultField.isPresent()) {
            Field field = defaultField.get();
            initializeField(model, field, value);
        }
        throw new PropertyParseException("Trying to create instance of " + clazz.getSimpleName()
                + " with value " + value + ", but no default field is declared");
    }

    private Object getInstanceFromProperty(Field field, Property property) {
        return parseInstanceOfClass(field.getType(), property);
    }

    private void pushToCollection(Object model, Field field, List<Property> propertiesByKey) {
        for (Property property : propertiesByKey) {
            Object instance = getInstanceFromProperty(field, property);
            helper.push(model, field, instance);
        }
    }



    private void initializeField(Object model, Field field, String value) {
        Object instance = getFromString(field, value);
        setField(model, field, instance);
    }

    private Object getFromString(Field field, String value) {
        Class<?> type = field.getType();
        return (String.class.isAssignableFrom(type)) ?
             processStringValue(field, value) : processNotStringValue(field, value);
    }

    private String processStringValue(Field field, String value) {
        if (field.isAnnotationPresent(UseQuotes.class)) {
            value = formatter.removeQuotes(value);
        }
        return value;
    }

    private Object processNotStringValue(Field field, String value) {
        Class<?> clazz = field.getType();
        if (clazz == Integer.class) {
            return Integer.parseInt(value);
        } else if (clazz == Long.class) {
            return Long.parseLong(value);
        } else if (clazz == Double.class) {
            return processDouble(field, value);
        } else if (clazz == Boolean.class) {
            return processBoolean(value);
        } else {
            Object object = parsedModelCreator.lookup(clazz);
            initializeWithDefaultField(clazz, object, value);
            return object;
        }
    }

    private static double processDouble(Field field, String value) {
        Accuracy accuracyAnnotation = field.getDeclaredAnnotation(Accuracy.class);
        if (accuracyAnnotation != null) {
            int decimalPlaces = accuracyAnnotation.digits();
            return Double.parseDouble(String.format("%." + decimalPlaces + "f", Double.parseDouble(value)));
        } else {
            return Double.parseDouble(value);
        }
    }

    private static Object processBoolean(String value) {
        if ("yes".equalsIgnoreCase(value)) {
            return true;
        } else if ("no".equalsIgnoreCase(value)) {
            return false;
        } else {
            throw new PropertyParseException ("Invalid value for Boolean: " + value);
        }
    }


    private void setField(Object model, Field field, Object instance) {
        try {
            field.set(model, instance);
        } catch (IllegalAccessException e) {
            throw new PropertyParseException("Cannot set value to field " + field.getName() + ", value: " + instance, e);
        }
    }

    private void initializeField(Object model, Field field, Property property) {
        Object instance = getInstanceFromProperty(field, property);
        setField(model, field, instance);
    }

    private Optional<Field> getDefaultField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DefaultField.class)) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }

    private BlockProperty toBlock(Property property) {
        if (property.isBlock()) {
            return (BlockProperty) property;
        }
        throw new PropertyParseException("Cannot parse property to block " + property.print());
    }

    private void validateBlockName(Property property, Class<?> modelClass) {
        Block blockAnnotation = modelClass.getDeclaredAnnotation(Block.class);
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
}
