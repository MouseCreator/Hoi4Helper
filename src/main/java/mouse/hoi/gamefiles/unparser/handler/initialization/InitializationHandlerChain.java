package mouse.hoi.gamefiles.unparser.handler.initialization;

import mouse.hoi.gamefiles.unparser.CreationParameters;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;

public interface InitializationHandlerChain {
    OutputProperty initializeProperty(Object model, CreationParameters creationParameters);
}
