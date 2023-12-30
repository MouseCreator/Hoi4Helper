package mouse.hoi.parser.property.input;


import lombok.ToString;
import mouse.hoi.parser.property.ParametrizedBlockProperty;

import java.util.List;
@ToString
public class BlockProperty extends ParametrizedBlockProperty<Property> implements Property {

    public BlockProperty(String key, String value) {
        super(key, value);
    }

    public BlockProperty(String key, String value, List<Property> properties) {
        super(key, value, properties);
    }

    public BlockProperty() {
        super();
    }

    public BlockProperty(String key) {
        super(key, "");
    }
}
