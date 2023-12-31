package mouse.hoi.gamefiles.unparser.unparsing;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;

import java.util.List;

public interface PropertyToStringUnparser {
    String unparse(List<OutputProperty> propertyList);
}
