package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Simple;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
@Service
public class SimpleAnnotationHelper implements BuilderAnnotationHelper {

    private final ParseHelper parseHelper;
    private final BuilderInitializer builderInitializer;
    @Autowired
    public SimpleAnnotationHelper(ParseHelper parseHelper, BuilderInitializer builderInitializer) {
        this.parseHelper = parseHelper;
        this.builderInitializer = builderInitializer;
    }

    @Override
    public List<OutputProperty> toProperties(Object model) {
        List<Field> simpleFields = parseHelper.getFieldsWithAnnotation(model, Simple.class);
        List<OutputPropertyBuilder> outputPropertyBuilders = builderInitializer.initializeProperties(model, simpleFields);
        for (OutputPropertyBuilder builder : outputPropertyBuilders) {
            builder.withType(PropertyType.SIMPLE);
        }
        return builderInitializer.toProperty(model, outputPropertyBuilders);
    }
}
