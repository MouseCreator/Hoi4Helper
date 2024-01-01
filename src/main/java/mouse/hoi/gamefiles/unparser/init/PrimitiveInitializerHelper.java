package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.List;

public class PrimitiveInitializerHelper implements InitializerHelper{

    private PrimitivesParser primitivesParser;
    public InitializerHelper next;
    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        if (primitivesParser.isPrimitiveClass(model.getClass())) {
            return processPrimitive(model, builder);
        }
        return next == null ? List.of() : next.initialize(builder, model);
    }

    private List<OutputProperty> processPrimitive(Object model, OutputPropertyBuilder builder) {
        String value = primitivesParser.convertToString(model, builder.getAnnotations());
        builder.withValue(value);
        return List.of(builder.get());
    }
}
