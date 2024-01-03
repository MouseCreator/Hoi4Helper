package mouse.hoi.gamefiles.parser.collection;

import mouse.hoi.exception.GameFileParseException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.handler.InsertionCaller;
import mouse.hoi.gamefiles.parser.handler.InsertionHandler;
import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class ListCollectionParser implements ParsingCollectionHandler, InsertionCaller {
    private final ParseHelper parseHelper;
    private final CollectionParserCommon collectionParserCommon;
    private InsertionHandler insertionHandler;
    @Autowired
    public ListCollectionParser(ParseHelper parseHelper, CollectionParserCommon collectionParserCommon) {
        this.parseHelper = parseHelper;
        this.collectionParserCommon = collectionParserCommon;
    }

    @Override
    public boolean canHandle(Field field) {
        Class<?> fieldType = field.getType();
        return List.class.isAssignableFrom(fieldType);
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
            pushToList(model, field, toPush);
        } catch (Exception e) {
            throw new GameFileParseException(e);
        }
    }
    private void pushToList(Object model, Field field, Object toAdd) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object list = collectionParserCommon.getCollectionObject(model, field, ArrayList::new);
        boolean valid = parseHelper.testGeneric(field, toAdd);
        if (!valid) {
            throw new IllegalArgumentException("Parsing List: Argument is not of Generic type!");
        }
        Method add = List.class.getDeclaredMethod("add", Object.class);
        add.invoke(list, toAdd);
        field.set(model, list);
    }

    @Override
    public void setInsertionHandler(InsertionHandler insertionHandler) {
        this.insertionHandler = insertionHandler;
    }
}
