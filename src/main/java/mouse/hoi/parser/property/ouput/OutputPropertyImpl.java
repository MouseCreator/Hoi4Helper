package mouse.hoi.parser.property.ouput;

import mouse.hoi.parser.property.PropertyType;
import mouse.hoi.parser.style.PrintStyle;

import java.util.List;

public class OutputPropertyImpl<T extends OutputProperty> implements OutputProperty{
    protected T base;
    protected OutputParams params;
    public OutputPropertyImpl(T base, OutputParams params) {
        this.base = base;
        this.params = params;
    }
    @Override
    public boolean isBlock() {
        return base.isBlock();
    }

    @Override
    public String getKey() {
        return base.getKey();
    }

    @Override
    public String getValue() {
        return base.getValue();
    }

    @Override
    public boolean isEmpty() {
        return base.isEmpty();
    }

    @Override
    public String print() {
        return base.print();
    }

    @Override
    public PropertyType type() {
        return base.type();
    }

    @Override
    public List<OutputProperty> getChildren() {
        return base.getChildren();
    }

    @Override
    public PrintStyle getStyle() {
        return params.getPrintStyle();
    }
}
