package mouse.hoi.gamefiles.unparser.unparsing;

import mouse.hoi.gamefiles.common.style.PrintStyle;
import mouse.hoi.gamefiles.unparser.StringResultBuilder;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyToStringUnparserImpl implements PropertyToStringUnparser {
    @Override
    public String unparse(List<OutputProperty> propertyList) {
        StringResultBuilder resultBuilder = new StringResultBuilder();
        for (OutputProperty property : propertyList) {
            unparseProperty(property, resultBuilder);
            resultBuilder.newline();
        }
        return resultBuilder.get();
    }

    private void unparseProperty(OutputProperty property, StringResultBuilder resultBuilder) {
        switch (property.getType()) {
            case SIMPLE -> unparseSimple(property, resultBuilder);
            case FIELD_VALUE -> unparseFieldValue(property, resultBuilder);
            case BLOCK -> unparseBlock(property, resultBuilder);
        }
    }

    private void unparseBlock(OutputProperty blockProperty, StringResultBuilder resultBuilder) {
        resultBuilder.append(blockProperty.getKey())
                .equality()
                .append(blockProperty.getValue()).spaceSafe().openBlock();
        PrintStyle style = blockProperty.getStyle();
        addSpaceSeparator(resultBuilder, style);
        int size = blockProperty.getChildren().size();
        for (int i = 0; i < size; i++) {
            OutputProperty child = blockProperty.getChildren().get(i);
            unparseProperty(child, resultBuilder);
            if (i == size - 1) {
                break;
            }
            if (style == PrintStyle.COMPLEX) {
                resultBuilder.newline();
            } else {
                resultBuilder.space();
            }
        }
        if (size > 0)
            addSpaceSeparator(resultBuilder, style);
        resultBuilder.closeBlock();
    }

    private void addSpaceSeparator(StringResultBuilder resultBuilder, PrintStyle style) {
        if (style == PrintStyle.SIMPLE) {
            resultBuilder.space();
        } else {
            resultBuilder.newline();
        }
    }

    private void unparseFieldValue(OutputProperty fvProperty, StringResultBuilder resultBuilder) {
        resultBuilder.append(fvProperty.getKey())
                .equality()
                .append(fvProperty.getValue());
    }

    private void unparseSimple(OutputProperty simpleProperty, StringResultBuilder resultBuilder) {
        resultBuilder.append(simpleProperty.getValue());
    }
}
