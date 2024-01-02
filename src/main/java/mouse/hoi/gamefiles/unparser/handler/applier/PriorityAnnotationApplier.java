package mouse.hoi.gamefiles.unparser.handler.applier;

import mouse.hoi.gamefiles.common.annotation.Priority;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PriorityAnnotationApplier implements BuilderAnnotationApplier {
    @Override
    public void apply(OutputPropertyBuilder builder, Object model) {
        Optional<Priority> annotation = builder.getAnnotation(Priority.class);
        if (annotation.isEmpty()) {
            return;
        }
        builder.withPriority(annotation.get().value());
    }
}
