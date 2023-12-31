package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.parser.property.Property;

import java.util.List;

public interface GameFileUnparser {
    <T> List<Property> unparseFrom(List<T> objects);
}
