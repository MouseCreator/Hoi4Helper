package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.handler.build.BuilderAnnotationHelper;
import mouse.hoi.gamefiles.unparser.handler.applier.BuilderAnnotationApplier;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class CommonInitializerHelper implements InitializerHelper {

    private final List<BuilderAnnotationHelper> builderAnnotationHelpers;
    private final List<BuilderAnnotationApplier> annotationAppliers;


    public CommonInitializerHelper(List<BuilderAnnotationHelper> builderAnnotationHelpers, List<BuilderAnnotationApplier> annotationAppliers) {
        this.builderAnnotationHelpers = builderAnnotationHelpers;
        this.annotationAppliers = annotationAppliers;
    }

    @Override
    public void setNext(InitializerHelper nextInitializer) {

    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        applyAnnotations(builder, model);
        return List.of(getOutputProperties(model, builder));
    }
    private OutputProperty getOutputProperties(Object model, OutputPropertyBuilder builder) {
        builder.withType(PropertyType.BLOCK);
        List<OutputProperty> children = new ArrayList<>();
        for (BuilderAnnotationHelper annotationHelper : builderAnnotationHelpers) {
            List<OutputProperty> properties = annotationHelper.toProperties(model);
            children.addAll(properties);
        }
        builder.withChildren(children);
        return builder.get();
    }

    private void applyAnnotations(OutputPropertyBuilder builder, Object model) {
        Class<?> modelClass = model.getClass();
        builder.getAnnotations().addAll(Arrays.asList(modelClass.getAnnotations()));
        for (BuilderAnnotationApplier applier : annotationAppliers) {
            applier.apply(builder, model);
        }
    }
}
