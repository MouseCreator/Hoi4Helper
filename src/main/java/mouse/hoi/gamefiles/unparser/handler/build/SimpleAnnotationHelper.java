package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Simple;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
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
        if (simpleFields.isEmpty()) {
            return new ArrayList<>();
        }
        ForEachBuilder consumer = b -> {
            b.withType(PropertyType.SIMPLE);
        };
        return builderInitializer.initializeProperties(model, simpleFields);
    }
}
