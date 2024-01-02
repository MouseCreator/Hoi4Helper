package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.handler.build.BuilderAnnotationHelper;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CommonInitializerHelper implements InitializerHelper {

    private final List<BuilderAnnotationHelper> builderAnnotationHelpers;

    private InitializerHelper next;
    @Autowired
    public CommonInitializerHelper(List<BuilderAnnotationHelper> builderAnnotationHelpers) {
        this.builderAnnotationHelpers = builderAnnotationHelpers;
    }

    @Override
    public void setNext(InitializerHelper nextInitializer) {
        next = nextInitializer;
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        createOutputProperties(model, builder);
        return next == null ? List.of(builder.get()) : next.initialize(builder, model);
    }
    private void createOutputProperties(Object model, OutputPropertyBuilder builder) {
        builder.withType(PropertyType.BLOCK);
        List<OutputProperty> children = new ArrayList<>();
        for (BuilderAnnotationHelper annotationHelper : builderAnnotationHelpers) {
            List<OutputProperty> properties = annotationHelper.toProperties(model);
            children.addAll(properties);
        }
        builder.withChildren(children);
    }


}
