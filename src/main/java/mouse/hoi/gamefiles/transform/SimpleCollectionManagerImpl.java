package mouse.hoi.gamefiles.transform;

import mouse.hoi.gamefiles.tempmodel.common.SimpleCollection;
import mouse.hoi.gamefiles.common.modelcreator.ModelCreator;
import mouse.hoi.gamefiles.tempmodel.common.SimpleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SimpleCollectionManagerImpl implements SimpleCollectionManager {

    private final ModelCreator modelCreator;
    @Autowired
    public SimpleCollectionManagerImpl(ModelCreator modelCreator) {
        this.modelCreator = modelCreator;
    }

    @Override
    public <T, R extends SimpleCollection<T>> R mergeLists(Class<R> targetClass, Collection<SimpleCollection<T>> simpleCollections) {
        Object obj = modelCreator.lookup(targetClass);
        R resultCollection = targetClass.cast(obj);
        for (SimpleCollection<T> included : simpleCollections) {
            resultCollection.getCollection().addAll(included.getCollection());
        }
        return resultCollection;
    }

    @Override
    public <T, V, R extends SimpleMap<T, V>> R mergeMaps(Class<R> targetClass, Collection<SimpleMap<T, V>> simpleMaps) {
        Object obj = modelCreator.lookup(targetClass);
        R resultMap = targetClass.cast(obj);
        for (SimpleMap<T, V> included : simpleMaps) {
            resultMap.getMap().putAll(included.getMap());
        }
        return resultMap;
    }
}
