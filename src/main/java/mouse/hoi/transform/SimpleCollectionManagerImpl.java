package mouse.hoi.transform;

import mouse.hoi.model.common.SimpleCollection;
import mouse.hoi.parser.ModelCreator;
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
    public <T, R extends SimpleCollection<T>> R merge(Class<R> targetClass, Collection<SimpleCollection<T>> simpleCollections) {
        Object obj = modelCreator.lookup(targetClass);
        R resultCollection = targetClass.cast(obj);
        for (SimpleCollection<T> included : simpleCollections) {
            resultCollection.getCollection().addAll(included.getCollection());
        }
        return resultCollection;
    }
}
