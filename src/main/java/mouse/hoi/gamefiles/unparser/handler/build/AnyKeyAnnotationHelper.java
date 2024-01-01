package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.AnyKey;

import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import java.util.ArrayList;
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
        if (anyKeyFields.isEmpty()) {
            return new ArrayList<>();
        }
        return builderInitializer.initializeProperties(model, anyKeyFields);
    }

}
