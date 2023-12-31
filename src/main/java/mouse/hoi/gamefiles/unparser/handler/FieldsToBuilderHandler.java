package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;

@Service
public class FieldsToBuilderHandler {

    private List<OutputAnnotationHandler> outputAnnotationHandlers;
    public void applyAnnotations(OutputPropertyBuilder builder, List<Annotation> annotationList, Object model) {
        for (OutputAnnotationHandler handler : outputAnnotationHandlers) {
            if (handler.canHandle(builder, annotationList, model)) {
                handler.handle(builder, annotationList, model);
            }
        }
    }



}
