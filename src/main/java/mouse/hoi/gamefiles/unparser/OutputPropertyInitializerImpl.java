package mouse.hoi.gamefiles.unparser;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.unparser.handler.annotation.FieldsToBuilderHandler;
import mouse.hoi.gamefiles.unparser.handler.initialization.DefaultInitializationHandler;
import mouse.hoi.gamefiles.unparser.handler.initialization.InitializationHandlerChain;
import mouse.hoi.gamefiles.unparser.handler.initialization.PrimitiveInitializationHandler;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OutputPropertyInitializerImpl implements OutputPropertyInitializer {

    private final FieldsToBuilderHandler fieldsToBuilderHandler;
    private final InitializationHandlerChain initializationHandlers;

    public OutputPropertyInitializerImpl(FieldsToBuilderHandler fieldsToBuilderHandler,
                                         InitializationHandlerChain initializationHandlers) {
        this.fieldsToBuilderHandler = fieldsToBuilderHandler;
        this.initializationHandlers = initializationHandlers;
    }

    @Override
    public OutputProperty initializeProperty(Object model, CreationParameters creationParameters) {
        List<Annotation> annotations = creationParameters.toAnnotations();
        OutputPropertyBuilder builder = new OutputPropertyBuilder();
        fieldsToBuilderHandler.applyAnnotations(builder, annotations);
        return initializationHandlers.initializeProperty(model, creationParameters);

    }




}
