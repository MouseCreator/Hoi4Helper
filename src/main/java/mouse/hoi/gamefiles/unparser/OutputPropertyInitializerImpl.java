package mouse.hoi.gamefiles.unparser;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.SkipDeclaration;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.init.CommonInitializerHelper;
import mouse.hoi.gamefiles.unparser.init.DefaultInitializerHelper;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class OutputPropertyInitializerImpl implements OutputPropertyInitializer {

    private final ParseHelper parseHelper;
    private final PrimitivesParser primitivesParser;
    private final BuilderInitializer builderInitializer;
    private final DefaultInitializerHelper defaultInitializerHelper;
    private final CommonInitializerHelper commonInitializerHelper;
    public OutputPropertyInitializerImpl(ParseHelper parseHelper,
                                         PrimitivesParser primitivesParser,
                                         BuilderInitializer builderInitializer,
                                         DefaultInitializerHelper defaultInitializerHelper,
                                         CommonInitializerHelper commonInitializerHelper) {
        this.parseHelper = parseHelper;
        this.primitivesParser = primitivesParser;
        this.builderInitializer = builderInitializer;
        this.defaultInitializerHelper = defaultInitializerHelper;
        this.commonInitializerHelper = commonInitializerHelper;
    }

    @PostConstruct
    public void init() {
        builderInitializer.setInitializer(this);
    }

    @Override
    public List<OutputProperty> initializeProperty(Object model) {
        OutputPropertyBuilder builder = new OutputPropertyBuilder();
        builder.withType(PropertyType.SIMPLE);
        List<OutputProperty> properties = initializeProperty(model, builder);
        if (model.getClass().isAnnotationPresent(SkipDeclaration.class)) {
            properties = handleSkipDeclaration(model, properties);
        }
        return properties;
    }

    private List<OutputProperty> handleSkipDeclaration(Object model, List<OutputProperty> properties) {
        if (properties.size() > 1) {
            throw new UnparsingException("Multiple properties + (" + properties.size() + ") " +
                    "for model with skipped declaration " + model.getClass().getSimpleName());
        }
        OutputProperty modelProperty = properties.get(0);
        if (modelProperty.getType()==PropertyType.BLOCK) {
            properties = properties.get(0).getChildren();
        }
        return properties;
    }
    @Override
    public List<OutputProperty> initializeProperty(Object model, OutputPropertyBuilder builder) {
        if (parseHelper.isCollectionModel(model)) {
            return processCollection(model, builder);
        }
        if (primitivesParser.isPrimitiveClass(model.getClass())) {
            return processPrimitive(model, builder);
        }

        return processModel(model, builder);
    }

    private List<OutputProperty> processModel(Object model, OutputPropertyBuilder builder) {
        return List.of();
    }

    private List<OutputProperty> processPrimitive(Object model, OutputPropertyBuilder builder) {
        String value = primitivesParser.convertToString(model, builder.getAnnotations());
        builder.withValue(value);
        return List.of(builder.get());
    }

    private List<OutputProperty> processCollection(Object model, OutputPropertyBuilder builder) {
        Collection<?> collection = (Collection<?>) model;
        List<OutputProperty> properties = new ArrayList<>();
        for (Object obj : collection) {
            properties.addAll(initializeProperty(obj, builder.duplicate()));
        }
        return properties;
    }




}
