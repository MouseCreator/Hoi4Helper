package mouse.hoi.gamefiles.parser.collection;

import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class CollectionsParserImpl implements CollectionsParser {
    private final List<ParsingCollectionHandler> handlerList;
    @Autowired
    public CollectionsParserImpl(List<ParsingCollectionHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public boolean isCollectionField(Field field) {
        for (ParsingCollectionHandler handler : handlerList) {
            if (handler.canHandle(field)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void pushToField(Object model, Field field, Property property) {
        for (ParsingCollectionHandler handler : handlerList) {
            if (handler.canHandle(field)) {
                handler.push(model, field, property);
                return;
            }
        }
    }
}
