package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.AnyKey;

import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import java.util.List;
@Service
public class AnyKeyAnnotationHelper implements BuilderAnnotationHelper {
    private final ParseHelper parseHelper;
    private final BuilderInitializer builderInitializer;

    public AnyKeyAnnotationHelper(ParseHelper parseHelper, BuilderInitializer builderInitializer) {
        this.parseHelper = parseHelper;
        this.builderInitializer = builderInitializer;
    }

    @Override
    public List<OutputProperty> toProperties(Object model) {
        List<Field> anyKeyFields = parseHelper.getFieldsWithAnnotation(model, AnyKey.class);
        List<OutputPropertyBuilder> builders = builderInitializer.initializeProperties(model, anyKeyFields);
        return builderInitializer.toProperty(model, builders);
    }

}
