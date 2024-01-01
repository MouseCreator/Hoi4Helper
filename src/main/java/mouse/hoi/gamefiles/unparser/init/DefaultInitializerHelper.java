package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.handler.DefaultFieldHandler;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultInitializerHelper implements InitializerHelper {

    private final DefaultFieldHandler defaultFieldHandler;
    private final PrimitivesParser primitivesParser;
    @Autowired
    public DefaultInitializerHelper(DefaultFieldHandler defaultFieldHandler, PrimitivesParser primitivesParser) {
        this.defaultFieldHandler = defaultFieldHandler;
        this.primitivesParser = primitivesParser;
    }

    private InitializerHelper next = null;
    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        if (allowsDefaultInitialization(model)) {
            builder.withType(PropertyType.FIELD_VALUE);
            return initDefault(builder, model);
        }
        return next == null ? List.of() : next.initialize(builder, model);
    }

    private List<OutputProperty> initDefault(OutputPropertyBuilder builder, Object model) {
        Object obj = defaultFieldHandler.getDefaultFieldValue(model);
        if (!primitivesParser.isPrimitiveClass(obj.getClass())) {
            throw new UnparsingException("Trying to initialize default field with non-primitive value");
        }
        String value = primitivesParser.convertToString(obj, builder.getAnnotations());
        builder.withValue(value);
        return List.of(builder.get());
    }

    private boolean allowsDefaultInitialization(Object model) {
        return defaultFieldHandler.allowsDefaultInitialization(model);
    }
}
