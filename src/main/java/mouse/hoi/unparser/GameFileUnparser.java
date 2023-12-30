package mouse.hoi.unparser;

import mouse.hoi.parser.property.input.Property;

import java.util.List;

public interface GameFileUnparser {
    <T> List<Property> unparseFrom(List<T> objects);
}
