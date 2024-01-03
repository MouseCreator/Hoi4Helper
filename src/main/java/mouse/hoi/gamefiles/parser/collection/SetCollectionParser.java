package mouse.hoi.gamefiles.parser.collection;

import mouse.hoi.exception.GameFileParseException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.handler.InsertionCaller;
import mouse.hoi.gamefiles.parser.handler.InsertionHandler;
import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
@Service
public class SetCollectionParser implements ParsingCollectionHandler, InsertionCaller {
    private final CollectionParserCommon collectionParserCommon;
    private final ParseHelper parseHelper;
    private InsertionHandler insertionHandler;

    public SetCollectionParser(CollectionParserCommon collectionParserCommon, ParseHelper parseHelper) {
        this.collectionParserCommon = collectionParserCommon;
        this.parseHelper = parseHelper;
    }

    @Override
    public boolean canHandle(Field field) {
        return collectionParserCommon.isType(Set.class, field);
    }

    @Override
    public void push(Object model, Field field, Property property) {
        Class<?> generic = parseHelper.getGeneric(field);
        Object value = insertionHandler.getPropertyValue(generic, field, property);
        push(model, field, value);
    }

    private void push(Object model, Field field, Object toPush) {
        try {
            field.setAccessible(true);
            pushToSet(model, field, toPush);
        } catch (Exception e) {
            throw new GameFileParseException(e);
        }
    }
    private void pushToSet(Object model, Field field, Object value) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object set = collectionParserCommon.getCollectionObject(model, field, HashSet::new);
        boolean valid = parseHelper.testGeneric(field, value);
        if (!valid) {
            throw new IllegalArgumentException("Parsing Set: Argument is not of Generic type!");
        }
        Method add = Set.class.getDeclaredMethod("add", Object.class);
        add.invoke(set, value);
        field.set(model, set);
    }

    @Override
    public void setInsertionHandler(InsertionHandler insertionHandler) {
        this.insertionHandler = insertionHandler;
    }
}
