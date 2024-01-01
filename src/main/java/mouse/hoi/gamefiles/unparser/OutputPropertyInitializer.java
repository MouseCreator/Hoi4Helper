package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.lang.annotation.Annotation;
import java.util.List;


public interface OutputPropertyInitializer {
    List<OutputProperty> initializeProperty(Object model);

    List<OutputProperty> initializeProperty(Object model, OutputPropertyBuilder builder);
}
