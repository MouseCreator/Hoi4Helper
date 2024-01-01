package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PrimitiveInitializerHelper implements InitializerHelper{

    private final PrimitivesParser primitivesParser;
    public InitializerHelper next;

    public PrimitiveInitializerHelper(PrimitivesParser primitivesParser) {
        this.primitivesParser = primitivesParser;
    }

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
