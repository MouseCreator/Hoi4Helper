package mouse.hoi.gamefiles.parser;


import mouse.hoi.gamefiles.parser.property.Property;

import java.util.List;

public interface GameFileParser {
    <T> List<T> parseFrom(Class<T> tClass, List<Property> properties);

}
