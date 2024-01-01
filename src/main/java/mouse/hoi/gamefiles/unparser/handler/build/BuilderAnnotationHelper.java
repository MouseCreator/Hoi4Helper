package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;

import java.util.List;

public interface BuilderAnnotationHelper {
    List<OutputProperty> toProperties(Object model);
}
