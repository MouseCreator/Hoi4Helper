package mouse.hoi.gamefiles.transform;

import mouse.hoi.gamefiles.tempmodel.common.SimpleCollection;

import java.util.Collection;

public interface SimpleCollectionManager {

    <T, R extends SimpleCollection<T>> R merge(Class<R> targetClass,
                                  Collection<SimpleCollection<T>> simpleCollections);
}
