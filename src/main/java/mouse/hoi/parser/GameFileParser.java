package mouse.hoi.parser;


import mouse.hoi.parser.property.Property;

import java.util.List;

public interface GameFileParser {
    <T> List<T> parseFrom(Class<T> tClass, List<Property> properties);

}
