package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UnexpectedTokensHandler implements ParsingAnnotationHandler{
    @Override
    public void handle(Object model, List<Property> propertyList, List<Property> unusedProperties) {
        if (unusedProperties.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder("Parsing exception while parsing "
                + model.getClass().getSimpleName()
                + ". Unexpected tokens:");
        for (Property property : unusedProperties) {
            builder.append(" ");
            if (property.type()== PropertyType.SIMPLE) {
                builder.append(property.getValue());
            } else {
                builder.append(property.getKey());
            }
        }
        builder.append(".");
        throw new PropertyParseException(builder.toString());
    }

    @Override
    public void setNext(ParsingAnnotationHandler parsingAnnotationHandler) {

    }
}
