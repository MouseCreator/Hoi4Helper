package mouse.hoi.gamefiles.unparser.handler.annotation;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.annotation.Simple;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
@Service
public class SimpleOutputAnnotationHandler implements OutputAnnotationHandler {

    private final OutputAnnotationHelper helper;
    @Autowired
    public SimpleOutputAnnotationHandler(OutputAnnotationHelper helper) {
        this.helper = helper;
    }

    @Override
    public boolean canHandle(OutputPropertyBuilder builder, List<Annotation> annotations) {
        return helper.hasAnnotation(annotations, Simple.class);
    }

    @Override
    public void handle(OutputPropertyBuilder builder, List<Annotation> annotations) {
        if (builder.hasType()) {
            throw new UnparsingException("Trying to change property type from " + builder.get().getType() + " to Simple");
        }
        builder.toSimple();
    }
}
