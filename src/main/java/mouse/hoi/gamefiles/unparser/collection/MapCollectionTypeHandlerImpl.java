package mouse.hoi.gamefiles.unparser.collection;

import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.unparser.InitializerCaller;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class MapCollectionTypeHandlerImpl implements CollectionTypeHandler, InitializerCaller {
    private final UnparsingCollectionCommon unparsingCollectionCommon;
    private final PrimitivesParser primitivesParser;
    private OutputPropertyInitializer outputPropertyInitializer;

    public MapCollectionTypeHandlerImpl(UnparsingCollectionCommon unparsingCollectionCommon,
                                        PrimitivesParser primitivesParser) {
        this.unparsingCollectionCommon = unparsingCollectionCommon;
        this.primitivesParser = primitivesParser;
    }

    @Override
    public boolean canHandle(Object model) {
        return unparsingCollectionCommon.isType(HashMap.class, model);
    }

    @Override
    public List<OutputProperty> unparseCollection(Object model, OutputPropertyBuilder builder) {
        Map<?, ?> map = (Map<?, ?>) model;
        List<OutputProperty> outputProperties = new ArrayList<>();
        for (Object keyObj : map.keySet()) {
            OutputPropertyBuilder instanceBuilder = builder.duplicate();
            String key = primitivesParser.convertToString(keyObj, instanceBuilder.getAnnotations());
            instanceBuilder.withKey(key);
            outputProperties.addAll(outputPropertyInitializer.initializeProperty(map.get(keyObj), instanceBuilder));

        }
        return outputProperties;
    }

    @Override
    public void setInitializer(OutputPropertyInitializer initializer) {
        outputPropertyInitializer = initializer;
    }
}
