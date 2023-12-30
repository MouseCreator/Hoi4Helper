package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.SkipDeclaration;
import mouse.hoi.parser.property.input.BlockProperty;
import mouse.hoi.parser.property.input.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GameFileParserImpl implements GameFileParser {
    private final PropertyToModelParser propertyToModelParser;
    @Autowired
    public GameFileParserImpl(PropertyToModelParser propertyToModelParser) {
        this.propertyToModelParser = propertyToModelParser;
    }

    public <T> List<T> parseFrom(Class<T> tClass, List<Property> properties) {
        if (properties.isEmpty()) {
            throw new PropertyParseException("No properties provided to initialize " + tClass.getName());
        }
        return parseToObject(tClass, properties);
    }
    private <T> List<T> parseToObject(Class<T> tClass, List<Property> properties) {
        List<T> list = new ArrayList<>();
        if (skipDeclaration(tClass)) {
            BlockProperty blockProperty = new BlockProperty("", "", properties);
            properties = List.of(blockProperty);
        } else {
            validateBlockName(properties.get(0), tClass);
        }
        for (Property property : properties) {
            Object model = propertyToModelParser.getModel(tClass, property);
            list.add(tClass.cast(model));
        }

        return list;
    }

    private boolean skipDeclaration(Class<?> tClass) {
        return tClass.isAnnotationPresent(SkipDeclaration.class);
    }

    private <T> void validateBlockName(Property property, Class<T> modelClass) {
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
}
