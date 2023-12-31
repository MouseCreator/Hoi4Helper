package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.lang.annotation.Annotation;
import java.util.List;

public interface OutputPropertyInitializer {
    OutputProperty initializeProperty(Object model, OutputPropertyBuilder builder, List<Annotation> annotations);
}
