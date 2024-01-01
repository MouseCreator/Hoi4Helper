package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.Optional;

public class CollectionInitializerHelper implements InitializerHelper{
    @Override
    public void setNext(InitializerHelper nextInitializer) {

    }

    @Override
    public Optional<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        return Optional.empty();
    }
}
