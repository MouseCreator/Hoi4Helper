package mouse.hoi.gamefiles.unparser.handler.initialization;

import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.unparser.CreationParameters;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;

import java.util.Optional;

public class PrimitiveInitializationHandler implements InitializationHandler{

    private InitializationHandler next = null;
    public PrimitivesParser primitivesParser;

    @Override
    public void setNext(InitializationHandler nextHandler) {
        this.next = nextHandler;
    }

    @Override
    public Optional<OutputProperty> handleInitialization(Object model, CreationParameters creationParameters) {
        Optional<OutputProperty> propertyOptional = initialize(model, creationParameters);
        if (propertyOptional.isPresent()) {
            return propertyOptional;
        }
        return next == null ? Optional.empty() : next.handleInitialization(model, creationParameters);
    }

    private Optional<OutputProperty> initialize(Object model, CreationParameters creationParameters) {
        if (!primitivesParser.isPrimitiveClass(model.getClass())) {
            return Optional.empty();
        }
        String value = primitivesParser.convertToString(model, creationParameters.toAnnotations());
        return Optional.empty();
    }
}
