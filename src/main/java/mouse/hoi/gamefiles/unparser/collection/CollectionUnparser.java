package mouse.hoi.gamefiles.unparser.collection;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.List;

public interface CollectionUnparser {
    boolean isCollectionModel(Object model);
    List<OutputProperty> unparseCollection(Object model, OutputPropertyBuilder builder);
}
