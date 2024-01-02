package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.unparser.handler.applier.BuilderAnnotationApplier;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class ApplyInitializer implements InitializerHelper {

    private final List<BuilderAnnotationApplier> annotationAppliers;

    private InitializerHelper next;
    @Autowired
    public ApplyInitializer(List<BuilderAnnotationApplier> annotationAppliers) {
        this.annotationAppliers = annotationAppliers;
    }

    private void applyAnnotations(OutputPropertyBuilder builder, Object model) {
        Class<?> modelClass = model.getClass();
        builder.getAnnotations().addAll(Arrays.asList(modelClass.getAnnotations()));
        for (BuilderAnnotationApplier applier : annotationAppliers) {
            applier.apply(builder, model);
        }
    }

    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        applyAnnotations(builder, model);
        return next == null ? List.of(builder.get()) : next.initialize(builder, model);
    }
}
