package mouse.hoi.unparser;

import mouse.hoi.parser.property.ouput.OutputProperty;

import java.util.List;

public interface PropertyToStringConvertor {
    String covert(List<OutputProperty> propertyList);
}
