package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.lang.annotation.Annotation;
import java.util.List;

public interface OutputAnnotationHandler {
    boolean canHandle(OutputPropertyBuilder builder, List<Annotation> annotations, Object model);
    void handle(OutputPropertyBuilder builder, List<Annotation> annotations, Object model);
}
