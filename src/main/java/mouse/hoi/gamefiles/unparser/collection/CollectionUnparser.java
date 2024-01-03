package mouse.hoi.gamefiles.unparser.collection;

import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

public interface CollectionUnparser {
    boolean canHandle(Class<?> collectionClazz);
    void unparseCollection(Class<?> type, OutputPropertyBuilder builder);
}
