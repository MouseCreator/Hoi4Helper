package mouse.hoi.gamefiles.unparser.collection;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Service
public class SetCollectionTypeHandlerImpl implements CollectionTypeHandler {
    private final UnparsingCollectionCommon unparsingCollectionCommon;
    @Autowired
    public SetCollectionTypeHandlerImpl(UnparsingCollectionCommon unparsingCollectionCommon) {
        this.unparsingCollectionCommon = unparsingCollectionCommon;
    }

    public boolean canHandle(Object model) {
        return unparsingCollectionCommon.isType(List.class, model);
    }
    public List<OutputProperty> unparseCollection(Object model, OutputPropertyBuilder builder) {
        Collection<?> collection = (Collection<?>) model;
        return unparsingCollectionCommon.asSimpleCollection(collection, builder);
    }
}
