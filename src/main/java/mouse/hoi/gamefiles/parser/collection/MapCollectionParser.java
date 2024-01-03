package mouse.hoi.gamefiles.parser.collection;

import mouse.hoi.exception.GameFileParseException;
import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.parser.handler.InsertionCaller;
import mouse.hoi.gamefiles.parser.handler.InsertionHandler;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
@Service
public class MapCollectionParser implements ParsingCollectionHandler, InsertionCaller {

    private final CollectionParserCommon collectionParserCommon;
    private final ParseHelper parseHelper;
    private InsertionHandler insertionHandler;
    private final PrimitivesParser primitivesParser;
    @Autowired
    public MapCollectionParser(CollectionParserCommon collectionParserCommon, ParseHelper parseHelper, PrimitivesParser primitivesParser) {
        this.collectionParserCommon = collectionParserCommon;
        this.parseHelper = parseHelper;
        this.primitivesParser = primitivesParser;
    }

    @Override
    public boolean canHandle(Field field) {
        return collectionParserCommon.isType(Map.class, field);
    }

    @Override
    public void push(Object model, Field field, Property property) {
        List<Class<?>> generics = parseHelper.getAllGenerics(field);
        if (generics.size() != 2) {
            throw new PropertyParseException("Map class is parametrized with unexpected number of parameters: " + generics.size());
        }
        Class<?> keyClass = generics.get(0);
        Class<?> valueClass = generics.get(1);
        if (property.type()== PropertyType.SIMPLE) {
            throw new PropertyParseException("Unable to convert simple property to a map!");
        }
        Object key = primitivesParser.parsePrimitiveType(property.getKey(), keyClass, parseHelper.getAnnotations(field));
        Object value = insertionHandler.getPropertyValue(valueClass, field, property);
        push(model, field, key, value);
    }

    private void push(Object model, Field field, Object key, Object value) {
        try {
            field.setAccessible(true);
            pushToMap(model, field, key, value);
        } catch (Exception e) {
            throw new GameFileParseException(e);
        }
    }

    private void pushToMap(Object model, Field field, Object key, Object value) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object map = collectionParserCommon.getCollectionObject(model, field, HashMap::new);
        boolean valid = parseHelper.testGeneric(field, key, value);
        if (!valid) {
            throw new IllegalArgumentException("Parsing Set: Argument is not of Generic type!");
        }
        Method add = Map.class.getDeclaredMethod("put", Object.class, Object.class);
        add.invoke(map, key, value);
        field.set(model, map);
    }

    @Override
    public void setInsertionHandler(InsertionHandler insertionHandler) {
        this.insertionHandler = insertionHandler;
    }
}
