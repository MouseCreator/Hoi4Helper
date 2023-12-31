package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;


public interface OutputPropertyInitializer {
    OutputProperty initializeProperty(Object model, CreationParameters creationParameters);
}
