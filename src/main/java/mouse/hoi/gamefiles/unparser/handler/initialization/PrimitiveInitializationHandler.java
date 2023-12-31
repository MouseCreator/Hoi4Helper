package mouse.hoi.gamefiles.unparser.handler.initialization;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public class PrimitiveInitializationHandler implements InitializationHandler{
    @Override
    public Optional<OutputProperty> handleInitialization(Object model, OutputPropertyBuilder builder, List<Annotation> annotations) {
        return Optional.empty();
    }
}
