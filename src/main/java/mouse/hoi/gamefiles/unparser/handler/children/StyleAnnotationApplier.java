package mouse.hoi.gamefiles.unparser.handler.children;

import mouse.hoi.gamefiles.common.annotation.Style;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;

import java.util.Optional;

public class StyleAnnotationApplier implements BuilderAnnotationApplier {
    @Override
    public void apply(OutputPropertyBuilder builder, Object model) {
        Optional<Style> annotation = builder.getAnnotation(Style.class);
        if (annotation.isEmpty()) {
            return;
        }
        if (builder.hasStyle()) {
            return;
        }
        builder.withStyle(annotation.get().printStyle());
    }
}
