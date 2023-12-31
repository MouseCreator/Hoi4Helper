package mouse.hoi.gamefiles.unparser.handler.annotation;

import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;

@Service
public class FieldsToBuilderHandler {

    private final List<OutputAnnotationHandler> outputAnnotationHandlers;
    @Autowired
    public FieldsToBuilderHandler(List<OutputAnnotationHandler> outputAnnotationHandlers) {
        this.outputAnnotationHandlers = outputAnnotationHandlers;
    }

    public void applyAnnotations(OutputPropertyBuilder builder, List<Annotation> annotationList) {
        for (OutputAnnotationHandler handler : outputAnnotationHandlers) {
            if (handler.canHandle(builder, annotationList)) {
                handler.handle(builder, annotationList);
            }
        }
    }



}
