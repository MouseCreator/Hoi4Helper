package mouse.hoi.parser.property.input;


import lombok.ToString;
import mouse.hoi.parser.property.ParametrizedSimpleProperty;


@ToString
public class SimpleProperty extends ParametrizedSimpleProperty<Property> implements Property {

    public SimpleProperty(String value) {
        this.value = value;
    }

    public SimpleProperty() {
        this.value = "";
    }

}
