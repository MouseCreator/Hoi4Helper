package mouse.hoi.parser.property.ouput;

import mouse.hoi.parser.property.BaseProperty;
import mouse.hoi.parser.property.ParametrizedProperty;
import mouse.hoi.parser.style.PrintStyle;

public interface OutputProperty extends BaseProperty, ParametrizedProperty<OutputProperty> {
    PrintStyle getStyle();
}
