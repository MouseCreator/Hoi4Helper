package mouse.hoi.parser.collectiontype;

import java.util.List;

public interface CollectionTypeManager {
    List<CollectionType> findTypeForToken(String token);
}
