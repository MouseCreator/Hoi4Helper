package mouse.hoi.transform;

import mouse.hoi.model.common.SimpleCollection;

import java.util.Collection;

public interface SimpleCollectionManager {

    <T, R extends SimpleCollection<T>> R merge(Class<R> targetClass,
                                  Collection<SimpleCollection<T>> simpleCollections);
}
