package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

public interface ModelToPropertyUnparser {
    OutputProperty unparse(Object model, OutputPropertyBuilder outputPropertyBuilder);
}
