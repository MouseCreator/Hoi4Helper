package mouse.hoi.gamefiles.unparser;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.annotation.SkipDeclaration;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.init.InitializerHelperChain;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;


@Service
public class OutputPropertyInitializerImpl implements OutputPropertyInitializer {
    private final List<InitializerCaller> initializerCallers;
    private final InitializerHelperChain chain;

    public OutputPropertyInitializerImpl(List<InitializerCaller> initializerCallers, InitializerHelperChain chain) {
        this.initializerCallers = initializerCallers;
        this.chain = chain;
    }

    @PostConstruct
    public void init() {
        for (InitializerCaller caller : initializerCallers) {
            caller.setInitializer(this);
        }
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
        return chain.initialize(builder, model);
    }



}
