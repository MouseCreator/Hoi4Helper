package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.common.annotation.Style;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Service
public class StyleOutputAnnotationHandler implements OutputAnnotationHandler {

    private final OutputAnnotationHelper helper;
    @Autowired
    public StyleOutputAnnotationHandler(OutputAnnotationHelper helper) {
        this.helper = helper;
    }

    @Override
    public boolean canHandle(OutputPropertyBuilder builder, List<Annotation> annotations, Object model) {
        return helper.hasAnnotation(annotations, Style.class);
    }

    @Override
    public void handle(OutputPropertyBuilder builder, List<Annotation> annotations, Object model) {
        if (builder.hasStyle())
            return;
        Optional<Style> styleOptional = helper.getAnnotation(annotations, Style.class);
        assert styleOptional.isPresent();
        builder.withStyle(styleOptional.get().printStyle());
    }
}
