package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;

import java.util.List;

public interface GameFileUnparser {
    <T> List<OutputProperty> unparseFrom(List<T> objects);
}
