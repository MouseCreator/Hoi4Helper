package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.Optional;

public interface InitializerHelper {
    Optional<OutputProperty> initialize(OutputPropertyBuilder builder, Object model);
}
