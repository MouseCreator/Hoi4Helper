package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.List;

public interface InitializerHelper {
    void setNext(InitializerHelper nextInitializer);
    List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model);
}
