package mouse.hoi.gamefiles.unparser.init;


import mouse.hoi.gamefiles.unparser.collection.CollectionUnparser;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class CollectionInitializerHelper implements InitializerHelper {

    private final CollectionUnparser collectionUnparser;
    private InitializerHelper next = null;
    @Autowired
    public CollectionInitializerHelper(CollectionUnparser collectionUnparser) {
        this.collectionUnparser = collectionUnparser;
    }

    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        if (collectionUnparser.isCollectionModel(model)) {
            return collectionUnparser.unparseCollection(model, builder);
        }
        return next == null ? List.of() : next.initialize(builder, model);
    }
}
