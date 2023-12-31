package mouse.hoi.gamefiles.unparser.handler.initialization;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public interface InitializationHandler {
    Optional<OutputProperty> handleInitialization(Object model, OutputPropertyBuilder builder, List<Annotation> annotations);
}
