package mouse.hoi.gamefiles.unparser.collection;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CollectionUnparserImpl implements CollectionUnparser {

    private final List<CollectionTypeHandler> collectionUnparsers;
    @Autowired
    public CollectionUnparserImpl(List<CollectionTypeHandler> collectionUnparsers) {
        this.collectionUnparsers = collectionUnparsers;
    }

    @Override
    public boolean isCollectionModel(Object model) {
        for (CollectionTypeHandler collectionUnparser : collectionUnparsers) {
            if(collectionUnparser.canHandle(model)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OutputProperty> unparseCollection(Object model, OutputPropertyBuilder builder) {
        for (CollectionTypeHandler collectionUnparser : collectionUnparsers) {
            if(collectionUnparser.canHandle(model)) {
                return collectionUnparser.unparseCollection(model, builder);
            }
        }
        return new ArrayList<>();
    }
}
