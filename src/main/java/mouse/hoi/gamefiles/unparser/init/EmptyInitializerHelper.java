package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.annotation.RequireField;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmptyInitializerHelper implements InitializerHelper {

    private InitializerHelper next;

    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        if (model == null) {
            if (builder.hasAnnotation(RequireField.class)) {
                throw new UnparsingException("Required field is not initialized for model with builder " + builder);
            }
            return new ArrayList<>();
        }
        return  (next == null) ? new ArrayList<>() : next.initialize(builder, model);
    }


}
