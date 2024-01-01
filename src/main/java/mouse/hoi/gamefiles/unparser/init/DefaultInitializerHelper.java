package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.List;
import java.util.Optional;

public class DefaultInitializerHelper implements InitializerHelper {
    @Override
    public void setNext(InitializerHelper nextInitializer) {

    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        return List.of();
    }
}
