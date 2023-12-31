package mouse.hoi.gamefiles.unparser.handler.initialization;

import mouse.hoi.gamefiles.unparser.CreationParameters;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;

import java.util.Optional;

public interface InitializationHandler {
    void setNext(InitializationHandler nextHandler);
    Optional<OutputProperty> handleInitialization(Object model, CreationParameters creationParameters);
}
