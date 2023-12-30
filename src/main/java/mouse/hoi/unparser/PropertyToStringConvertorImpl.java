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
        return resultBuilder.toString();
    }

    private static class ResultBuilder {
        private final StringBuilder stringBuilder;
        private int tabulationLevel;
        private boolean isAfterNewline = false;

        public ResultBuilder() {
            stringBuilder = new StringBuilder();
            tabulationLevel = 0;
        }

        public ResultBuilder append(Object o) {
            if (isAfterNewline) {
                stringBuilder.append("\t".repeat(tabulationLevel));
            }
            stringBuilder.append(o);
            return this;
        }

        public String get() {
            return stringBuilder.toString();
        }
        public ResultBuilder newline() {
            stringBuilder.append("\n");
            isAfterNewline = true;
            return this;
        }
        public ResultBuilder space() {
            stringBuilder.append(" ");
            return this;
        }
        public ResultBuilder openBlock() {
            stringBuilder.append(" = {");
            tabulationLevel++;
            return this;
        }
        public ResultBuilder closeBlock() {
            stringBuilder.append("}");
            tabulationLevel--;
            return newline();
        }

        public ResultBuilder equality() {
            stringBuilder.append(" = ");
            return this;
        }
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
