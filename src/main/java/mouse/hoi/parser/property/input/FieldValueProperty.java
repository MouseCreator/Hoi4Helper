package mouse.hoi.parser.property.input;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mouse.hoi.parser.property.ParametrizedFieldValueProperty;
import mouse.hoi.parser.property.PropertyType;

import java.util.List;


@ToString
public class FieldValueProperty extends ParametrizedFieldValueProperty<Property> implements Property {
    public FieldValueProperty() {
    }

    public FieldValueProperty(String key, String value) {
        super(key, value);
    }
}
