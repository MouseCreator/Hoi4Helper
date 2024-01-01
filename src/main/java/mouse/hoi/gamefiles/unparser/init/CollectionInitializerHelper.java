package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.unparser.InitializerCaller;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class CollectionInitializerHelper implements InitializerHelper, InitializerCaller {

    private final ParseHelper parseHelper;
    private InitializerHelper next = null;
    private OutputPropertyInitializer outputPropertyInitializer;
    @Autowired
    public CollectionInitializerHelper(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
    }

    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        if (parseHelper.isCollectionModel(model)) {
            return processCollection(model, builder);
        }
        return next == null ? List.of() : next.initialize(builder, model);
    }

    private List<OutputProperty> processCollection(Object model, OutputPropertyBuilder builder) {
        Collection<?> collection = (Collection<?>) model;
        List<OutputProperty> properties = new ArrayList<>();
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
