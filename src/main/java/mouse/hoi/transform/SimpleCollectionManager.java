package mouse.hoi.transform;

import mouse.hoi.model.common.SimpleCollection;

import java.util.Collection;

public interface SimpleCollectionManager {

    <T> SimpleCollection<T> merge(Class<? extends SimpleCollection<T>> targetClass,
                                  Collection<SimpleCollection<T>> simpleCollections);
}
