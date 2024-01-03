package mouse.hoi.gamefiles.unparser.collection;


import mouse.hoi.gamefiles.common.annotation.ShowIfEmpty;
import mouse.hoi.gamefiles.unparser.InitializerCaller;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class UnparsingCollectionCommon implements InitializerCaller {

    private OutputPropertyInitializer outputPropertyInitializer;
    public boolean isType(Class<?> clazz, Object model) {
        Class<?> fieldType = model.getClass();
        return clazz.isAssignableFrom(fieldType);
    }

    public boolean isEmptyAndSkip(Collection<?> collection, OutputPropertyBuilder builder) {
        if (collection.isEmpty()) {
            return !builder.hasAnnotation(ShowIfEmpty.class);
        }
        return false;
    }

    public List<OutputProperty> asSimpleCollection(Collection<?> collection, OutputPropertyBuilder builder) {
        List<OutputProperty> properties = new ArrayList<>();
        if (isEmptyAndSkip(collection, builder)) {
            return properties;
        }
        for (Object obj : collection) {
            properties.addAll(outputPropertyInitializer.initializeProperty(obj, builder.duplicate()));
        }
        return properties;
    }

    @Override
    public void setInitializer(OutputPropertyInitializer initializer) {
        this.outputPropertyInitializer = initializer;
    }
}
