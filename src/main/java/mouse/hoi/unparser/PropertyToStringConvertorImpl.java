package mouse.hoi.unparser;

import mouse.hoi.parser.style.PrintStyle;
import mouse.hoi.parser.property.ouput.OutputProperty;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyToStringConvertorImpl implements PropertyToStringConvertor {
    @Override
    public String covert(List<OutputProperty> propertyList) {
        ResultBuilder resultBuilder = new ResultBuilder();
        convert(resultBuilder, propertyList);
        return resultBuilder.get();
    }


    private void convert(ResultBuilder resultBuilder, List<OutputProperty> propertyList) {
        for (OutputProperty property : propertyList) {
            convertProperty(resultBuilder, property);
        }
    }

    private void convertProperty(ResultBuilder resultBuilder, OutputProperty property) {
        switch (property.type()) {
            case SIMPLE -> convertSimple(resultBuilder, property);
            case FIELD_VALUE -> convertFieldValue(resultBuilder, property);
            case BLOCK -> convertBlock(resultBuilder, property);
        }
    }

    private void convertBlock(ResultBuilder resultBuilder, OutputProperty property) {
        resultBuilder.append(property.getKey())
                .equality()
                .append(property.getValue())
                .openBlock();
        if (property.getStyle() == PrintStyle.SIMPLE) {
            resultBuilder.space();
        } else {
            resultBuilder.newline();
        }
        for (OutputProperty child : property.getChildren()) {
            convertProperty(resultBuilder, child);
            if (property.getStyle() == PrintStyle.COMPLEX) {
                resultBuilder.newline();
            } else {
                resultBuilder.space();
            }
        }
        resultBuilder.closeBlock();
    }

    private void convertFieldValue(ResultBuilder resultBuilder, OutputProperty property) {
        resultBuilder.append(property.getKey()).equality().append(property.getValue());
    }

    private void convertSimple(ResultBuilder resultBuilder, OutputProperty property) {
        resultBuilder.append(property.getValue());
        if (property.getStyle() == PrintStyle.SIMPLE) {
            resultBuilder.space();
        } else {
            resultBuilder.newline();
        }
    }
}
