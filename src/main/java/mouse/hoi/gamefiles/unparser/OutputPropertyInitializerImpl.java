package mouse.hoi.gamefiles.unparser;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.unparser.handler.initialization.DefaultInitializationHandler;
import mouse.hoi.gamefiles.unparser.handler.annotation.FieldsToBuilderHandler;
import mouse.hoi.gamefiles.unparser.handler.initialization.InitializationHandler;
import mouse.hoi.gamefiles.unparser.handler.initialization.PrimitiveInitializationHandler;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
@Service
public class OutputPropertyInitializerImpl implements OutputPropertyInitializer {

    private final FieldsToBuilderHandler fieldsToBuilderHandler;
    private final List<InitializationHandler> initializationHandlers;

    public OutputPropertyInitializerImpl(FieldsToBuilderHandler fieldsToBuilderHandler,
                                         List<InitializationHandler> initializationHandlers) {
        this.fieldsToBuilderHandler = fieldsToBuilderHandler;
        this.initializationHandlers = initializationHandlers;
    }

    @Override
    public OutputProperty initializeProperty(Object model, OutputPropertyBuilder builder, List<Annotation> annotations) {
        fieldsToBuilderHandler.applyAnnotations(builder, annotations);
        for (InitializationHandler initializationHandler : initializationHandlers) {
            Optional<OutputProperty> outputProperty = initializationHandler.handleInitialization(model, builder, annotations);
            if (outputProperty.isPresent()) {
                return outputProperty.get();
            }
        }
        throw new UnparsingException("Unable to initialize property from model " + model);

    }


}
