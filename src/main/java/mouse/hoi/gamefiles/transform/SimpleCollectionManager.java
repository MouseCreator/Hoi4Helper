package mouse.hoi.gamefiles.transform;

import mouse.hoi.gamefiles.tempmodel.extras.SimpleCollection;
import mouse.hoi.gamefiles.tempmodel.extras.SimpleMap;

import java.util.Collection;

public interface SimpleCollectionManager {

    <T, R extends SimpleCollection<T>> R mergeLists(Class<R> targetClass,
                                  Collection<SimpleCollection<T>> simpleCollections);
    <T, V, R extends SimpleMap<T, V>> R mergeMaps(Class<R> targetClass, Collection<SimpleMap<T, V>> simpleCollections);
}
